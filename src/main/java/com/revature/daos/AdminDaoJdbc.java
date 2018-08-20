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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.revature.beans.User;
import com.revature.util.AppState;
import com.revature.util.ConnectionUtil;

public class AdminDaoJdbc implements AdminDao {
	private ConnectionUtil cu = ConnectionUtil.cu;
	private Logger log = Logger.getRootLogger();

	@Override
	public User findByFirstnameAndLastname(String firstname, String lastname) {
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT * FROM user_info WHERE firstname=? and lastname=?");
			ps.setString(1, firstname);
			ps.setString(2, lastname);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				User u = new User();
				u.setId(rs.getInt("user_id"));
				u.setFirstName(rs.getString("firstname"));
				u.setLastName(rs.getString("lastname"));
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("pass"));
				u.setAge(rs.getInt("age"));
				u.setEmail(rs.getString("email"));
				u.setBalance(rs.getDouble("balance"));
				u.setEnable(rs.getBoolean("active"));
				AppState.state.setCurrentUser(u);
				return u;
			} else {
				//lob.warn("failed to find user with provided credentials from the db");
				return null;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String adminDeleteUser() {
		User u = AppState.state.getCurrentUser();
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE user_info SET active = false WHERE user_id = ?");
			ps.setInt(1, u.getId());
			int recordsCreated = ps.executeUpdate();
			////lob.trace(recordsCreated + " records created");
			return u.getFirstName().toUpperCase() + " " + u.getLastName().toUpperCase() + " account deleted!";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//lob.error(e.getMessage());
			for(StackTraceElement ste: e.getStackTrace()) {
				//lob.error(ste);
			}
			//lob.warn("failed to create new user");
			return "Invalid User";
		}
	}

	@Override
	public String adminDisplayUserInformation() {
		return AppState.state.getCurrentUser().toString();
	}

	@Override
	public String adminDisplayUserTransaction() {
		User currentUser = AppState.state.getCurrentUser();
		String print = "";
		try (Connection conn = cu.getConnection()) {
			print += "\t\t\t\t\t\t\tAccount Number:\t" + currentUser.getAccountNumber() + "\t\t\t\tRouting Number:\t" + currentUser.getRoutingNumber();
			for (int i = 0; i < 3; i++)
				print += "\n";
			print += "\t\t\t\t\t|Transaction No.\t|\tMethod\t|\t\tType\t\t|\tAmount\t\t|\t\t\tDate    \t\t|\n";
			print += "\t\t\t\t\t----------------------------------------------------------------------------"
					+ "-------------------------------------------------------------\n";
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
				if(split[0].equals("Direct"))
					print += "\t\t\t\t\t|\tXXXXXX" + acc.substring(6) + "\t|\t" + split[0] + "\t|\t" + split[1] + "\t\t|\t" + "$" + rs.getDouble("amount")
					+ "\t\t\t|" + rs.getString("transaction_date") + "|\n";
				else
					print += "\t\t\t\t\t|\tXXXXXX" + acc.substring(6) + "\t|\t" + split[0] + "\t\t|\t" + split[1] + "\t\t|\t" + "$" + rs.getDouble("amount")
							+ "\t\t\t|" + rs.getString("transaction_date") + "|\n";
			}
			String bal = "";
			for (int i = 0; i < 21; i++)
				bal += "\t";
			for (int i = 0; i < 3; i++)
				print += "\n";
			print += bal + "Balance: " + currentUser.getBalance();
			return print;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String adminDisplayAllUserInformation() {
		String print = "";
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT * FROM user_info");
			ResultSet rs = ps.executeQuery();
			User u;
			while(rs.next()) {
				u = findByFirstnameAndLastname(rs.getString("firstname"), rs.getString("lastname"));
				print += u.toString() + "\n\n\n";
			}
			return print;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String adminPurge(String table) {
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
					"DROP TABLE " + table);
			ps.executeUpdate();
			if(table.equals("account_transactions"))
				return "Account Table Deleted!";
			else
				return "User Table Deleted!";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			if(table.equals("account_transactions"))
				return "Account Table Doesn't Exist!";
			else
				return "User Table Doesn't Exist!";
		}
	}

}
