package com.hoangminh.dto;

import java.math.BigDecimal;

import javax.persistence.Column;

public class BillInfoDTO extends BaseDTO{
private String billCode;
	
	private String customercode;
	
	private String customerName;
	private String billcode;
	public String getBillcode() {
		return billcode;
	}

	public void setBillcode(String billcode) {
		this.billcode = billcode;
	}

	private String PhoneNumber;
	
	@Column(name = "address")
	private String address;
	private String meterNumber;
	private BigDecimal amount;
	
	private Boolean status;
	
	private long billTypeId;

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}

	public String getCustomercode() {
		return customercode;
	}

	public void setCustomercode(String customercode) {
		this.customercode = customercode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMeterNumber() {
		return meterNumber;
	}

	public void setMeterNumber(String meterNumber) {
		this.meterNumber = meterNumber;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public long getBillTypeId() {
		return billTypeId;
	}

	public void setBillTypeId(long billTypeId) {
		this.billTypeId = billTypeId;
	}
	
	
}
