package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.model.Transaction;
import com.revature.util.ConnectionUtil;

public class TransactionRepositoryJdbc implements TransactionRepository {

	private static Logger logger = Logger.getLogger(TransactionRepositoryJdbc.class);
	
	private static TransactionRepository instance = new TransactionRepositoryJdbc();
	
	private TransactionRepositoryJdbc() {}
	
	public static TransactionRepository getInstance() {
		return instance;
	}
	
	@Override
	public List<Transaction> getAllTransactions(String account_number) {
		logger.trace("Getting all transaction for: " + account_number);
		try (Connection connection = ConnectionUtil.getConnection()) {
			int index = 0;
			String sql = "SELECT * FROM TRANSACTION_TB WHERE ACC_NUMBER = ? ORDER BY T_TIMESTAMP DESC";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(++index, account_number);
			
			ResultSet result = statement.executeQuery();
			
			List<Transaction> list = new ArrayList<>();
			while (result.next()) {
				list.add(new Transaction(result.getTimestamp("T_TIMESTAMP"),
								result.getString("ACC_NUMBER"),
								result.getString("T_TYPE"), 
								result.getDouble("T_AMOUNT")));
			}
			
			return list;
		} catch (SQLException e) {
			logger.error("transactions query failed", e);
		}
		
		return null;
	}

	@Override
	public boolean addTransaction(Transaction transaction) {
		logger.trace("Adding transaction");
		try (Connection connection = ConnectionUtil.getConnection()) {
			int index = 0;
			String sql = "INSERT INTO TRANSACTION_TB VALUES(?,?,?,?)";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setTimestamp(++index, transaction.getTimestamp());
			statement.setString(++index, transaction.getType());
			statement.setDouble(++index, transaction.getAmount());
			statement.setString(++index, transaction.getAccountNumber());
			
			if (statement.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			logger.error("Transaction insertion failed", e);
		}
		
		return false;
	}
}
