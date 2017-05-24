package com.omgcms.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.omgcms.admin.util.Config;
import com.omgcms.util.CmsUtil;
import com.omgcms.util.StringPool;

public class LoadVariableInterceptor implements HandlerInterceptor {

	private Logger logger = LoggerFactory.getLogger(LoadVariableInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		logger.debug("Set cmsUtil, config and basePath in interceptor.");
		
		request.setAttribute("cmsUtil", CmsUtil.getInstance());
		request.setAttribute("config", Config.getInstance());
		request.setAttribute("basePath", getBasePath(request));
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	private String getBasePath(HttpServletRequest request) {

		String serverName = request.getServerName();
		String scheme = request.getScheme();
		int serverPort = request.getServerPort();
		String contextPath = request.getContextPath();

		String basePath = StringPool.BLANK;

		if (80 == serverPort) {
			basePath = scheme.concat(StringPool.COLON).concat(StringPool.DOUBLE_SLASH).concat(serverName).concat(contextPath);
		} else {
			basePath = scheme.concat(StringPool.COLON).concat(StringPool.DOUBLE_SLASH).concat(serverName).concat(StringPool.COLON)
					.concat(String.valueOf(serverPort)).concat(contextPath);
		}
		
		return basePath;
	}
}
