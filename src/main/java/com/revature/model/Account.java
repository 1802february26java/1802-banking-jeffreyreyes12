package com.revature.model;

import java.util.Random;

public class Account {
	
	private final static Random random = new Random(System.currentTimeMillis());
	private final static char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9'};
	
	private final String account_number;
	private double balance;
	private final String username;
	
	public Account(String account_number, double balance, String username) {
		this.account_number = account_number;
		this.balance = balance;
		this.username = username;
	}

	public Account(double balance, String username) {
		this.account_number = generateAccountNumber();
		this.balance = balance;
		this.username = username;
	}

	private static String generateAccountNumber() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			builder.append(digits[random.nextInt(10)]);
		}
		
		return builder.toString();
	}
	
	public String getAccountNumber() {
		return account_number;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void deposit(double amount) {
		balance += amount;
	}
	
	public boolean withdraw(double amount) {
		double bal = balance - amount;
		if (bal < 0) {
			return false;
		}
		balance = bal;
		return true;
	}
}
