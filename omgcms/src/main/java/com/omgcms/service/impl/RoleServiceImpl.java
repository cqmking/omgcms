package com.omgcms.service.impl;

import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omgcms.model.core.Role;
import com.omgcms.model.core.UserRole;
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
	public void deleteRole(long roleId) {
		roleRepository.delete(roleId);
	}

	@Override
	public void deleteRoles(long[] roleIds) {
		for (long roleId : roleIds) {
			roleRepository.delete(roleId);
		}
	}
	
	@Override
	public List<Role> getRolesByIds(Long[] roleIds) {
		
		List<Long> roleIdList = Arrays.asList(roleIds);
		return roleRepository.findAll(roleIdList);
	}

	@Override
	public Role getByName(String name) {
		return roleRepository.getByName(name);
	}

	@Override
	public Role getByRoleKey(String roleKey) {
		return roleRepository.getByRoleKey(roleKey);
	}

	@Override
	public Page<Role> getRoles(int pageNo, int pageSize, String orderByProperty, String sortType) {
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
	public Page<Role> getRolesByUserId(int pageNo, int pageSize, String orderByProperty, String sortType, long userId) {

		Specification<Role> specification = new Specification<Role>() {

			@Override
			public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Join<Role, UserRole> userRoles = root.join("userRoles", JoinType.LEFT);

				Path<String> path = userRoles.get("id").get("userId");
				return cb.equal(path, userId);
			}
		};

		Direction direction = Direction.ASC;

		if ("DESC".equals(sortType)) {
			direction = Direction.DESC;
		}

		Order idOrder = new Order(direction, orderByProperty);
		Sort sort = new Sort(idOrder);

		PageRequest pageable = new PageRequest(pageNo - 1, pageSize, sort);

		Page<Role> page = roleRepository.findAll(specification, pageable);

		return page;
	}
	
	@Override
	public Page<Role> getUnassignedUserRoles(int pageNo, int pageSize, String orderByProperty, String sortType, long userId) {

		
		Specification<Role> specification = new Specification<Role>() {

			@Override
			public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				Subquery<Long> subQuery = query.subquery(Long.class);
				Root<UserRole> fromUR = subQuery.from(UserRole.class);
				
				subQuery.select(fromUR.get("id").get("roleId")).where(cb.equal(fromUR.get("id").get("userId"), userId));
				
				return cb.not(cb.in(root.get("roleId")).value(subQuery));
				
			}
		};
		
		Direction direction = Direction.ASC;

		if ("DESC".equals(sortType)) {
			direction = Direction.DESC;
		}

		Order idOrder = new Order(direction, orderByProperty);
		Sort sort = new Sort(idOrder);

		PageRequest pageable = new PageRequest(pageNo - 1, pageSize, sort);

		Page<Role> page = roleRepository.findAll(specification, pageable);

		return page;
		
	}

}
