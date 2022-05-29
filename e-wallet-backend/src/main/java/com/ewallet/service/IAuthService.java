package com.ewallet.service;

import com.ewallet.dto.CustomerDTO;
import com.ewallet.dto.JwtRespone;
import com.ewallet.dto.LoginRequest;
import com.ewallet.dto.SignupRequest;

public interface IAuthService {
	JwtRespone signinJwtRespone(LoginRequest loginRequest);
	
	String register(SignupRequest signUpRequest);
	
	CustomerDTO userdetail();
}
