package com.sos.service;

import java.util.Set;

import com.sos.entity.Role;

public interface RoleService {

	Set<Role> getUserRoles();
	
	Set<Role> getAdminRoles();
	
	Role getAdminRole();
	
}
