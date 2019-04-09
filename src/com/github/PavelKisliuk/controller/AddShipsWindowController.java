package com.github.PavelKisliuk.controller;

import com.github.PavelKisliuk.model.data.Area;
import com.github.PavelKisliuk.model.data.Ship;
import com.github.PavelKisliuk.util.Creator;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class AddShipsWindowController {
	private final Image SHIP = new Image("com/github/PavelKisliuk/image/ship.jpg");
	private final Image POINTED = new Image("com/github/PavelKisliuk/image/pointed.jpg");

	private Ship[] shipsGroup;
	private int shipsCounter = 0;
	private int boxesCounter;
	private List<Integer> rowsGroup;
	private List<Integer> columnsGroup;

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
		if(image.getImage() != SHIP) {
			image.setImage(SHIP);

			Integer row = GridPane.getRowIndex(image);
			Integer column = GridPane.getColumnIndex(image);
			if (row == null) row = 0;
			if (column == null) column = 0;
			rowsGroup.add(row);
			columnsGroup.add(column);
			boxesCounter++;
		} else {
			image.setImage(null);

			Integer row = GridPane.getRowIndex(image);
			Integer column = GridPane.getColumnIndex(image);
			if (row == null) row = 0;
			if (column == null) column = 0;
			rowsGroup.remove(row);
			columnsGroup.remove(column);
			boxesCounter--;
		}

		if(boxesCounter > 0) {
			shipAddedButton.setVisible(true);
		}
	}

	@FXML
	void imageViewsOnMouseEntered(MouseEvent event) {
		ImageView image = (ImageView) event.getTarget();
		if(image.getImage() != SHIP) {
			image.setImage(POINTED);
		}
	}

	@FXML
	void imageViewsOnMouseExited(MouseEvent event) {
		ImageView image = (ImageView) event.getTarget();
		if(image.getImage() != SHIP) {
			image.setImage(null);
		}
	}

	@FXML
	void initialize() {
		shipsGroup = new Ship[Area.SHIPS_AMOUNT];
		rowsGroup = new ArrayList<>();
		columnsGroup = new ArrayList<>();

		addShipButton.setOnAction(actionEvent -> addShipButtonOnAction());
		shipAddedButton.setOnAction(actionEvent -> shipAddedButtonOnAction());
	}

	private void addShipButtonOnAction() {
		addShipButton.setVisible(false);
		mainGridPane.setDisable(false);
		boxesCounter = 0;
	}

	private void shipAddedButtonOnAction() {
		if (shipsCounter < Area.SHIPS_AMOUNT){
			addShipButton.setVisible(true);
		}
		mainGridPane.setDisable(true);
		shipAddedButton.setVisible(false);

		shipsGroup[shipsCounter] = new Ship(rowsGroup.size(),
				Creator.INSTANCE.getIntArray((Integer[]) rowsGroup.toArray()),
				Creator.INSTANCE.getIntArray((Integer[]) columnsGroup.toArray()));
		shipsCounter--;

		setDisableChoicesBoxes();
	}

	private void setDisableChoicesBoxes() {
		for (Node node : mainGridPane.getChildren()) {
			if (node instanceof ImageView &&
					((ImageView) node).getImage() == SHIP) {
				node.setDisable(true);
			}
		}
	}
}
