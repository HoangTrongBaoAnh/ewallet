package com.ewallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ewallet.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

}
