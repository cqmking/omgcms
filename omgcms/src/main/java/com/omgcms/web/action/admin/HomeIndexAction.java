package com.omgcms.web.action.admin;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.omgcms.util.CmsUtil;

@Controller
public class HomeIndexAction {

	private Logger logger = LoggerFactory.getLogger(HomeIndexAction.class);

	@RequestMapping("/index.do")
	public String index(Model model) {
		logger.debug("Access admin home page.");
		return "redirect:/admin/system_info.do";
	}
	
	@RequestMapping("/system_info.do")
	public String systemInfo(Model model, HttpServletRequest request) {

		model.addAttribute("systemInfo", CmsUtil.getSystemInfo(request));
		
		return "admin/system_info";
	}

}
