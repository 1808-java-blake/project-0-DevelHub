package com.revature.screens;

import com.revature.beans.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomeScreen extends Application implements Screen {
	private static final HomeScreen hs = new HomeScreen();
	private static Stage home;

	public HomeScreen(User currentUser) {
		home = new Stage();
		start(home);
	}
	private HomeScreen() {
		super();
	}
	public static Stage getHomeInstance() {
		return home;
	}
	
	public Screen start(String[] args) {
		System.out.println("In HomeScreen Screen Befor Launch");
		return null;
	}
	
	@Override
	public void start(Stage home) {
		try {
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("\\com\\revature\\screens\\Home.fxml"));
			Scene scene = new Scene(root,400,400);
			home.setScene(scene);
			home.setTitle("Home");
			home.show();
			System.out.println("at home");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
