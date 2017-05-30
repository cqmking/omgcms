package com.omgcms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omgcms.model.core.User;
import com.omgcms.repository.UserRepository;
import com.omgcms.service.UserService;

@Transactional
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User saveAndFlush(User user) {
		return userRepository.saveAndFlush(user);
	}

	@Override
	public User getByUserAccount(String userAccount) {
		return userRepository.getByUserAccount(userAccount);
	}
	
	@Override
	public User getUser(long userId){
		return userRepository.getByUserId(userId);
	}

	@Override
	public Page<User> findUsers(int pageNo, int pageSize, String orderByProperty, String sortType) {
		
		Direction direction = Direction.ASC;
		
		if ("DESC".equals(sortType)) {
			direction = Direction.DESC;
		}
		
		Order idOrder = new Order(direction, "userId");
		Sort sort = new Sort(idOrder);

		PageRequest pageable = new PageRequest(pageNo - 1, pageSize, sort);
		
		Page<User> page = userRepository.findAll(pageable);
		
		return page;
	}

}
