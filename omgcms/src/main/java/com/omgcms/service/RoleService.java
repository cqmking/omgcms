package com.omgcms.service;

import org.springframework.data.domain.Page;

import com.omgcms.model.core.Role;

public interface RoleService {

	Role saveAndFlush(Role userRole);
	
	Role getByRoleId(long roleId);
	
	Role getByName(String name);
	
	Role getByRoleKey(String roleKey);
	
	void deleteRole(long roleId);
	
	void deleteRoles(long []roleIds);
	
	Page<Role> findRoles(int pageNo, int pageSize, String orderByProperty, String sortType);
	
}
