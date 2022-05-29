package com.ewallet.service.impl;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ewallet.config.jwt.JwtUtils;
import com.ewallet.converter.CustomerConverter;
import com.ewallet.dto.CustomerDTO;
import com.ewallet.dto.JwtRespone;
import com.ewallet.dto.LoginRequest;
import com.ewallet.dto.MessageResponse;
import com.ewallet.dto.SignupRequest;
import com.ewallet.entity.CustomerEntity;
import com.ewallet.entity.ERole;
import com.ewallet.entity.RoleEntity;
import com.ewallet.exception.BadRequestException;
import com.ewallet.exception.NotFoundException;
import com.ewallet.exception.ResourceNotFoundException;
import com.ewallet.repository.CustomerRepository;
import com.ewallet.repository.RoleRepository;
import com.ewallet.service.IAuthService;

@Service
public class AuthServiceImpl implements IAuthService{
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired 
	PasswordEncoder encoder;
	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	private CustomerConverter customerConverter;
	
	@Override
	public JwtRespone signinJwtRespone(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		return new JwtRespone(jwt, 
				 userDetails.getId(), 
				 userDetails.getUsername(), 
				 userDetails.getEmail(), 
				 roles);
	}

	@Override
	public String register(SignupRequest signUpRequest) {
		if (customerRepository.existsByUsername(signUpRequest.getUsername())) {
			throw new BadRequestException("Username is already taken!");
		
		}
		if (customerRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new BadRequestException("Email is already in use!");
		}
		// Create new user's account
		CustomerEntity user = new CustomerEntity(signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));
		Set<String> strRoles = signUpRequest.getRole();
		Set<RoleEntity> roles = new HashSet<>();
		if (strRoles == null) {
			RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					RoleEntity adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);
					break;
				case "mod":
					RoleEntity modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);
					break;
				default:
					RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		user.setRoles(roles);
		user.setBalance(new BigDecimal("1000"));
		customerRepository.save(user);
		return "User registered successfully!";
	}

	@Override
	public CustomerDTO userdetail() {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();	
		CustomerEntity user = customerRepository.findById(userDetails.getId()).orElseThrow(()->new NotFoundException("User with " + userDetails.getId() + " cant be found"));
		return customerConverter.toDTO(user);
	}

}
