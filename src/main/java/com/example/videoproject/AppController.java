package com.example.videoproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;


import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class HelloController implements Initializable {
    int index;
    @FXML
    private TableColumn<FileDetails, String> fileName;                //lines 23-27 are all the columns used in the files table
    @FXML
    private TableColumn<FileDetails, String> outputFormat;
    @FXML
    private TableColumn<FileDetails, String> fileSize;
    @FXML
    private TableView<FileDetails> fileTable;      //table that lists all the files to be converted
    @FXML
    private TextField outputFileName;
    ObservableList<FileDetails> videoFiles = FXCollections.observableArrayList();
    @FXML
    private ListView<String> fileFormats;
    @FXML
    private ComboBox<String> qualityPresets;
    ObservableList<String> fileFormatsOptions = FXCollections.observableArrayList(
            "mp4",
            "mkv",
            "ogg",
            "flv");
    ObservableList<String> qualityOptions = FXCollections.observableArrayList("Low", "Normal", "High");
    ObservableList<VideoConversion> conversionQueue = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fileName.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        outputFormat.setCellValueFactory(new PropertyValueFactory<>("outputFormat"));
        fileSize.setCellValueFactory(new PropertyValueFactory<>("fileSize"));

        fileFormats.setItems(fileFormatsOptions);
        qualityPresets.setItems(qualityOptions);
    }

    @FXML
    void addVideos(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);

        float fileSize = file.length();
        String videoName = file.getName();
        String videoNameWithoutExtension = Conversions.removeFileExtension(file.getName());
        String sourcePath = file.getAbsolutePath();
        String targetPath = "C:\\Users\\roadr\\Desktop";
        String readableSize = Conversions.byteConversion(fileSize);

        FileDetails videoFile = new FileDetails(videoName, null, readableSize);
        VideoConversion videoConversion = new VideoConversion(sourcePath, targetPath, videoNameWithoutExtension);

        videoFiles.add(videoFile);
        conversionQueue.add(videoConversion);

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

        String fileOutputName = conversionQueue.get(index).getOutputName();
        outputFileName.setText(fileOutputName);
        System.out.println(conversionQueue.get(index).getOutputName());
    }

    @FXML
    void changeVideoFormat(MouseEvent event) {
        String selectedFormat = fileFormats.getSelectionModel().getSelectedItem();
        conversionQueue.get(index).setOutputFormat(selectedFormat);

        videoFiles.get(index).setOutputFormat(selectedFormat);
        fileTable.setItems(videoFiles);
        fileTable.refresh();
    }

    @FXML
    void setOutputName(KeyEvent event) {
        String newOutputName = outputFileName.getText();
        conversionQueue.get(index).setOutputName(newOutputName);
    }
}