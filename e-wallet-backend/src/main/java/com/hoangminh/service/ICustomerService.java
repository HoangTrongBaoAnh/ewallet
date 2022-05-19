package com.hoangminh.service;

import com.hoangminh.dto.CustomerDTO;
import com.hoangminh.entity.CustomerEntity;

import java.util.List;

public interface ICustomerService {

    CustomerDTO createCustomer(CustomerDTO customerDTO);
    void delete(long id);
    CustomerDTO updateCustomer(long id, CustomerDTO customerDTO);
    CustomerDTO getCustomer(long id);
    List<CustomerDTO> getAllCustomer();
    
    List<CustomerEntity> getContainingUserName(String name);
    
    List<CustomerEntity> getContainingUserNameExcept();

}
