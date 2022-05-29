package com.ewallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ewallet.entity.TransactionCategory;

public interface TransactionCategoryRepository extends JpaRepository<TransactionCategory, Long>{
	TransactionCategory findByName(String name);
}
