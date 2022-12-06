package com.sos.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sos.common.ApplicationConstant.AccountStatus;
import com.sos.common.ApplicationConstant.OAuthProvider;
import com.sos.dto.JwtResponse;
import com.sos.entity.Account;
import com.sos.entity.RefreshToken;
import com.sos.entity.Role;
import com.sos.repository.AccountRepository;
import com.sos.repository.RefreshTokenRepository;
import com.sos.repository.RoleRepository;
import com.sos.security.CustomOAuth2User;
import com.sos.security.jwt.JwtUtils;
import com.sos.service.OAuth2AuthenticationService;
import com.sos.service.RoleService;

@Service
public class OAuth2AuthenticationServiceImpl implements OAuth2AuthenticationService {
	
	@Autowired
	private RoleService roleService;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private ObjectMapper mapper;

	@Value("${security.jwt.refresh-expiration}")
	private long jwtRefreshExpirationMs;

	@Override
	public OAuth2User signin(OAuthProvider oauthProvider, OAuth2User oAuth2User) {
		Account account;
		Collection<? extends GrantedAuthority> authorities;
		switch (oauthProvider) {
		case google:
			account = accountRepository
					.findAccountByGoogleOAuthEmail(oAuth2User.getAttribute("email").toString(), AccountStatus.ACTIVE)
					.orElseGet(() -> registerWithGoogle(oAuth2User));
			authorities = roleRepository.findByAccountId(account.getId()).stream().map(Role::getName)
					.map(SimpleGrantedAuthority::new).collect(Collectors.toList());
			return new CustomOAuth2User(generateJwtResponse(account, authorities), oAuth2User.getName(),
					oAuth2User.getAttributes(), authorities);
		case facebook:
			account = accountRepository
					.findAccountFacebookOAuthId(oAuth2User.getAttribute("id").toString(), AccountStatus.ACTIVE)
					.orElseGet(() -> registerWithFacebook(oAuth2User));
			authorities = roleRepository.findByAccountId(account.getId()).stream().map(Role::getName)
					.map(SimpleGrantedAuthority::new).collect(Collectors.toList());
			return new CustomOAuth2User(generateJwtResponse(account, authorities), oAuth2User.getName(),
					oAuth2User.getAttributes(), authorities);
		default:
			break;
		}
		return null;
	}

	private JwtResponse generateJwtResponse(Account account, Collection<? extends GrantedAuthority> authorities) {
		String token = jwtUtils.generateToken(String.valueOf(account.getId()), authorities);
		Date date = new Date();
		Date refreshTokenEmpiredDate = new Date(date.getTime() + jwtRefreshExpirationMs);
		String refreshToken = jwtUtils.generateRefreshToken(String.valueOf(account.getId()), refreshTokenEmpiredDate);
		RefreshToken refreshTokenEntity = new RefreshToken(account, refreshToken, date, refreshTokenEmpiredDate);
		refreshTokenRepository.save(refreshTokenEntity);
		return new JwtResponse(account.getId(), token, "Bearer", refreshToken);
	}

	private Account registerWithGoogle(OAuth2User oAuth2User) {
		Date date = new Date();

		Account account = new Account();
		account.setGoogleOAuthEmail(oAuth2User.getAttribute("email").toString());
		account.setEmail(oAuth2User.getAttribute("email").toString());
		account.setFullname(oAuth2User.getAttribute("name").toString());
		account.setRoles(roleService.getUserRoles());
		account.setAccountStatus(AccountStatus.ACTIVE);
		account.setPicture(oAuth2User.getAttribute("picture").toString());
		account.setCreateDate(date);
		account.setUpdateDate(date);
		return accountRepository.save(account);
	}

	private Account registerWithFacebook(OAuth2User oAuth2User) {
		Date date = new Date();

		Account account = new Account();
		account.setEmail(oAuth2User.getAttribute("email").toString());
		account.setFacebookOAuthId(oAuth2User.getAttribute("id").toString());
		account.setFullname(oAuth2User.getAttribute("name").toString());
		account.setRoles(roleService.getUserRoles());
		account.setAccountStatus(AccountStatus.ACTIVE);
		JsonNode node = mapper.convertValue(oAuth2User.getAttribute("picture"), JsonNode.class);
		account.setPicture(node.get("data").get("url").asText());
		account.setCreateDate(date);
		account.setUpdateDate(date);
		return accountRepository.save(account);
	}

}
