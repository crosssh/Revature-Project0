package com.revature.bank.promts;

import java.util.List;
import java.util.Scanner;

import com.revature.bank.account.Transaction;
import com.revature.bank.dao.BankDao;
import com.revature.bank.dao.BankSerializer;
import com.revature.bank.user.User;

public class MenuPromt implements Prompt {

	private BankDao bd = new BankSerializer();
	private Scanner scan = new Scanner(System.in);
	private List<Transaction> transactions;

	@Override
	public Prompt run() {

		User user = getCurrentUser(bd);
		System.out.println("Press 1 for withdral.");
		System.out.println("Press 2 for deposit.");
		System.out.println("Press 3 to view balance.");
		System.out.println("Press 4 to view transactions.");
		System.out.println("Press 5 to Log out");
		if (user.getBankAccount().getAccountNumber() == 0)
			System.out.println("Press 6 to view all transactions");

		String input = scan.nextLine();

		switch (input) {
		case "1":
			bd.witdrawl(user);
			break;
		case "2":
			bd.deposit(user);
			break;
		case "3":
			bd.viewBalance(user);
			break;
		case "4":
			transactions = bd.getTransactions(user);
			for (Transaction transaction : transactions) {
				System.out.println("A " + transaction.getType() + " for the amount of " + transaction.getAmount()
						+ " was made on " + transaction.getDate());
			}
			break;
		case "5":
			bd.userLogout(user);
			return new LoginPrompt();
		case "6":
			bd.viewAllTransactions(user);
			break;

		default:
			break;
		}

		return this;
	}

	public User getCurrentUser(BankDao bd) {
		List<User> users = bd.getUsers();

		for (User u : users) {
			if (u.isLoggedIn() == true)
				return u;
		}

		return null;
	}

}
