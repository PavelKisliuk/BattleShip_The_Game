package com.github.PavelKisliuk.controller;

import com.github.PavelKisliuk.model.data.Area;
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
	private final Image SHIP = new Image("com/github/PavelKisliuk/image/ship.jpg");

	private AddShipsWindowController ASWController;

	private Area playerArea;

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

	private void openWindow(String path, String title) {
		try {
			//primaryStage adjustment
			//-----------------------------------------------
			Stage dialogueStage = new Stage();
			dialogueStage.setResizable(false);
			dialogueStage.sizeToScene();
			dialogueStage.setTitle(title);
			dialogueStage.centerOnScreen();

			//FXML adjustment
			//-----------------------------------------------
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource(path));
			Parent fxmlDialogueWindow = fxmlLoader.load();

			switch (title) {
				case "Arrangement of ships":
					ASWController = fxmlLoader.getController();
					break;
			}

			//modality adjustment
			//-----------------------------------------------
			dialogueStage.initModality(Modality.WINDOW_MODAL);
			dialogueStage.initOwner(startButton.getScene().getWindow());

			//start-up window
			//-----------------------------------------------
			Scene choice = new Scene(fxmlDialogueWindow);
			dialogueStage.setScene(choice);
			dialogueStage.showAndWait();


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void startButtonOnAction() {
		String path = "/com/github/PavelKisliuk/view/AddShipsWindow.fxml";
		String title = "Arrangement of ships";
		openWindow(path, title);

		if(!(ASWController.isCancel())) {
			playerArea = ASWController.getArea();

			for (Node n : playerGridPane.getChildren()) {
				if (n instanceof ImageView) {
					Integer row = GridPane.getRowIndex(n);
					Integer column = GridPane.getColumnIndex(n);
					if (row == null) row = 0;
					if (column == null) column = 0;

					if (playerArea.getCell(row, column) == Area.CellsType.SHIP) {
						((ImageView) n).setImage(SHIP);
					}
				}
			}

			for (Node n : opponentGridPane.getChildren()) {
				if (n instanceof ImageView) {
					ImageView image = (ImageView) n;
					image.setImage(COVERT);
				}
			}
			opponentGridPane.setGridLinesVisible(true);
			playerGridPane.setGridLinesVisible(true);
			opponentGridPane.setDisable(false);
		} else {

		}
	}

	private void newGameOnAction(){
		for(Node n : opponentGridPane.getChildren()) {
			if(n instanceof ImageView) {
				ImageView image = (ImageView) n;
				image.setImage(null);
			}
		}

		for(Node n : playerGridPane.getChildren()) {
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
