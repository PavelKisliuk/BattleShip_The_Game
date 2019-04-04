package com.github.PavelKisliuk.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

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

	}

	@FXML
	void imageViewsOnMouseEntered(MouseEvent event) {
		ImageView image = (ImageView)event.getTarget();
		image.setImage(POINTED);
	}

	@FXML
	void imageViewsOnMouseExited(MouseEvent event) {
		ImageView image = (ImageView)event.getTarget();
		image.setImage(COVERT);
	}

	@FXML
	void initialize() {
		startButton.setOnAction(actionEvent -> startButtonOnAction());
	}

	private void startButtonOnAction(){
		for(Node n : opponentGridPane.getChildren()) {
			if(n instanceof ImageView) {
				ImageView image = (ImageView) n;
				image.setImage(COVERT);
				image.setDisable(false);
			}
		}
	}

}
