package com.hoangminh.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.hoangminh.entity.RoleEntity;
import com.hoangminh.entity.TransactionCategory;
import com.hoangminh.repository.RoleRepository;
import com.hoangminh.repository.TransactionCategoryRepository;
import com.hoangminh.entity.ERole;

@Component
public class seeder implements CommandLineRunner{
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private TransactionCategoryRepository transactionCategoryRepository;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		initRoleData();
		initTransCateData();
	}
	
	private void initRoleData() {
		if(roleRepository.count() <= 0) {
			RoleEntity role1 = new RoleEntity();
			role1.setName(ERole.ROLE_USER);
			roleRepository.save(role1);
			
			RoleEntity role2 = new RoleEntity();
			role2.setName(ERole.ROLE_ADMIN);
			roleRepository.save(role2);
			
			RoleEntity role3 = new RoleEntity();
			role3.setName(ERole.ROLE_MODERATOR);
			roleRepository.save(role3);
			//INSERT INTO roles(name) VALUES('ROLE_USER');
			//INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
			//INSERT INTO roles(name) VALUES('ROLE_ADMIN');
		}
		
		System.out.println(roleRepository.count());
	}
	
	
	private void initTransCateData() {
		if(transactionCategoryRepository.count() <= 0) {
			TransactionCategory transactionCategory = new TransactionCategory();
			transactionCategory.setName("cashin");
			transactionCategoryRepository.save(transactionCategory);
			
			TransactionCategory transactionCategory2 = new TransactionCategory();
			transactionCategory2.setName("cashout");
			transactionCategoryRepository.save(transactionCategory2);
			
			TransactionCategory transactionCategory3 = new TransactionCategory();
			transactionCategory3.setName("transfermoney");
			transactionCategoryRepository.save(transactionCategory3);

		}
		
		System.out.println(roleRepository.count());
	}
}
