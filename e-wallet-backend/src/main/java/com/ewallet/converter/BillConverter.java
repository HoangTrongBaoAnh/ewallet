package com.ewallet.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ewallet.dto.BillInfoDTO;
import com.ewallet.entity.BillInfo;
import com.ewallet.entity.Category;
import com.ewallet.exception.NotFoundException;
import com.ewallet.repository.CategoryRepository;

@Component
public class BillConverter {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public BillInfo dtoToEntity(BillInfoDTO billInfoDTO) {
		BillInfo billInfo = new BillInfo();
		billInfo.setBillCode(billInfoDTO.getBillCode());
		billInfo.setAddress(billInfoDTO.getAddress());
		billInfo.setCustomercode(billInfoDTO.getCustomercode());
		billInfo.setPhoneNumber(billInfoDTO.getPhoneNumber());
		Category category = categoryRepository.findById(billInfoDTO.getBillTypeId()).orElseThrow(() -> new NotFoundException("cate id not found"));
		
		billInfo.setCategory(category);
		billInfo.setAmount(billInfoDTO.getAmount());
		billInfo.setBillCode(billInfoDTO.getBillcode());
		billInfo.setCustomerName(billInfoDTO.getCustomerName());
		billInfo.setMeterNumber(billInfoDTO.getMeterNumber());
		return billInfo;
	}
	
	public BillInfo toEntity(BillInfoDTO billInfoDTO, BillInfo billInfo ) {
		
		billInfo.setBillCode(billInfoDTO.getBillCode());
		billInfo.setAddress(billInfoDTO.getAddress());
		billInfo.setCustomercode(billInfoDTO.getCustomercode());
		billInfo.setPhoneNumber(billInfoDTO.getPhoneNumber());
		Category category = categoryRepository.findById(billInfoDTO.getBillTypeId()).orElseThrow(() -> new NotFoundException("cate id not found"));
		
		billInfo.setCategory(category);
		
		billInfo.setCustomerName(billInfoDTO.getCustomerName());
		billInfo.setMeterNumber(billInfoDTO.getMeterNumber());
		
		return billInfo;
	}
}
