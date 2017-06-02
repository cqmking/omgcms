package com.omgcms.web.action.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.omgcms.exception.CmsExceptionConstants;
import com.omgcms.exception.CmsRuntimeException;
import com.omgcms.exception.ExceptionI18nMessage;
import com.omgcms.model.core.User;
import com.omgcms.service.UserService;
import com.omgcms.util.CmsConstants;
import com.omgcms.web.action.admin.HomeIndexAction;
import com.omgcms.web.util.ParamUtil;

/***
 * UserManagement Action
 * 
 * @author Freddy
 * @version create date: 2017年5月11日
 */
@Controller
@RequestMapping("/user")
public class UserAction {

	private Logger logger = LoggerFactory.getLogger(HomeIndexAction.class);

	@Autowired
	private UserService userService;

	@ResponseBody
	@RequestMapping(value = "/list/page-{pageNum}/page-size-{pageSize}", method = RequestMethod.GET)
	public Page<User> getUserList(@PathVariable(value = "pageNum") Integer pageNum,
			@PathVariable(value = "pageSize") Integer pageSize) {

		pageNum = ParamUtil.get(pageNum, 1);
		pageSize = ParamUtil.get(pageSize, CmsConstants.ADMIN_PAGE_SIZE);

		Page<User> usersPage = userService.findUsers(pageNum, pageSize, "userId", CmsConstants.ORDER_ASC);

		return usersPage;
	}

	@ResponseBody
	@RequestMapping(value = "/userid/{userId}", method = RequestMethod.GET)
	public User getUser(@PathVariable(value = "userId") Integer userId) {

		logger.debug("Start to get user by userId[{}].", userId);

		userId = ParamUtil.get(userId, -1);

		User user = null;

		if (userId != null && userId > 0) {
			user = userService.getUser(userId);
		}

		if (user == null) {
			String arg = ExceptionI18nMessage.getLocaleMessage("label.user");
			throw new CmsRuntimeException(CmsExceptionConstants.ERROR_OBJECT_NOT_EXIST, new String[] { arg },
					"User not exsit for userId " + userId);
		}

		return user;

	}

}
