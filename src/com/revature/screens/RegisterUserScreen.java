package com.revature.screens;

import com.revature.beans.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RegisterUserScreen extends Application implements Screen{
	private static final RegisterUserScreen rus = new RegisterUserScreen();
	private static Stage register;

	public RegisterUserScreen(User currentUser) {
		register = new Stage();
		System.out.println("START");
		start(register);
	}

	private RegisterUserScreen() {
		super();
	}

	public static Stage getRegisterInstance() {
		return register;
	}
	
	public Screen start(String[] args) {
		return null;
	}

	@Override
	public void start(Stage stage) {
		try {
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("\\com\\revature\\screens\\Register.fxml"));
			Scene scene = new Scene(root,400,500);
			register.setScene(scene);
			register.setTitle("Register");
			register.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
