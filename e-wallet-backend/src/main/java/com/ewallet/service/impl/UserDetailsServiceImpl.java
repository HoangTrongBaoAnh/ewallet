package com.ewallet.service.impl;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ewallet.entity.CustomerEntity;
import com.ewallet.repository.CustomerRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	CustomerRepository userRepository;
	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CustomerEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		// TODO Auto-generated method stub
		return UserDetailsImpl.build(user);
	}

	

}
