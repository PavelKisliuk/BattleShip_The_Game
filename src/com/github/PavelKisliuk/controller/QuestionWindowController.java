package com.github.PavelKisliuk.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class QuestionWindowController {
	private boolean isCancel = true;

	@FXML
	private Label questionLabel;

	@FXML
	void okButtonOnAction(ActionEvent event) {
		isCancel = false;
		Node b = (Node)event.getTarget();
		Stage stage = (Stage)b.getScene().getWindow();
		stage.close();
	}

	@FXML
	void cancelButtonOnAction(ActionEvent event) {
		Node b = (Node)event.getTarget();
		Stage stage = (Stage)b.getScene().getWindow();
		stage.close();
	}

	public void setQuestionLabel(String questionLabel) {
		this.questionLabel.setText(questionLabel);
	}

	public boolean isCancel() {
		return isCancel;
	}
}
