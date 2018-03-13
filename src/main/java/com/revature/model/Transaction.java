package com.revature.model;

import java.sql.Timestamp;

public final class Transaction{
	
	public static final String DEPOSIT = "deposit";
	public static final String WITHDRAW = "withdraw";
	
	private final Timestamp timestamp;
	private final String account_number;
	private final String type;
	private final double amount;
	
	public Transaction(String account_number, String type, double amount) {
		this.timestamp = new Timestamp(System.currentTimeMillis());
		this.account_number = account_number;
		this.type = type;
		this.amount = amount;
	}
	
	public Transaction(Timestamp timestamp, String account_number, String type, double amount) {
		this.timestamp = timestamp;
		this.account_number = account_number;
		this.type = type;
		this.amount = amount;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}
	
	public String getAccountNumber() {
		return account_number;
	}

	public String getType() {
		return type;
	}

	public double getAmount() {
		return amount;
	}
}
