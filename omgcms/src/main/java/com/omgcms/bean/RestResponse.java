package com.omgcms.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 
 * @author luffy
 * 2017-12-20
 * @param <T>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResponse<T> {

	public static final String STATUS_SUCCESS = "success";
	
	public static final String STATUS_ERROR = "error";
	
	private String status;
	
	private String message;
	
	private T data;
	
	private RestResponse() {}

    public static <T> RestResponse<T> newInstance() {
        return new RestResponse<>();
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "RestResponse [status=" + status + ", message=" + message + ", data=" + data + "]";
	}
    
}
