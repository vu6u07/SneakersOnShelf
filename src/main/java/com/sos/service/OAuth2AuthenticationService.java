package com.sos.service;

import org.springframework.security.oauth2.core.user.OAuth2User;

import com.sos.common.ApplicationConstant.OAuthProvider;

public interface OAuth2AuthenticationService {

	public OAuth2User signin(OAuthProvider oauthProvider, OAuth2User oAuth2User);

}
