package com.hoangminh.service;

import java.io.IOException;
import java.util.List;

import com.hoangminh.dto.categoryDTO;
import com.hoangminh.entity.Category;

public interface ICategoryService {
	List<Category> findCategories();
	Category creaCategory(categoryDTO categorydto) throws IOException;
	Category updateCategory(long id,categoryDTO categoryDTO) throws IOException;
	void deleteCagetory(long id);
}
