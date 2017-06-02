package com.omgcms.exception;

public class CmsRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -6524944438484229154L;

	private String errorCode;

	private Object[] args;

	public CmsRuntimeException() {
		super();
	}

	public CmsRuntimeException(String errorCode) {
		super();
		this.errorCode = errorCode;
	}

	public CmsRuntimeException(String errorCode, Object[] args) {
		super();
		this.errorCode = errorCode;
		this.args = args;
	}
	
	public CmsRuntimeException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public CmsRuntimeException(String errorCode, Object[] args, String message) {
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
