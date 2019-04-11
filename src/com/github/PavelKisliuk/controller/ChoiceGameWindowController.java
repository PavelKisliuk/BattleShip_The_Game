package com.github.PavelKisliuk.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class ChoiceGameWindowController {
	private Boolean isPvPGame;

	@FXML
	void PvCOnAction(ActionEvent event) {
		this.isPvPGame = false;
		Node b = (Node)event.getTarget();
		Stage stage = (Stage)b.getScene().getWindow();
		stage.close();
	}

	@FXML
	void PvPOnAction(ActionEvent event) {
		this.isPvPGame = true;
		Node b = (Node)event.getTarget();
		Stage stage = (Stage)b.getScene().getWindow();
		stage.close();
	}

	public Boolean getPvPGame() {
		return isPvPGame;
	}
}
