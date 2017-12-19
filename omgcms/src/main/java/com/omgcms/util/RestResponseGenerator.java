package com.omgcms.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omgcms.bean.RestResponse;

public class RestResponseGenerator {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestResponseGenerator.class);

	/**
	 * normal
	 * 
	 * @param success
	 * @param data
	 * @param message
	 * @param <T>
	 * @return
	 */
	public static <T> RestResponse<T> genResult(String status, T data, String message) {
		RestResponse<T> result = RestResponse.newInstance();
		result.setStatus(status);
		result.setData(data);
		result.setMessage(message);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("generate rest result:{}", result);
		}
		LOGGER.debug("==>{}",result);
		return result;
	}

	/**
	 * success
	 * 
	 * @param data
	 * @param <T>
	 * @return
	 */
	public static <T> RestResponse<T> genSuccessResult(T data) {

		return genResult(RestResponse.STATUS_SUCCESS, data, null);
	}

	/**
	 * error message
	 * 
	 * @param message
	 *            error message
	 * @param <T>
	 * @return
	 */
	public static <T> RestResponse<T> genErrorResult(String message) {

		return genResult(RestResponse.STATUS_ERROR, null, message);
	}

	/**
	 * error
	 * 
	 * @param error
	 *            error enum
	 * @param <T>
	 * @return
	 */
	public static <T> RestResponse<T> genErrorResult(ErrorCode error) {

		return genErrorResult(error.getMessage());
	}

	/**
	 * success no message
	 * 
	 * @return
	 */
	public static <T> RestResponse<T> genSuccessResult() {
		return genSuccessResult(null);
	}

}
