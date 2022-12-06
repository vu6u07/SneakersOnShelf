package com.sos.service.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sos.entity.Role;
import com.sos.exception.ResourceNotFoundException;
import com.sos.repository.RoleRepository;
import com.sos.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	private Set<Role> userRoles;

	private Set<Role> adminRoles;

	private Role adminRole;
	
	private Role userRole;

	@PostConstruct
	private void init() {
		userRole = roleRepository.findByName("ROLE_USER")
				.orElseThrow(() -> new ResourceNotFoundException("Role not found."));
		adminRole = roleRepository.findByName("ROLE_ADMIN")
				.orElseThrow(() -> new ResourceNotFoundException("Role not found."));
		userRoles = Collections.singleton(userRole);
		adminRoles = new HashSet<Role>();
		adminRoles.add(adminRole);
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

	@Override
	public Role getAdminRole() {
		return adminRole;
	}
	
}
