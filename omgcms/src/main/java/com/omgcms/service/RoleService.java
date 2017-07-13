package com.omgcms.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.omgcms.model.core.Role;

public interface RoleService {

	Role saveAndFlush(Role userRole);
	
	Role getByRoleId(long roleId);
	
	Role getByName(String name);
	
	Role getByRoleKey(String roleKey);
	
	void deleteRole(long roleId);
	
	void deleteRoles(long []roleIds);
	
	List<Role> getRolesByIds(Long []roleIds);
	
	Page<Role> getRoles(int pageNo, int pageSize, String orderByProperty, String sortType);
	
	Page<Role> getRolesByUserId(int pageNo, int pageSize, String orderByProperty, String sortType, long userId);
	
	/**
	 * Get unassigned roles for user, these roles can be assigned to the refer user
	 * 
	 * @param pageNo Page number
	 * @param pageSize Page size
	 * @param orderByProperty Order by propperty
	 * @param sortType sort type, ASC or DESC, CmsConstants.ORDER_ASC or CmsConstants.ORDER_DESC
	 * @param userId User's userId
	 * @return roles list
	 */
	Page<Role> getUnassignedUserRoles(int pageNo, int pageSize, String orderByProperty, String sortType, long userId);
	
	
}
