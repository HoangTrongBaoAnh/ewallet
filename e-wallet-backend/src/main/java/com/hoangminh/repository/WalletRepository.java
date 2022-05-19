package com.hoangminh.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hoangminh.entity.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long>{
	
	@Modifying
	@Transactional
	@Query(value = "delete from wallets WHERE card_number=:cardnumber ",nativeQuery=true)
	void deletebycardnumber(@Param("cardnumber") String cardnumber);
	
	@Query(value = "select * from wallets WHERE user_id= :id ",nativeQuery=true)
	List<Wallet> findAllByUserId(@Param("id") long id);
	
	Wallet findBycardNumber(String cardNumber);
	
	
}
