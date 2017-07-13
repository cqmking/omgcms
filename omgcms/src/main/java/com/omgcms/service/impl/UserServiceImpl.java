package com.omgcms.service.impl;

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

import com.omgcms.exception.CmsExceptionConstants;
import com.omgcms.exception.CmsRuntimeException;
import com.omgcms.model.core.User;
import com.omgcms.model.core.UserRole;
import com.omgcms.repository.UserRepository;
import com.omgcms.service.UserService;
import com.omgcms.util.CmsUtil;

/**
 * @author Freddy
 * @version create date: 2017年7月14日
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User saveAndFlush(User user) {
		
		if(user.getUserId()==null||user.getUserId()<0){
			// 新增
			User exsitAccountUser = getByUserAccount(user.getUserAccount());
			if(exsitAccountUser!=null){
				throw new CmsRuntimeException(CmsExceptionConstants.ERROR_USERACCOUNT_ALREADY_EXIST,
						"User already exsit for userAccount " + user.getUserAccount());
			}
			
			User exsitEmailUser = getByEmail(user.getEmail());
			if(exsitEmailUser!=null){
				throw new CmsRuntimeException(CmsExceptionConstants.ERROR_USEREMAIL_ALREADY_EXIST,
						"User already exsit for user email " + user.getEmail());
			}
		}
		
		return userRepository.saveAndFlush(user);
	}

	@Override
	public User getByUserAccount(String userAccount) {
		return userRepository.getByUserAccount(userAccount);
	}

	@Override
	public User getByEmail(String email) {
		return userRepository.getByEmail(email);
	}
	
	@Override
	public void deleteUser(long userId){
		userRepository.delete(userId);
	}
	
	public void deleteUsers(long []userIds){
		for(long userId:userIds){
			userRepository.delete(userId);
		}
	}
	
	@Override
	public User getUser(long userId){
		
		User user = userRepository.getByUserId(userId);
		
		if (user == null) {
			String arg = CmsUtil.getLocaleMessage("label.user");
			throw new CmsRuntimeException(CmsExceptionConstants.ERROR_OBJECT_NOT_EXIST, new String[] { arg },
					"User not exsit for userId " + userId);
		}
		
		return user;
	}
	

	@Override
	public Page<User> findUsers(int pageNo, int pageSize, String orderByProperty, String sortType) {

		Direction direction = Direction.ASC;

		if ("DESC".equals(sortType)) {
			direction = Direction.DESC;
		}

		Order idOrder = new Order(direction, orderByProperty);
		Sort sort = new Sort(idOrder);

		PageRequest pageable = new PageRequest(pageNo - 1, pageSize, sort);

		Page<User> page = userRepository.findAll(pageable);

		return page;
	}
	

	@Override
	public Page<User> getUsersByRoleId(int pageNo, int pageSize, String orderByProperty, String sortType, long roleId) {
		
		Specification<User> specification = new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Join<User, UserRole> userRoles = root.join("userRoles", JoinType.LEFT);

				Path<String> path = userRoles.get("id").get("roleId");
				return cb.equal(path, roleId);
			}
			
		};

		Direction direction = Direction.ASC;

		if ("DESC".equals(sortType)) {
			direction = Direction.DESC;
		}

		Order idOrder = new Order(direction, orderByProperty);
		Sort sort = new Sort(idOrder);

		PageRequest pageable = new PageRequest(pageNo - 1, pageSize, sort);

		Page<User> page = userRepository.findAll(specification, pageable);

		return page;
	}

	@Override
	public Page<User> getUnassignedRoleUsers(int pageNo, int pageSize, String orderByProperty, String sortType, long roleId) {
		
		Specification<User> specification = new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				Subquery<Long> subQuery = query.subquery(Long.class);
				Root<UserRole> fromUR = subQuery.from(UserRole.class);
				
				subQuery.select(fromUR.get("id").get("userId")).where(cb.equal(fromUR.get("id").get("roleId"), roleId));
				
				return cb.not(cb.in(root.get("userId")).value(subQuery));
				
			}
		};
		
		Direction direction = Direction.ASC;

		if ("DESC".equals(sortType)) {
			direction = Direction.DESC;
		}

		Order idOrder = new Order(direction, orderByProperty);
		Sort sort = new Sort(idOrder);

		PageRequest pageable = new PageRequest(pageNo - 1, pageSize, sort);

		Page<User> page = userRepository.findAll(specification, pageable);

		return page;
	}

}
