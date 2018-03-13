package com.revature.repository;

import com.revature.model.Customer;

public interface CustomerRepository {
	
	public Customer getCustomer(String username);
	public boolean addCustomer(Customer customer);
}
