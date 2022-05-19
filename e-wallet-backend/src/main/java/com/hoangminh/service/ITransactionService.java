package com.hoangminh.service;

import java.util.List;
import java.util.Map;

import com.hoangminh.dto.MoneyTranferRequest;
import com.hoangminh.dto.TopupRequest;
import com.hoangminh.dto.TransactionDTO;
import com.hoangminh.dto.chartByCategories;
import com.hoangminh.dto.paymentRequest;
import com.hoangminh.entity.Transaction;

public interface ITransactionService {
	List<Transaction> geTransactions();
	Map<String, Object> CashinCashout(String cardnumber,TopupRequest topupRequest);
	
	Map<String, Object> getAllTransactionsPage(long id,int page);
	
	String tranferMoney(MoneyTranferRequest moneyTranferRequest);
	
	List<TransactionDTO> chartjs();
	
	List<chartByCategories> chartByCategories();
	
	String payBill(paymentRequest transactionDTO);
}
