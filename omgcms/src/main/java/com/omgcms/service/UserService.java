package com.omgcms.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.omgcms.model.core.User;

public interface UserService {

	User saveAndFlush(User user);
	
	User getByUserAccount(String userAccount);

	User getByEmail(String email);
	
	void deleteUser(long userId);
	
	void deleteUsers(long []userIds);
	
	User getUser(long userId);
	
	List<User> getUsersByIds(Long []userIds);

	Page<User> findUsers(int pageNo, int pageSize, String orderByProperty, String sortType);
	
	Page<User> getUsersByRoleId(int pageNo, int pageSize, String orderByProperty, String sortType, long roleId);
	
	Page<User> getUnassignedRoleUsers(int pageNo, int pageSize, String orderByProperty, String sortType, long roleId);

}
