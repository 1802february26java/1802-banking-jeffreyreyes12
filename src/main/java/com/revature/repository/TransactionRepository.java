package com.revature.repository;

import java.util.List;

import com.revature.model.Transaction;

public interface TransactionRepository {
	/**
	 * Return a Set containing all the transactions associated 
	 * with account_number. Return null, if an error occurs.
	 **/
	public List<Transaction> getAllTransactions(String account_number);
	
	/**
	 * Adds transaction to the repository.
	 **/
	public boolean addTransaction(Transaction transaction);
}
