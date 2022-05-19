package com.hoangminh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hoangminh.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

}
