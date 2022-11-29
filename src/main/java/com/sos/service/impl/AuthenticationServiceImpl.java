package com.sos.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sos.common.ApplicationConstant.AccountStatus;
import com.sos.dto.CreateAccountRequestDTO;
import com.sos.dto.EmailRequest;
import com.sos.dto.JwtResponse;
import com.sos.dto.LoginRequest;
import com.sos.dto.RegisterRequest;
import com.sos.entity.Account;
import com.sos.entity.RefreshToken;
import com.sos.entity.Role;
import com.sos.exception.ResourceNotFoundException;
import com.sos.repository.AccountRepository;
import com.sos.repository.RefreshTokenRepository;
import com.sos.repository.RoleRepository;
import com.sos.security.CustomUserDetail;
import com.sos.security.jwt.JwtUtils;
import com.sos.service.AuthenticationService;
import com.sos.service.EmailService;
import com.sos.service.RoleService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private RoleService roleService;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtils jwtUtils;

	@Value("${security.jwt.refresh-expiration}")
	private long jwtRefreshExpirationMs;

	@Override
	public JwtResponse signin(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();
		String token = jwtUtils.generateToken(String.valueOf(user.getId()), authentication.getAuthorities());

		Date date = new Date();
		Date refreshTokenEmpiredDate = new Date(date.getTime() + jwtRefreshExpirationMs);
		String refreshToken = jwtUtils.generateRefreshToken(String.valueOf(user.getId()), refreshTokenEmpiredDate);
		RefreshToken refreshTokenEntity = new RefreshToken(new Account(user.getId()), refreshToken, date,
				refreshTokenEmpiredDate);
		refreshTokenRepository.save(refreshTokenEntity);
		return new JwtResponse(user.getId(), token, "Bearer", refreshToken);
	}

	@Override
	public JwtResponse signup(RegisterRequest register) {
		Date date = new Date();
		Account account = new Account();
		account.setUsername(register.getUsername());
		account.setFullname(register.getFullname());
		account.setEmail(register.getEmail());
		account.setPassword(passwordEncoder.encode(register.getPassword()));
		account.setRoles(roleService.getUserRoles());
		account.setAccountStatus(AccountStatus.ACTIVE);
		account.setCreateDate(date);
		account.setUpdateDate(date);
		accountRepository.save(account);

		Authentication authentication = new UsernamePasswordAuthenticationToken(account.getId(), null, account
				.getRoles().stream().map(Role::getName).map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		String token = jwtUtils.generateToken(String.valueOf(account.getId()), authentication.getAuthorities());

		Date refreshTokenEmpiredDate = new Date(date.getTime() + jwtRefreshExpirationMs);
		String refreshToken = jwtUtils.generateRefreshToken(String.valueOf(account.getId()), refreshTokenEmpiredDate);
		RefreshToken refreshTokenEntity = new RefreshToken(account, refreshToken, date, refreshTokenEmpiredDate);
		refreshTokenRepository.save(refreshTokenEntity);

		return new JwtResponse(account.getId(), token, "Bearer", refreshToken);
	}

	@Override
	public JwtResponse refreshToken(String token) throws AuthenticationException {
		jwtUtils.validateJwtRefreshToken(token);
		Date date = new Date();
		int accountId = refreshTokenRepository.findAccountIdByTokenValue(token, date)
				.orElseThrow(() -> new NonceExpiredException("Token not found"));

		Collection<? extends GrantedAuthority> authorities = roleRepository.findByAccountId(accountId).stream()
				.map(Role::getName).map(SimpleGrantedAuthority::new).collect(Collectors.toList());

		String accessToken = jwtUtils.generateToken(String.valueOf(accountId), authorities);
		return new JwtResponse(accountId, accessToken, "Bearer", token);
	}

	@Transactional
	@Override
	public void updateAccountPassword(int id, String password, String newPassword) {
		String encryptedPassword = accountRepository.findAccountPassword(id).orElseThrow(
				() -> new ResourceNotFoundException("Không tìm thấy tài khoản mật khẩu với account id : " + id));

		if (!passwordEncoder.matches(password, encryptedPassword)) {
			throw new ValidationException("Mật khẩu hiện tại không chính xác.");
		}

		accountRepository.updatePassword(id, passwordEncoder.encode(newPassword));
	}

	@Transactional
	@Override
	public void resetAccountPassword(String username, String email)
			throws UnsupportedEncodingException, MessagingException {
		if (!StringUtils.hasText(username) || !StringUtils.hasText(email)) {
			throw new ValidationException("Tên tài khoản hoặc email không chính xác.");
		}

		Account account = accountRepository.findAccountIdByUsernameEmail(username, email)
				.orElseThrow(() -> new ValidationException("Tên tài khoản hoặc email không chính xác."));
		String password = RandomStringUtils.randomAlphabetic(10);

		if (accountRepository.updatePassword(account.getId(), passwordEncoder.encode(password)) != 1) {
			throw new ValidationException("Tên tài khoản hoặc email không chính xác.");
		}

		emailService.sendEmail(new EmailRequest(new String[] { email }, null, null,
				"[Sneakers On Shelf] Mật khẩu của bạn đã được đổi lại.", String.format("Mật khẩu mới : %s", password),
				false));
	}

	@Transactional
	@Override
	public String resetAccountPassword(int id) {
		String password = RandomStringUtils.randomAlphabetic(10);

		if (accountRepository.updatePassword(id, passwordEncoder.encode(password)) != 1) {
			throw new ValidationException("Tên tài khoản hoặc email không chính xác.");
		}

		accountRepository.getAccountEmail(id).ifPresent(email -> {
			try {
				emailService.sendEmail(new EmailRequest(new String[] { email }, null, null,
						"[Sneakers On Shelf] Mật khẩu của bạn đã được đổi lại.",
						String.format("Mật khẩu mới : %s", password), false));
			} catch (UnsupportedEncodingException | MessagingException e) {
				e.printStackTrace();
			}
		});

		return password;
	}

	@Override
	public Account signup(CreateAccountRequestDTO register) throws UnsupportedEncodingException, MessagingException {
		Date date = new Date();
		Account account = new Account();
		account.setUsername(register.getUsername());
		account.setFullname(register.getFullname());
		account.setEmail(register.getEmail());
		String password = RandomStringUtils.randomAlphabetic(10);
		account.setPassword(passwordEncoder.encode(password));
		account.setRoles(register.isAdmin() ? roleService.getAdminRoles() : roleService.getUserRoles());
		account.setAccountStatus(AccountStatus.ACTIVE);
		account.setCreateDate(date);
		account.setUpdateDate(date);
		accountRepository.save(account);

		emailService.sendEmail(new EmailRequest(new String[] { account.getEmail() }, null, null,
				"[Sneakers On Shelf] Chào mừng đến với sneakers on shelf, đây là thông tin tài khoản của bạn.",
				String.format("Tên tài khoản : %s\n" + "Mật khẩu      : %s", register.getUsername(), password), false));
		return account;
	}

}
