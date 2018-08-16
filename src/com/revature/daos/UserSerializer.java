package com.revature.daos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import com.revature.beans.User;

public class UserSerializer implements UserDao {
	private ArrayList<User> users = new ArrayList<>();
	private ArrayList<String> transHistory = new ArrayList<>();
	private User currentUser = null;
	Date date = new Date();
	Random ran = new Random();

	@SuppressWarnings("unchecked")
	public User findByUsernameAndPassword(String username, String password) {
				if (username == null || password == null) {
					return null;
				}
				try (ObjectInputStream ois = new ObjectInputStream(
						new FileInputStream("src/main/resources/users/accounts.txt"))) {
					users = (ArrayList<User>) ois.readObject();
					for (int i = 0; i < users.size(); i++) {
						if (username.equals(users.get(i).getUsername()) && password.equals(users.get(i).getPassword())) {
							currentUser = users.get(i);
							transHistory = users.get(i).getTransactionHistory();
							return users.get(i);
						}
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public User findByFirstnameAndLastname(String firstname, String lastname) {
		if (firstname == null || lastname == null) {
			return null;
		}
		try (ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream("src/main/resources/users/accounts.txt"))) {
			users = (ArrayList<User>) ois.readObject();
			for (int i = 0; i < users.size(); i++) {
				if (firstname.equalsIgnoreCase(users.get(i).getFirstName()) && lastname.equalsIgnoreCase(users.get(i).getLastName())) {
					currentUser = users.get(i);
					transHistory = users.get(i).getTransactionHistory();
					return users.get(i);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public void createUser(User u) {
		if (u == null) {
			return;
		}
		File f = new File("src/main/resources/users/accounts.txt");
		System.out.println(f.getName());
		if (f.exists()) {
			try (ObjectInputStream ois = new ObjectInputStream(
					new FileInputStream("src/main/resources/users/accounts.txt"))) {
				users = (ArrayList<User>) ois.readObject();
				for (int i = 0; i < users.size(); i++) {
					if (users.get(i).getUsername().equals(u.getUsername())) {
						System.out.println("Account already exists!\nPlease try login!");
						return;
					}
				}
				users.add(u);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			try (ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream("src/main/resources/users/accounts.txt"))) {
				oos.writeObject(users);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				f.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
				return;
			}
			try (ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream("src/main/resources/users/accounts.txt"))) {
				users.add(u);
				oos.writeObject(users);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void deposit(double d) {
		String transNum = "";
		for (int i = 0; i < 12; i++) {
			transNum += ran.nextInt(9);
		}
		date = new Date();
		currentUser.setBalance(currentUser.getBalance() + d);
		transHistory = currentUser.getTransactionHistory();
		transHistory.add("|\t" + transNum + "\t\t|\tPhysical\t|\tDeposit\t\t|\t" + "$" + d 
				+ "\t\t|" + date.toString() + "|");
		currentUser.setTransactionHistory(transHistory);
		
	}

	@Override
	public void withdraw(double d) {
		String transNum = "";
		for (int i = 0; i < 12; i++) {
			transNum += ran.nextInt(9);
		}
		date = new Date();
		currentUser.setBalance(currentUser.getBalance() - d);
		transHistory = currentUser.getTransactionHistory();
		transHistory.add("|\t" + transNum + "\t\t|\tPhysical\t|\tWithdraw\t\t|\t" + "$" + d 
				+ "\t\t|" + date.toString() + "|");
		currentUser.setTransactionHistory(transHistory);
		
	}
	
	@Override
	public double getBalance() {
		return currentUser.getBalance();		
	}

	@Override
	public void getTransactionHistory() {
		File f = new File("src/main/resources/users_transactions/" 
				+ currentUser.getLastName().toUpperCase() + "_" + currentUser.getFirstName().toUpperCase() + ".txt");
		try {
			f.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
		try (FileWriter fw = new FileWriter(f);
				BufferedWriter bw = new BufferedWriter(fw)) {
			
			bw.write(currentUser.getTransactionHistory().get(0));
			for (int i = 0; i < 7; i++) {
				bw.newLine();
			}
			for (int i = 1; i < transHistory.size(); i++) {
				bw.write(currentUser.getTransactionHistory().get(i));
				bw.newLine();
			}
			String bal = "";
			for (int i = 0; i < 15; i++) {
				bal += "\t";
			}
			for (int i = 0; i < 7; i++) {
				bw.newLine();
			}
			bw.write(bal + "Balance: " + currentUser.getBalance());
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String wire(long accountNumber, long routingNumber, double wire) {
		try (ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream("src/main/resources/users/accounts.txt"))) {
			
			users = (ArrayList<User>) ois.readObject();
			for (int i = 0; i < users.size(); i++) {
				if (accountNumber == users.get(i).getAccountNumber() && routingNumber == users.get(i).getRoutingNumber()) {
					String last4 = users.get(i).getAccountNumber() + "";
					date = new Date();
					currentUser.setBalance(currentUser.getBalance() - wire);
					transHistory = currentUser.getTransactionHistory();
					transHistory.add("|\t  XXXXXX" + last4.substring(6) + "\t\t|\tWire\t\t|\tWithdraw\t|\t" + "$" + wire 
							+ "\t\t|" + date.toString() + "|");
					currentUser.setTransactionHistory(transHistory);
					
					User tempUser = currentUser;
					ArrayList<String> tempTransHistory = transHistory;
					
					last4 = currentUser.getAccountNumber() + "";
					users.get(i).setBalance(users.get(i).getBalance() + wire);
					transHistory = users.get(i).getTransactionHistory();
					transHistory.add("|\t  XXXXXX" + last4.substring(6) + "\t\t|\tWire\t\t|\tDeposit\t\t|\t" + "$" + wire 
							+ "\t\t|" + date.toString() + "|");
					users.get(i).setTransactionHistory(transHistory);
					
					currentUser = users.get(i);
					transHistory = users.get(i).getTransactionHistory();
					updateUser();
					
					currentUser = tempUser;
					transHistory = tempTransHistory;
					return "Transfer Sucessful";
				}
			}
		} catch (FileNotFoundException e) {
//			e.printStackTrace();
		} catch (IOException e) {
//			e.printStackTrace();
		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
		}
		return "Invalid User";
	}
	
	@SuppressWarnings("unchecked")
	public void updateUser() {
		try (ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream("src/main/resources/users/accounts.txt"))) {
			users = (ArrayList<User>) ois.readObject();
			for (int i = 0; i < users.size(); i++) {
				if (currentUser.getUsername().equals(users.get(i).getUsername()) 
						&& currentUser.getPassword().equals(users.get(i).getPassword())) {
					users.get(i).setBalance(currentUser.getBalance());
					users.get(i).setTransactionHistory(currentUser.getTransactionHistory());
				}
			}
			try (ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream("src/main/resources/users/accounts.txt"))) {
				oos.writeObject(users);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String adminDisplayUserInformation() {
		return currentUser.toString();
	}

	@Override
	public String adminDisplayUserTransaction() {
		String str = "";
		str += "\t\t\t\t\t\t\tCurrent Balance: \t" + "$" + currentUser.getBalance() + "\t\t\t\t\t\t\t\t\t\t\t\n\n" 
		+ currentUser.getTransactionHistory().get(0) + "\n\n";
		for (int i = 1; i < currentUser.getTransactionHistory().size(); i++) {
			str += "\t\t\t\t\t\t\t" + currentUser.getTransactionHistory().get(i) + "\n";
		}
		return str;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String adminDeleteUser() {
		String str = "";
		try (ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream("src/main/resources/users/accounts.txt"))) {
			users = (ArrayList<User>) ois.readObject();
			for (int i = 0; i < users.size(); i++) {
				if (currentUser.getFirstName().equalsIgnoreCase(users.get(i).getFirstName()) 
						&& currentUser.getLastName().equalsIgnoreCase(users.get(i).getLastName())) {
					users.get(i).setEnable(false);
				}
			}
			try (ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream("src/main/resources/users/accounts.txt"))) {
				oos.writeObject(users);
				str += currentUser.getFirstName().toUpperCase() 
						+ " " + currentUser.getLastName().toUpperCase() + " account deleted!";
				return str;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return "Invalid User";
	}

	@SuppressWarnings("unchecked")
	@Override
	public String adminDisplayAllUserInformation() {
		String str = "";
		try (ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream("src/main/resources/users/accounts.txt"))) {
			users = (ArrayList<User>) ois.readObject();
			if (users.isEmpty())
				return null;
			for (int i = 0; i < users.size(); i++) {
				str += users.get(i).toString() + "\n";
			}
			return str;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void adminPurge() {
		File f;
		try (ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream("src/main/resources/users/accounts.txt"))) {
			users = (ArrayList<User>) ois.readObject();
			if (users.isEmpty()) {
				System.out.println("No data found");
				return;
			}
			for (int i = 0; i < users.size(); i++) {
				f = new File("src/main/resources/users_transactions/" 
						+ users.get(i).getLastName().toUpperCase() + "_" + users.get(i).getFirstName().toUpperCase() + ".txt");
				f.delete();
			}
			ois.close();
			f = new File("src/main/resources/users/accounts.txt");
			f.delete();
			System.out.println("\nAll data has been removed!\nApplication Closing!\n\n");
			System.exit(0);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("\nNo Data!\n\n");
	}
}