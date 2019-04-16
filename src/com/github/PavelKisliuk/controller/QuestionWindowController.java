package com.github.PavelKisliuk.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class QuestionWindowController {
	private Boolean isNo;

	@FXML
	private Label questionLabel;

	@FXML
	void yesButtonOnAction(ActionEvent event) {
		isNo = false;
		Node b = (Node)event.getTarget();
		Stage stage = (Stage)b.getScene().getWindow();
		stage.close();
	}

	@FXML
	void noButtonOnAction(ActionEvent event) {
		isNo = true;
		Node b = (Node)event.getTarget();
		Stage stage = (Stage)b.getScene().getWindow();
		stage.close();
	}

	void setQuestionLabel(String questionLabel) {
		this.questionLabel.setText("Do you want to " + questionLabel.toLowerCase());
	}

	Boolean isNo() {
		return isNo;
	}
}
