package com.sportyshoes.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sportyshoes.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	Customer findByUsername(String username);

}
