package com.ewallet.service;

import java.util.List;
import java.util.Map;

import com.ewallet.dto.MoneyTranferRequest;
import com.ewallet.dto.TopupRequest;
import com.ewallet.dto.TransactionDTO;
import com.ewallet.dto.chartByCategories;
import com.ewallet.dto.paymentRequest;
import com.ewallet.entity.Transaction;

public interface ITransactionService {
	List<Transaction> geTransactions();
	Map<String, Object> CashinCashout(String cardnumber,TopupRequest topupRequest);
	
	Map<String, Object> getAllTransactionsPage(long id,int page);
	
	String tranferMoney(MoneyTranferRequest moneyTranferRequest);
	
	List<TransactionDTO> chartjs();
	
	List<chartByCategories> chartByCategories();
	
	String payBill(paymentRequest transactionDTO);
}
