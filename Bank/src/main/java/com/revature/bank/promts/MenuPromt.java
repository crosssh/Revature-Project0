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
		System.out.println("\nPress 1 for withdral.");
		System.out.println("Press 2 for deposit.");
		System.out.println("Press 3 to view balance.");
		System.out.println("Press 4 to view transactions.");
		System.out.println("Press 5 to Log out");

		String input = scan.nextLine();

		switch (input) {
		//Withdrawal money from the users account.
		case "1":
			bd.witdrawal(user);
			break;
		//deposit money into the users account
		case "2":
			bd.deposit(user);
			break;
		//display the users current balance
		case "3":
			bd.viewBalance(user);
			break;
		//list all of the users transactions since the account was created
		case "4":
			transactions = bd.getTransactions(user);
			for (Transaction transaction : transactions) {
				System.out.printf("\nA %s for the amount of $%,.2f was made on %tB %<te, %<tY at %<tH:%<tM %<Tp",
						transaction.getType(), transaction.getAmount(), transaction.getDate());
			}
			System.out.println();
			break;
		//log the user out
		case "5":
			bd.userLogout(user);
			return new LoginPrompt();

		default:
			break;
		}

		return this;
	}

	//this will get the current user that is logged in
	public static User getCurrentUser(BankDao bd) {
		List<User> users = bd.getUsers();

		for (User u : users) {
			if (u.isLoggedIn() == true)
				return u;
		}

		return null;
	}

}
