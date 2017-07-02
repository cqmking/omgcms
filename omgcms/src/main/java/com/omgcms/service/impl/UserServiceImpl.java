package com.omgcms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omgcms.exception.CmsExceptionConstants;
import com.omgcms.exception.CmsRuntimeException;
import com.omgcms.model.core.User;
import com.omgcms.repository.UserRepository;
import com.omgcms.service.UserService;
import com.omgcms.util.CmsUtil;

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

		Order idOrder = new Order(direction, "userId");
		Sort sort = new Sort(idOrder);

		PageRequest pageable = new PageRequest(pageNo - 1, pageSize, sort);

		Page<User> page = userRepository.findAll(pageable);

		return page;
	}

}
