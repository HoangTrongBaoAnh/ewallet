package com.hoangminh.dto;

import org.springframework.web.multipart.MultipartFile;

public class categoryDTO {
private Long id;
	
	private String name;
	
	private MultipartFile url;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MultipartFile getUrl() {
		return url;
	}

	public void setUrl(MultipartFile url) {
		this.url = url;
	}

	

	
	
	
}
