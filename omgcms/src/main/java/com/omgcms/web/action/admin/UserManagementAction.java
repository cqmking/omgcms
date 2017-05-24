package com.omgcms.web.action.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserManagementAction {

	private Logger logger = LoggerFactory.getLogger(UserManagementAction.class);

	@RequestMapping("/{page}.do")
	public String userList(Model model, @PathVariable(value = "page") String page) {
		logger.debug("Access page[{}].", page);
		return "admin/user/".concat(page);
	}
	
}
