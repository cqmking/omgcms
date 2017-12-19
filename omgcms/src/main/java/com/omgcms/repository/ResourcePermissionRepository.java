package com.omgcms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.omgcms.model.core.ResourcePermission;
import com.omgcms.model.core.Role;

public interface ResourcePermissionRepository
		extends JpaSpecificationExecutor<ResourcePermission>, JpaRepository<ResourcePermission, Long> {

	ResourcePermission getByResourceNameAndPrimaryKeyAndRoleAndOwnerId(String resourceName, long primaryKey, Role role, long ownerId);
	
}
