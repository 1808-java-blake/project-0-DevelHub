package com.revature.screens;

import com.revature.beans.User;
import com.revature.daos.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegisterController {
private UserDao ud = UserDao.currentUserDao;
	@FXML
	private Label errorLabel;
	@FXML
	private TextField usernameField;
	@FXML
	private TextField passwordField;
	@FXML
	private TextField confirmPasswordField;
	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;
	@FXML
	private TextField ageField;
	@FXML
	private TextField emailField;
	
	public void back(ActionEvent event) {
		RegisterUserScreen.getRegisterInstance().close();
		LoginScreen.getLoginInstance().show();
	}
	public void register(ActionEvent event) {
		User u = new User();
		if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || usernameField.getText().isEmpty()
			|| passwordField.getText().isEmpty() || ageField.getText().isEmpty() || emailField.getText().isEmpty()) {
			errorLabel.setText("All Fields Are Required!");
		}
		else if ( !(passwordField.getText().equals(confirmPasswordField.getText())) ) {
			errorLabel.setText("Password Do Not Match!");
		}
		else if(ud.findByUsername(usernameField.getText())) {
			errorLabel.setText("Username Already Exist");
		}
		else {
			u.setFirstName(firstNameField.getText());
			u.setLastName(lastNameField.getText());
			u.setUsername(usernameField.getText());
			u.setPassword(passwordField.getText());
			u.setEmail(emailField.getText());
			try {
				u.setAge(Integer.valueOf(ageField.getText()));
				ud.createUser(u);
				RegisterUserScreen.getRegisterInstance().close();
				LoginScreen.getLoginInstance().show();
			} catch (NumberFormatException e) {
				errorLabel.setText("Invalid Age");
			}
		}
	}
}
