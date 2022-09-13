package com.example.videoproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private TableColumn<FileDetails, String> fileName;

    @FXML
    private TableColumn<FileDetails, String> filePath;

    @FXML
    private TableView<FileDetails> table;

    ObservableList<FileDetails> videoFilesList = FXCollections.observableArrayList();

    @FXML
    void addVideo(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File videoFile = fileChooser.showOpenDialog(null);

        String filePath = videoFile.getAbsolutePath();
        String fileName = videoFile.getName();

        FileDetails fileDetails = new FileDetails(fileName, filePath);

        videoFilesList.add(fileDetails);
        table.setItems(videoFilesList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filePath.setCellValueFactory(new PropertyValueFactory<>("filePath"));
        fileName.setCellValueFactory(new PropertyValueFactory<>("fileName"));
    }
}