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
public class UserManagementAction {

	private Logger logger = LoggerFactory.getLogger(UserManagementAction.class);
	
	@RequestMapping("/index.do")
	public String index(Model model) {
		return "redirect:/admin/user/list.do";
	}

	@RequestMapping("/list.do")
	public String userList(Model model) {
		logger.debug("View user list!");
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

}
