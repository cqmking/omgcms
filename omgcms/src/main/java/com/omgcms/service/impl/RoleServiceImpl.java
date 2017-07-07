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
	public Role getRole(long roleId) {
		return roleRepository.getOne(roleId);
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

}
