package com.sos.service.impl;

import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sos.entity.Role;
import com.sos.repository.RoleRepository;
import com.sos.service.RoleService;

@Service
public class RoleServiceImpl  implements RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	private Set<Role> userRoles;
	
	private Set<Role> adminRoles;

	@PostConstruct
	private void init() {
		userRoles = roleRepository.findByName("ROLE_USER");
		adminRoles = roleRepository.findByName("ROLE_ADMIN");
		adminRoles.addAll(userRoles);
	}

	@Override
	public Set<Role> getUserRoles() {
		return userRoles;
	}

	@Override
	public Set<Role> getAdminRoles() {
		return adminRoles;
	}

}
