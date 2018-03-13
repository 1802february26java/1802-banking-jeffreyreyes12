package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.revature.model.Customer;
import com.revature.util.ConnectionUtil;

public class CustomerRepositoryJdbc implements CustomerRepository {

	private static Logger logger = Logger.getLogger(CustomerRepositoryJdbc.class);
	
	private static CustomerRepository instance = new CustomerRepositoryJdbc();
	
	private CustomerRepositoryJdbc() {}
	
	public static CustomerRepository getInstance() {
		return instance;
	}
	
	@Override
	public Customer getCustomer(String username) {
		logger.trace("Getting customer: " + username);
		try (Connection connection = ConnectionUtil.getConnection()) {
			int index = 0;
			String sql = "SELECT * FROM CUSTOMER WHERE C_USERNAME = ?";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(++index, username);
			
			ResultSet result = statement.executeQuery();
		
			if (result.next()) {
				return new Customer(result.getString("C_USERNAME"),
						result.getString("C_PASSWORD"),
						result.getString("C_FIRSTNAME"),
						result.getString("C_LASTNAME"),
						result.getString("C_STREET"),
						result.getString("C_CITY"),
						result.getString("C_STATE"),
						result.getInt("C_ZIPCODE"));
			}
			
		} catch (SQLException e) {
			logger.error("Customer query failed", e);
		}
		
		return null;
	}

	@Override
	public boolean addCustomer(Customer customer) {
		logger.trace("Adding customer");
		try (Connection connection = ConnectionUtil.getConnection()) {
			int index = 0;
			String sql = "INSERT INTO CUSTOMER VALUES(?,?,?,?,?,?,?,?)";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(++index, customer.getUsername());
			statement.setString(++index, customer.getPassword());
			statement.setString(++index, customer.getFirstname());
			statement.setString(++index, customer.getStreet());
			statement.setString(++index, customer.getCity());
			statement.setString(++index, customer.getState());
			statement.setInt(++index, customer.getZipcode());
			statement.setString(++index, customer.getLastname());
			
			if (statement.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			logger.error("customer insertion failed", e);
		}
		
		return false;
	}
}
