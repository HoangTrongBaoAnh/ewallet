package com.hoangminh.repository;

import com.hoangminh.entity.CustomerEntity;
import com.hoangminh.dto.TransactionDTO;
import com.hoangminh.dto.chartByCategories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
	Optional<CustomerEntity> findByUsername(String username);

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);
	
	List<CustomerEntity> findByUsernameContaining(String title);
	
	@Query(value = "select * from customer where id <> :id",nativeQuery=true)
	List<CustomerEntity> findByUsernameExcep(@Param("id") long id);

	
	@Query(value = "select DISTINCT MONTH(transactions.created_date) as transcmonth,transactions.*,name as category from transactions, customer, transaction_categories WHERE customer.id=transactions.customer_id and transaction_categories.id=trans_category_id and customer_id= :id order by transactions.created_date DESC",nativeQuery=true)
	Page<TransactionDTO> findallTransactions(@Param("id") long id,Pageable pageable);
	
	
	@Query(value = "select month(transactions.created_date) as transcmonth,SUM(amount) as amount from transactions WHERE year(transactions.created_date)=2022 and transactions.customer_id=:id and year(created_date) = :year GROUP BY month(created_date)",nativeQuery=true)
	List<TransactionDTO> chart(@Param("id") long id,@Param("year") int year);
	
	@Query(value = "select transaction_categories.name,COUNT(transactions.id) as count,sum(transactions.amount) as sum FROM transaction_categories,transactions,customer WHERE customer.id = transactions.customer_id and transaction_categories.id = transactions.trans_category_id and customer.id = :id and year(transactions.created_date)=:year GROUP BY transaction_categories.id",nativeQuery=true)
	List<chartByCategories> chartByCategories(@Param("id") long id,@Param("year") int year);
	
	
}
