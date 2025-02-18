package main;

import javafx.application.Application;
import javafx.stage.Stage;
import shooter.MenuStage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage){
		MenuStage menu = new MenuStage();
		menu.setSplashStage(stage);

	}

}
