package com.revature.screens;

import com.revature.beans.User;
import com.revature.daos.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AdminController {
private UserDao ud = UserDao.currentUserDao;
private String output;
private int option;

	@FXML
	private Label errorLabel;
	@FXML
	private Button viewAllButton;
	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;
	@FXML
	private TextArea outputArea;
	@FXML
	private Button searchButton;
	
	public void logout(ActionEvent event) {
		AdminUserScreen.getAdminInstance().close();
		LoginScreen.getLoginInstance().show();
	}
	
	public void accounts(ActionEvent event) {
		option = 1;
		firstNameField.setText("");
		lastNameField.setText("");
		viewAllButton.setVisible(true);
		firstNameField.setVisible(true);
		lastNameField.setVisible(true);
		searchButton.setVisible(true);
	}
	
	public void viewAll(ActionEvent event) {
		output = ud.adminDisplayAllUserInformation();
		if (output == null)
			errorLabel.setText("Data Not Available!");
		else
			outputArea.setText(output);
	}
	
	public void transactions(ActionEvent event) {
		option = 2;
		firstNameField.setText("");
		lastNameField.setText("");
		viewAllButton.setVisible(false);
		firstNameField.setVisible(true);
		lastNameField.setVisible(true);
		searchButton.setVisible(true);
	}
	
	public void delete(ActionEvent event) {
		option = 3;
		firstNameField.setText("");
		lastNameField.setText("");
		viewAllButton.setVisible(false);
		firstNameField.setVisible(true);
		lastNameField.setVisible(true);
		searchButton.setVisible(true);
	}
	
	public void purge(ActionEvent event) {
		ud.adminPurge();
	}
	
	public void search(ActionEvent event) {
		switch (option) {
		case 1:
			if (isInput())
				output = ud.adminDisplayUserInformation();
			outputArea.setText(output);
			break;
		case 2:
			if (isInput())
				output = ud.adminDisplayUserTransaction();
			outputArea.setText(output);
			break;
		case 3:
			if (isInput())
				output = ud.adminDeleteUser();
			errorLabel.setText(output);
			break;

		default:
			break;
		}
		
	}

	private boolean isInput() {
		User u = ud.findByFirstnameAndLastname(firstNameField.getText(), lastNameField.getText());
		if (u == null) {
			errorLabel.setText("User Not Valid!");
			return false;
		}
		return true;
	}
}
