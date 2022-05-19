package com.hoangminh.entity;


import java.math.BigDecimal;

import javax.persistence.CascadeType;
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


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity{
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
	
//	@JsonFormat(pattern = "yyyy/MM/dd")
//	@Column(name = "transaction_date")
//	private Date transactionDate;
	
	@Column(name = "froms")
	private String from;
	
	@Column(name = "tos")
	private String to;
	
	@Column(name = "amount")
	private BigDecimal amount;
	
	private String description;
	
	@JsonIgnoreProperties("customer")
    @JsonProperty("customer_id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@ManyToOne(fetch = FetchType.EAGER, optional = false,cascade = CascadeType.ALL)
	@JoinColumn(name = "customer_id", referencedColumnName = "id",nullable = true)
	private CustomerEntity customer;
	
	@JsonIgnoreProperties("transactionCategory")
    @JsonProperty("transaction_category_id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@ManyToOne(fetch = FetchType.EAGER, optional = false,cascade = CascadeType.ALL)
	@JoinColumn(name = "trans_category_id", referencedColumnName = "id",nullable = true)
	private TransactionCategory TransactionCategory;
}
