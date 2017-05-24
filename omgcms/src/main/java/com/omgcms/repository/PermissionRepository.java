package com.omgcms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.omgcms.model.core.Permission;

public interface PermissionRepository extends JpaSpecificationExecutor<Permission>, JpaRepository<Permission, Integer>{
	
	@Query("SELECT p FROM Permission p JOIN p.roles r WHERE r.roleId IN :roleIds")
	public List<Permission> findPermissionsByRoleIds(@Param("roleIds") List<Long> roleIds);
	
}
