package com.omgcms.service;

import java.util.List;

import com.omgcms.model.core.Role;
import com.omgcms.model.core.User;
import com.omgcms.model.core.UserRole;

public interface UserRoleService {

	UserRole saveAndFlush(UserRole userRole);

	void deleteUserRole(long userId, long roleId);

	void deleteUserRoles(long userId, long[] roleIds);

	void deleteUserRoles(long[] userIds, long roleId);
	
	UserRole getUserRole(long userId, long roleId);
	
	List<UserRole> addUserRoles(User user, List<Role> roles);
	
	List<UserRole> addUserRoles(Role role, List<User> users);
	
}
