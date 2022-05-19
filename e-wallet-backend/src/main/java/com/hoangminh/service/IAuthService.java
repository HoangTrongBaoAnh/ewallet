package com.hoangminh.service;

import com.hoangminh.dto.CustomerDTO;
import com.hoangminh.dto.JwtRespone;
import com.hoangminh.dto.LoginRequest;
import com.hoangminh.dto.SignupRequest;

public interface IAuthService {
	JwtRespone signinJwtRespone(LoginRequest loginRequest);
	
	String register(SignupRequest signUpRequest);
	
	CustomerDTO userdetail();
}
