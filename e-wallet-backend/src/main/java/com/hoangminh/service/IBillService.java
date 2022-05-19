package com.hoangminh.service;

import java.util.List;

import com.hoangminh.dto.BillInfoDTO;
import com.hoangminh.entity.BillInfo;

public interface IBillService {
	List<BillInfo> getBills();
	
	BillInfo creBillInfo(BillInfoDTO billInfoDTO);
	
	BillInfo upBillInfo(BillInfoDTO billInfoDTO, long id);
	
	void delBill(long id);
	
	BillInfo findBillInfoByCustomerCode(String customercode,long cateid);
	
	String setactivebill(long id);
}
