package com.revature.daos;

import com.revature.beans.User;

public interface AdminDao {
	public static final AdminDao currentAdminDao = new AdminDaoJdbc();
	
	String adminDeleteUser();
	String adminDisplayUserInformation();
	String adminDisplayUserTransaction();
	String adminDisplayAllUserInformation();
	String adminPurge(String table);
	User findByFirstnameAndLastname(String username, String password);
}