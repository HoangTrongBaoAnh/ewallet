package com.hoangminh.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoangminh.dto.CardDTO;
import com.hoangminh.entity.Card;
import com.hoangminh.exception.UnknownException;
import com.hoangminh.service.ICardService;


@CrossOrigin
@RestController
@RequestMapping("/api/card")
public class CardController {
	private static Logger logger = Logger.getLogger(TransactionController.class);
	@Autowired
	private ICardService cardService;
	
	@GetMapping
	public ResponseEntity<List<Card>> getallCards(){
		return new ResponseEntity<List<Card>>(cardService.findAllCards(),HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Card> creCard(@ModelAttribute CardDTO cardDTO){
		return new ResponseEntity<Card>(cardService.createCard(cardDTO),HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delCard(@PathVariable long id){
		try{
            logger.info("Success!");
            cardService.deleteCard(id);
            return new ResponseEntity<String>("Card with "+ id + " has been deleted successfully",HttpStatus.OK);
        } catch (Exception exc) {
            logger.error(exc);
            throw new UnknownException("Unknown Error");
        }
	}
	
	@PostMapping("/remove/wallet/{cardnumber}")
	public ResponseEntity<String> removefromwallet(@PathVariable String cardnumber){
		try{
            logger.info("Success!");
            return new ResponseEntity<String>(cardService.removeFromWallet(cardnumber),HttpStatus.OK);
        } catch (Exception exc) {
            logger.error(exc);
            throw new UnknownException("Unknown Error");
        }
		
	}
	
	@PostMapping("wallet/{cardnumber}")
	public ResponseEntity<String> setactive(@PathVariable String cardnumber){
		try{
            logger.info("Success!");
            return new ResponseEntity<String>(cardService.setActive(cardnumber),HttpStatus.OK);
        } catch (Exception exc) {
            logger.error(exc);
            throw new UnknownException("Unknown Error");
        }
		
		
	}
}
