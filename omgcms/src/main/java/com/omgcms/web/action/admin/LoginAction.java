package com.omgcms.web.action.admin;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.omgcms.exception.CmsExceptionConstants;
import com.omgcms.exception.CmsRuntimeException;

@Controller
public class LoginAction {

	private Logger logger = LoggerFactory.getLogger(HomeIndexAction.class);

	@RequestMapping("/login.do")
	public String index(Model model) {
		logger.debug("Access admin login page.");
		return "admin/login";
	}

	@RequestMapping("/checkLogin.do")
	public String checkLogin(Model model, @RequestParam(value = "oc_account", required = false) String account,
			@RequestParam(value = "oc_password", required = false) String password,
			@RequestParam(value = "oc_rememberme", required = false) boolean rememberme) {

		UsernamePasswordToken token = null;

		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.isAuthenticated()) {
			return "redirect:/admin/index.do";
		}

		try {

			if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
				throw new CmsRuntimeException(CmsExceptionConstants.LOGIN_ERROR_EMPTY_ACCOUNT_PWD);
			}
			
			token = new UsernamePasswordToken(account, password);

			if (rememberme) {
				token.setRememberMe(true);
			}

			currentUser.login(token);
			return "redirect:/admin/index.do";

		} catch (Exception e) {

			logger.error("Login failed:" + e);

			String errorCode = CmsExceptionConstants.ERROR_UNKNOWN;

			if (e instanceof IncorrectCredentialsException) {
				errorCode = CmsExceptionConstants.LOGIN_ERROR_INVALID_CREDENTIAL;
			} else if (e instanceof UnknownAccountException) {
				errorCode = CmsExceptionConstants.LOGIN_ERROR_UNKNOWN_ACCOUNT;
			} else if (e instanceof CmsRuntimeException) {
				errorCode = ((CmsRuntimeException) e).getErrorCode();
			} else {
				throw e;
			}
			
			model.addAttribute("errorCode", errorCode);
			if (token != null) {
				token.clear();
			}

			return "admin/login";
		}

	}
	
}
