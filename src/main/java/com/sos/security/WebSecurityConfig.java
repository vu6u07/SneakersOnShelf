package com.sos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sos.security.jwt.JwtAuthenticationFilter;
import com.sos.security.jwt.JwtUtils;
import com.sos.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.sos.security.oauth2.OAuth2AuthenticationSuccessHandler;
import com.sos.service.impl.CustomOAuth2UserService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private CustomOAuth2UserService oauth2UserService;

	@Autowired
	private OAuth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

	@Autowired
	private AuthenticationEntryPointImpl authenticationEntryPointImpl;

//	@Bean
//	public WebSecurityCustomizer webSecurityCustomizer() {
//		return (web) -> web.ignoring().antMatchers("/**");
//	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors();
		http.csrf().disable();

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(new JwtAuthenticationFilter(jwtUtils), UsernamePasswordAuthenticationFilter.class);

		http.oauth2Login()
			.authorizationEndpoint().baseUri("/oauth2/authorize")
				.authorizationRequestRepository(getAuthorizationRequestRepository()).and().userInfoEndpoint()
				.userService(oauth2UserService).and().successHandler(oauth2AuthenticationSuccessHandler);

		http.exceptionHandling().authenticationEntryPoint(authenticationEntryPointImpl)
				.accessDeniedHandler(accessDeniedHandler);

		return http.build();
	}

	@Bean
	public AuthorizationRequestRepository<OAuth2AuthorizationRequest> getAuthorizationRequestRepository() {
		return new HttpCookieOAuth2AuthorizationRequestRepository();
	}

	@Bean
	public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> getOAuth2AccessTokenResponseClient() {
		return new DefaultAuthorizationCodeTokenResponseClient();
	}

}
