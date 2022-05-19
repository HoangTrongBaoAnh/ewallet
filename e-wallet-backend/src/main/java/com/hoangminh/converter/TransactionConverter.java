package com.hoangminh.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.hoangminh.dto.MoneyTranferRequest;
import com.hoangminh.dto.TopupRequest;
import com.hoangminh.dto.paymentRequest;
import com.hoangminh.entity.CustomerEntity;
import com.hoangminh.entity.Transaction;
import com.hoangminh.entity.TransactionCategory;
import com.hoangminh.exception.NotFoundException;
import com.hoangminh.repository.CustomerRepository;
import com.hoangminh.repository.TransactionCategoryRepository;
import com.hoangminh.service.impl.UserDetailsImpl;

@Component
public class TransactionConverter {
	@Autowired
	private TransactionCategoryRepository transactionCategoryRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	public Transaction moneyTranferToEntity(MoneyTranferRequest moneyTranferRequest,CustomerEntity user) {
		UserDetails userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//CustomerEntity user = customerRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->new NotFoundException("User with the name" + userDetails.getUsername() + " not found"));
		Transaction transaction = new Transaction();
		transaction.setAmount(moneyTranferRequest.getAmount().negate());
		transaction.setCustomer(user);
		transaction.setFrom(userDetails.getUsername());
		transaction.setDescription(moneyTranferRequest.getDescription());
		
		CustomerEntity receiver = customerRepository.findByUsername(moneyTranferRequest.getTo()).orElseThrow(()->new NotFoundException("User with the name" + moneyTranferRequest.getTo() + " not found"));
		transaction.setTo(receiver.getUsername());
		
		//TransactionCategory category = transactionCategoryRepository.findById(moneyTranferRequest.getTransactionCategoryId()).orElseThrow(()->new NotFoundException("category with id " + moneyTranferRequest.getTransactionCategoryId() + " not found"));
		TransactionCategory category = transactionCategoryRepository.findByName(moneyTranferRequest.getTransactionCategory());
		if(category == null) {
			throw new NotFoundException("cate not found");
		}
		
		transaction.setTransactionCategory(category);
		return transaction;
	}
	
	public Transaction topUpRequestToEntity(TopupRequest topupRequest) {
		Transaction transaction = new Transaction();
		UserDetails userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomerEntity user = customerRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->new NotFoundException("User with the name" + userDetails.getUsername() + " not found"));
		
		transaction.setAmount(topupRequest.getAmount());
		transaction.setCustomer(user);
		transaction.setFrom(user.getUsername());
		TransactionCategory category = transactionCategoryRepository.findByName(topupRequest.getTransactionCategory());
		if(category == null) {
			throw new NotFoundException("TransCate not found");
		}
		transaction.setTransactionCategory(category);
		return transaction;
	}
	
	public Transaction paymentRequestToEntity(paymentRequest paymentRequest) {
		UserDetails userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomerEntity user = customerRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->new NotFoundException("user not found"));
		Transaction transaction = new Transaction();
		transaction.setAmount(paymentRequest.getAmount().negate());
		transaction.setCustomer(user);
		transaction.setFrom(user.getUsername());
		transaction.setDescription(paymentRequest.getDescription());
		
		
		TransactionCategory category = transactionCategoryRepository.findByName(paymentRequest.getTransactionCategory());
		if(category == null) {
			throw new NotFoundException("cate not found");
		}
		
		transaction.setTransactionCategory(category);

		
		return transaction;
	}
}
