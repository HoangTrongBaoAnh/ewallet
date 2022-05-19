package com.hoangminh.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoangminh.dto.*;
import com.hoangminh.exception.BadRequestException;
import com.hoangminh.exception.NotFoundException;
import com.hoangminh.exception.UnknownException;
import com.hoangminh.service.IAuthService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private static Logger logger = Logger.getLogger(CustomerController.class);
	
	@Autowired
	private IAuthService authService;
	
	@PostMapping("/userDetail")
	public CustomerDTO userDetail() {
		try {
            logger.info("Success!");
            return authService.userdetail();
        } catch (NotFoundException exc) {
            logger.error(exc);
            throw new NotFoundException(exc.getMessage());
        } catch (Exception exc) {
            logger.error(exc);
            throw new UnknownException("Unknown Error");
        }
		
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser( @RequestBody LoginRequest loginRequest) {
		try {
            logger.info("Success!");
            return ResponseEntity.ok(authService.signinJwtRespone(loginRequest));
        } catch (BadCredentialsException exc) {
            logger.error(exc);
            throw new NotFoundException("Username or password is incorrect");
        } catch (UsernameNotFoundException exc) {
            logger.error(exc);
            throw new UsernameNotFoundException(exc.getMessage());
        } catch (Exception exc) {
            logger.error(exc);
            throw new UnknownException("Unknown Error");
        }
		
	}
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser( @RequestBody SignupRequest signUpRequest) {
		try {
            logger.info("Success!");
            return ResponseEntity.ok(authService.register(signUpRequest));
        }catch (BadRequestException exc) {
            logger.error(exc);
            throw new BadRequestException(exc.getMessage());
        } catch (Exception exc) {
            logger.error(exc);
            throw new UnknownException("Unknown Error");
        }
	}
}