package com.ewallet.converter;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.ewallet.dto.BankDto;
import com.ewallet.entity.Bank;
import com.ewallet.helper.upload;

@Component
public class BankConverter {
	public Bank toEntity(BankDto bankDto) throws IOException {
		Bank bank = new Bank();
		bank.setName(bankDto.getName());
		upload upload = new upload();
		bank.setUrl(upload.uploadImage(bankDto.getUrl()));
		return bank;
	}
	
	public Bank DtoToEntity(Bank bank, BankDto bankDto) throws IOException {
		bank.setName(bankDto.getName());
		bank.setUrl(bank.getUrl());
		if(bankDto.getUrl() != null) {
			upload upload = new upload();
			bank.setUrl(upload.uploadImage(bankDto.getUrl()));
		}
		
		return bank;
	}
}
