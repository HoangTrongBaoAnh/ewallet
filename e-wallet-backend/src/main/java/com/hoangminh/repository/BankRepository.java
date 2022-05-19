package com.hoangminh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hoangminh.entity.Bank;

public interface BankRepository extends JpaRepository<Bank, Long>{

}
