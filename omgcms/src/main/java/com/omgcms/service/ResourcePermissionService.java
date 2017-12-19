package com.omgcms.service;

import java.util.List;

import com.omgcms.model.core.ResourcePermission;
import com.omgcms.model.core.Role;

public interface ResourcePermissionService {

	ResourcePermission save(ResourcePermission resourcePermission);

	List<ResourcePermission> findAll(Iterable<Long> ids);

	List<ResourcePermission> saveOrUpdate(List<ResourcePermission> list);

	ResourcePermission getByResourceNameAndPrimaryKeyAndRoleAndOwnerId(String resourceName, long primaryKey, Role role, long ownerId);
}
