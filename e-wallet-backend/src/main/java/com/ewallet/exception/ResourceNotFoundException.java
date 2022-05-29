package com.ewallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resourceName;
	private String filedName;
	private Object fieldValue;
	public ResourceNotFoundException(String resourceName, String filedName, Object fieldValue) {
		super(String.format("%s not found with %s: '%s'", resourceName,filedName,fieldValue));
		this.resourceName = resourceName;
		this.filedName = filedName;
		this.fieldValue = fieldValue;
	}
	
	public String getResourceName() {
		return resourceName;
	}
	
	public String getFiledName() {
		return filedName;
	}
	public Object getFieldValue() {
		return fieldValue;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}

	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}
}
