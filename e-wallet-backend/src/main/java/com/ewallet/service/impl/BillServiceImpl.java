package com.ewallet.service.impl;

import java.util.List;

import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewallet.converter.BillConverter;
import com.ewallet.dto.BillInfoDTO;
import com.ewallet.entity.BillInfo;
import com.ewallet.entity.Category;
import com.ewallet.exception.NotFoundException;
import com.ewallet.repository.BillinfoRepository;
import com.ewallet.repository.CategoryRepository;
import com.ewallet.service.IBillService;

@Service
public class BillServiceImpl implements IBillService{
	
	@Autowired
	private BillinfoRepository billinfoRepository;
	
	@Autowired
	private BillConverter billConverter;
	@Autowired
	private CategoryRepository categoryRepository;
	@Override
	public List<BillInfo> getBills() {
		// TODO Auto-generated method stub
		return billinfoRepository.findAll();
	}

	@Override
	public BillInfo creBillInfo(BillInfoDTO billInfoDTO) {
		BillInfo billInfo = billConverter.dtoToEntity(billInfoDTO);
		// TODO Auto-generated method stub
		billinfoRepository.save(billInfo);
		return billInfo;
	}

	@Override
	public BillInfo upBillInfo(BillInfoDTO billInfoDTO, long id) {
		BillInfo billInfo = billinfoRepository.findById(id).orElseThrow(()-> new NotFoundException("Bill not found"));
		BillInfo billInfo2 = billConverter.toEntity(billInfoDTO, billInfo);
		billinfoRepository.save(billInfo2);
		return billInfo2;
	}

	@Override
	public void delBill(long id) {
		// TODO Auto-generated method stub
		billinfoRepository.deleteById(id);
		
	}

	@Override
	public BillInfo findBillInfoByCustomerCode(String customercode,long cateid) {
		// TODO Auto-generated method stub
		categoryRepository.findById(cateid).orElseThrow(() -> new NotFoundException("Bill with this id cant not be found! please try again"));
		BillInfo billInfo = billinfoRepository.findBycustomercode(customercode, cateid).orElseThrow(() -> new NotFoundException("Bill with this id cant not be found! please try again"));
		if(billInfo.getStatus() == true) {
			throw new NotFoundException("Bill with this id cant not be found! please try again");
		}
		return billInfo;
	}

	@Override
	public String setactivebill(long id) {
		BillInfo billInfo = billinfoRepository.findById(id).orElseThrow();
		billInfo.setStatus(!billInfo.getStatus());
		// TODO Auto-generated method stub
		billinfoRepository.save(billInfo);
		return "set active";
	}

}
