package com.hoangminh.controller;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hoangminh.dto.MoneyTranferRequest;
import com.hoangminh.dto.TopupRequest;
import com.hoangminh.dto.TransactionDTO;
import com.hoangminh.dto.chartByCategories;
import com.hoangminh.dto.paymentRequest;
import com.hoangminh.entity.Transaction;
import com.hoangminh.exception.BadRequestException;
import com.hoangminh.exception.NotFoundException;
import com.hoangminh.exception.UnknownException;
import com.hoangminh.service.ITransactionService;

@CrossOrigin
@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

	private static Logger logger = Logger.getLogger(TransactionController.class);

    @Autowired
    private ITransactionService transactionService;
    
	@GetMapping
	public ResponseEntity<List<Transaction>> getAllTransactionCategory(){
		try{
            logger.info("Success!");
            return new ResponseEntity<List<Transaction>>(transactionService.geTransactions(),HttpStatus.OK);
        } catch (Exception exc) {
            logger.error(exc);
            throw new UnknownException("Unknown Error");
        }
	}
	
	@PostMapping("/cashin/{cardnumber}")
	public ResponseEntity<Map<String, Object>> cashin(@RequestBody TopupRequest topupRequest, @PathVariable String cardnumber){
		try{
            logger.info("Success!");
            return new ResponseEntity<Map<String, Object>>(transactionService.CashinCashout(cardnumber, topupRequest),HttpStatus.OK);
        } catch (NotFoundException exc) {
            logger.error(exc);
            throw new BadRequestException(exc.getMessage());
        } catch (BadRequestException exc) {
            logger.error(exc);
            throw new BadRequestException(exc.getMessage());
        }catch (Exception exc) {
            logger.error(exc);
            throw new UnknownException(exc.getMessage());
        }
	}
	
	@GetMapping("/alltransaction/{id}")
	public ResponseEntity<Map<String, Object>> gettransactions(@PathVariable long id,@RequestParam(defaultValue = "0") int page){
		try{
            logger.info("Success!");
            return new ResponseEntity<Map<String, Object>>(transactionService.getAllTransactionsPage(id, page),HttpStatus.OK);
        } catch (Exception exc) {
            logger.error(exc);
            throw new UnknownException("Unknown Error");
        }		
	}
	
	@PostMapping
	public ResponseEntity<String> moneyTranfer(@RequestBody MoneyTranferRequest moneyTranferRequest){
		try{
            logger.info("Success!");
            return new ResponseEntity<String>(transactionService.tranferMoney(moneyTranferRequest),HttpStatus.OK);
        }catch (NotFoundException exc) {
            logger.error(exc);
            throw new NotFoundException(exc.getMessage());
        } catch (BadRequestException exc) {
            logger.error(exc);
            throw new BadRequestException(exc.getMessage());
        } catch (Exception exc) {
            logger.error(exc);
            throw new UnknownException("Unknown Error");
        }
	}
	
	@PostMapping("/payment")
	public String payment(@RequestBody paymentRequest transactionDTO){
		try{
            logger.info("Success!");
            return transactionService.payBill(transactionDTO);
		}catch (NotFoundException exc) {
            logger.error(exc);
            throw new NotFoundException(exc.getMessage());
        } catch (BadRequestException exc) {
            logger.error(exc);
            throw new BadRequestException(exc.getMessage());
        } catch (Exception exc) {
            logger.error(exc);
            throw new UnknownException("Unknown Error");
        }
		
	}
	
	@PostMapping("/chartjs")
	public List<TransactionDTO> chartjs(){
		
		return transactionService.chartjs();
	}
	
	@PostMapping("/chartbycategories")
	public List<chartByCategories> chartbycategories(){
		
		return transactionService.chartByCategories();
	}
}
