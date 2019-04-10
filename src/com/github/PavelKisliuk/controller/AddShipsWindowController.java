package com.github.PavelKisliuk.controller;

import com.github.PavelKisliuk.model.data.Area;
import com.github.PavelKisliuk.model.data.Ship;
import com.github.PavelKisliuk.util.AreaArranger;
import com.github.PavelKisliuk.util.Checker;
import com.github.PavelKisliuk.util.Creator;
import com.github.PavelKisliuk.util.RandomAreaArranger;
import com.github.PavelKisliuk.util.exception.AreaArrangementException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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

	private Area area;
	private boolean isCancel;
	private String arrangementInfo;

	@FXML
	private Button automaticArrangementButton;

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
		if (image.getImage() != SHIP) {
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

		if (boxesCounter > 0) {
			shipAddedButton.setVisible(true);
		} else {
			shipAddedButton.setVisible(false);
		}
	}

	@FXML
	void imageViewsOnMouseEntered(MouseEvent event) {
		ImageView image = (ImageView) event.getTarget();
		if (image.getImage() != SHIP) {
			image.setImage(POINTED);
		}
	}

	@FXML
	void imageViewsOnMouseExited(MouseEvent event) {
		ImageView image = (ImageView) event.getTarget();
		if (image.getImage() != SHIP) {
			image.setImage(null);
		}
	}

	@FXML
	void initialize() {
		shipsGroup = new Ship[Area.SHIPS_AMOUNT];
		isCancel = true;

		automaticArrangementButton.setOnAction(this::automaticArrangementButtonOnAction);
		addShipButton.setOnAction(actionEvent -> addShipButtonOnAction());
		shipAddedButton.setOnAction(actionEvent -> shipAddedButtonOnAction());
		discardButton.setOnAction(actionEvent -> discardButtonOnAction());
		okButton.setOnAction(this::okButtonOnAction);
		cancelButton.setOnAction(this::cancelButtonOnAction);
	}

	private void automaticArrangementButtonOnAction(ActionEvent actionEvent) {
		area = new Area();
		if (RandomAreaArranger.INSTANCE.arrangeRandomArea(area)) {
			isCancel = false;
			arrangementInfo = "Arrangement successful.";
			AreaArranger.INSTANCE.changeCelltype(area, Area.CellsType.NEIGHBOR, Area.CellsType.EMPTY);
			Node b = (Node) actionEvent.getTarget();
			Stage stage = (Stage) b.getScene().getWindow();
			stage.close();
		} else {
			arrangementInfo = "WARNING! Arrangement was failed!!!";
			Node b = (Node) actionEvent.getTarget();
			Stage stage = (Stage) b.getScene().getWindow();
			stage.close();
		}
	}

	private void addShipButtonOnAction() {
		infoLabel.setTextFill(Color.BLACK);
		addShipButton.setVisible(false);
		mainGridPane.setDisable(false);
		boxesCounter = 0;

		rowsGroup = new ArrayList<>();
		columnsGroup = new ArrayList<>();
	}

	private void shipAddedButtonOnAction() {
		infoLabel.setTextFill(Color.BLACK);
		mainGridPane.setDisable(true);
		shipAddedButton.setVisible(false);

		shipsGroup[shipsCounter] = new Ship(rowsGroup.size(),
				Creator.INSTANCE.getIntArray(rowsGroup),
				Creator.INSTANCE.getIntArray(columnsGroup));

		Creator.INSTANCE.correctShip(shipsGroup[shipsCounter]);

		setDisableChoicesBoxes();

		shipsCounter++;
		if (shipsCounter < Area.SHIPS_AMOUNT) {
			addShipButton.setVisible(true);
		}

		infoLabel.setText(String.format("%s%d%s", "Need to add ", (Area.SHIPS_AMOUNT - shipsCounter), " ships."));
	}

	private void setDisableChoicesBoxes() {
		for (Node node : mainGridPane.getChildren()) {
			if (node instanceof ImageView &&
					((ImageView) node).getImage() == SHIP) {
				node.setDisable(true);
			}
		}
	}

	private void discardButtonOnAction() {
		infoLabel.setTextFill(Color.BLACK);
		shipsGroup = new Ship[Area.SHIPS_AMOUNT];
		shipsCounter = 0;
		boxesCounter = 0;
		rowsGroup = null;
		columnsGroup = null;

		for (Node n : mainGridPane.getChildren()) {
			if (n instanceof ImageView) {
				ImageView image = (ImageView) n;
				image.setImage(null);
			}
		}

		addShipButton.setVisible(true);
		shipAddedButton.setVisible(false);
		mainGridPane.setDisable(true);
		infoLabel.setText(String.format("%s%d%s", "Need to add ", (Area.SHIPS_AMOUNT - shipsCounter), " ships."));
	}

	private void okButtonOnAction(ActionEvent actionEvent) {
		try {
			area = new Area();
			AreaArranger.INSTANCE.arrangeFewShips(area, shipsGroup);
			if (Checker.INSTANCE.isRightArrangement(area)) {
				isCancel = false;
				arrangementInfo = "Arrangement successful.";
				AreaArranger.INSTANCE.changeCelltype(area, Area.CellsType.NEIGHBOR, Area.CellsType.EMPTY);
				Node b = (Node) actionEvent.getTarget();
				Stage stage = (Stage) b.getScene().getWindow();
				stage.close();
			} else {
				if (shipsCounter >= Area.SHIPS_AMOUNT) {
					infoLabel.setText("Incorrect arrangement!!!");
					infoLabel.setTextFill(Color.RED);
				} else {
					infoLabel.setTextFill(Color.RED);
				}
			}
		} catch (AreaArrangementException | NullPointerException e) {
			infoLabel.setTextFill(Color.RED);
		}
	}

	private void cancelButtonOnAction(ActionEvent actionEvent) {
		Node b = (Node) actionEvent.getTarget();
		Stage stage = (Stage) b.getScene().getWindow();
		stage.close();
	}


	public boolean isCancel() {
		return isCancel;
	}

	public Area getArea() {
		return area;
	}

	public String getArrangementInfo() {
		return arrangementInfo;
	}
}
