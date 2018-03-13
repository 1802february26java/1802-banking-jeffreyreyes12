package com.revature.repository;

import java.util.List;

import com.revature.model.Account;

public interface AccountRepository {
	
	public List<Account> getAllAccounts(String username);
	public boolean addAccount(Account account);
	public boolean upadateBalance(String account_number, double balance);
}
