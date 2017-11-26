package com.omgcms.web.action.api;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.omgcms.exception.CmsRuntimeException;
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

@Controller
@RequestMapping("/role")
public class RoleController {

	private Logger logger = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserRoleService userRoleService;

	@ResponseBody
	@RequestMapping(value = "/list/page-{pageNum}/page-size-{pageSize}", method = RequestMethod.GET)
	public Page<Role> getRoleList(@PathVariable(value = "pageNum") Integer pageNum,
			@PathVariable(value = "pageSize") Integer pageSize) {

		logger.debug("Get role list for page {}", pageNum);

		pageNum = ParamUtil.get(pageNum, 1);
		pageSize = ParamUtil.get(pageSize, CmsConstants.ADMIN_PAGE_SIZE);

		Page<Role> pageRoles = roleService.getRoles(pageNum, pageSize, "name", CmsConstants.ORDER_ASC);

		return pageRoles;
	}

	@ResponseBody
	@RequestMapping(value = "/roleid/{roleId}", method = RequestMethod.GET)
	public Role getRole(@PathVariable(value = "roleId") Integer roleId) {

		logger.debug("Start to get role by roleId[{}].", roleId);

		roleId = ParamUtil.get(roleId, -1);

		Role role = null;

		if (roleId != null) {
			role = roleService.getByRoleId(roleId);
		}

		return role;

	}

	@ResponseBody
	@RequestMapping(value = "/delete/{roleId}", method = RequestMethod.DELETE)
	public boolean deleteRole(@PathVariable(value = "roleId") Integer roleId) {

		roleId = ParamUtil.get(roleId, -1);

		roleService.deleteRole(roleId);

		return true;
	}

	@ResponseBody
	@RequestMapping(value = "/batchDelete/{roleIds}", method = RequestMethod.DELETE)
	public boolean deleteRoles(@PathVariable(value = "roleIds") String roleIds) {

		roleIds = ParamUtil.get(roleIds, StringPool.BLANK);
		if (StringUtils.isBlank(roleIds)) {
			return false;
		}
		String[] idsStr = roleIds.split(StringPool.COMMA);
		long[] _roleIds = new long[idsStr.length];

		for (int i = 0; i < idsStr.length; i++) {
			_roleIds[i] = Long.valueOf(idsStr[i]);
		}

		logger.debug("Will delete roles with ids:{}", roleIds);
		roleService.deleteRoles(_roleIds);

		return true;
	}

	@ResponseBody
	@RequestMapping(value = "/create", method = { RequestMethod.POST, RequestMethod.PUT })
	public Role createRole(@RequestBody Role role) {

		validateRole(role, true);

		Date now = new Date();
		role.setCreateDate(now);
		role.setModifyDate(now);

		Role savedRole = roleService.saveAndFlush(role);

		return savedRole;
	}

	@ResponseBody
	@RequestMapping(value = "/update", method = { RequestMethod.POST, RequestMethod.PUT })
	public Role updateRole(@RequestBody Role role) {

		validateRole(role, false);

		Role savedRole = roleService.getByRoleId(role.getRoleId());

		Date now = new Date();
		savedRole.setModifyDate(now);
		savedRole.setName(role.getName());
		savedRole.setRoleKey(role.getRoleKey());
		savedRole.setDescription(role.getDescription());

		Role _role = roleService.saveAndFlush(savedRole);

		return _role;
	}

	@ResponseBody
	@RequestMapping(value = "/unassigned-user-list/roleid-{roleId}/page-{pageNum}/page-size-{pageSize}", method = RequestMethod.GET)
	public Page<User> getUnassignedUserList(@PathVariable(value = "roleId") Long roleId,
			@PathVariable(value = "pageNum") Integer pageNum, @PathVariable(value = "pageSize") Integer pageSize) {

		roleId = ParamUtil.get(roleId, -1);
		pageNum = ParamUtil.get(pageNum, 1);
		pageSize = ParamUtil.get(pageSize, CmsConstants.ADMIN_PAGE_SIZE);

		Page<User> pageRoles = userService.getUnassignedRoleUsers(pageNum, pageSize, "userName", CmsConstants.ORDER_ASC, roleId);

		return pageRoles;
	}

