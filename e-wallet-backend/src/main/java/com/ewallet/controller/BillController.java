package com.ewallet.controller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ewallet.dto.BillInfoDTO;
import com.ewallet.entity.BillInfo;
import com.ewallet.exception.BadRequestException;
import com.ewallet.exception.NotFoundException;
import com.ewallet.exception.UnknownException;
import com.ewallet.service.IBillService;

@RestController
@CrossOrigin
@RequestMapping("/api/bill")
public class BillController {
	private static Logger logger = Logger.getLogger(TransactionController.class);

	@Autowired
	private IBillService billService;
	
	@GetMapping
	public ResponseEntity<List<BillInfo>> listBills(){
		List<BillInfo> billInfos = billService.getBills();
		return new ResponseEntity<List<BillInfo>>(billInfos,HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<BillInfo> creBill(@ModelAttribute BillInfoDTO billInfoDTO){
		//return billInfoDTO;
		return new ResponseEntity<BillInfo>(billService.creBillInfo(billInfoDTO),HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<BillInfo> updateBill(@ModelAttribute BillInfoDTO billInfoDTO,@PathVariable long id){
		return new ResponseEntity<BillInfo>(billService.upBillInfo(billInfoDTO, id),HttpStatus.OK);
				
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleBill(@PathVariable long id){
		billService.delBill(id);
		return new ResponseEntity<String>("delete successfullt", HttpStatus.OK);
	}
	
	@GetMapping("/{customercode}/category/{cateid}")
	public ResponseEntity<BillInfo> getBillinfoByCustomerCode(@PathVariable String customercode,@PathVariable long cateid){
		try{
            logger.info("Success!");
            return new ResponseEntity<BillInfo>(billService.findBillInfoByCustomerCode(customercode,cateid),HttpStatus.OK);
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
	
	@PostMapping("/setactive/{id}")
	public String setactivebill(@PathVariable long id){
		
		return billService.setactivebill(id);
	}
}
