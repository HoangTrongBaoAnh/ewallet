package com.ewallet.dto;

import java.math.BigDecimal;


public class MoneyTranferRequest {
	private String to;
	
	private BigDecimal amount;
	public Long TransactionCategoryId;
	
	private String description;

	public String TransactionCategory;
	
	public String getTransactionCategory() {
		return TransactionCategory;
	}

	public void setTransactionCategory(String transactionCategory) {
		TransactionCategory = transactionCategory;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	
	public Long getTransactionCategoryId() {
		return TransactionCategoryId;
	}

	public void setTransactionCategoryId(Long transactionCategoryId) {
		TransactionCategoryId = transactionCategoryId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
