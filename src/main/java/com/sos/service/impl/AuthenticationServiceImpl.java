package com.sos.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import javax.validation.ValidationException;

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

import com.sos.common.ApplicationConstant.AccountStatus;
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

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtils jwtUtils;

	@Value("${security.jwt.refresh-expiration}")
	private long jwtRefreshExpirationMs;

	private Set<Role> userRoles;

	@PostConstruct
	private void init() {
		userRoles = roleRepository.findByName("ROLE_USER");
	}

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
		account.setRoles(userRoles);
		account.setAccountStatus(AccountStatus.ACTIVE);
		account.setCreateDate(date);
		account.setUpdateDate(date);
		accountRepository.save(account);

		Authentication authentication = new UsernamePasswordAuthenticationToken(account.getId(), null,
				userRoles.stream().map(Role::getName).map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
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

}
