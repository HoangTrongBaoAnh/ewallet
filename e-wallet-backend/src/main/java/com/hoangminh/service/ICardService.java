package com.hoangminh.service;

import java.util.List;

import com.hoangminh.dto.CardDTO;
import com.hoangminh.entity.Card;

public interface ICardService {
	List<Card> findAllCards();
	Card createCard(CardDTO cardDTO);
	void deleteCard(long id);
	
	String removeFromWallet(String cardNumber);
	
	String setActive(String cardNumber);
}
