package com.ewallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ewallet.entity.Bank;

public interface BankRepository extends JpaRepository<Bank, Long>{

}
