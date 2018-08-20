package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

//import org.apache.//lob4j.//lobger;

import com.revature.beans.User;
import com.revature.util.AppState;
import com.revature.util.ConnectionUtil;

public class UserDaoJdbc implements UserDao {
	private ConnectionUtil cu = ConnectionUtil.cu;
	private Logger log = Logger.getRootLogger();
	
	static {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void createUser(User u) {
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO user_info (firstname, lastname, username, pass, age, email, account_number, routing_number, balance, active) VALUES (?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1, u.getFirstName());
			ps.setString(2, u.getLastName());
			ps.setString(3, u.getUsername());
			ps.setString(4, u.getPassword());
			ps.setInt(5, u.getAge());
			ps.setString(6, u.getEmail());
			ps.setLong(7, u.getAccountNumber());
			ps.setLong(8, u.getRoutingNumber());
			ps.setDouble(9, u.getBalance());
			ps.setBoolean(10, u.isEnable());
			int recordsCreated = ps.executeUpdate();
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
	public User findByUsernameAndPassword(String username, String password) {
		try (Connection conn = cu.getConnection()) {
			// don't do this
//			Statement s = conn.createStatement();
//			ResultSet rs = s.executeQuery("SELECT * FROM app_users WHERE username='" + 
//							username + "' AND pass='" + password + "'");
			
			// do this
			PreparedStatement ps = conn.prepareStatement(
					"SELECT * FROM user_info WHERE username=? and pass=?");
			ps.setString(1, username);
			ps.setString(2, password);
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
	public boolean findByUsername(String username) {
		try (Connection conn = cu.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT * FROM user_info WHERE username=?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				return true;
			else
				return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public String wire(long accountNumber, long routingNumber, double wire) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateUser() {
		User currentUser = AppState.state.getCurrentUser();
		try (Connection conn = cu.getConnection()) {
		PreparedStatement ps = conn.prepareStatement(
				"UPDATE user_info SET balance = ? WHERE user_id = ?");
		ps.setDouble(1, currentUser.getBalance());
		ps.setInt(2, currentUser.getId());
//		ps.setDouble(3, amount);
//		ps.setString(4, string);
		int recordsCreated = ps.executeUpdate();
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
	public String adminDeleteUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String adminDisplayUserInformation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String adminDisplayUserTransaction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String adminDisplayAllUserInformation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void adminPurge() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getBalance() {
		return AppState.state.getCurrentUser().getBalance();
	}

	@Override
	public void getTransactionHistory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User findByFirstnameAndLastname(String firstname, String lastname) {
		// TODO Auto-generated method stub
		return null;
	}

}
