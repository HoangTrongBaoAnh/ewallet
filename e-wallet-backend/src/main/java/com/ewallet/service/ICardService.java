package com.ewallet.service;

import java.util.List;

import com.ewallet.dto.CardDTO;
import com.ewallet.entity.Card;

public interface ICardService {
	List<Card> findAllCards();
	Card createCard(CardDTO cardDTO);
	void deleteCard(long id);
	
	String removeFromWallet(String cardNumber);
	
	String setActive(String cardNumber);
}
