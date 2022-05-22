package com.hoangminh.config;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.hoangminh.entity.RoleEntity;
import com.hoangminh.entity.TransactionCategory;
import com.hoangminh.repository.BankRepository;
import com.hoangminh.repository.BillinfoRepository;
import com.hoangminh.repository.CategoryRepository;
import com.hoangminh.repository.CustomerRepository;
import com.hoangminh.repository.RoleRepository;
import com.hoangminh.repository.TransactionCategoryRepository;
import com.hoangminh.entity.Bank;
import com.hoangminh.entity.BillInfo;
import com.hoangminh.entity.Category;
import com.hoangminh.entity.CustomerEntity;
import com.hoangminh.entity.ERole;

@Component
public class seeder implements CommandLineRunner{
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private TransactionCategoryRepository transactionCategoryRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private BillinfoRepository billinfoRepository;
	
	@Autowired
	private BankRepository bankRepository;
	
	@Autowired 
	PasswordEncoder encoder;
	
	@Autowired CategoryRepository categoryRepository;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		initRoleData();
		initTransCateData();
		initAdminData();
		initServiceCategoryData();
		initBankData();
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
			
			TransactionCategory transactionCategory4 = new TransactionCategory();
			transactionCategory4.setName("payment");
			transactionCategoryRepository.save(transactionCategory4);

		}
		
		System.out.println(roleRepository.count());
	}
	
	private void initServiceCategoryData() {
		if(categoryRepository.count() <= 0) {
			Category category = new Category();
			category.setName("Water");
			category.setUrl("https://www.carlsberggroup.com/media/42520/cg_sr2020_illustration_ttz_single_1200x1200_web-08.png?width=1390&mode=max");
			categoryRepository.save(category);
			
			Category category2 = new Category();
			category2.setName("Electricity");
			category2.setUrl("https://media.istockphoto.com/vectors/-vector-id1061291518");
			categoryRepository.save(category2);
			if(billinfoRepository.count() <= 0) {
				BillInfo billInfo = new BillInfo();
				billInfo.setCustomercode("KH001");
				billInfo.setAmount(new BigDecimal("1000"));
				billInfo.setAddress("số 6 Đặng Xuân Bảng, phường Đại Kim, quận Hoàng Mai, thành phố Hà Nội. ");
				billInfo.setCustomerName("Đặng Thu Trang");
				billInfo.setBillCode("BI002WJS");
				billInfo.setStatus(false);
				billInfo.setPhoneNumber("0923949129");
				billInfo.setCategory(category);
				billinfoRepository.save(billInfo);
			}
			
		}
		
		System.out.println(roleRepository.count());
	}
	
	private void initBankData() {
		if(bankRepository.count() <= 0) {
			Bank bank = new Bank();
			bank.setName("OCB");
			bank.setUrl("https://thebank.vn/uploads/2021/05/05/thebank_nganhangocb_1620185389.jpg");
			bankRepository.save(bank);
		}
		
		System.out.println(roleRepository.count());
	}
	
	private void initAdminData() {
		if(customerRepository.count() <= 0) {
			CustomerEntity customerEntity = new CustomerEntity("admin", "admin@gmail.com", encoder.encode("123123123"));
			customerEntity.setUsername("admin");
			customerEntity.setFirstName("admin");
			customerEntity.setLastName("a");
			Set<String> strRoles = new HashSet<>(Arrays.asList("admin", "user"));
			Set<RoleEntity> roles = new HashSet<>();
			RoleEntity adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(adminRole);
			RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
			
			customerEntity.setRoles(roles);
			customerEntity.setBalance(new BigDecimal("1000"));
			customerRepository.save(customerEntity);
			
			
			CustomerEntity customerEntity1 = new CustomerEntity("user", "user@gmail.com", encoder.encode("123123123"));
			customerEntity1.setUsername("user");
			customerEntity1.setFirstName("user");
			customerEntity1.setLastName("a");
			//Set<String> strRoles = new HashSet<>(Arrays.asList("admin", "user"));
			Set<RoleEntity> roles1 = new HashSet<>();
			
			roles1.add(userRole);
			customerEntity1.setRoles(roles1);
			customerEntity1.setBalance(new BigDecimal("1000"));
			customerRepository.save(customerEntity1);
		}
		
		System.out.println(roleRepository.count());
	}
}
