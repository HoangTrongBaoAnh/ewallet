package com.hoangminh.dto;

import java.math.BigDecimal;

public class TopupRequest {

	private String to;
	
	private BigDecimal amount;
	
	private String transactionCategory;
	
	
	private String description;

	public String getTo() {
		return to;
	}

	public String getTransactionCategory() {
		return transactionCategory;
	}

	public void setTransactionCategory(String transactionCategory) {
		this.transactionCategory = transactionCategory;
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


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TopupRequest(String to, BigDecimal amount, Long transactionCategory_id, String description) {
		super();
		this.to = to;
		this.amount = amount;
		this.description = description;
	}
	
	
	
	
	
}
