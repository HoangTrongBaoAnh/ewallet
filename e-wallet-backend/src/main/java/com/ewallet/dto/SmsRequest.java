package com.ewallet.dto;

public class SmsRequest {
	private String phonenumber;
	private String message;
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "SmsRequest [phonenumber=" + phonenumber + ", message=" + message + "]";
	}
	public SmsRequest(String phonenumber, String message) {
		super();
		this.phonenumber = phonenumber;
		this.message = message;
	}
	
	public SmsRequest() {
		
	}
	
}
