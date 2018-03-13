package com.revature.controller;

import java.util.Scanner;

import com.revature.model.Account;
import com.revature.model.Customer;
import com.revature.service.BankService;

public class Controller {
	
	private BankService service = new BankService();
	
	public void view() {
		
		Scanner scanner = new Scanner(System.in);
		
		while (true) {
			System.out.print("(command) ");
			
			String command = scanner.nextLine();
			if (command.equals("register")) {
				Customer customer = new Customer();
				
				System.out.print("Username: ");
				customer.setUsername(scanner.nextLine().trim());
				
				System.out.print("password: ");
				customer.setPassword(scanner.nextLine().trim());
				
				System.out.print("Firstname: ");
				customer.setFirstname(scanner.nextLine().trim());
				
				System.out.print("Lastname: ");
				customer.setLastname(scanner.nextLine().trim());
				
				System.out.print("Street: ");
				customer.setStreet(scanner.nextLine().trim());
				
				System.out.print("City: ");
				customer.setCity(scanner.nextLine().trim());
				
				System.out.print("State: ");
				customer.setState(scanner.nextLine().trim());
				
				System.out.print("Zipcode: ");
				customer.setZipcode(Integer.parseInt(scanner.nextLine().trim()));
				
				service.register(customer, new Account(0.0, customer.getUsername()));
			}
			
			else if (command.startsWith("signin")) {
				String[] parts = command.split("\\s");
				
				service.signin(parts[1], parts[2]);
			}
			
			else if (command.startsWith("signout")) {
				service.signout();
			}
			
			else if (command.startsWith("accounts")) {
				service.showAllAccounts();
			}
			
			else if (command.startsWith("deposit")) {
				String[] parts = command.split("\\s");
				
				service.deposit(parts[1], Double.parseDouble(parts[2]));
			}
			
			else if (command.startsWith("withdraw")) {
				String[] parts = command.split("\\s");
				
				service.withdraw(parts[1], Double.parseDouble(parts[2]));
			}
			
			else if (command.startsWith("transactions")) {
				String[] parts = command.split("\\s");
				
				service.showTransactions(parts[1]);
			}
			
			else if (command.equals("add_account")) {
				service.addNewAccount();
			}
			
			else if (command.equals("exit")) {
				scanner.close();
				break;
			}
		}
	}
}
