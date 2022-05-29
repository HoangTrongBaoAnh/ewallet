package com.ewallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ewallet.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
