package com.ewallet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ewallet.dto.AddCardToWalletRequest;
import com.ewallet.entity.CustomerEntity;
import com.ewallet.entity.Wallet;
import com.ewallet.exception.BadRequestException;
import com.ewallet.exception.NotFoundException;
import com.ewallet.exception.ResourceNotFoundException;
import com.ewallet.repository.CardRepository;
import com.ewallet.repository.CustomerRepository;
import com.ewallet.repository.WalletRepository;
import com.ewallet.service.IWalletService;
import com.ewallet.service.OTPService;

@Service
public class WalletServiceImpl implements IWalletService{

	@Autowired
	private WalletRepository walletRepository;
	
	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private CustomerRepository userRepository;
	
	@Autowired
	private OTPService otpService;
	
	@Override
	public List<Wallet> getAllWallets() {
		// TODO Auto-generated method stub
		UserDetails userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomerEntity user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->new NotFoundException("User with the name" + userDetails.getUsername() + " not found"));
		
		return walletRepository.findAllByUserId(user.getId());
	}

	@Override
	public Wallet addCardToWallet(AddCardToWalletRequest addCardToWalletRequest) {
		int otpnum = addCardToWalletRequest.getOtp();
		String cardnumber = addCardToWalletRequest.getCardnumber();
		//final String SUCCESS = "Entered OTP is valid";
		
		final String FAIL = "Entered Otp is NOT valid. Please Retry!";
	
		//Validate the OTP
		if(otpnum >= 0){
			int serverOtp = otpService.getOtp(cardnumber);
		
			if(serverOtp > 0){
				//if OTP is correct then add card to wallet
				if(otpnum == serverOtp){
					otpService.clearOTP(cardnumber);
					com.ewallet.entity.Card card = cardRepository.findByIdAndcardnumber(addCardToWalletRequest.getBankId(), addCardToWalletRequest.getCardnumber());
					if(card == null) {
						throw new ResourceNotFoundException("Card", "cardnumber", addCardToWalletRequest.getCardnumber()); 
					}
					CustomerEntity user = userRepository.findById(addCardToWalletRequest.getUserId()).orElseThrow(()-> new ResourceNotFoundException("user_id", "id", addCardToWalletRequest.getUserId()));
					Wallet wallet2 = new Wallet();
					wallet2.setCard(card);
					
					List<Wallet> wallets = walletRepository.findAllByUserId(addCardToWalletRequest.getUserId());
					
					for(Wallet wallet : wallets) {
						if(wallet.getCardNumber().equals(addCardToWalletRequest.getCardnumber())) {
							throw new BadRequestException("This card already in ur wallet");
						}
					}
					
					if(wallets.size() > 0) {
						wallet2.setActive(false);
					}
					else {
						wallet2.setActive(true);
					}
					
					//wallet2.setActive(false);
					wallet2.setBalance(card.getBalance());
					wallet2.setCardNumber(card.getCardnumber());
					wallet2.setCustomerEntity(user);
					walletRepository.save(wallet2);
					return wallet2;
					//return ("Entered OTP is valid");
				}
				else{
					throw new BadRequestException(FAIL);
				}
			}else {
				throw new BadRequestException(FAIL);
			}
		}else {
			throw new BadRequestException(FAIL);
			//return FAIL;
		}
		
	}
	
}
