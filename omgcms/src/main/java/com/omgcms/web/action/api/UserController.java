package com.omgcms.web.action.api;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.codec.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.omgcms.admin.util.Config;
import com.omgcms.model.core.Role;
import com.omgcms.model.core.User;
import com.omgcms.model.core.UserRole;
import com.omgcms.service.RoleService;
import com.omgcms.service.UserRoleService;
import com.omgcms.service.UserService;
import com.omgcms.util.CmsConstants;
import com.omgcms.util.CmsUtil;
import com.omgcms.util.StringPool;
import com.omgcms.web.util.ParamUtil;

/***
 * UserManagement Action
 * 
 * @author Freddy
 * @version create date: 2017年5月11日
 */
@Controller
@RequestMapping("/user")
public class UserController {

	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserRoleService userRoleService;

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

		if (userId != null) {
			user = userService.getUser(userId);
		}

		return user;

	}

	@ResponseBody
	@RequestMapping(value = "/delete/{userId}", method = RequestMethod.DELETE)
	public boolean deleteUser(@PathVariable(value = "userId") Integer userId) {

		userId = ParamUtil.get(userId, -1);

		userService.deleteUser(userId);

		return true;
	}

	@ResponseBody
	@RequestMapping(value = "/batchDelete/{userIds}", method = RequestMethod.DELETE)
	public boolean deleteUsers(@PathVariable(value = "userIds") String userIds) {

		userIds = ParamUtil.get(userIds, StringPool.BLANK);
		if (StringUtils.isBlank(userIds)) {
			return false;
		}
		String[] idsStr = userIds.split(StringPool.COMMA);
		long[] _userIds = new long[idsStr.length];

		for (int i = 0; i < idsStr.length; i++) {
			_userIds[i] = Long.valueOf(idsStr[i]);
		}

		logger.debug("Will delete users with ids:{}", userIds);
		userService.deleteUsers(_userIds);

		return true;
	}

	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public User createUser(@RequestBody User user, @RequestParam(required = false) String password) {

		String newSalt = String.valueOf(new Date().getTime());

		if (!StringUtils.isBlank(password)) {
			String realPassword = Base64.decodeToString(password);
			String md5password = CmsUtil.md5encodePassword(realPassword, newSalt);
			user.setPassword(md5password);
			user.setSalt(newSalt);

		} else {
			String defaultPassword = Config.get("admin.user.defaultPassword");
			String md5password = CmsUtil.md5encodePassword(defaultPassword, newSalt);
			user.setPassword(md5password);
			user.setSalt(newSalt);
		}

		if (user.getBirthday() == null) {
			user.setBirthday(new GregorianCalendar(1970, 0, 1).getTime());
		}

		Date now = new Date();
		user.setCreateDate(now);
		user.setModifyDate(now);

		User savedUser = userService.saveAndFlush(user);

		return savedUser;
	}

	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public User updateUser(@RequestBody User user, @RequestParam(required = false) String password) {

		if (!StringUtils.isBlank(password)) {
			String realPassword = Base64.decodeToString(password);
			String newSalt = String.valueOf(new Date().getTime());
			String md5password = CmsUtil.md5encodePassword(realPassword, newSalt);
			user.setPassword(md5password);
			user.setSalt(newSalt);

		} else {
			User dbUser = userService.getUser(user.getUserId());
			user.setPassword(dbUser.getPassword());
			user.setSalt(dbUser.getSalt());
		}

		User savedUser = userService.saveAndFlush(user);

		return savedUser;
	}

	@ResponseBody
	@RequestMapping(value = "/user-role-list/userid-{userId}/page-{pageNum}/page-size-{pageSize}", method = RequestMethod.GET)
	public Page<Role> getUserRoleList(@PathVariable(value = "userId") Long userId, @PathVariable(value = "pageNum") Integer pageNum,
			@PathVariable(value = "pageSize") Integer pageSize) {

		userId = ParamUtil.get(userId, -1);
		pageNum = ParamUtil.get(pageNum, 1);
		pageSize = ParamUtil.get(pageSize, CmsConstants.ADMIN_PAGE_SIZE);

		Page<Role> pageRoles = roleService.getRolesByUserId(pageNum, pageSize, "name", CmsConstants.ORDER_ASC, userId);
		
		return pageRoles;
	}

	@ResponseBody
	@RequestMapping(value = "/unassigned-user-role-list/userid-{userId}/page-{pageNum}/page-size-{pageSize}", method = RequestMethod.GET)
	public Page<Role> getUnassignedUserRoleList(@PathVariable(value = "userId") Long userId,
			@PathVariable(value = "pageNum") Integer pageNum, @PathVariable(value = "pageSize") Integer pageSize) {

		userId = ParamUtil.get(userId, -1);
		pageNum = ParamUtil.get(pageNum, 1);
		pageSize = ParamUtil.get(pageSize, CmsConstants.ADMIN_PAGE_SIZE);

		Page<Role> pageRoles = roleService.getUnassignedUserRoles(pageNum, pageSize, "name", CmsConstants.ORDER_ASC, userId);

		return pageRoles;
	}

	@ResponseBody
	@RequestMapping(value = "/remove-user-roles/userid-{userId}/{roleIds}", method = { RequestMethod.POST, RequestMethod.PUT,
			RequestMethod.DELETE })
	public boolean removeUserRoles(@PathVariable(value = "userId") Long userId, @PathVariable(value = "roleIds") String roleIds) {

		userId = ParamUtil.get(userId, -1);
		roleIds = ParamUtil.get(roleIds, StringPool.BLANK);

		if (StringUtils.isBlank(roleIds)) {
			return false;
		}

		String[] idsStr = roleIds.split(StringPool.COMMA);
		long[] _roleIds = new long[idsStr.length];

		for (int i = 0; i < idsStr.length; i++) {
			_roleIds[i] = Long.valueOf(idsStr[i]);
		}

		logger.debug("Will delete roleIds with ids:{}", roleIds);

		userRoleService.deleteUserRoles(userId, _roleIds);

		return true;
	}

	@ResponseBody
	@RequestMapping(value = "/add-user-roles/userid-{userId}/{roleIds}", method = { RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
	public List<UserRole> addUserRoles(@PathVariable(value = "userId") Long userId, @PathVariable(value = "roleIds") String roleIds) {
		
		userId = ParamUtil.get(userId, -1);
		roleIds = ParamUtil.get(roleIds, StringPool.BLANK);

		if (StringUtils.isBlank(roleIds)) {
			return null;
		}
		
		String[] idsStr = roleIds.split(StringPool.COMMA);
		Long[] _roleIds = new Long[idsStr.length];

		for (int i = 0; i < idsStr.length; i++) {
			_roleIds[i] = Long.valueOf(idsStr[i]);
		}
		
		User user = userService.getUser(userId);
		List<Role> roles = roleService.getRolesByIds(_roleIds);
		List<UserRole> userRoles = userRoleService.addUserRoles(user, roles);
		
		return userRoles;
	}

}
