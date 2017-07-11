package com.omgcms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.omgcms.model.core.UserRole;
import com.omgcms.model.core.pk.UserRolePK;

public interface UserRoleRepository extends JpaSpecificationExecutor<UserRole>, JpaRepository<UserRole, UserRolePK> {
	
}
