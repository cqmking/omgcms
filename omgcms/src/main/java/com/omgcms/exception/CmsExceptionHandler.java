package com.omgcms.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.omgcms.util.CmsUtil;

public class CmsExceptionHandler extends SimpleMappingExceptionResolver {

	private static Logger logger = LoggerFactory.getLogger(CmsExceptionHandler.class);

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

		logger.warn(ex.toString());
		
		String viewName = determineViewName(ex, request);

		// vm方式返回
		if (viewName != null) {
			if (!((request.getHeader("accept") != null && request.getHeader("accept").indexOf("application/json") > -1)
					|| (request.getHeader("X-Requested-With") != null
							&& request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
				// 非异步方式返回
				Integer statusCode = determineStatusCode(request, viewName);
				if (statusCode != null) {
					applyStatusCodeIfPossible(request, response, statusCode);
				}
				// 跳转到提示页面
				return getModelAndView(viewName, ex, request);

			} else {
				// 异步方式返回
				try {

					String exceptionKey = null;
					String errorMessage = null;

					if (ex instanceof CmsRuntimeException) {

						exceptionKey = ((CmsRuntimeException) ex).getErrorCode();
						Object[] args = ((CmsRuntimeException) ex).getArgs();

						if (!StringUtils.isBlank(exceptionKey)) {
							if (args != null) {
								errorMessage = CmsUtil.getLocaleMessage(exceptionKey, args);
							} else {
								errorMessage = CmsUtil.getLocaleMessage(exceptionKey);
							}
						} else {
							errorMessage = ex.getMessage();
						}
						
						logger.warn(ex.toString());
						
					} else {
						errorMessage = ex.getMessage();
						// 将异常栈信息记录到日志中
						logger.error(getTrace(ex));
					}

					

					writeResponseForJson(response, errorMessage);

				} catch (Exception e) {
					e.printStackTrace();
				}
				// 不进行页面跳转
				return new ModelAndView();

			}
		}
		
		return new ModelAndView();

	}

	public static String getTrace(Throwable t) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		t.printStackTrace(writer);
		StringBuffer buffer = stringWriter.getBuffer();
		return buffer.toString();
	}

	@SuppressWarnings("unused")
	private void writeResponse(HttpServletResponse response, String errorMessage) throws IOException {

		PrintWriter writer = response.getWriter();
		writer.write(errorMessage);
		// response.sendError(404, errorMessage);

	}

	private void writeResponseForJson(HttpServletResponse response, String errorMessage) throws IOException {
		
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache, must-revalidate");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", "error");
		resultMap.put("message", errorMessage);
		
		response.getWriter().write(CmsUtil.objectToJsonString(resultMap));
		
	}

}
