package com.ewallet.converter;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.ewallet.dto.CardDTO;
import com.ewallet.entity.Card;

@Component
public class CardConverter {
	public Card cardDtoToEntity(CardDTO cardDTO) {
		Card card = new Card();
		card.setBalance(cardDTO.getBalance());
		card.setSecuritycode(cardDTO.getSecuritycode());
		card.setCardnumber(cardDTO.getCardnumber());
		card.setBank_id(cardDTO.getBank_id());
		if(cardDTO.getCustomerId() != null) {
			card.setCustomerId(cardDTO.getCustomerId());
		}
		
		LocalDate date = LocalDate.now();
		card.setValidfrom_date(java.sql.Date.valueOf(date));
		date = date.plusYears(2); //here n is no.of year you want to increase
		card.setExpires_date(java.sql.Date.valueOf(date));
		return card;
	}
}
