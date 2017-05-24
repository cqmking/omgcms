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

import com.omgcms.model.core.User;
import com.omgcms.service.UserService;
import com.omgcms.util.CmsConstants;
import com.omgcms.util.CmsUtil;
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
	@RequestMapping(value = "/list/page-{pageNum}/page-size-{pageSize}", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	public String getUserList(@PathVariable(value = "pageNum") Integer pageNum, @PathVariable(value = "pageSize") Integer pageSize) {
		
		logger.debug("Called get user list, pageNum:{} pageSize:{}", pageNum, pageSize);

		pageSize = ParamUtil.get(pageNum, 1);
		pageSize = ParamUtil.get(pageSize, CmsConstants.ADMIN_PAGE_SIZE);

		Page<User> usersPage = userService.findUsers(pageNum, pageSize, "userId", CmsConstants.ORDER_ASC);

		return CmsUtil.objectToJsonString(usersPage);
	}

}
