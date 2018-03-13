package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.model.Account;
import com.revature.util.ConnectionUtil;

public class AccountRepositoryJdbc implements AccountRepository {
	private static Logger logger = Logger.getLogger(TransactionRepositoryJdbc.class);
	
	private static AccountRepository instance = new AccountRepositoryJdbc();
	
	private AccountRepositoryJdbc() {}
	
	public static AccountRepository getInstance() {
		return instance;
	}
	
	@Override
	public List<Account> getAllAccounts(String username) {
		logger.trace("Getting all accounts for: " + username);
		try (Connection connection = ConnectionUtil.getConnection()) {
			int index = 0;
			String sql = "SELECT * FROM ACCOUNT_TB WHERE C_USERNAME = ?";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(++index, username);
			
			ResultSet result = statement.executeQuery();
			
			List<Account> list = new ArrayList<>();
			while (result.next()) {
				list.add(new Account(result.getString("ACC_NUMBER"), 
						result.getDouble("ACC_BALANCE"), 
						result.getString("C_USERNAME")));
			}
			
			return list;
		} catch (SQLException e) {
			logger.error("Account query failed", e);
		}
		
		return null;
	}

	@Override
	public boolean addAccount(Account account) {
		// TODO Auto-generated method stub
		logger.trace("Adding account");
		try (Connection connection = ConnectionUtil.getConnection()) {
			int index = 0;
			String sql = "INSERT INTO ACCOUNT_TB VALUES(?,?,?)";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(++index, account.getAccountNumber());
			statement.setDouble(++index, account.getBalance());
			statement.setString(++index, account.getUsername());
			
			if (statement.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			logger.error("Account insertion failed", e);
		}
		
		return false;
	}
	
	@Override
	public boolean upadateBalance(String account_number, double balance) {
		logger.trace("Updating account balance");
		try (Connection connection = ConnectionUtil.getConnection()) {
			int index = 0;
			String sql = "UPDATE ACCOUNT_TB SET ACC_BALANCE = ? WHERE ACC_NUMBER = ?";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setDouble(++index, balance);
			statement.setString(++index, account_number);
			
			if (statement.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			logger.error("balance update failed", e);
		}
		
		return false;
	}
}
