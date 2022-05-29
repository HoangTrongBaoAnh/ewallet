package com.ewallet.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.ewallet.converter.CardConverter;
import com.ewallet.dto.CardDTO;
import com.ewallet.entity.Card;
import com.ewallet.entity.CustomerEntity;
import com.ewallet.entity.Wallet;
import com.ewallet.exception.BadRequestException;
import com.ewallet.exception.NotFoundException;
import com.ewallet.exception.ResourceNotFoundException;
import com.ewallet.repository.CardRepository;
import com.ewallet.repository.WalletRepository;
import com.ewallet.service.ICardService;

@Service
public class CardServiceImpl implements ICardService{

	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private WalletRepository walletRepository;
	
	@Autowired
	private CardConverter cardConverter;
	
	@Override
	public List<Card> findAllCards() {
		// TODO Auto-generated method stub
		return cardRepository.findAll();
	}

	@Override
	public Card createCard(CardDTO cardDTO) {
		if(cardRepository.findBycardnumber(cardDTO.getCardnumber()) == null) {
			Card card = cardConverter.cardDtoToEntity(cardDTO);
			return cardRepository.save(card);
			// TODO Auto-generated method stub
		}
		
		throw new BadRequestException("Card number with this id already in the system");
		
	}

	@Override
	public void deleteCard(long id) {
		Card card = cardRepository.findById(id).orElseThrow(()-> new NotFoundException("card with this id not found"));
		Wallet wallet = walletRepository.findBycardNumber(card.getCardnumber());
		if(wallet != null) {
			walletRepository.deletebycardnumber(card.getCardnumber());
		}
		cardRepository.delete(card);
		
	}

	@Override
	public String removeFromWallet(String cardNumber) {
		// TODO Auto-generated method stub
		Wallet wallet = walletRepository.findBycardNumber(cardNumber);

		if(wallet == null) {
			throw new BadRequestException("wallet not found");
		}
		CustomerEntity user = wallet.getCustomerEntity();
		boolean active = wallet.getactive();
		if(active==false) {
			walletRepository.deletebycardnumber(cardNumber);
			return "delete succesfully";
		}
		else {
			List<Wallet> wallets = walletRepository.findAllByUserId(user.getId());
			for(Wallet wallet2 : wallets) {
				if(wallet2 != wallet) {
					wallet2.setActive(true);
					walletRepository.save(wallet2);
					break;
				}
			}
			walletRepository.deletebycardnumber(cardNumber);
			return "delete succesfully";
		}
		
	}

	@Override
	public String setActive(String cardNumber) {
		// TODO Auto-generated method stub
		Wallet wallet = walletRepository.findBycardNumber(cardNumber);

		if(wallet == null) {
			throw new NotFoundException("wallet not found");
		}
		CustomerEntity user = wallet.getCustomerEntity();
		boolean active = wallet.getactive();
		if(active==false) {
			List<Wallet> wallets = walletRepository.findAllByUserId(user.getId());
			for(Wallet wallet2 : wallets) {
				if(wallet2.getactive() == true) {
					wallet2.setActive(false);
					walletRepository.save(wallet2);
					break;
				}
				
			}
			wallet.setActive(true);
			walletRepository.save(wallet);
			return "set actice";
		}
		else {
			wallet.setActive(false);
			walletRepository.save(wallet);
			return "set actice";
		}
	}

}
