package com.omgcms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omgcms.model.core.Role;
import com.omgcms.repository.RoleRepository;
import com.omgcms.service.RoleService;

@Transactional
@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public Role saveAndFlush(Role userRole) {
		return roleRepository.saveAndFlush(userRole);
	}
}
