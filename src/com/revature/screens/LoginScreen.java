package com.revature.screens;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginScreen extends Application implements Screen {
	private static Stage login;
	
	@Override
	public Screen start(String[] args) {
		launch(args);
		return null;
	}
	
	@Override
	public void start(Stage login) {
		try {
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("\\com\\revature\\screens\\Login.fxml"));
			Scene scene = new Scene(root,300,200);
			login.setScene(scene);
			login.setTitle("Login");
			login.show();
			LoginScreen.login = login;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static Stage getLoginInstance() {
		return login;
	}
}
