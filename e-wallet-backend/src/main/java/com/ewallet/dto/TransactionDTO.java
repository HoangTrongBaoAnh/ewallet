package com.ewallet.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;




import com.fasterxml.jackson.annotation.JsonFormat;

public interface TransactionDTO {
	
	public Long getid();
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getcreated_date();
	
	
	public String getfroms();
	
	
	public String gettos();
	
	
	public BigDecimal getamount();
	
	public String getdescription();
	
	public Long gettrans_category_id();
	
	public String getcategory();
	
	public String gettranscmonth();
	
	
}
