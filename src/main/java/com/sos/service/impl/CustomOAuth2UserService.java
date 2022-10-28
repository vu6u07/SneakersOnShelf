package com.sos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.sos.common.ApplicationConstant.OAuthProvider;
import com.sos.service.OAuth2AuthenticationService;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	@Autowired
	private OAuth2AuthenticationService oauth2AuthenticationService;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuthProvider oauthProvider = OAuthProvider.valueOf(userRequest.getClientRegistration().getRegistrationId());
		OAuth2User oAuth2User = super.loadUser(userRequest);
		try {
			return oauth2AuthenticationService.signin(oauthProvider, oAuth2User);
		} catch (AuthenticationException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
		}
	}

}
