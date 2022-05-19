package com.hoangminh.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoangminh.entity.TransactionCategory;
import com.hoangminh.exception.UnknownException;
import com.hoangminh.service.ITransactionCategoryService;

@RestController
@RequestMapping("/api/transactioncategory")
public class TransactionCategoryController {
	
	private static Logger logger = Logger.getLogger(TransactionCategoryController.class);

    @Autowired
    private ITransactionCategoryService TransactionCategoryService;
    
	@GetMapping
	public ResponseEntity<List<TransactionCategory>> getAllTransactionCategory(){
		try{
            logger.info("Success!");
            return new ResponseEntity<List<TransactionCategory>>(TransactionCategoryService.geTransactionCategories(),HttpStatus.OK);
        } catch (Exception exc) {
            logger.error(exc);
            throw new UnknownException("Unknown Error");
        }
	}
}
