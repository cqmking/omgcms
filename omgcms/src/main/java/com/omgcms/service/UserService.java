package com.omgcms.service;

import org.springframework.data.domain.Page;

import com.omgcms.model.core.User;

public interface UserService {
	
	User saveAndFlush(User user);
	
	User getByUserAccount(String userAccount);

	Page<User> findUsers(int pageNo, int pageSize, String orderByProperty, String sortType);
	
}
