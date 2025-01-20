package com.ace.estore.common.config.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

	@Value("#{'${endpoints.auth.open}'.split(',')}")
	private List<String> unauthEndpoints;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		log.info("Endpoints : {} are not authenticated", unauthEndpoints);
		String[] openEndpoints = unauthEndpoints.toArray(new String[unauthEndpoints.size()]);
		return http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(
						req -> req.requestMatchers(openEndpoints).permitAll().anyRequest().authenticated())
				.oauth2ResourceServer(
						oauth -> oauth.jwt(token -> token.jwtAuthenticationConverter(new JwtAuthenticationConverter())))
				.build();
	}

}
