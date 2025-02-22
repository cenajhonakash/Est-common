package com.ace.estore.common.service;

import java.util.Set;

public interface AuthenticationService {

	boolean hasUserRole(String role);

	Set<String> getUserRoles();

	boolean isUserAdmin();

	boolean isUserStoreManager();

}
