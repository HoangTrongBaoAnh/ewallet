package com.hoangminh.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "customer")
public class CustomerEntity extends BaseEntity {
	
	@Column(name = "username", unique = true)
	private String username;
	
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "lastName")
    private String lastName;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "dateOfBirth")
    private String dateOfBirth;

    @Column(name = "email")
    private String email;
    
    public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	@Column(name = "phonenumber")
    private String phonenumber;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "balance")
    private BigDecimal balance;

    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Column(name = "nationality")
    private String nationality;

    @Column(name = "address")
    private String address;
    
    public CustomerEntity() {
	}
	public CustomerEntity(String username, String email, String password) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	
	

	private Set<RoleEntity> roles = new HashSet<>();
    
    public Set<RoleEntity> getRoles() {
		return roles;
	}
    
	public void setRoles(Set<RoleEntity> roles) {
		this.roles = roles;
	}
	
	@JsonIgnoreProperties("customerEntity")
	@OneToMany(mappedBy = "customerEntity",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private Set<Wallet> wallets = new HashSet<>();
	
	public Set<Wallet> getWallets() {
		return wallets;
	}

	public void setWallets(Set<Wallet> wallets) {
		this.wallets = wallets;
	}
	
	@JsonIgnoreProperties("customer")
	@OneToMany(mappedBy = "customer",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private List<Transaction> transactions  = new ArrayList<>();
	
	
	
	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @OneToMany(mappedBy="customerEntity")
    private Set<DocumentEntity> documents;

    public Set<DocumentEntity> getDocuments() {
        return documents;
    }

    public void setDocuments(Set<DocumentEntity> documents) {
        this.documents = documents;
    }
}
