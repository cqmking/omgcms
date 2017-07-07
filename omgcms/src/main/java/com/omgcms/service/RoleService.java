package com.omgcms.service;

import org.springframework.data.domain.Page;

import com.omgcms.model.core.Role;

public interface RoleService {

	Role saveAndFlush(Role userRole);
	
	Role getRole(long roleId);
	
	Page<Role> findRoles(int pageNo, int pageSize, String orderByProperty, String sortType);
	
}
