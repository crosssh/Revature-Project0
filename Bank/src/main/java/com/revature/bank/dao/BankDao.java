package com.revature.bank.dao;

import java.util.List;

import com.revature.bank.account.Transaction;
import com.revature.bank.user.User;

public interface BankDao {

	boolean addUser(User user);

	boolean witdrawal(User user);

	boolean deposit(User user);

	void viewBalance(User user);

	List<Transaction> getTransactions(User user);

	void viewAllTransactions(User user);

	boolean userLoggedIn(User user);

	boolean userLogout(User user);

	List<User> getUsers();

}
