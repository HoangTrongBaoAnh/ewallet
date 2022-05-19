package com.hoangminh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoangminh.entity.TransactionCategory;
import com.hoangminh.repository.TransactionCategoryRepository;
import com.hoangminh.service.ITransactionCategoryService;

@Service
public class TransactionCategoryImpl implements ITransactionCategoryService{
	
	@Autowired
	private TransactionCategoryRepository transactionCategoryRepository;
	
	@Override
	public List<TransactionCategory> geTransactionCategories() {
		List<TransactionCategory> transactionCategories = transactionCategoryRepository.findAll();
		// TODO Auto-generated method stub
		return transactionCategories;
	}

}
