package com.omgcms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omgcms.model.core.Role;
import com.omgcms.model.core.User;
import com.omgcms.model.core.UserRole;
import com.omgcms.model.core.pk.UserRolePK;
import com.omgcms.repository.UserRoleRepository;
import com.omgcms.service.UserRoleService;

@Transactional
@Service
public class UserRoleServiceImpl implements UserRoleService {

	// private Logger logger = LoggerFactory.getLogger(UserRoleServiceImpl.class);
	
	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public UserRole saveAndFlush(UserRole userRole) {
		return userRoleRepository.saveAndFlush(userRole);
	}

	@Override
	public void deleteUserRole(long userId, long roleId) {
		UserRolePK pk = new UserRolePK(userId, roleId);
		userRoleRepository.delete(pk);
	}

	@Override
	public void deleteUserRoles(long userId, long[] roleIds) {
		for (long roleId : roleIds) {
			UserRolePK pk = new UserRolePK(userId, roleId);
			userRoleRepository.delete(pk);
		}
	}

	@Override
	public void deleteUserRoles(long[] userIds, long roleId) {
		for (long userId : userIds) {
			UserRolePK pk = new UserRolePK(userId, roleId);
			userRoleRepository.delete(pk);
		}
	}
	
	@Override
	public UserRole getUserRole(long userId, long roleId) {
		UserRolePK pk = new UserRolePK(userId, roleId);
		return userRoleRepository.getOne(pk);
	}

	@Override
	public List<UserRole> addUserRoles(User user, List<Role> roles) {

		List<UserRole> userRoles = new ArrayList<UserRole>();
		for (Role role : roles) {
			UserRole userRole = new UserRole(user, role);
			UserRole savedObj = userRoleRepository.save(userRole);
			if (!userRoles.contains(savedObj)) {
				userRoles.add(savedObj);
			}
		}
		
		userRoleRepository.flush();
		
		return userRoles;
	}

	@Override
	public List<UserRole> addUserRoles(Role role, List<User> users) {
		
		List<UserRole> userRoles = new ArrayList<UserRole>();
		for (User user : users) {
			UserRole userRole = new UserRole(user, role);
			UserRole savedObj = userRoleRepository.save(userRole);
			if (!userRoles.contains(savedObj)) {
				userRoles.add(savedObj);
			}
		}
		
		userRoleRepository.flush();
		
		return userRoles;
	}

}
