package com.ewallet.service;

import java.util.List;

import com.ewallet.entity.TransactionCategory;

public interface ITransactionCategoryService {
	List<TransactionCategory> geTransactionCategories();
}
