package com.revature.service;

import java.util.List;

import com.revature.model.Account;
import com.revature.model.Customer;
import com.revature.model.Transaction;
import com.revature.repository.AccountRepository;
import com.revature.repository.AccountRepositoryJdbc;
import com.revature.repository.CustomerRepository;
import com.revature.repository.CustomerRepositoryJdbc;
import com.revature.repository.TransactionRepository;
import com.revature.repository.TransactionRepositoryJdbc;

public class BankService {
	
	private CustomerRepository customerRepo = CustomerRepositoryJdbc.getInstance();
	private AccountRepository accountRepo = AccountRepositoryJdbc.getInstance();
	private TransactionRepository transactionRepo = TransactionRepositoryJdbc.getInstance();
	
	private Customer customer;
	List<Account> accounts;
	
	public void register(Customer customer, Account account) {
		customerRepo.addCustomer(customer);
		accountRepo.addAccount(account);
	}
	
	public boolean signin(String username, String password) {
		customer = customerRepo.getCustomer(username);
		if (customer == null) {
			System.out.println("Invalid username or password");
		}
		if (!password.equals(customer.getPassword())) {
			customer = null;
			System.out.println("Invalid username or password");
			return false;
		}
		
		accounts = accountRepo.getAllAccounts(customer.getUsername());
		
		return true;
	}
	
	public void signout() {
		customer = null;
	}
	
	public void showAllAccounts() {
		for (Account account: accounts) {
			System.out.println(account.getAccountNumber() + "\t" + account.getBalance());
		}
	}
	
	public void addNewAccount() {
		Account account = new Account(0.0, customer.getUsername());
		accounts.add(account);
		accountRepo.addAccount(account);
	}
	
	public void deposit(String account_number, double amount) {
		for (Account account: accounts) {
			if (account_number.equals(account.getAccountNumber())) {
				account.deposit(amount);
				transactionRepo.addTransaction(new Transaction(account_number, Transaction.DEPOSIT, amount));
				accountRepo.upadateBalance(account_number, account.getBalance());
				return;
			}
		}
	}
	
	public void withdraw(String account_number, double amount) {
		for (Account account: accounts) {
			if (account_number.equals(account.getAccountNumber())) {
				account.withdraw(amount);
				transactionRepo.addTransaction(new Transaction(account_number, Transaction.WITHDRAW, amount));
				accountRepo.upadateBalance(account_number, account.getBalance());
				return;
			}
		}
	}
	
	public void showTransactions(String account_number) {
		for (Transaction transaction: transactionRepo.getAllTransactions(account_number)) {
			System.out.println(transaction.getTimestamp().toString() + "\t" + transaction.getType() + "\t" + transaction.getAmount());
		}
	}
}
