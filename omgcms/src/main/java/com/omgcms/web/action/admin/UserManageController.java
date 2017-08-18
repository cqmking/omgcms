package com.omgcms.web.action.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.omgcms.web.util.ParamUtil;

@Controller
@RequestMapping("/user")
public class UserManageController {

	private Logger logger = LoggerFactory.getLogger(UserManageController.class);

	@RequestMapping("/index.do")
	public String index(Model model) {
		logger.debug("Redirect to /admin/user/list.do");
		
		return "redirect:/admin/user/list.do";
	}

	@RequestMapping("/list.do")
	public String userList(Model model) {
		
		return "admin/user/user_list";
	}

	@RequestMapping("/edit.do")
	public String editUser(Model model, @RequestParam(value = "userId", required = false) Long userId) {

		userId = ParamUtil.get(userId, -1);

		model.addAttribute("userId", userId);
		return "admin/user/edit_user";
	}

	@RequestMapping("/add.do")
	public String addUser(Model model) {

		return "admin/user/edit_user";
	}
	
	@RequestMapping("/assign_user_roles.do")
	public String assignUserRoles(Model model, @RequestParam(value = "userId") Long userId) {
		userId = ParamUtil.get(userId, -1);
		model.addAttribute("userId", userId);
		return "admin/user/assign_user_roles";
	}
}
