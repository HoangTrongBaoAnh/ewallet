package com.hoangminh.entity;


import javax.persistence.Entity;

import javax.persistence.Table;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "banks")
public class Bank extends BaseEntity{
	
	private String Name;
	private String url;
	
	
}
