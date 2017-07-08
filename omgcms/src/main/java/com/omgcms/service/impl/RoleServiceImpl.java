package com.omgcms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omgcms.model.core.Role;
import com.omgcms.repository.RoleRepository;
import com.omgcms.service.RoleService;

@Transactional
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Role saveAndFlush(Role userRole) {
		return roleRepository.saveAndFlush(userRole);
	}

	@Override
	public Role getByRoleId(long roleId) {
		return roleRepository.getByRoleId(roleId);
	}
	
	@Override
	public Page<Role> findRoles(int pageNo, int pageSize, String orderByProperty, String sortType) {
		Direction direction = Direction.ASC;

		if ("DESC".equals(sortType)) {
			direction = Direction.DESC;
		}

		Order idOrder = new Order(direction, orderByProperty);
		Sort sort = new Sort(idOrder);

		PageRequest pageable = new PageRequest(pageNo - 1, pageSize, sort);

		Page<Role> page = roleRepository.findAll(pageable);

		return page;
	}

	@Override
	public void deleteRole(long roleId) {
		roleRepository.delete(roleId);
	}

	@Override
	public void deleteRoles(long[] roleIds) {
		for(long roleId:roleIds){
			roleRepository.delete(roleId);
		}
	}

	@Override
	public Role getByName(String name) {
		return roleRepository.getByName(name);
	}
	
	@Override
	public Role getByRoleKey(String roleKey) {
		return roleRepository.getByRoleKey(roleKey);
	}

}
