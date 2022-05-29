package com.ewallet.service;

import java.util.List;

import com.ewallet.dto.CustomerDTO;
import com.ewallet.entity.CustomerEntity;

public interface ICustomerService {

    CustomerDTO createCustomer(CustomerDTO customerDTO);
    void delete(long id);
    CustomerDTO updateCustomer(long id, CustomerDTO customerDTO);
    CustomerDTO getCustomer(long id);
    List<CustomerDTO> getAllCustomer();
    
    List<CustomerEntity> getContainingUserName(String name);
    
    List<CustomerEntity> getContainingUserNameExcept();

}
