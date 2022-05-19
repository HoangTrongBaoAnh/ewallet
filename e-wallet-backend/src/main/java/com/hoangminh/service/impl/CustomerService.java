package com.hoangminh.service.impl;

import com.hoangminh.converter.CustomerConverter;
import com.hoangminh.dto.CustomerDTO;
import com.hoangminh.entity.CustomerEntity;
import com.hoangminh.exception.NotFoundException;
import com.hoangminh.repository.CustomerRepository;
import com.hoangminh.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerConverter customerConverter;

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        CustomerEntity customerEntity;
        customerEntity = customerConverter.toEntity(customerDTO);
        customerEntity = customerRepository.saveAndFlush(customerEntity);
        return customerConverter.toDTO(customerEntity);
    }

    @Override
    public void delete(long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public CustomerDTO updateCustomer(long id, CustomerDTO customerDTO) {
        CustomerEntity oldCustomerEntity = customerRepository.getById(id);
        CustomerEntity customerEntity = customerConverter.toEntity(customerDTO, oldCustomerEntity);
        customerEntity = customerRepository.saveAndFlush(customerEntity);
        return customerConverter.toDTO(customerEntity);
    }

    @Override
    public CustomerDTO getCustomer(long id) {
        CustomerEntity customerEntity = customerRepository.getById(id);
        return customerConverter.toDTO(customerEntity);
    }

    @Override
    public List<CustomerDTO> getAllCustomer() {
        List<CustomerDTO> dtoList= new ArrayList<>();
        List<CustomerEntity> entityList = customerRepository.findAll();
        for(CustomerEntity item: entityList){
            dtoList.add(customerConverter.toDTO(item));
        }
        return dtoList;
    }

	@Override
	public List<CustomerEntity> getContainingUserName(String name) {
		// TODO Auto-generated method stub
		return customerRepository.findByUsernameContaining(name);
		
	}

	@Override
	public List<CustomerEntity> getContainingUserNameExcept() {
		// TODO Auto-generated method stub
		UserDetails userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomerEntity user = customerRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->new NotFoundException("user not found"));
		return customerRepository.findByUsernameExcep(user.getId());
	}
}
