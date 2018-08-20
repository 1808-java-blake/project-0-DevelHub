package com.revature.daos;

import com.revature.beans.User;

public interface TransactionDao {
	public static final TransactionDao currentTransactionDao = new TransactionDaoJdbc();

	String wire(long accountNumber, long routingNumber, double wire);
	double getBalance();
	void getTransactionHistory();
	void updateTransaction(int userId, String methodOf, double amount, String string);
	void withdraw(double w, String methodOf);
	void deposit(double d, String methodOf);
}