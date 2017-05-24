package com.omgcms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omgcms.model.core.Permission;
import com.omgcms.model.core.Role;
import com.omgcms.repository.PermissionRepository;
import com.omgcms.service.PermissionService;

@Transactional
@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionRepository PpermissionRepository;

	@Override
	public List<Permission> findPermissionsByRoleIds(List<Long> roleIds) {
		
		return PpermissionRepository.findPermissionsByRoleIds(roleIds);
	}
	
	@Override
	public List<Permission> findPermissionsByRoles(List<Role> roles) {

		List<Long> roleIds = new ArrayList<Long>();

		for (Role role : roles) {
			roleIds.add(role.getRoleId());
		}
		
		return findPermissionsByRoleIds(roleIds);
	}

}
