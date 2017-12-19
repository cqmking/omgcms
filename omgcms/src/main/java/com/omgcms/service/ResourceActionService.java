package com.omgcms.service;

import java.util.List;

import com.omgcms.model.core.ResourceAction;

public interface ResourceActionService {

	List<ResourceAction> findAll();
	
	List<ResourceAction> findAll(Iterable<Long> ids);
	
	ResourceAction saveAndFlush(ResourceAction resourceAction);
	
	ResourceAction save(ResourceAction resourceAction);
	
	void flush();
	
	void deleteResourceAction(ResourceAction resourceAction);
	
	void deleteResourceAction(long resourceActionId);
	
	void deleteResourceActions(List<ResourceAction> resourceActions);
	
	ResourceAction getByResourceNameAndActionId(String resourceName, long actionId);

	List<ResourceAction> getByResourceName(String resourceName);

}
