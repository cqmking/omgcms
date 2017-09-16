package com.omgcms.web.interceptor;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.omgcms.admin.util.Config;
import com.omgcms.util.CmsUtil;
import com.omgcms.util.StringPool;

public class LoadVariableInterceptor implements HandlerInterceptor {

	private Logger logger = LoggerFactory.getLogger(LoadVariableInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.debug("Set cmsUtil, config and basePath in interceptor.");

		Locale locale = LocaleContextHolder.getLocale();
		Locale defaultLocale = Locale.getDefault();
		
		request.setAttribute("cmsUtil", CmsUtil.getInstance());
		request.setAttribute("config", Config.getInstance());
		request.setAttribute("basePath", getBasePath(request));
		request.setAttribute("locale", locale);
		request.setAttribute("defaultLocale", defaultLocale);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		String messageCode = request.getParameter("messageCode");
		String noteType = request.getParameter("noteType");

		logger.debug("request.getRequestURL():{}", request.getRequestURL());

		if (!StringUtils.isBlank(messageCode)) {
			String noteMessgae = CmsUtil.getLocaleMessage(messageCode);
			request.setAttribute("noteMessgae", noteMessgae);
			logger.debug("afterCompletion message {}", noteMessgae);
		}

		if (!StringUtils.isBlank(noteType)) {
			request.setAttribute("noteType", noteType);
		} else {
			request.setAttribute("noteType", "info");
		}

	}

	private String getBasePath(HttpServletRequest request) {

		String serverName = request.getServerName();
		String scheme = request.getScheme();
		int serverPort = request.getServerPort();
		String contextPath = request.getContextPath();

		String basePath = StringPool.BLANK;

		if (80 == serverPort) {
			basePath = scheme.concat(StringPool.COLON).concat(StringPool.DOUBLE_SLASH).concat(serverName)
					.concat(contextPath);
		} else {
			basePath = scheme.concat(StringPool.COLON).concat(StringPool.DOUBLE_SLASH).concat(serverName)
					.concat(StringPool.COLON).concat(String.valueOf(serverPort)).concat(contextPath);
		}

		return basePath;
	}
}
