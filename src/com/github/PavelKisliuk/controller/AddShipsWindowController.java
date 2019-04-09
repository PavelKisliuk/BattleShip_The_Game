package com.github.PavelKisliuk.controller;

import com.github.PavelKisliuk.model.data.Ship;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class AddShipsWindowController {
	private final Image SHIP = new Image("com/github/PavelKisliuk/image/ship.jpg");
	private final Image POINTED = new Image("com/github/PavelKisliuk/image/pointed.jpg");

	private Ship[] shipsGroup;
	private int shipsCounter = 10;

	@FXML
	private GridPane mainGridPane;

	@FXML
	private Button addShipButton;

	@FXML
	private Button shipAddedButton;

	@FXML
	private Label infoLabel;

	@FXML
	private Button discardButton;

	@FXML
	private Button okButton;

	@FXML
	private Button cancelButton;

	@FXML
	void imageViewsOnMouseClicked(MouseEvent event) {
		ImageView image = (ImageView) event.getTarget();
		image.setImage(POINTED);
		image.setDisable(true);
		shipAddedButton.setVisible(true);
	}

	@FXML
	void imageViewsOnMouseEntered(MouseEvent event) {
		ImageView image = (ImageView) event.getTarget();
		image.setImage(POINTED);
	}

	@FXML
	void imageViewsOnMouseExited(MouseEvent event) {
		ImageView image = (ImageView) event.getTarget();
		image.setImage(null);
	}

	@FXML
	void initialize() {
		shipsGroup = new Ship[10];

		addShipButton.setOnAction(actionEvent -> addShipButtonOnAction());
	}

	private void addShipButtonOnAction() {
		addShipButton.setVisible(false);
		mainGridPane.setDisable(false);
	}
}
