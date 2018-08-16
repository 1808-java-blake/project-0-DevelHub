package com.revature.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private ArrayList<String> transactionHistory = new ArrayList<>();
	private int age;
	private double balance = 0.00;
	private long accountNumber;
	private long routingNumber;
	private boolean enable;

	public User() {
		super();
		Random ran = new Random();
		String string = "";
		int val;
		for (int i = 0; i < 10; i++) {
			val = ran.nextInt(9);
			if (i == 0 && val == 0) {
				i = -1;
				continue;
			}
			string += val;
		}
		accountNumber = Long.valueOf(string);
		string = "17100";
		for (int i = 0; i < 4; i++) {
			val = ran.nextInt(9);
			if (i == 0 && val == 0) {
				i = -1;
				continue;
			}
			string += val;
		}
		routingNumber = Long.valueOf(string);
		transactionHistory.add("\tAccount Number:\t" + accountNumber + "\t\t\t\tRouting Number:\t" + routingNumber);
		transactionHistory.add("|\tTransaction No.\t\t|\tMethod\t\t|\tType\t\t|\tAmount\t\t|\tDate    \t\t|");
		transactionHistory.add("----------------------------------------------------------------------------"
				+ "-------------------------------------------------------------");
		enable = true;
	}

//	private User(String username, String password, String firstName, String lastName, int age) {
//		super();
//		this.username = username;
//		this.password = password;
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.age = age;
//	}
/**
 * takes new user name
 * @return
 */
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public long getRoutingNumber() {
		return routingNumber;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public ArrayList<String> getTransactionHistory() {
		return transactionHistory;
	}

	public void setTransactionHistory(ArrayList<String> transactionHistory) {
		this.transactionHistory = transactionHistory;
	}
	
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + age;
//		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
//		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
//		result = prime * result + ((password == null) ? 0 : password.hashCode());
//		result = prime * result + ((username == null) ? 0 : username.hashCode());
//		return result;
//	}
//	
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		User other = (User) obj;
//		if (age != other.age)
//			return false;
//		if (firstName == null) {
//			if (other.firstName != null)
//				return false;
//		} else if (!firstName.equals(other.firstName))
//			return false;
//		if (lastName == null) {
//			if (other.lastName != null)
//				return false;
//		} else if (!lastName.equals(other.lastName))
//			return false;
//		if (password == null) {
//			if (other.password != null)
//				return false;
//		} else if (!password.equals(other.password))
//			return false;
//		if (username == null) {
//			if (other.username != null)
//				return false;
//		} else if (!username.equals(other.username))
//			return false;
//		return true;
//	}
//	
	@Override
	public String toString() {
		return "\t\t\t\t\tFirst Name: \t" + firstName + "\t\t\tLast Name: \t" + lastName + "\t\t\tUsername: \t" + username 
				+ "\t\t\tPassword: \t" + password + "\t\t\tAge: \t" + age + "\t\t\tBalance: \t" + "$" + balance;
	}


}

