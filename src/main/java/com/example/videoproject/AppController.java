package com.example.videoproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import ws.schild.jave.EncoderException;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
public class AppController implements Initializable {
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
    @FXML
    private ListView<String> fileFormats;
    @FXML
    private ComboBox<String> qualityPresets;
    ObservableList<String> fileFormatsOptions = FXCollections.observableArrayList(
            "mp4",
            "mkv",
            "ogg",
            "flv");
    ObservableList<String> qualityOptions = FXCollections.observableArrayList("Low", "Default", "High");
    ObservableList<VideoConversion> conversionQueue = FXCollections.observableArrayList();
    ObservableList<FileDetails> videoFiles = FXCollections.observableArrayList();

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
        String targetPath = "C:\\Users\\roadr\\Desktop"; //placeholder
        String readableSize = Conversions.byteConversion(fileSize);
        int qualityPreset = 1;

        FileDetails videoFile = new FileDetails(videoName, null, readableSize, qualityPreset);
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
        int qualityPreset = videoFiles.get(index).getQualityPreset();

        outputFileName.setText(fileOutputName);
        qualityPresets.getSelectionModel().clearAndSelect(qualityPreset);
    }


    @FXML
    void changeVideoFormat(MouseEvent event) {
        String selectedFormat = fileFormats.getSelectionModel().getSelectedItem().equals("mkv") ? "matroska" : fileFormats.getSelectionModel().getSelectedItem();
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

    @FXML
    void setVideoQuality(ActionEvent event) {
        String selectedQualityPreset = qualityPresets.getSelectionModel().getSelectedItem();
        int qualityPreset = 0;
        Integer bitrate;

        if (selectedQualityPreset.equals("Low")){
            bitrate = Conversions.kpbsToBps(800);
        } else if (selectedQualityPreset.equals("High")) {
            bitrate = Conversions.kpbsToBps(4000);
            qualityPreset = 2;
        } else {
            bitrate = null;
        }

        videoFiles.get(index).setQualityPreset(qualityPreset);
        conversionQueue.get(index).setVideoAttributes(null, bitrate, null);
    }

    @FXML
    void convertVideos(ActionEvent event) throws EncoderException {
        for (VideoConversion videoConversion : conversionQueue) {
            videoConversion.encode();
        }
    }
}