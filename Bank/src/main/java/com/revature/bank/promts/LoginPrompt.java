package com.revature.bank.promts;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.bank.dao.BankDao;
import com.revature.bank.dao.BankSerializer;
import com.revature.bank.user.User;

public class LoginPrompt implements Prompt {

	private BankDao bd = new BankSerializer();
	private Scanner scan = new Scanner(System.in);
	private List<User> users = new ArrayList<>();
	private User user;

	public Prompt run() {

		users = bd.getUsers();
		System.out.println("Press 1 to login");
		System.out.println("press 2 to register");
		String input = scan.nextLine();
		switch (input) {
		case "1":
			System.out.println("Please enter username:");
			String username = scan.nextLine();
			System.out.println("Please enter password:");
			String password = scan.nextLine();

			if (checkUserCridentials(users, username, password, bd)) {
				user = MenuPromt.getCurrentUser(bd);
				if (user.getBankAccount().getAccountNumber() == 0)
					return new AdminPrompt();
				else
					return new MenuPromt();
			} else
				System.out.println("Invalid username or password");

			break;
		case "2":
			return new addNewUserPrompt();

		default:
			break;
		}

		return this;
	}

	public boolean checkUserCridentials(List<User> users, String username, String password, BankDao bd) {
		for (User user : users) {
			if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
				System.out.println("Welcome " + username);
				if (bd.userLoggedIn(user))
					return true;
			}
		}

		return false;
	}

}
