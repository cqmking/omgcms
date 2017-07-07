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

import com.omgcms.model.core.Role;
import com.omgcms.service.RoleService;
import com.omgcms.util.CmsConstants;
import com.omgcms.web.util.ParamUtil;

@Controller
@RequestMapping("/role")
public class RoleAction {

	private Logger logger = LoggerFactory.getLogger(RoleAction.class);

	@Autowired
	private RoleService roleService;

	@ResponseBody
	@RequestMapping(value = "/list/page-{pageNum}/page-size-{pageSize}", method = RequestMethod.GET)
	public Page<Role> getUserList(@PathVariable(value = "pageNum") Integer pageNum,
			@PathVariable(value = "pageSize") Integer pageSize) {

		logger.debug("Get role list for page {}", pageNum);

		pageNum = ParamUtil.get(pageNum, 1);
		pageSize = ParamUtil.get(pageSize, CmsConstants.ADMIN_PAGE_SIZE);
		
		Page<Role> pageRoles = roleService.findRoles(pageNum, pageSize, "roleKey", CmsConstants.ORDER_ASC);
		
		return pageRoles;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/roleid/{roleId}", method = RequestMethod.GET)
	public Role getRole(@PathVariable(value = "roleId") Integer roleId) {

		logger.debug("Start to get role by roleId[{}].", roleId);

		roleId = ParamUtil.get(roleId, -1);

		Role role = null;

		if (roleId != null) {
			role = roleService.getRole(roleId);
		}
		
		return role;

	}
	
	
}
