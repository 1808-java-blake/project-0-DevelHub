package com.revature.screens;

import com.revature.daos.TransactionDao;
import com.revature.daos.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HomeController {
private UserDao ud = UserDao.currentUserDao;
private TransactionDao td = TransactionDao.currentTransactionDao;
private int input;
	
	@FXML
	private Label amountLabel;
	@FXML
	private Label balanceLabel;
	@FXML
	private TextField amountField;
	@FXML
	private TextField accountField;
	@FXML
	private TextField routingField;
	@FXML
	private Button submitButton;
	
	public void logout(ActionEvent event) {
		HomeScreen.getHomeInstance().close();
		LoginScreen.getLoginInstance().show();
	}
	
	public void balance(ActionEvent event) {
		balanceLabel.setText("Balance: \t$" + td.getBalance());
	}

	public void history(ActionEvent event) {
		balanceLabel.setText("");
		amountLabel.setText("");
		amountField.setText("");
		accountField.setText("");
		routingField.setText("");
		amountField.setVisible(false);
		accountField.setVisible(false);
		routingField.setVisible(false);
		submitButton.setVisible(false);
		td.getTransactionHistory();
		balanceLabel.setText("Transaction History Printed");
	}
	
	public void wire(ActionEvent event) {
		input = 1;
		balanceLabel.setText("");
		amountField.setText("");
		accountField.setText("");
		routingField.setText("");
		amountLabel.setText("Transfer Amount: ");
		amountField.setVisible(true);
		accountField.setVisible(true);
		routingField.setVisible(true);
		submitButton.setVisible(true);
	}
	
	public void withdraw(ActionEvent event) {
		input = 2;
		balanceLabel.setText("");
		amountField.setText("");
		amountLabel.setText("Withdraw Amount: ");
		amountField.setVisible(true);
		accountField.setVisible(false);
		routingField.setVisible(false);
		submitButton.setVisible(true);
	}
	
	public void deposit(ActionEvent event) {
		input = 3;
		balanceLabel.setText("");
		amountField.setText("");
		amountLabel.setText("Deposit Amount: ");
		amountField.setVisible(true);
		accountField.setVisible(false);
		routingField.setVisible(false);
		submitButton.setVisible(true);
	}
	
	public void submit(ActionEvent event) {
		switch (input) {
		case 1:
			String str = "";
			double amount;
			long accNum, routNum;
			try {
				amount = Double.valueOf(amountField.getText());
				accNum = Long.valueOf(accountField.getText());
				routNum = Long.valueOf(routingField.getText());
				if (amount > td.getBalance() || amount == 0) {
					balanceLabel.setText("Invalid Amount");
				}
				else {
					str = td.wire(accNum, routNum, amount);
					balanceLabel.setText(str);
				}
			} catch (NumberFormatException e) {
				balanceLabel.setText("Invalid Amount");
			}
			break;
		case 2:
			try {
				amount = Double.valueOf(amountField.getText());
				if (amount > td.getBalance() || amount == 0) {
					balanceLabel.setText("Invalid Amount");
				}
				else {
					td.withdraw(amount, "Direct Withdraw");
					balanceLabel.setText("Withdraw Completed");
				}
			} catch (NumberFormatException e) {
				balanceLabel.setText("Invalid Amount");
			}
			break;
		case 3:
			try {
				amount = Double.valueOf(amountField.getText());
				if (amount == 0) {
					balanceLabel.setText("Invalid Amount");
				}
				else {
					td.deposit(amount, "Direct Deposit");
					balanceLabel.setText("Deposit Completed");
				}
			} catch (NumberFormatException e) {
				balanceLabel.setText("Invalid Amount");
			}
			break;
		default:
			break;
		}
		ud.updateUser();
	}
}
