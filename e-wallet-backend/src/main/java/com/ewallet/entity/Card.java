package com.ewallet.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;

import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cards")
public class Card extends BaseEntity{
	
	private String cardnumber;
	private String securitycode;
	private BigDecimal balance;
	
	private Long bank_id;
	
	private Date expires_date;
	
	private Date validfrom_date;
	
	private String customerId;
	
	private String description;
	
	
	
	@JsonIgnoreProperties("card")
	@OneToOne(mappedBy = "card", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Wallet wallet;
	
}