	@ResponseBody
	@RequestMapping(value = "/assigned-user-list/roleid-{roleId}/page-{pageNum}/page-size-{pageSize}", method = RequestMethod.GET)
	public Page<User> getAssignedUserList(@PathVariable(value = "roleId") Long roleId,
			@PathVariable(value = "pageNum") Integer pageNum, @PathVariable(value = "pageSize") Integer pageSize) {

		roleId = ParamUtil.get(roleId, -1);
		pageNum = ParamUtil.get(pageNum, 1);
		pageSize = ParamUtil.get(pageSize, CmsConstants.ADMIN_PAGE_SIZE);

		Page<User> pageRoles = userService.getUsersByRoleId(pageNum, pageSize, "userName", CmsConstants.ORDER_ASC, roleId);

		return pageRoles;
	}

	@ResponseBody
	@RequestMapping(value = "/add-role-users/roleid-{roleId}/{userIds}", method = { RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
	public List<UserRole> addRoleUsers(@PathVariable(value = "roleId") Long roleId, @PathVariable(value = "userIds") String userIds) {
		
		roleId = ParamUtil.get(roleId, -1);
		userIds = ParamUtil.get(userIds, StringPool.BLANK);

		if (StringUtils.isBlank(userIds)) {
			return null;
		}
		
		String[] idsStr = userIds.split(StringPool.COMMA);
		Long[] _userIds = new Long[idsStr.length];

		for (int i = 0; i < idsStr.length; i++) {
			_userIds[i] = Long.valueOf(idsStr[i]);
		}
		
		Role role = roleService.getByRoleId(roleId);
		List<User> users = userService.getUsersByIds(_userIds);
		List<UserRole> userRoles = userRoleService.addUserRoles(role, users);
		
		return userRoles;
	}
	
	@ResponseBody
	@RequestMapping(value = "/remove-role-users/roleid-{roleId}/{userIds}", method = { RequestMethod.POST, RequestMethod.PUT,
			RequestMethod.DELETE })
	public boolean removeUserRoles(@PathVariable(value = "roleId") Long roleId, @PathVariable(value = "userIds") String userIds) {

		roleId = ParamUtil.get(roleId, -1);
		userIds = ParamUtil.get(userIds, StringPool.BLANK);

		if (StringUtils.isBlank(userIds)) {
			return false;
		}

		String[] idsStr = userIds.split(StringPool.COMMA);
		long[] _userIds = new long[idsStr.length];

		for (int i = 0; i < idsStr.length; i++) {
			_userIds[i] = Long.valueOf(idsStr[i]);
		}
		
		userRoleService.deleteUserRoles(_userIds, roleId);
		
		return true;
	}
	
	@ResponseBody
	@RequestMapping(value = "/addResPemissions/roleid-{roleId}/{userIds}", method = { RequestMethod.POST, RequestMethod.PUT,
			RequestMethod.DELETE })
	public void addResourcePermission() {
		
	}
	
	private boolean validateRole(Role role, boolean isNew) {

		if (StringUtils.isBlank(role.getRoleKey())) {
			throw new CmsRuntimeException("error.form.field.required", new Object[] { CmsUtil.getLocaleMessage("label.common.code") },
					"Role key should not be null");
		}

		if (StringUtils.isBlank(role.getName())) {
			throw new CmsRuntimeException("error.form.field.required", new Object[] { CmsUtil.getLocaleMessage("label.common.name") },
					"Role name should not be null");
		}

		if (isNew) {

			Role roleByRoleKey = roleService.getByRoleKey(role.getRoleKey());
			if (roleByRoleKey != null) {
				throw new CmsRuntimeException("error.object.already.exsit",
						new Object[] { CmsUtil.getLocaleMessage("label.common.code"), role.getRoleKey(),
								CmsUtil.getLocaleMessage("label.role") },
						"There already had a role with roleKey ".concat("[").concat(role.getRoleKey()).concat("]"));
			}

			Role roleByName = roleService.getByName(role.getName());
			if (roleByName != null) {
				throw new CmsRuntimeException("error.object.already.exsit",
						new Object[] { CmsUtil.getLocaleMessage("label.common.name"), role.getName(),
								CmsUtil.getLocaleMessage("label.role") },
						"There already had a role with roleName ".concat("[").concat(role.getName()).concat("]"));
			}

		}

		return true;
	}
	
	

}
