package com.github.PavelKisliuk.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
	private final Image COVERT = new Image("com/github/PavelKisliuk/image/covert.jpg");
	private final Image POINTED = new Image("com/github/PavelKisliuk/image/pointed.jpg");

	@FXML
	private GridPane playerGridPane;

	@FXML
	private GridPane opponentGridPane;

	@FXML
	private Button startButton;

	@FXML
	private CheckBox goFirstCheckBox;

	@FXML
	private Button newGameButton;

	@FXML
	void imageViewsOnMouseClicked(MouseEvent event) {
		ImageView image = (ImageView)event.getTarget();
		image.setImage(null);
		image.setDisable(true);
	}

	@FXML
	void imageViewsOnMouseEntered(MouseEvent event) {
		ImageView image = (ImageView)event.getTarget();
		image.setImage(POINTED);
	}

	@FXML
	void imageViewsOnMouseExited(MouseEvent event) {
		ImageView image = (ImageView)event.getTarget();
		if(image.getImage() != null) {
			image.setImage(COVERT);
		}
	}

	@FXML
	void initialize() {
		startButton.setOnAction(actionEvent -> startButtonOnAction());
		newGameButton.setOnAction(actionEvent -> newGameOnAction());
	}

	private void startButtonOnAction(){
		try {
			//primaryStage adjustment
			//-----------------------------------------------
			Stage dialogueStage = new Stage();
			dialogueStage.setResizable(false);
			dialogueStage.sizeToScene();
			dialogueStage.setTitle("Arrangement of ships");
			dialogueStage.centerOnScreen();

			//FXML adjustment
			//-----------------------------------------------
			FXMLLoader fxmlLoaderDialogue = new FXMLLoader();
			fxmlLoaderDialogue.setLocation(getClass().getResource("/com/github/PavelKisliuk/view/AddShipsWindow.fxml"));
			Parent fxmlDialogue = fxmlLoaderDialogue.load();

			//modality adjustment
			//-----------------------------------------------
			dialogueStage.initModality(Modality.WINDOW_MODAL);
			dialogueStage.initOwner(startButton.getScene().getWindow());

			//start-up window
			//-----------------------------------------------
			Scene choice = new Scene(fxmlDialogue);
			dialogueStage.setScene(choice);
			dialogueStage.show();


		} catch (IOException e) {
			e.printStackTrace();
		}

		for(Node n : opponentGridPane.getChildren()) {
			if(n instanceof ImageView) {
				ImageView image = (ImageView) n;
				image.setImage(COVERT);
			}
		}
		opponentGridPane.setGridLinesVisible(true);
		playerGridPane.setGridLinesVisible(true);
		opponentGridPane.setDisable(false);
	}

	private void newGameOnAction(){
		for(Node n : opponentGridPane.getChildren()) {
			if(n instanceof ImageView) {
				ImageView image = (ImageView) n;
				image.setImage(null);
			}
		}
		opponentGridPane.setGridLinesVisible(false);
		playerGridPane.setGridLinesVisible(false);
		opponentGridPane.setDisable(true);
	}
}
