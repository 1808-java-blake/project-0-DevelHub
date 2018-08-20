package com.revature.daos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.revature.beans.User;
import com.revature.util.AppState;
import com.revature.util.ConnectionUtil;

public class TransactionDaoJdbc implements TransactionDao {
	private ConnectionUtil cu = ConnectionUtil.cu;
	private Logger log = Logger.getRootLogger();
	private UserDao ud = UserDao.currentUserDao;
	private Date date = new Date();

	@Override
	public void deposit(double d, String methodOf) {
		User currentUser = AppState.state.getCurrentUser();
		currentUser.setBalance(currentUser.getBalance() + d);
		updateTransaction(currentUser.getId(), methodOf, d, date.toString());
	}

	@Override
	public void withdraw(double w, String methodOf) {
		User currentUser = AppState.state.getCurrentUser();
		currentUser.setBalance(currentUser.getBalance() - w);
		updateTransaction(currentUser.getId(), methodOf, w, date.toString());
	}

	@Override
	public void updateTransaction(int userId, String methodOf, double amount, String date) {
		try (Connection conn = cu.getConnection()) {
		PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO account_transactions (user_id, method_of, amount, transaction_date) VALUES (?,?,?,?)");
		ps.setInt(1, userId);
		ps.setString(2, methodOf);
		ps.setDouble(3, amount);
		ps.setString(4, date);
		int recordsCreated = ps.executeUpdate();
		ud.updateUser();
		////lob.trace(recordsCreated + " records created");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//lob.error(e.getMessage());
			for(StackTraceElement ste: e.getStackTrace()) {
				//lob.error(ste);
			}
			//lob.warn("failed to create new user");
		}
	}

	@Override
	public String wire(long accountNumber, long routingNumber, double wire) {
		try (Connection conn = cu.getConnection()) {
		PreparedStatement ps = conn.prepareStatement(
				"SELECT * FROM user_info WHERE account_number=? and routing_number=?");
		ps.setLong(1, accountNumber);
		ps.setLong(2, routingNumber);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			User tempU = AppState.state.getCurrentUser();
			User u = new User();
			u.setId(rs.getInt("user_id"));
			u.setBalance(rs.getDouble("balance"));
			AppState.state.setCurrentUser(u);
			deposit(wire, "Wire Deposit");
			tempU = ud.findByUsernameAndPassword(tempU.getUsername(), tempU.getPassword());
			AppState.state.setCurrentUser(tempU);
			withdraw(wire, "Wire Withdraw");
			return "Transfer Sucessful";
		} else {
			//lob.warn("failed to find user with provided credentials from the db");
			return "Invalid User";
		}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Invalid User";
		}
	}

	@Override
	public double getBalance() {
		User currentUser = AppState.state.getCurrentUser();
		currentUser = ud.findByUsernameAndPassword(currentUser.getUsername(), currentUser.getPassword());
		return currentUser.getBalance();
	}

	@Override
	public void getTransactionHistory() {
		User currentUser = AppState.state.getCurrentUser();
		File f = new File("src/main/resources/users_transactions/" 
			+ currentUser.getLastName().toUpperCase() + "_" + currentUser.getFirstName().toUpperCase() + ".txt");
		try (FileWriter fw = new FileWriter(f);
				BufferedWriter bw = new BufferedWriter(fw); Connection conn = cu.getConnection()) {
			f.createNewFile();
			bw.write("\tAccount Number:\t" + currentUser.getAccountNumber() + "\t\t\t\tRouting Number:\t" + currentUser.getRoutingNumber());
			bw.newLine();
			for (int i = 0; i < 7; i++)
				bw.newLine();
			bw.write("|\tTransaction No.\t\t|\tMethod\t\t|\tType\t\t|\tAmount\t\t|\tDate    \t\t|");
			bw.newLine();
			bw.write("----------------------------------------------------------------------------"
					+ "-------------------------------------------------------------");
			bw.newLine();
			PreparedStatement ps = conn.prepareStatement(
					"SELECT * FROM account_transactions WHERE user_id=?");
			ps.setLong(1, currentUser.getId());
			ResultSet rs = ps.executeQuery();
			String acc, method;
			String[] split;
			while(rs.next()) {
				acc = currentUser.getAccountNumber() + "";
				method = rs.getString("method_of");
				split = method.split(" ");
				if(split[1].equals("Withdraw"))
					bw.write("|\tXXXXXX" + acc.substring(6) + "\t\t|\t" + split[0] + "\t\t|\t" + split[1] + "\t|\t" + "$" + rs.getDouble("amount")
					+ "\t\t|" + rs.getString("transaction_date") + "|");
				else
					bw.write("|\tXXXXXX" + acc.substring(6) + "\t\t|\t" + split[0] + "\t\t|\t" + split[1] + "\t\t|\t" + "$" + rs.getDouble("amount")
							+ "\t\t|" + rs.getString("transaction_date") + "|");
				bw.newLine();
			}
			String bal = "";
			for (int i = 0; i < 15; i++)
				bal += "\t";
			for (int i = 0; i < 7; i++)
				bw.newLine();
			bw.write(bal + "Balance: " + currentUser.getBalance());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
