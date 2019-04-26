package com.github.PavelKisliuk.controller;

import com.github.PavelKisliuk.model.data.Area;
import com.github.PavelKisliuk.model.logic.AbstractGame;
import com.github.PavelKisliuk.model.logic.GameVsComputer;
import com.github.PavelKisliuk.model.logic.GameVsPlayer;
import com.github.PavelKisliuk.util.Checker;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

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
	private LoadWindowController LWController;
	private QuestionWindowController QWController;

	private AbstractGame game;
	private Area playerArea;
	private Area opponentArea;
	private boolean isGameGo;
	private Timeline progressTimeline;
	private Timeline timeline;

	@FXML
	private BorderPane mainBorderPane;

	@FXML
	private MenuItem loadMenuItem;

	@FXML
	private MenuItem saveMenuItem;

	@FXML
	private MenuItem exitMenuItem;

	@FXML
	private MenuItem anotherGameTypeMenuItem;

	@FXML
	private MenuItem aboutMenuItem;

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
	private Label gameSpeedLabel;

	@FXML
	private Slider gameSpeedSlider;

	@FXML
	private ProgressBar timeoutProgressBar;

	@FXML
	private Label goesInfoLabel;

	@FXML
	private Label gameInfoLabel;

	@FXML
	private VBox rightVBox;

	@FXML
	private ImageView saveImageView;

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
		} else {
			setOpponentKilled();
			redisplay(opponentGridPane, opponentArea);
			if (game.isWin(opponentArea)) {
				//---------------------------------------------------------------
				if (game instanceof GameVsPlayer) {
					progressTimeline.stop();
					timeoutProgressBar.setVisible(false);
					//((GameVsPlayer) game).disconnect();
				}
				//---------------------------------------------------------------
				goesInfoLabel.setText("You won!!!");
				gameInfoLabel.setText("Click New game.");
				opponentGridPane.setDisable(true);
				saveMenuItem.setDisable(true);
				isGameGo = false;
			} else {
				setProgressBar(Color.BLUE);
			}
		}
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
		isGameGo = false;
		playerArea = new Area();
		opponentArea = new Area();
		startButton.setOnAction(actionEvent -> startButtonOnAction());
		newGameButton.setOnAction(actionEvent -> newGameOnAction());
		loadMenuItem.setOnAction(actionEvent -> loadMenuItemOnAction());
		saveMenuItem.setOnAction(actionEvent -> saveMenuItemOnAction());
		exitMenuItem.setOnAction(actionEvent -> Platform.exit());
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
				case "Load":
					LWController = fxmlLoader.getController();
					break;
				default:
					QWController = fxmlLoader.getController();
					QWController.setQuestionLabel(title);
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
	private void loadMenuItemOnAction() {
		String path = "/com/github/PavelKisliuk/view/LoadWindow.fxml";
		String title = "Load";
		openWindow(path, title);

		if (LWController.getPath() != null) {
			((GameVsComputer) game).loadGame(LWController.getPath(), playerArea, opponentArea);
			setDefaultArea(opponentGridPane);
			setWindowElementsOnStartButton();
			coverOpponentArea();
			setOpponentAlive();
			setShipsOnPlayerArea();
			redisplay(playerGridPane, playerArea);
			redisplay(opponentGridPane, opponentArea);
			goesInfoLabel.setText("Game load. You go.");
			gameInfoLabel.setText("Game start.");
			isGameGo = true;
		}
	}

	private void saveMenuItemOnAction() {
		String path = ("save/");
		if (!(Files.exists(Path.of(path)))) {
			try {
				Files.createDirectory(Path.of(path));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String time = LocalDateTime.now().toString();
		time = time.substring(0, 19);
		time = time.replaceAll(":", "-");
		path = String.format("%s%19s%s", path, time, ".dat");
		((GameVsComputer) game).saveGame(path, playerArea, opponentArea);
		saveImageView.setVisible(true);
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000),
				timelineEvent -> saveImageView.setVisible(false)));
		timeline.play();
		goesInfoLabel.setText("Game save. You go.");
	}

	//---------------------------------------------------------------
	//---------------------------------------------------------------
	private void startButtonOnAction() {
		setPlayerArea();
		if (!(ASWController.isCancel())) {
			coverOpponentArea();
			setTimeline();
			saveImageView.setVisible(true);
			new Thread(this::waitArea).start();
		}
	}

	private void waitArea() {
		opponentArea = game.getOpponentArea();

		Platform.runLater(() -> {
			gameInfoLabel.setText(ASWController.getArrangementInfo() + " Game start!");
			goesInfoLabel.setText("You go.");

			setWindowElementsOnStartButton();
			isGameGo = true;
			setProgressBar(Color.BLUE);
			if (!(goFirstCheckBox.isSelected()) &&
					!(game.playerGoFirst())) {
				goesInfoLabel.setText("Computer go.");
				new Thread(this::opponentGoConfigure).start();
			}
		});
		saveImageView.setVisible(false);
	}

	private void setPlayerArea() {
		String path = "/com/github/PavelKisliuk/view/AddShipsWindow.fxml";
		String title = "Arrangement of ships";
		openWindow(path, title);
		//------------------------------------------------------------------
		if (!(ASWController.isCancel())) {
			playerArea = ASWController.getArea();
			//------------------------------------------------------------------
			if (game instanceof GameVsPlayer) {
				((GameVsPlayer) game).sendArea(playerArea);
				gameInfoLabel.setText("Wait opponent.  ");
				startButton.setDisable(true);
			}
			//------------------------------------------------------------------
			setShipsOnPlayerArea();
		} else {
			gameInfoLabel.setText(ASWController.getArrangementInfo() + "Try again.");
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
				} else {
					((ImageView) n).setImage(null);
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
		startButton.setVisible(false);
		opponentGridPane.setGridLinesVisible(true);
		playerGridPane.setGridLinesVisible(true);
		opponentGridPane.setDisable(false);
		goFirstCheckBox.setVisible(false);
		newGameButton.setVisible(true);
		rightVBox.setVisible(true);
		saveMenuItem.setDisable(false);
	}

	private void opponentGoConfigure() {
		setElementsForOpponentGor(true);

		setProgressBar(Color.RED);

		if (game.opponentGo(playerArea)) {
			if (game.isWin(playerArea)) {
				Platform.runLater(() -> {
					//---------------------------------------------------------------
					if (game instanceof GameVsPlayer) {
						progressTimeline.stop();
						timeoutProgressBar.setVisible(false);
						//((GameVsPlayer) game).disconnect();
					}
					//---------------------------------------------------------------
					redisplay(playerGridPane, playerArea);
					goesInfoLabel.setText("Computer won!!!");
					gameInfoLabel.setText("Click New game.");
					newGameButton.setDisable(false);
					showOpponentShips();
					loadMenuItem.setDisable(false);
					exitMenuItem.setDisable(false);
					anotherGameTypeMenuItem.setDisable(false);
					aboutMenuItem.setDisable(false);
					isGameGo = false;
				});
			} else {
				Platform.runLater(() -> redisplay(playerGridPane, playerArea));
				new Thread(this::opponentGoConfigure).start();
			}
		} else {
			Platform.runLater(() -> {
				redisplay(playerGridPane, playerArea);
				goesInfoLabel.setText("You go.");
			});
			setElementsForOpponentGor(false);
			setProgressBar(Color.BLUE);
		}
	}

	private void setElementsForOpponentGor(boolean condition) {
		if (game instanceof GameVsComputer) {
			opponentGridPane.setDisable(condition);
			loadMenuItem.setDisable(condition);
			saveMenuItem.setDisable(condition);
			exitMenuItem.setDisable(condition);
			anotherGameTypeMenuItem.setDisable(condition);
			newGameButton.setDisable(condition);
		} else {
			opponentGridPane.setDisable(condition);
		}

	}

	private void setProgressBar(Color color) {
		if (game instanceof GameVsComputer) {
			if (color == Color.RED) {
				if ((int) gameSpeedSlider.getValue() == gameSpeedSlider.getMax()) {
					timeoutProgressBar.setVisible(true);
				}
				try {
					Thread.sleep((int) gameSpeedSlider.getValue());
					timeoutProgressBar.setVisible(false);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} else {
			timeoutProgressBar.setVisible(true);
			timeoutProgressBar.setProgress(0);
			if (Color.RED == color) {
				timeoutProgressBar.lookup(".bar").setStyle("-fx-accent: #BF1515;");
			} else {
				timeoutProgressBar.lookup(".bar").setStyle("-fx-accent: #1085BF;");
			}
			if (progressTimeline != null) {
				progressTimeline.stop();
			}
			progressTimeline = new Timeline(new KeyFrame(Duration.millis(30),
					timelineEvent ->
							timeoutProgressBar.setProgress(timeoutProgressBar.getProgress() + 0.001)));
			progressTimeline.setCycleCount(1000);
			progressTimeline.setOnFinished(evt -> Platform.runLater(() -> this.onTimeOut(color)));
			progressTimeline.play();
		}
	}

	private void onTimeOut(Color color) {
		timeoutProgressBar.setVisible(false);
		timeoutProgressBar.setProgress(0);
		opponentGridPane.setDisable(true);
		if (Color.BLUE == color) {
			gameInfoLabel.setText("Timeout! Opponent won!");
		} else {
			gameInfoLabel.setText("Timeout! You won!");
		}
	}

	private void showOpponentShips() {
		for (Node n : opponentGridPane.getChildren()) {
			if (n instanceof ImageView) {
				Integer row = GridPane.getRowIndex(n);
				Integer column = GridPane.getColumnIndex(n);
				if (row == null) row = 0;
				if (column == null) column = 0;

				switch (opponentArea.getCell(row, column)) {
					case EMPTY:
						((ImageView) n).setImage(null);
						break;
					case SHIP:
						((ImageView) n).setImage(SHIP);
						break;
				}
			}
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
		if (savedStopGame()) {
			setDefaultArea(opponentGridPane);
			setDefaultArea(playerGridPane);

			setOpponentAlive();

			setWindowElementsOnNewGameButton();
			isGameGo = false;
		}
	}

	private void setDefaultArea(GridPane gridPane) {
		for (Node n : gridPane.getChildren()) {
			if (n instanceof ImageView) {
				ImageView image = (ImageView) n;
				image.setImage(null);
				image.setDisable(false);
			}
		}
	}

	private void setWindowElementsOnNewGameButton() {
		if (game instanceof GameVsComputer) {
			goFirstCheckBox.setVisible(true);
		}
		opponentGridPane.setGridLinesVisible(false);
		playerGridPane.setGridLinesVisible(false);
		opponentGridPane.setDisable(true);
		startButton.setVisible(true);
		startButton.setDisable(false);
		newGameButton.setVisible(false);
		rightVBox.setVisible(false);
		goesInfoLabel.setText(null);
		gameInfoLabel.setText("Click start to play.");
		saveMenuItem.setDisable(true);
	}

	private void setOpponentKilled() {
		int counter = 0;
		for (Node n : rightVBox.getChildren()) {
			if (n instanceof ImageView &&
					!Checker.INSTANCE.isShipAlive(opponentArea, counter++)) {
				ImageView image = (ImageView) n;
				switch (counter - 1) {
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

	private void setOpponentAlive() {
		int counter = 0;
		for (Node n : rightVBox.getChildren()) {
			if (n instanceof ImageView) {
				ImageView image = (ImageView) n;
				switch (counter++) {
					case 0:
						image.setImage(FOUR_LIVE);
						break;
					case 1:
					case 2:
						image.setImage(THREE_LIVE);
						break;
					case 3:
					case 4:
					case 5:
						image.setImage(TWO_LIVE);
						break;
					case 6:
					case 7:
					case 8:
					case 9:
						image.setImage(ONE_LIVE);
						break;
				}
			}
		}
	}

	public void setGame(AbstractGame game) {
		this.game = game;
		mainBorderPane.setVisible(true);
		if (this.game instanceof GameVsComputer) {
			loadMenuItem.setDisable(false);
			saveMenuItem.setDisable(true);
			goFirstCheckBox.setSelected(false);
			goFirstCheckBox.setVisible(true);
			gameSpeedLabel.setVisible(true);
			gameSpeedSlider.setVisible(true);
			timeoutProgressBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
			timeoutProgressBar.lookup(".bar").setStyle("-fx-accent: #1085BF;");
			anotherGameTypeMenuItem.setText("Choice game vsPlayer");
		} else {
			startButton.setDisable(false);
			loadMenuItem.setDisable(true);
			saveMenuItem.setDisable(true);
			goFirstCheckBox.setSelected(false);
			goFirstCheckBox.setVisible(false);
			gameSpeedLabel.setVisible(false);
			gameSpeedSlider.setVisible(false);
			timeoutProgressBar.setProgress(0);
			gameSpeedSlider.setValue(gameSpeedSlider.getMax());
			anotherGameTypeMenuItem.setText("Choice game vsComputer");

			startButton.setDisable(true);
			gameInfoLabel.setText("Connecting with server.  ");
			setTimeline();
			new Thread(() -> {
				if (((GameVsPlayer) game).connect()) {
					Platform.runLater(() -> gameInfoLabel.setText("Connected. Click start to play."));
					startButton.setDisable(false);
				} else {
					Platform.runLater(() -> gameInfoLabel.setText("Server did not request. Click edit > change game"));
					startButton.setDisable(true);
				}
			}).start();
		}
	}

	private void setTimeline() {
		if (timeline != null) {
			timeline.stop();
		}
		timeline = new Timeline();
		int duration = 500;
		switch (gameInfoLabel.getText()) {
			case "Connecting with server.  ":
				timeline = new Timeline(new KeyFrame(Duration.millis(duration),
						timelineEvent -> setTextOnConnection()));
				break;
			case "Wait opponent.  ":
				timeline = new Timeline(new KeyFrame(Duration.millis(duration),
						timelineEvent -> setTextOnWaitOpponent()));
				break;
		}

		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

	private void setTextOnWaitOpponent() {
		switch (gameInfoLabel.getText()) {
			case "Wait opponent.  ":
				gameInfoLabel.setText("Wait opponent.. ");
				break;
			case "Wait opponent.. ":
				gameInfoLabel.setText("Wait opponent...");
				break;
			case "Wait opponent...":
				gameInfoLabel.setText("Wait opponent.  ");
				break;
			default:
				timeline.stop();
				startButton.setDisable(false);
				break;
		}
	}

	private void setTextOnConnection() {
		switch (gameInfoLabel.getText()) {
			case "Connecting with server.  ":
				gameInfoLabel.setText("Connecting with server.. ");
				break;
			case "Connecting with server.. ":
				gameInfoLabel.setText("Connecting with server...");
				break;
			case "Connecting with server...":
				gameInfoLabel.setText("Connecting with server.  ");
				break;
			default:
				timeline.stop();
				break;
		}
	}

	public boolean savedStopGame() {
		if (isGameGo) {
			String path = "/com/github/PavelKisliuk/view/QuestionWindow.fxml";
			String title;
			if (game instanceof GameVsComputer) {
				title = "Save game?";
				openWindow(path, title);
				if (QWController.isNo() == null) {
					return false;
				} else if (!QWController.isNo()) {
					saveMenuItemOnAction();
					return true;
				}
			}

			return QWController.isNo() != null;
		} else return true;
	}
}
