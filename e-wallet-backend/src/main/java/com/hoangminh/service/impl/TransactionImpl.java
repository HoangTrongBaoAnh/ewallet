package com.hoangminh.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.hoangminh.dto.TransactionDTO;
import com.hoangminh.dto.paymentRequest;
import com.hoangminh.converter.TransactionConverter;
import com.hoangminh.dto.MoneyTranferRequest;
import com.hoangminh.dto.TopupRequest;
import com.hoangminh.entity.CustomerEntity;
import com.hoangminh.entity.Transaction;
import com.hoangminh.repository.CardRepository;
import com.hoangminh.repository.CustomerRepository;
import com.hoangminh.repository.TransactionCategoryRepository;
import com.hoangminh.repository.TransactionRepository;
import com.hoangminh.repository.WalletRepository;
import com.hoangminh.service.IBillService;
import com.hoangminh.service.ITransactionService;
import com.hoangminh.exception.BadRequestException;
import com.hoangminh.exception.NotFoundException;
import com.hoangminh.entity.Card;
import com.hoangminh.entity.TransactionCategory;
import com.hoangminh.entity.Wallet;


@Service
public class TransactionImpl implements ITransactionService{

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private WalletRepository walletRepository;
	
	@Autowired
	private TransactionCategoryRepository transactionCategoryRepository;
	
	@Autowired
    private TransactionConverter transactionConverter;
	
	@Autowired
    private IBillService billService;
	
	@Override
	public List<Transaction> geTransactions() {
		List<Transaction> transactions = transactionRepository.findAll();
		// TODO Auto-generated method stub
		return transactions;
	}

	@Override
	public Map<String, Object> CashinCashout(String cardnumber, TopupRequest topupRequest) {
		Transaction transaction = transactionConverter.topUpRequestToEntity(topupRequest);
		CustomerEntity user = transaction.getCustomer();

		Wallet wallet = walletRepository.findBycardNumber(cardnumber);
		Card card = cardRepository.findBycardnumber(cardnumber);

		if (wallet == null || card == null) {
			throw new NotFoundException("card or id not found");
		}

		// Calculation
		TransactionCategory transactionCategory = transactionCategoryRepository
				.findByName(topupRequest.getTransactionCategory());
		if (transactionCategory.getName().equals("cashin")) {
			//code for cash in
			BigDecimal substract = wallet.getBalance().subtract(topupRequest.getAmount());
			if (substract.signum() != 1) {

				throw new BadRequestException("Your balance is not enough to process this transaction");
			}

			BigDecimal sum = user.getBalance().add(topupRequest.getAmount());
			user.setBalance(sum);
			wallet.setBalance(substract);
			card.setBalance(substract);
			
			walletRepository.save(wallet);
			customerRepository.save(user);
			cardRepository.save(card);
		} else {
			//code for cash out
			BigDecimal substract = user.getBalance().add(topupRequest.getAmount());
			
			if (substract.signum() != 1) {

				throw new BadRequestException("Your balance is not enough to process this transaction");
			}

			BigDecimal sum = user.getBalance().add(topupRequest.getAmount());
			substract = wallet.getBalance().add(topupRequest.getAmount());
			
			user.setBalance(sum);
			wallet.setBalance(substract);
			card.setBalance(substract);
			
			customerRepository.save(user);
			walletRepository.save(wallet);
			cardRepository.save(card);

		}
		transactionRepository.save(transaction);

		Map<String, Object> response = new HashMap<>();
		response.put("transaction", transaction);
		// response.put("categories", transactionCategories);
		return response;
	}

	@Override
	public Map<String, Object> getAllTransactionsPage(long id, int page) {
		try {
			List<TransactionDTO> transactionDTOs = new ArrayList<TransactionDTO>();
			Pageable paging = PageRequest.of(page, 8);
			Page<TransactionDTO> pageTransc = customerRepository.findallTransactions(id,paging);
			
			transactionDTOs = pageTransc.getContent();
			
			Map<String, Object> response = new HashMap<>();
		      response.put("transactions", transactionDTOs);
		      //response.put("categories", transactionCategories);
		      response.put("currentPage", pageTransc.getNumber());
		      response.put("totalItems", pageTransc.getTotalElements());
		      response.put("totalPages", pageTransc.getTotalPages());
		      return response;
			
		}
		catch(Exception e) {
			Map<String, Object> response = new HashMap<>();
		      response.put("err", e);
		      
		      return response;
	    }
	}

	@Override
	public String tranferMoney(MoneyTranferRequest moneyTranferRequest) {
		
		UserDetails userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomerEntity user = customerRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->new NotFoundException("User with the name" + userDetails.getUsername() + " not found"));
		
		Transaction transaction = transactionConverter.moneyTranferToEntity(moneyTranferRequest,user);
		
		if(moneyTranferRequest.getAmount().compareTo(user.getBalance()) == 1 ) {		
			throw new BadRequestException("Money tranfer exceed the balance of user");
		}
		
		
		String receiverName = transaction.getTo();	
		CustomerEntity receiver = customerRepository.findByUsername(receiverName).orElseThrow(()->new NotFoundException("User with the name" + receiverName + " not found"));

		//Calculation
		BigDecimal reveiverbalance = receiver.getBalance();
		BigDecimal sum = reveiverbalance.add(moneyTranferRequest.getAmount());
		receiver.setBalance(sum);
		
		BigDecimal substract = user.getBalance().add(transaction.getAmount());
		user.setBalance(substract);
		
		Transaction transaction2 = transactionConverter.moneyTranferToEntity(moneyTranferRequest,receiver);
		transaction2.setAmount(moneyTranferRequest.getAmount());
		customerRepository.save(user);
		customerRepository.save(receiver);
		transactionRepository.save(transaction);
		transactionRepository.save(transaction2);
		return "Money tranfer to user name " + receiverName + " successfully";
	}

	@Override
	public List<TransactionDTO> chartjs() {
		UserDetails userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomerEntity user = customerRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->new NotFoundException("User with the name" + userDetails.getUsername() + " not found"));
		
		return customerRepository.chart(user.getId(),Calendar.getInstance().get(Calendar.YEAR));
	}

	@Override
	public List<com.hoangminh.dto.chartByCategories> chartByCategories() {
		UserDetails userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomerEntity user = customerRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->new NotFoundException("User with the name" + userDetails.getUsername() + " not found"));
		
		return customerRepository.chartByCategories(user.getId(),Calendar.getInstance().get(Calendar.YEAR));
	}

	@Override
	public String payBill(paymentRequest paymentRequest) {
		UserDetails userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomerEntity user = customerRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->new NotFoundException("user not found"));
		if(paymentRequest.getAmount().compareTo(user.getBalance()) == 1 ) {		
			throw new BadRequestException("Money tranfer exceed the balance of user");
		}
		Transaction transaction = transactionConverter.paymentRequestToEntity(paymentRequest);
		
		BigDecimal substract = user.getBalance().add(transaction.getAmount());
		user.setBalance(substract);
		
		billService.setactivebill(paymentRequest.getBillInfoId());
		
		customerRepository.save(user);
		transactionRepository.save(transaction);
		
		return "pay successfully";
	}

}
