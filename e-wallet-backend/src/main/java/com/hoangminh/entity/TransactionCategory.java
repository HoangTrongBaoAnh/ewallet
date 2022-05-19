package com.hoangminh.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import javax.persistence.OneToMany;
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
@Table(name = "transaction_categories")
public class TransactionCategory extends BaseEntity{
	
	@Column(name = "name")
	private String name;
	
	@JsonIgnoreProperties("transactionCategory")
	@OneToMany(mappedBy = "TransactionCategory",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private List<Transaction> transactions  = new ArrayList<>();
}
