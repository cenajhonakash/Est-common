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

//	@Value("#{'${allowed.cors.hosts}'.split(',')}")
//	private List<String> allowedOrigins;

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

	/*
	 * CORS config
	 */
//	@Bean
//	@Primary
//	public WebMvcConfigurer addCorsConfigurer() {
//		log.info("Allowed origins : {}", allowedOrigins);
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**") // You can restrict the mapping to specific paths if needed
//						.allowedOrigins(unauthEndpoints.toArray(new String[allowedOrigins.size()])) // List allowed
//																									// origins
//						.allowedMethods("*") // Allow only certain HTTP methods
//						.allowedHeaders("*") // Allow all headers
//				;
//			}
//		};
//	}
}
