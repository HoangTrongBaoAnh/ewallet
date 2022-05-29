package com.ewallet.service;

public interface OTPService {
	public int generateOTP(String key);
	public void clearOTP(String key);
	public int getOtp(String key);
}
