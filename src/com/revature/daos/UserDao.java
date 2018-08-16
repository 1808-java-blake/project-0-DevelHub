package com.revature.daos;


import com.revature.beans.User;

public interface UserDao {
	public static final UserDao currentUserDao = new UserSerializer();
	
	User findByUsernameAndPassword(String username, String password);
	void createUser(User u);
	void deposit(double d);
	void withdraw(double d);
	String wire(long accountNumber, long routingNumber, double wire);
	void updateUser();
	String adminDeleteUser();
	String adminDisplayUserInformation();
	String adminDisplayUserTransaction();
	String adminDisplayAllUserInformation();
	void adminPurge();
	double getBalance();
	void getTransactionHistory();
	User findByFirstnameAndLastname(String firstname, String lastname);
}
