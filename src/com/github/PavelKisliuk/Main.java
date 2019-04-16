package com.github.PavelKisliuk;

import com.github.PavelKisliuk.controller.ChoiceGameWindowController;
import com.github.PavelKisliuk.controller.MainController;
import com.github.PavelKisliuk.model.logic.GameVsComputer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
	private MainController mainController;
	private ChoiceGameWindowController CGWController;

	public void start(Stage primaryStage) throws Exception {
		String title = "BattleShip";
		String path = "view/Main.fxml";
		//primaryStage adjustment
		//-----------------------------------------------
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		primaryStage.setTitle(title);
		primaryStage.centerOnScreen();
		primaryStage.setOnShown(windowEvent -> choiceGame(primaryStage));
		primaryStage.setOnCloseRequest(windowEvent -> {
			if(!mainController.savedStopGame()) {
				windowEvent.consume();
			}
		});

		//FXML adjustment
		//-----------------------------------------------
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource(path));
		Parent fxmlMainWindow = fxmlLoader.load();
		mainController = fxmlLoader.getController();

		//start-up window
		//-----------------------------------------------
		Scene s = new Scene(fxmlMainWindow);
		primaryStage.setScene(s);
		primaryStage.show();
	}

	public static void main(String... args) {
		launch(args);
	}


	private void choiceGame(Stage owner) {
		String path = "view/ChoiceGameWindow.fxml";
		try {
			//primaryStage adjustment
			//-----------------------------------------------
			Stage dialogueStage = new Stage();
			dialogueStage.setResizable(false);
			dialogueStage.sizeToScene();
			dialogueStage.setTitle(null);
			dialogueStage.centerOnScreen();
			dialogueStage.setOnHidden(windowEvent -> onChoiceGame());

			//FXML adjustment
			//-----------------------------------------------
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource(path));
			Parent fxmlDialogueWindow = fxmlLoader.load();
			CGWController = fxmlLoader.getController();

			//modality adjustment
			//-----------------------------------------------
			dialogueStage.initModality(Modality.WINDOW_MODAL);
			dialogueStage.initOwner(owner);

			//start-up window
			//-----------------------------------------------
			Scene choice = new Scene(fxmlDialogueWindow);
			dialogueStage.setScene(choice);
			dialogueStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void onChoiceGame() {
		if (CGWController.getPvPGame() == null) {
			Platform.exit();
		} else if (CGWController.getPvPGame()) {
			this.mainController.setGame(null);
		} else {
			this.mainController.setGame(new GameVsComputer());
		}
	}
}
