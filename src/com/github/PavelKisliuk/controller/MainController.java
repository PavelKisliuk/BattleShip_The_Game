package com.github.PavelKisliuk.controller;

import com.github.PavelKisliuk.model.data.Area;
import com.github.PavelKisliuk.model.data.Ship;
import com.github.PavelKisliuk.model.logic.AbstractGame;
import com.github.PavelKisliuk.util.Checker;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
	private final Image COVERT = new Image("com/github/PavelKisliuk/image/covert.jpg");
	private final Image POINTED = new Image("com/github/PavelKisliuk/image/pointed.jpg");
	private final Image SHIP = new Image("com/github/PavelKisliuk/image/ship.jpg");
	private final Image WOUNDED = new Image("com/github/PavelKisliuk/image/wounded.jpg");
	private final Image MISSED = new Image("com/github/PavelKisliuk/image/missed.jpg");
	private final Image KILLED = new Image("com/github/PavelKisliuk/image/killed.jpg");
	private final Image LAST = new Image("com/github/PavelKisliuk/image/last.jpg");
	//---------------------------------------------------------------
	private final Image ONE_LIVE = new Image("com/github/PavelKisliuk/image/ship/live/one.jpg");
	private final Image TWO_LIVE = new Image("com/github/PavelKisliuk/image/ship/live/two.jpg");
	private final Image THREE_LIVE = new Image("com/github/PavelKisliuk/image/ship/live/three.jpg");
	private final Image FOUR_LIVE = new Image("com/github/PavelKisliuk/image/ship/live/four.jpg");
	//---------------------------------------------------------------
	private final Image ONE_KILLED = new Image("com/github/PavelKisliuk/image/ship/killed/one.jpg");
	private final Image TWO_KILLED = new Image("com/github/PavelKisliuk/image/ship/killed/two.jpg");
	private final Image THREE_KILLED = new Image("com/github/PavelKisliuk/image/ship/killed/three.jpg");
	private final Image FOUR_KILLED = new Image("com/github/PavelKisliuk/image/ship/killed/four.jpg");
	//---------------------------------------------------------------
	private AddShipsWindowController ASWController;

	private AbstractGame game;
	private Area playerArea;
	private Area opponentArea;

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
	private ProgressBar timeoutProgressBar;

	@FXML
	private Label goesInfoLabel;

	@FXML
	private Label gameInfoLabel;

	@FXML
	private VBox rightVBox;

	@FXML
	void imageViewsOnMouseClicked(MouseEvent event) {
		ImageView image = (ImageView) event.getTarget();
		Integer row = GridPane.getRowIndex(image);
		Integer column = GridPane.getColumnIndex(image);
		if (row == null) row = 0;
		if (column == null) column = 0;

		if (!(game.playerGo(opponentArea, row, column))) {
			redisplay(opponentGridPane, opponentArea);
			goesInfoLabel.setText("Computer go.");
			new Thread(this::opponentGoConfigure).start();
		}
		setKilled();
		redisplay(opponentGridPane, opponentArea);
	}

	@FXML
	void imageViewsOnMouseEntered(MouseEvent event) {
		ImageView image = (ImageView) event.getTarget();
		image.setImage(POINTED);
	}

	@FXML
	void imageViewsOnMouseExited(MouseEvent event) {
		ImageView image = (ImageView) event.getTarget();
		if (image.getImage() == POINTED) {
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

	//---------------------------------------------------------------
	//---------------------------------------------------------------
	private void startButtonOnAction() {
		setPlayerArea();
		coverOpponentArea();
		setWindowElementsOnStartButton();
		opponentArea = game.getOpponentArea();
		if (!(game.playerGoFirst())) {
			goesInfoLabel.setText("Computer go.");
			new Thread(this::opponentGoConfigure).start();
		}
	}

	private void setPlayerArea() {
		String path = "/com/github/PavelKisliuk/view/AddShipsWindow.fxml";
		String title = "Arrangement of ships";
		openWindow(path, title);

		if (!(ASWController.isCancel())) {
			playerArea = ASWController.getArea();
			setShipsOnPlayerArea();
		} else {
			gameInfoLabel.setText("Ships not arrange! Try again.");
		}
	}

	private void setShipsOnPlayerArea() {
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
	}

	private void coverOpponentArea() {
		for (Node n : opponentGridPane.getChildren()) {
			if (n instanceof ImageView) {
				ImageView image = (ImageView) n;
				image.setImage(COVERT);
			}
		}
	}

	private void setWindowElementsOnStartButton() {
		opponentGridPane.setGridLinesVisible(true);
		playerGridPane.setGridLinesVisible(true);
		opponentGridPane.setDisable(false);
		startButton.setVisible(false);
		goFirstCheckBox.setVisible(false);
		newGameButton.setVisible(true);
		gameInfoLabel.setText(String.format("%s%s", ASWController.getArrangementInfo(), " Game start!"));
	}

	private void opponentGoConfigure() {
		opponentGridPane.setDisable(true);
		timeoutProgressBar.setVisible(true);
		try {
			Thread.sleep(2000);
			timeoutProgressBar.setVisible(false);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (game.opponentGo(playerArea)) {
			Platform.runLater(() -> redisplay(playerGridPane, playerArea));
			new Thread(this::opponentGoConfigure).start();
		} else {
			Platform.runLater(() -> {
				redisplay(playerGridPane, playerArea);
				goesInfoLabel.setText("You go.");
			});
			opponentGridPane.setDisable(false);
		}
	}

	private void redisplay(GridPane gridPane, Area area) {
		for (Node n : gridPane.getChildren()) {
			if (n instanceof ImageView) {
				Integer row = GridPane.getRowIndex(n);
				Integer column = GridPane.getColumnIndex(n);
				if (row == null) row = 0;
				if (column == null) column = 0;


				switch (area.getCell(row, column)) {
					case LAST:
						((ImageView) n).setImage(LAST);
						n.setDisable(true);
						break;
					case MISS:
						((ImageView) n).setImage(MISSED);
						n.setDisable(true);
						break;
					case BEATEN:
						((ImageView) n).setImage(WOUNDED);
						n.setDisable(true);
						break;
					case KILLED:
						((ImageView) n).setImage(KILLED);
						n.setDisable(true);
						break;
				}
			}
		}
	}

	//---------------------------------------------------------------
	//---------------------------------------------------------------
	private void newGameOnAction() {
		for (Node n : opponentGridPane.getChildren()) {
			if (n instanceof ImageView) {
				ImageView image = (ImageView) n;
				image.setImage(null);
				image.setDisable(false);
			}
		}

		for (Node n : playerGridPane.getChildren()) {
			if (n instanceof ImageView) {
				ImageView image = (ImageView) n;
				image.setImage(null);
			}
		}

		opponentGridPane.setGridLinesVisible(false);
		playerGridPane.setGridLinesVisible(false);
		opponentGridPane.setDisable(true);
		startButton.setVisible(true);
		goFirstCheckBox.setVisible(true);
		newGameButton.setVisible(false);
		gameInfoLabel.setText("Click start to play.");
	}

	private void setKilled() {
		int counter = 0;
		for (Node n : rightVBox.getChildren()) {
			if (n instanceof ImageView &&
					!Checker.INSTANCE.isShipAlive(opponentArea, counter++)) {
				ImageView image = (ImageView) n;
				switch(counter - 1){
					case 0:
						image.setImage(FOUR_KILLED);
						break;
					case 1:
					case 2:
						image.setImage(THREE_KILLED);
						break;
					case 3:
					case 4:
					case 5:
						image.setImage(TWO_KILLED);
						break;
					case 6:
					case 7:
					case 8:
					case 9:
						image.setImage(ONE_KILLED);
						break;
				}
			}
		}
	}

	public void setGame(AbstractGame game) {
		this.game = game;
	}
}
