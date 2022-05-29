package com.ewallet.service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.ewallet.dto.BankDto;
import com.ewallet.dto.CardDTO;
import com.ewallet.dto.OCBClientRequest;
import com.ewallet.entity.Bank;
import com.ewallet.entity.Card;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface IBankService {
	Bank creBank(BankDto bankDto) throws IOException;
	List<Bank> getBanks();
	
	Bank upBank(BankDto bankDto,long id) throws IOException;
	
	void delBank(long id);
	
	String getBankListInVN() throws InvalidKeyException, JsonProcessingException, NoSuchAlgorithmException;
	
	String baoKimToken() throws InvalidKeyException, JsonProcessingException, NoSuchAlgorithmException;
	
	ResponseEntity<String> topUpBaoKim() throws InvalidKeyException, JsonProcessingException, NoSuchAlgorithmException;
	
	String getOCBtoken() throws IOException;
	
	String getsignature(String signature, String cardnumber) throws IOException;
	
	String getaccount( OCBClientRequest ocbClientRequest) throws IOException;
	
	Card StoreOcbAccToDatabase(CardDTO cardDTO);
	
	String sentmail(@PathVariable String cardnumber);
	
	String sendSMS(String cardnumber);
}
