package com.omgcms.web.action.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.omgcms.web.util.ParamUtil;

@Controller
@RequestMapping("/role")
public class RoleManageAction {

	private Logger logger = LoggerFactory.getLogger(RoleManageAction.class);
	
	@RequestMapping("/index.do")
	public String index(Model model) {
		logger.debug("Redirect to /admin/role/list.do");
		return "redirect:/admin/role/list.do";
	}
	
	@RequestMapping("/list.do")
	public String userList(Model model) {
		
		return "admin/role/role_list";
	}
	
	@RequestMapping("/edit.do")
	public String editRole(Model model, @RequestParam(value = "roleId", required = false) Long roleId) {
		roleId = ParamUtil.get(roleId, -1);

		model.addAttribute("roleId", roleId);
		return "admin/role/edit_role";
	}
	
	@RequestMapping("/add.do")
	public String addRole(Model model) {
		
		return "admin/role/edit_role";
	}
	
	@RequestMapping("/assign_role_users.do")
	public String assignRoleUsers(Model model, @RequestParam(value = "roleId") Long roleId){
		
		roleId = ParamUtil.get(roleId, -1);
		model.addAttribute("roleId", roleId);
		
		return "admin/role/assign_role_users";
	}
	
	
	@RequestMapping("/assign_role_permissions.do")
	public String assignRolePermissions(Model model, @RequestParam(value = "roleId") Long roleId){
		
		roleId = ParamUtil.get(roleId, -1);
		model.addAttribute("roleId", roleId);
		
		return "admin/role/assign_role_permissions";
	}
}
