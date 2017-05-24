package com.omgcms.exception;

public class CmsRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -6524944438484229154L;
	
	private String errorCode; 
	
	public CmsRuntimeException() {
		super();
	}
	
	public CmsRuntimeException(String errorCode) {
		super();
		this.errorCode = errorCode;
	}

	public CmsRuntimeException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
}
