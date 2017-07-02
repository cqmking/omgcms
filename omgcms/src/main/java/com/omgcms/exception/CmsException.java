package com.omgcms.exception;

public class CmsException extends Exception {
	
	private static final long serialVersionUID = 7475764000683024704L;

	private String errorCode;
	
	private Object[] args;

	public CmsException() {
		super();
	}

	public CmsException(String errorCode) {
		super();
		this.errorCode = errorCode;
	}

	public CmsException(String errorCode, Object[] args) {
		super();
		this.errorCode = errorCode;
		this.args = args;
	}

	public CmsException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public CmsException(String errorCode, Object[] args, String message) {
		super(message);
		this.errorCode = errorCode;
		this.args = args;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

}
