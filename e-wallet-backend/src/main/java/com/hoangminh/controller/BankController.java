package com.hoangminh.controller;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.fasterxml.jackson.core.JsonProcessingException;

import com.hoangminh.dto.BankDto;
import com.hoangminh.dto.CardDTO;

import com.hoangminh.dto.OCBClientRequest;
import com.hoangminh.entity.Bank;
import com.hoangminh.entity.Card;
import com.hoangminh.exception.ApiException;
import com.hoangminh.exception.BadRequestException;
import com.hoangminh.exception.UnknownException;

import com.hoangminh.service.IBankService;

import org.apache.log4j.Logger;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/bank")
public class BankController {
	
	private static Logger logger = Logger.getLogger(CustomerController.class);
	
	@Autowired
	private IBankService bankService;
	
	@GetMapping
	public ResponseEntity<List<Bank>> getbanks(){
		try {
            logger.info("Success!");
            return new ResponseEntity<List<Bank>>(bankService.getBanks(),HttpStatus.OK);
        } catch (Exception exc) {
            logger.error(exc);
            throw new UnknownException("Unknown Error");
        }
    }
	
	@PostMapping
	public ResponseEntity<Bank>creBank(@ModelAttribute BankDto bankDto) throws IOException{
		try {
            logger.info("Success!");
            return new ResponseEntity<Bank>(bankService.creBank(bankDto),HttpStatus.CREATED); 
		} catch (Exception exc) {
            logger.error(exc);
            throw new UnknownException("Unknown Error");
        }
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Bank> upbank(@ModelAttribute BankDto bankDto, @PathVariable long id) throws IOException{
		try {
            logger.info("Success!");
            return new ResponseEntity<Bank> (bankService.upBank(bankDto, id),HttpStatus.OK);
        } catch (Exception exc) {
            logger.error(exc);
            throw new UnknownException("Unknown Error");
        }	
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> upbank(@PathVariable long id){
		try {
            logger.info("Success!");
            bankService.delBank(id);
    		return new ResponseEntity<String> ("Delete succesffully",HttpStatus.OK);
        } catch (Exception exc) {
            logger.error(exc);
            throw new UnknownException("Unknown Error");
        }
	}
	
	@GetMapping("/list")
	public String getbank() throws InvalidKeyException, JsonProcessingException, NoSuchAlgorithmException{
		try {
            logger.info("Success!");
            return bankService.getBankListInVN();
        } catch (Exception exc) {
            logger.error(exc);
            throw new UnknownException("Unknown Error");
        }
		
    }
	
	@PostMapping("/topup")
	public ResponseEntity<String> topup() throws InvalidKeyException, JsonProcessingException, NoSuchAlgorithmException{
        return bankService.topUpBaoKim();
    }  
	
	@GetMapping("/token")
	public String gettoken() throws InvalidKeyException, JsonProcessingException, NoSuchAlgorithmException {
		return bankService.baoKimToken();
	}
	
	@PostMapping("/ocbtoken")
	public String getOCBtoken() throws IOException{
		try {
            logger.info("Success!");
    		return bankService.getOCBtoken();
        } catch (Exception exc) {
            logger.error(exc);
            throw new UnknownException("Unknown Error");
        }
	}
	
	@GetMapping("/getsignature/{signature}/cardnumber/{cardnumber}")
	public String getsignature(@PathVariable String signature, @PathVariable String cardnumber) throws IOException {
		try {
            logger.info("Success!");
    		return bankService.getsignature(signature, cardnumber);
        } catch (Exception exc) {
            logger.error(exc);
            throw new UnknownException("Unknown Error");
        }
	}
	
	@PostMapping("/getaccount/")
	public String getaccount(@org.springframework.web.bind.annotation.RequestBody OCBClientRequest ocbClientRequest) throws IOException {
		try {
            logger.info("Success!");
    		return bankService.getaccount(ocbClientRequest);
        } catch (Exception exc) {
            logger.error(exc);
            throw new UnknownException("Unknown Error");
        }
	}
	
	@PostMapping("/addocbaccount")
	public ResponseEntity<Card> StoreOcbAccToDatabase(CardDTO cardDTO){
		try {
            logger.info("Success!");
    		return new ResponseEntity<Card>(bankService.StoreOcbAccToDatabase(cardDTO),HttpStatus.OK);
        } catch (Exception exc) {
            logger.error(exc);
            throw new UnknownException("Unknown Error");
        }
		
	}
	
	@GetMapping("/mail/{cardnumber}")
	public String sentmail(@PathVariable String cardnumber)  {
		try {
            logger.info("Success!");
    		return bankService.sentmail(cardnumber);
        } catch (BadRequestException exc) {
            logger.error(exc.getMessage());
            throw new BadRequestException(exc.getMessage());
        }catch (Exception exc) {
            logger.error(exc);
            throw new UnknownException("Unknown Error");
        }
		
	}
	
	@GetMapping("/sms/{cardnumber}")
	public String sentSMS(@PathVariable String cardnumber) {
		try {
            logger.info("Success!");
            return bankService.sendSMS(cardnumber);
        } catch (com.twilio.exception.ApiException exc) {
            logger.error(exc.getMessage());
            throw new BadRequestException(exc.getMessage());
        }catch (BadRequestException exc) {
            logger.error(exc.getMessage());
            throw new BadRequestException(exc.getMessage());
        }catch (Exception exc) {
            logger.error(exc);
            throw new UnknownException("Unknown Error");
        }
		
	}
	
}
