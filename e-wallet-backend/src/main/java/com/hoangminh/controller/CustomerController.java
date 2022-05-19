package com.hoangminh.controller;

import com.hoangminh.dto.CustomerDTO;
import com.hoangminh.entity.CustomerEntity;
import com.hoangminh.exception.*;
import com.hoangminh.service.ICustomerService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@CrossOrigin
@RestController
public class CustomerController {

    private static Logger logger = Logger.getLogger(CustomerController.class);

    @Autowired
    private ICustomerService customerService;

    @PostMapping("/api/customer")
    public CustomerDTO createCustomer(@RequestBody CustomerDTO model) {
        try {
            logger.info("Success!");
            return customerService.createCustomer(model);
        } catch (Exception exc) {
            logger.error(exc);
            throw new UnknownException("Unknown Error");
        }
    }

    @DeleteMapping("/api/customer")
    public void deleteCustomer(@RequestBody String id) {
        try{
            logger.info("Success!");
            customerService.delete(Long.parseLong(id));
        } catch (NumberFormatException exc) {
            logger.error(exc);
            throw new FormatException("Can't Parse String Value");
        } catch (EmptyResultDataAccessException exc) {
            logger.error(exc);
            throw new EmptyResultException("Can't Found Customer ID");
        } catch (Exception exc) {
            logger.error(exc);
            throw new UnknownException("Unknown Error");
        }
    }

    @PutMapping("/api/customer/{id}")
    public CustomerDTO updateCustomer(@RequestBody CustomerDTO model, @PathVariable("id") String id) {
        try {
            logger.info("Success!");
            return customerService.updateCustomer(Long.parseLong(id), model);
        } catch (EntityNotFoundException exc) {
            logger.error(exc);
            throw new NotFoundException("Customer Not Found");
        } catch (NumberFormatException exc) {
            logger.error(exc);
            throw new FormatException("Can't Parse String Value");
        } catch (Exception exc) {
            logger.error(exc);
            throw new UnknownException("Unknown Error");
        }
    }

    @GetMapping("/api/customer")
    public List<CustomerDTO> getAllCustomer() {
        try{
            logger.info("Success!");
            return customerService.getAllCustomer();
        } catch (Exception exc) {
            logger.error(exc);
            throw new UnknownException("Unknown Error");
        }
    }

    @GetMapping("/api/customer/{id}")
    public CustomerDTO getCustomer(@PathVariable("id") String id) {
        try{
            logger.info("Success!");
            return customerService.getCustomer(Long.parseLong(id));
        } catch (EntityNotFoundException exc) {
            logger.error(exc);
            throw new NotFoundException("Customer Not Found");
        } catch (NumberFormatException exc) {
            logger.error(exc);
            throw new FormatException("Can't Parse String Value");
        } catch (Exception exc){
            logger.error(exc);
            throw new UnknownException("Unknown Error");
        }
    }
    
    @GetMapping("/api/customer/find")
	public List<CustomerEntity> findAll(){
    	return customerService.getContainingUserNameExcept();
	}
    
    @GetMapping("/api/customer/find/{name}")
	public List<CustomerEntity> findContainCustomerEntities(@PathVariable String name){
    	return customerService.getContainingUserName(name);
	}
    
//    @GetMapping("/api/customer/findListUsername")
//	public List<CustomerDTO> findListUsername(){
//    	return customerService.getContainingUserNameExcept();
//	}
}
