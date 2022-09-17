package com.example.videoproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
public class HelloController implements Initializable {
    int index;
    @FXML
    private TableColumn<FileDetails, String> fileName;

    @FXML
    private TableColumn<FileDetails, String> outputFormat;
    @FXML
    private TableColumn<FileDetails, String> fileSize;
    @FXML
    private TableView<FileDetails> fileTable;

    ObservableList<FileDetails> videoFiles = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fileName.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        outputFormat.setCellValueFactory(new PropertyValueFactory<>("outputFormat"));
        fileSize.setCellValueFactory(new PropertyValueFactory<>("fileSize"));
    }
    @FXML
    void addVideos(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);

        float fileSize = file.length();
        String videoName = file.getName();
        String readableSize = Conversions.byteConversion(fileSize);

        FileDetails videoFile = new FileDetails(videoName, null, readableSize);
        videoFiles.add(videoFile);
        fileTable.setItems(videoFiles);
    }
    @FXML
    void deleteVideo(ActionEvent event) {
        videoFiles.remove(index);
        fileTable.setItems(videoFiles);
    }
    @FXML
    void getSelectedIndex(MouseEvent event) {
        index = fileTable.getSelectionModel().getSelectedIndex();
        System.out.println(index);
    }
}