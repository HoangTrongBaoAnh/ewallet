package com.hoangminh.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoangminh.dto.categoryDTO;
import com.hoangminh.entity.Category;
import com.hoangminh.service.ICategoryService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/category")
public class CategoryController {
	
	@Autowired
	private ICategoryService categoryService;
//	private CategoryService categoryService;
	
	@GetMapping
	public ResponseEntity<List<Category>> categoriesFind(){
		return new ResponseEntity<List<Category>>(categoryService.findCategories(),HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Category> categoryAdd(@ModelAttribute categoryDTO category) throws IOException{
		return new ResponseEntity<Category>(categoryService.creaCategory(category),HttpStatus.CREATED);
	}
	
	@PostMapping("/{id}")
	public ResponseEntity<Category> categoryupdate(@ModelAttribute categoryDTO category,@PathVariable long id) throws IOException{
		return new ResponseEntity<Category>(categoryService.updateCategory(id,category),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCagetory(@PathVariable long id){
		categoryService.deleteCagetory(id);
		return new ResponseEntity<String>("delete successfully",HttpStatus.OK);
	}
}
