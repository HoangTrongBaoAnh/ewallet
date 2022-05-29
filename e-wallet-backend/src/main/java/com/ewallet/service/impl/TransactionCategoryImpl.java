package com.ewallet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewallet.entity.TransactionCategory;
import com.ewallet.repository.TransactionCategoryRepository;
import com.ewallet.service.ITransactionCategoryService;

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
