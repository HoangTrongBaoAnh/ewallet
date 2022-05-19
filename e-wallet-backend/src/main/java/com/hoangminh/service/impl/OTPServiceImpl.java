package com.hoangminh.service.impl;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.hoangminh.service.OTPService;

@Service
public class OTPServiceImpl implements OTPService{
	private static final Integer EXPIRE_MINS = 5;

	 private LoadingCache<String, Integer> otpCache;

	 public OTPServiceImpl(){
	 super();
	 otpCache = CacheBuilder.newBuilder().
	     expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
	      public Integer load(String key) {
	             return 0;
	       }
	   });
	 }
	@Override
	public int generateOTP(String key) {
		// TODO Auto-generated method stub
		Random random = new Random();
		int otp = 100000 + random.nextInt(900000);
		otpCache.put(key, otp);
		return otp;
	}

	@Override
	public void clearOTP(String key) {
		otpCache.invalidate(key);
		// TODO Auto-generated method stub
		
	}
	@Override
	public int getOtp(String key) {
		// TODO Auto-generated method stub
		try{
			 return otpCache.get(key); 
		}catch (Exception e){
			 return 0; 
		}
	}

}
