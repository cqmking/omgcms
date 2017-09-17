package com.omgcms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omgcms.model.core.ResourcePermission;
import com.omgcms.model.core.Role;
import com.omgcms.repository.ResourcePermissionRepository;
import com.omgcms.service.ResourcePermissionService;

@Transactional
@Service
public class ResourcePermissionServiceImpl implements ResourcePermissionService {

	@Autowired
	private ResourcePermissionRepository resourcePermissionRepository;

	@Override
	public ResourcePermission save(ResourcePermission resourcePermission) {
		return resourcePermissionRepository.save(resourcePermission);
	}

	@Override
	public List<ResourcePermission> findAll(Iterable<Long> ids) {
		return resourcePermissionRepository.findAll(ids);
	}

	@Override
	public ResourcePermission getByResourceNameAndPrimaryKeyAndRoleAndOwnerId(String resourceName, long primaryKey, Role role,
			long ownerId) {
		return resourcePermissionRepository.getByResourceNameAndPrimaryKeyAndRoleAndOwnerId(resourceName, primaryKey, role, ownerId);
	}

	@Override
	public List<ResourcePermission> saveOrUpdate(List<ResourcePermission> list) {

		List<ResourcePermission> updateList = new ArrayList<ResourcePermission>();

		for (ResourcePermission resPermission : list) {

			ResourcePermission exsitResPerm = getByResourceNameAndPrimaryKeyAndRoleAndOwnerId(resPermission.getResourceName(),
					resPermission.getPrimaryKey(), resPermission.getRole(), resPermission.getOwnerId());
			
			if (resPermission != null && resPermission.getActionIds() == 0) {
				// When resource permission value is zero, then delete the record from db.
				resourcePermissionRepository.delete(exsitResPerm);
				continue;
			}

			if (exsitResPerm != null) {
				exsitResPerm.setActionIds(resPermission.getActionIds());
				ResourcePermission updateResPerm = resourcePermissionRepository.save(exsitResPerm);
				updateList.add(updateResPerm);
			} else {
				ResourcePermission savedResPerm = resourcePermissionRepository.save(resPermission);
				updateList.add(savedResPerm);
			}
		}

		return updateList;
	}
}
