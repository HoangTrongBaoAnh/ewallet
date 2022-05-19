package com.hoangminh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hoangminh.entity.TransactionCategory;

public interface TransactionCategoryRepository extends JpaRepository<TransactionCategory, Long>{
	TransactionCategory findByName(String name);
}
