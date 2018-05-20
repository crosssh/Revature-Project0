package com.revature.bank.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.bank.account.Transaction;
import com.revature.bank.user.User;

public class BankSerializer implements BankDao {
	private final String FILE_LOCATION = "src/main/resources/bank.txt";
	private Scanner scan = new Scanner(System.in);
	private List<User> users = getUsers();
	private List<Transaction> transaction;
	private List<Transaction> transactions;
	private double amount;

	@Override
	public boolean witdrawl(User user) {

		System.out.println("Please enter the amount you would like to withdrawl.");
		amount = scan.nextDouble();

		for (User u : users)
			if (u.getBankAccount().getAccountNumber() == user.getBankAccount().getAccountNumber()) {
				if (u.getBankAccount().getAccountBalance() >= amount) {
					transaction = getTransactions(user);
					transaction.add(new Transaction("withdrawl", amount, LocalDateTime.now()));
					u.getBankAccount().setAccountBalance(u.getBankAccount().getAccountBalance() - amount);
					u.getBankAccount().setTransactions(transaction);
				} else {
					System.out.println("Not enough money to withdrawl that amount.");
					return false;
				}
			}
		try (ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(FILE_LOCATION))) {
			outStream.writeObject(users); // serialize the list to the
											// file
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean deposit(User user) {
		System.out.print("Please enter the amount you would like to deposit: ");
		amount = scan.nextDouble();

		for (User u : users)
			if (u.getBankAccount().getAccountNumber() == user.getBankAccount().getAccountNumber()) {
				transaction = getTransactions(user);
				transaction.add(new Transaction("deposit", amount, LocalDateTime.now()));
				u.getBankAccount().setAccountBalance(u.getBankAccount().getAccountBalance() + amount);
				u.getBankAccount().setTransactions(transaction);
			}
		try (ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(FILE_LOCATION))) {
			outStream.writeObject(users); // serialize the list to the
											// file
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return false;
	}

	@Override
	public void viewBalance(User user) {
		// TODO Auto-generated method stub
		for (User u : users) {
			if (u.getBankAccount().getAccountNumber() == user.getBankAccount().getAccountNumber()) {
				System.out.println("Account balance: " + u.getBankAccount().getAccountBalance());

			}
		}
	}

	@Override
	public List<Transaction> getTransactions(User user) {
		// TODO Auto-generated method stub
		for (User u : users) {
			if (u.getBankAccount().getAccountNumber() == user.getBankAccount().getAccountNumber()) {
				// System.out.println(u.getBankAccount().getTransactions());
				transactions = u.getBankAccount().getTransactions();
			}
		}

		return transactions;
	}

	@Override
	public boolean userLoggedIn(User user) {
		for (User u : users)
			if (u.getBankAccount().getAccountNumber() == user.getBankAccount().getAccountNumber())
				u.setLoggedIn(true);
		try (ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(FILE_LOCATION))) {
			outStream.writeObject(users); // serialize the list to the
											// file
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return false;

	}

	@Override
	public boolean addUser(User user) {
		boolean newUser = true;
		if (users == null) { // initialize a new list if none was given
								// to us
			users = new ArrayList<>();
		}
		// add the new book to the list
		for (User u : users) {
			if (u.getBankAccount().getAccountNumber() == user.getBankAccount().getAccountNumber())
				newUser = false;
		}

		if (newUser)
			users.add(user);

		try (ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(FILE_LOCATION))) {
			outStream.writeObject(users); // serialize the list to the
											// file
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return false;

	}

	@Override
	public List<User> getUsers() {
		try (ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(FILE_LOCATION))) {
			return (List<User>) inStream.readObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean userLogout(User user) {
		// TODO Auto-generated method stub
		for (User u : users)
			if (u.getBankAccount().getAccountNumber() == user.getBankAccount().getAccountNumber())
				u.setLoggedIn(false);
		try (ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(FILE_LOCATION))) {
			outStream.writeObject(users); // serialize the list to the
											// file
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return false;
	}

	@Override
	public void viewAllTransactions(User user) {
		// TODO Auto-generated method stub
		for (User u : users) {
			if (u.getBankAccount().getAccountNumber() != 0) {
				transactions = u.getBankAccount().getTransactions();
				for (Transaction transaction : transactions) {
					System.out.println(u.getUsername() + " made a " + transaction.getType() + " for "
							+ transaction.getAmount() + " on " + transaction.getDate());
				}
			}
		}
	}

}
