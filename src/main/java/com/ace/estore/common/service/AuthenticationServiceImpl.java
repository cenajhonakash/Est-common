package com.ace.estore.common.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.ace.estore.common.constants.UserEnum;
import com.nimbusds.jose.shaded.gson.internal.LinkedTreeMap;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
class AuthenticationServiceImpl implements AuthenticationService {

	@Override
	public boolean hasUserRole(String roleName) {
		return getUserRoles().stream().anyMatch(role -> role.equalsIgnoreCase(roleName));
	}

	@Override
	public Set<String> getUserRoles() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		log.info("Authentication profile for current user is: {}, {}", authentication.getAuthorities(),
				authentication.getDetails());// principal -> claims[] == realm_access
		Jwt token = (Jwt) authentication.getPrincipal();
		LinkedTreeMap xyz = (LinkedTreeMap) token.getClaims().get("realm_access");
		List roles = (List) xyz.get("roles");
		return (Set<String>) roles.stream().collect(Collectors.toSet());

//		return authentication.getAuthorities().stream().map(r -> r.getAuthority()).collect(Collectors.toSet());
	}

	@Override
	public boolean isUserAdmin() {
		return getUserRoles().stream().anyMatch(role -> role.equalsIgnoreCase(UserEnum.STORE_ADMIN.name())
				|| role.equalsIgnoreCase(UserEnum.STORE_ASSOCIATE.name()));
	}

	@Override
	public boolean isUserStoreManager() {
		return getUserRoles().stream().anyMatch(role -> role.equalsIgnoreCase(UserEnum.STORE_MANAGER.name()));
	}

}
