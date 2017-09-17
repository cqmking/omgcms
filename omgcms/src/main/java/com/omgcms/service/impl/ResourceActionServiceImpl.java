package com.omgcms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omgcms.model.core.ResourceAction;
import com.omgcms.repository.ResourceActionRepository;
import com.omgcms.service.ResourceActionService;

@Transactional
@Service
public class ResourceActionServiceImpl implements ResourceActionService {

	@Autowired
	private ResourceActionRepository resourceActionRepository;
	
	@Override
	public List<ResourceAction> findAll() {
		return resourceActionRepository.findAll();
	}
	
	@Override
	public List<ResourceAction> findAll(Iterable<Long> ids) {
		return resourceActionRepository.findAll(ids);
	}
	
	@Override
	public ResourceAction saveAndFlush(ResourceAction resourceAction) {

		return resourceActionRepository.saveAndFlush(resourceAction);
	}

	@Override
	public ResourceAction save(ResourceAction resourceAction) {

		return resourceActionRepository.save(resourceAction);
	}

	@Override
	public void flush() {
		resourceActionRepository.flush();
	}

	@Override
	public void deleteResourceAction(ResourceAction resourceAction) {
		resourceActionRepository.delete(resourceAction);
	}

	@Override
	public void deleteResourceAction(long resourceActionId) {
		resourceActionRepository.delete(resourceActionId);
	}

	@Override
	public void deleteResourceActions(List<ResourceAction> resourceActions) {
		resourceActionRepository.deleteInBatch(resourceActions);
	}

	@Override
	public ResourceAction getByResourceNameAndActionId(String resourceName, long actionId) {
		return resourceActionRepository.getByResourceNameAndActionId(resourceName, actionId);
	}

	@Override
	public List<ResourceAction> getByResourceName(String resourceName) {
		return resourceActionRepository.getByResourceName(resourceName);
	}
	
}
