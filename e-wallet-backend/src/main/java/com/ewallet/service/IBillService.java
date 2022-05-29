package com.ewallet.service;

import java.util.List;

import com.ewallet.dto.BillInfoDTO;
import com.ewallet.entity.BillInfo;

public interface IBillService {
	List<BillInfo> getBills();
	
	BillInfo creBillInfo(BillInfoDTO billInfoDTO);
	
	BillInfo upBillInfo(BillInfoDTO billInfoDTO, long id);
	
	void delBill(long id);
	
	BillInfo findBillInfoByCustomerCode(String customercode,long cateid);
	
	String setactivebill(long id);
}
