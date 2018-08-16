package com.revature.screens;

import com.revature.beans.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminUserScreen extends Application implements Screen {
	private static final AdminUserScreen hs = new AdminUserScreen();
	private static Stage admin;

	public AdminUserScreen(User currentUser) {
		admin = new Stage();
		start(admin);
	}
	private AdminUserScreen() {
		super();
	}
	public static Stage getAdminInstance() {
		return admin;
	}
	
	@Override
	public Screen start(String[] args) {
		return this;
	}

	@Override
	public void start(Stage stage) {
		// TODO Auto-generated method stub
		try {
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("\\com\\revature\\screens\\Admin.fxml"));
			Scene scene = new Scene(root,1200,500);
			admin.setScene(scene);
			admin.setTitle("Admin");
			admin.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
