package com.omgcms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.omgcms.model.core.Role;

public interface RoleRepository extends JpaSpecificationExecutor<Role>, JpaRepository<Role, Integer>{
	
	
}
