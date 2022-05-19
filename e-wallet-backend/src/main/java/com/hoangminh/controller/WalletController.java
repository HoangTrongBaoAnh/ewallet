package com.hoangminh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoangminh.dto.AddCardToWalletRequest;
import com.hoangminh.entity.Wallet;
import com.hoangminh.service.IWalletService;

@CrossOrigin
@RestController
@RequestMapping("/api/wallet")
public class WalletController {
	@Autowired
	private IWalletService walletService;
	
	@GetMapping
	public ResponseEntity<List<Wallet>> getAllWallet(){
		return new ResponseEntity<List<Wallet>>(walletService.getAllWallets(),HttpStatus.OK);
	}
	
	@PostMapping("/addcardtowallet")
	public ResponseEntity<Wallet> addToWallet(@ModelAttribute AddCardToWalletRequest addCardToWalletRequest){
		return new ResponseEntity<Wallet>(walletService.addCardToWallet(addCardToWalletRequest),HttpStatus.CREATED);
	}
}
