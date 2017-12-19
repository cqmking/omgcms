package com.omgcms.util;

public enum ErrorCode {

	ILLEGAL_PARAMS("ILLEGAL_PARAMS", "request params invalid"), SERVER_ERROR("SERVER_ERROR", "server is busy");

	ErrorCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	private String code;

	private String message;

	public String getCode() {
		return code;
	}

	public String getMessage() {
		try {
			String localeMessage = CmsUtil.getLocaleMessage(code);
			return localeMessage;
		} catch (Exception e) {
			return message;
		}
	}
}
