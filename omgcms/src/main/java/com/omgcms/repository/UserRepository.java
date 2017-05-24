package com.omgcms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.omgcms.model.core.User;

public interface UserRepository extends JpaSpecificationExecutor<User>, JpaRepository<User, Integer> {

	// 根据 userAccount 来获取对应的 User
	public User getByUserAccount(String userAccount);
	
	
}
