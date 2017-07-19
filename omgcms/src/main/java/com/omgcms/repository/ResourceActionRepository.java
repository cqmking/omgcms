package com.omgcms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.omgcms.model.core.ResourceAction;

public interface ResourceActionRepository extends JpaSpecificationExecutor<ResourceAction>, JpaRepository<ResourceAction, Long> {
	
	ResourceAction getByResourceNameAndActionId(String resourceName,long actionId);
	
	List<ResourceAction> getByResourceName(String resourceName);
	
}
