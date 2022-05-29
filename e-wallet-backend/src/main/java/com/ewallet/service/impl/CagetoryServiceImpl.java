package com.ewallet.service.impl;

import java.io.IOException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewallet.dto.categoryDTO;
import com.ewallet.entity.BillInfo;
import com.ewallet.entity.Category;
import com.ewallet.exception.ResourceNotFoundException;
import com.ewallet.helper.upload;
import com.ewallet.repository.CategoryRepository;
import com.ewallet.service.ICategoryService;

@Service
public class CagetoryServiceImpl implements ICategoryService{
	@Autowired
	private CategoryRepository categoryRepository;
	@Override
	public List<Category> findCategories() {
		// TODO Auto-generated method stub
		return categoryRepository.findAll();
	}

	@Override
	public Category creaCategory(categoryDTO category) throws IOException {
		Category category2 = new Category();
		category2.setName(category.getName());
		upload upload = new upload();
		category2.setUrl(upload.uploadImage(category.getUrl()));
		// TODO Auto-generated method stub
		return categoryRepository.save(category2);
	}

	@Override
	public void deleteCagetory(long id) {
		Category category = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Cagetory", "id", id));
		//List<BillInfo> billInfos = category.getBillInfos();
		
		for(BillInfo billInfo : category.getBillInfos()) {
			//category.removeChild(billInfo);
			billInfo.setCategory(null);
			
			
		}
		categoryRepository.delete(category);
		// TODO Auto-generated method stub
		
	}

	@Override
	public Category updateCategory(long id,categoryDTO categoryDTO) throws IOException {
		Category category = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category", "id", id));
		category.setName(categoryDTO.getName());
		if(categoryDTO.getUrl() != null) {
			upload upload = new upload();
			category.setUrl(upload.uploadImage(categoryDTO.getUrl()));
		}
		String nameString = category.getUrl();
		category.setUrl(nameString);
		categoryRepository.save(category);
		// TODO Auto-generated method stub
		return null;
	}

}
