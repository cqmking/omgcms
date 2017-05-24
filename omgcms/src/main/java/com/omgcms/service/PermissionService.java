package com.omgcms.service;

import java.util.List;

import com.omgcms.model.core.Permission;
import com.omgcms.model.core.Role;

public interface PermissionService {
	
	public List<Permission> findPermissionsByRoleIds(List<Long> roleIds);
	
	public List<Permission> findPermissionsByRoles(List<Role> roles);
	
}
