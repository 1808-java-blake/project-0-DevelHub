package com.revature.screens;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.revature.beans.User;
import com.revature.daos.UserDao;

public class LoginController {
	private UserDao ud = UserDao.currentUserDao;

	@FXML
	private Label errorLabel;

	@FXML
	private TextField userText;

	@FXML
	private TextField passText;
	
	public void register(javafx.event.ActionEvent event) {
		LoginScreen.getLoginInstance().close();
		new RegisterUserScreen(null);
	}
	
	public void login(javafx.event.ActionEvent event) {
		errorLabel.setText("");
		String username = userText.getText();
		String password = passText.getText();
		User currentUser = ud.findByUsernameAndPassword(username, password);
		if ("admin".equalsIgnoreCase(username) && "pass".equals(password)) {
			LoginScreen.getLoginInstance().close();
			passText.setText("");
			new AdminUserScreen(null);
		}
		else if (currentUser != null && currentUser.isEnable()) {
			LoginScreen.getLoginInstance().close();
			passText.setText("");
			new HomeScreen(currentUser);
		}
		else
			errorLabel.setText("Invalid Login");
	}
}
