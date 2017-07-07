package com.omgcms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omgcms.repository.ResourcePermissionRepository;
import com.omgcms.service.ResourcePermissionService;

@Transactional
@Service
public class ResourcePermissionServiceImpl implements ResourcePermissionService {

	@Autowired
	private ResourcePermissionRepository PpermissionRepository;
	
}
