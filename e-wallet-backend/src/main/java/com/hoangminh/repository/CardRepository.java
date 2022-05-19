package com.hoangminh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hoangminh.entity.Card;

public interface CardRepository extends JpaRepository<Card, Long>{
	@Query(value = "select * from cards WHERE cardnumber= :cardnumber and bank_id=:id",nativeQuery=true)
	Card findByIdAndcardnumber(@Param("id") long Id,@Param("cardnumber") String cardnumber);
	
	Card findBycardnumber(String cardnumber);
}
