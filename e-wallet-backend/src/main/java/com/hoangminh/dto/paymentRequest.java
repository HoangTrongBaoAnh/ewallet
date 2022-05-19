package com.hoangminh.dto;

import java.math.BigDecimal;

public class paymentRequest {
private String to;
	
	private BigDecimal amount;
	
	public String TransactionCategory;
	
	public long billInfoId;
	
	private String description;

	public long getBillInfoId() {
		return billInfoId;
	}

	public void setBillInfoId(long billInfoId) {
		this.billInfoId = billInfoId;
	}

	public String getTo() {
		return to;
	}

	public String getTransactionCategory() {
		return TransactionCategory;
	}

	public void setTransactionCategory(String transactionCategory) {
		this.TransactionCategory = transactionCategory;
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
}
