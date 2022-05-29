package com.ewallet.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BillInfos")
public class BillInfo extends BaseEntity{
	
	private String billCode;
	
	@Column(name = "customer_code")
	private String customercode;
	
	private String customerName;
	
	private String PhoneNumber;
	
	@Column(name = "address")
	private String address;
	private String meterNumber;
	private BigDecimal amount;
	
	private Boolean status;
	
	@JsonIgnoreProperties("category")
    @JsonProperty("cagetory_id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "cagetory_id", referencedColumnName = "id",nullable = true)
	private Category category;
}
