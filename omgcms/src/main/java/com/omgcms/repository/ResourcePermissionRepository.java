package com.omgcms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.omgcms.model.core.ResourcePermission;

public interface ResourcePermissionRepository extends JpaSpecificationExecutor<ResourcePermission>, JpaRepository<ResourcePermission, Integer>{
	
}
