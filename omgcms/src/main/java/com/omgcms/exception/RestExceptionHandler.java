package com.omgcms.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.omgcms.bean.RestResponse;
import com.omgcms.util.ErrorCode;
import com.omgcms.util.RestResponseGenerator;

@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

	@ExceptionHandler
	@ResponseBody
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
	private <T> RestResponse<T> runtimeExceptionHandler(Exception e) {
		logger.error("---------> huge error!", e);
		return RestResponseGenerator.genErrorResult(ErrorCode.SERVER_ERROR);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	private <T> RestResponse<T> illegalParamsExceptionHandler(MethodArgumentNotValidException e) {
		logger.error("---------> invalid request!", e);
		return RestResponseGenerator.genErrorResult(ErrorCode.ILLEGAL_PARAMS);
	}

}
