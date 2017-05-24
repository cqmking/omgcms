package com.omgcms.web.action.admin;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.omgcms.service.RealmService;

@Controller
public class LogoutAction {
	
	private Logger logger = LoggerFactory.getLogger(LogoutAction.class);
	
	@Autowired
	private RealmService realmService;
	
	@RequestMapping("/logout.do")
	public String logout(Model model) {
		
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		
		realmService.clearShiroRealmCache();
		
		logger.debug("Logout from admin console.");
		
		return "redirect:/admin/login.do";
	}
	
}
