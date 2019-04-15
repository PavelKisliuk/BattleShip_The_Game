package com.github.PavelKisliuk.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

public class LoadWindowController {
	private String path;
	private ObservableList<File> allFiles;

	@FXML
	private TableView<File> loadingTableView;

	@FXML
	private TableColumn<File, String> loadingTableColumn;

	@FXML
	private Button loadButton;

	@FXML
	private Button deleteButton;

	@FXML
	void loadButtonOnAction(ActionEvent event) {
		path = loadingTableView.getSelectionModel().getSelectedItem().getPath();
		Node b = (Node)event.getTarget();
		Stage stage = (Stage)b.getScene().getWindow();
		stage.close();
	}

	@FXML
	void initialize() {
		//-----------------------------------------------------------------------------
		allFiles = FXCollections.observableArrayList();
		String pathName = ("save/");
		File folder = new File(pathName);
		//-----------------------------------------------------------------------------
		File[] listOfFiles = folder.listFiles();
		if((listOfFiles != null) && (listOfFiles.length > 0)) {
			allFiles.addAll(Arrays.asList(listOfFiles));
			Collections.reverse(allFiles);
			loadingTableView.setItems(allFiles);
			loadingTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		}
		else {
			loadButton.setDisable(true);
			deleteButton.setDisable(true);
		}
		//-----------------------------------------------------------------------------
		loadingTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				loadButton.setDisable(false);
				deleteButton.setDisable(false);
			}
			else {
				loadButton.setDisable(true);
				deleteButton.setDisable(true);
			}
		});
		deleteButton.setOnAction(actionEvent -> deleteOnAction());
	}

	private void deleteOnAction() {
		try{
			Files.delete(Paths.get(loadingTableView.getSelectionModel().getSelectedItem().getPath()));
			allFiles.remove(loadingTableView.getSelectionModel().getSelectedItem());
		} catch (IOException e) {
			e.printStackTrace();
		}
		loadingTableView.getSelectionModel().select(null);
	}

	public String getPath() {
		return path;
	}
}
