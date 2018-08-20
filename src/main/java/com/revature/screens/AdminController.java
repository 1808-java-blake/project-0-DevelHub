package com.revature.screens;

import com.revature.beans.User;
import com.revature.daos.AdminDao;
import com.revature.daos.UserDao;
import com.revature.util.AppState;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AdminController {
private UserDao ud = UserDao.currentUserDao;
private AdminDao ad = AdminDao.currentAdminDao;
private String output;
private int option, exit;

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
	@FXML
	private Button dropButton;
	
	public void logout(ActionEvent event) {
		AdminUserScreen.getAdminInstance().close();
		LoginScreen.getLoginInstance().show();
	}
	
	public void accounts(ActionEvent event) {
		option = 1;
		firstNameField.setText("");
		lastNameField.setText("");
		dropButton.setVisible(false);
		viewAllButton.setVisible(true);
		firstNameField.setVisible(true);
		lastNameField.setVisible(true);
		searchButton.setVisible(true);
	}
	
	public void viewAll(ActionEvent event) {
		output = ad.adminDisplayAllUserInformation();
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
		dropButton.setVisible(false);
		firstNameField.setVisible(true);
		lastNameField.setVisible(true);
		searchButton.setVisible(true);
	}
	
	public void delete(ActionEvent event) {
		option = 3;
		firstNameField.setText("");
		lastNameField.setText("");
		viewAllButton.setVisible(false);
		dropButton.setVisible(false);
		firstNameField.setVisible(true);
		lastNameField.setVisible(true);
		searchButton.setVisible(true);
	}
	
	public void purge(ActionEvent event) {
		errorLabel.setText(ad.adminPurge("account_transactions"));
		dropButton.setVisible(true);
		if(exit == 1)
			System.exit(exit);
	}
	public void purgePartTwo(ActionEvent event) {
		exit = 1;
		errorLabel.setText(ad.adminPurge("user_info"));
		dropButton.setVisible(false);
	}
	
	public void search(ActionEvent event) {
		switch (option) {
		case 1:
			if (isInput())
				output = ad.adminDisplayUserInformation();
			outputArea.setText(output);
			break;
		case 2:
			if (isInput())
				output = ad.adminDisplayUserTransaction();
			outputArea.setText(output);
			break;
		case 3:
			if (isInput())
				output = ad.adminDeleteUser();
			errorLabel.setText(output);
			break;

		default:
			break;
		}
		
	}

	private boolean isInput() {
		User u = ad.findByFirstnameAndLastname(firstNameField.getText(), lastNameField.getText());
		AppState.state.setCurrentUser(u);
		if (u == null) {
			errorLabel.setText("User Not Valid!");
			return false;
		}
		return true;
	}
}
