package com.revature.screens;

import javafx.stage.Stage;

public interface Screen {
	Screen start(String[] args);

	void start(Stage stage);
}
