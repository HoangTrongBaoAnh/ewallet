package com.ewallet.service;

import java.io.IOException;
import java.util.List;

import com.ewallet.dto.categoryDTO;
import com.ewallet.entity.Category;

public interface ICategoryService {
	List<Category> findCategories();
	Category creaCategory(categoryDTO categorydto) throws IOException;
	Category updateCategory(long id,categoryDTO categoryDTO) throws IOException;
	void deleteCagetory(long id);
}
