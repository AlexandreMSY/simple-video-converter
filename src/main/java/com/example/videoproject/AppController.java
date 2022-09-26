package com.example.videoproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import utilitaries.Conversions;
import ws.schild.jave.EncoderException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class AppController implements Initializable {
    private final String usersFolder = System.getProperty("user.home");
    private int index;
    private Integer bitRate;
    @FXML
    private TableColumn<FileDetails, String> fileName;                //lines 23-27 are all the columns used in the files table
    @FXML
    private TableColumn<FileDetails, String> outputFormat;
    @FXML
    private TableColumn<FileDetails, String> fileSize;

    @FXML
    private TableColumn<FileDetails, String> status;
    @FXML
    private TableView<FileDetails> fileTable;      //table that lists all the files to be converted
    @FXML
    private TextField outputFileName;

    @FXML
    private Label outputFolder;
    @FXML
    private ListView<String> fileFormats;

    @FXML
    private ListView<String> videoCodecs;
    @FXML
    private ComboBox<String> qualityPresets;
    @FXML
    private ComboBox<String> videoBitRate;
    @FXML
    private ComboBox<String> videoFrameRate;
    ObservableList<String> fileFormatsOptions = FXCollections.observableArrayList(
            "mp4",
            "mkv",
            "ogg",
            "flv",
            "avi",
            "m4v",
            "mov",
            "swf",
            "gif");

    ObservableList<String> qualityOptions = FXCollections.observableArrayList("Low", "Default", "High");
    ArrayList<VideoConversion> conversionQueue = new ArrayList();
    ObservableList<FileDetails> files = FXCollections.observableArrayList(); //all the files that are going to be set for conversion
    ObservableList<String> videoBitRateOptions = FXCollections.observableArrayList(
            "Default",
            "256",
            "384",
            "512",
            "768",
            "1024",
            "1200",
            "1600",
            "2400",
            "5000",
            "10000",
            "16000");

    ObservableList<String> videoFrameRateOptions = FXCollections.observableArrayList(
            "Default",
            "5",
            "12",
            "15",
            "18",
            "20",
            "23",
            "24",
            "25",
            "29",
            "30",
            "50",
            "60"
    );

    ObservableList<String> videoCodecsOptions = FXCollections.observableArrayList(
            "Default",
            "h264",
            "mpeg4",
            "mpeg2video"
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fileName.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        outputFormat.setCellValueFactory(new PropertyValueFactory<>("outputFormat"));
        fileSize.setCellValueFactory(new PropertyValueFactory<>("fileSize"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        outputFolder.setText(usersFolder + "\\Videos");
        fileFormats.setItems(fileFormatsOptions);
        qualityPresets.setItems(qualityOptions);
        videoBitRate.setItems(videoBitRateOptions);
        videoFrameRate.setItems(videoFrameRateOptions);
        videoCodecs.setItems(videoCodecsOptions);
    }

    @FXML
    void addVideos(@SuppressWarnings("unused") ActionEvent event){
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All supported Video Files",
                            "*.mp4",
                            "*.mkv",
                            "*.ogg",
                            "*.flv",
                            "*.avi",
                            "*.m4v",
                            "*.wmv",
                            "*.mov",
                            "*.3gp",
                            "*.swf"),
                    new FileChooser.ExtensionFilter("MP4 (*.mp4)", "*.mp4"),
                    new FileChooser.ExtensionFilter("MKV (*.mkv)", "*.mkv"),
                    new FileChooser.ExtensionFilter("FLV (*.flv)", "*.flv"),
                    new FileChooser.ExtensionFilter("AVI (*.avi)", "*.avi"),
                    new FileChooser.ExtensionFilter("M4V (*.m4v)", "*.m4v"),
                    new FileChooser.ExtensionFilter("WMV (*.wmv)", "*.wmv"),
                    new FileChooser.ExtensionFilter("MOV (*.mov)", "*.mov"),
                    new FileChooser.ExtensionFilter("3GP (*.3gp)", "*.3gp"),
                    new FileChooser.ExtensionFilter("SWF (*.swf)", "*.swf")
            );
            File file = fileChooser.showOpenDialog(null);

            float fileSize = file.length();
            String videoName = file.getName();
            String videoNameWithoutExtension = Conversions.removeFileExtension(file.getName());
            String sourcePath = file.getAbsolutePath();
            String targetPath = outputFolder.getText(); //placeholder
            String readableSize = Conversions.byteConversion(fileSize);
            String status = "In queue";
            int qualityPreset = 1;

            FileDetails videoFile = new FileDetails(videoName, null, readableSize, status, qualityPreset);
            VideoConversion videoConversion = new VideoConversion(sourcePath, targetPath, videoNameWithoutExtension);

            files.add(videoFile);
            conversionQueue.add(videoConversion);

            fileTable.setItems(files);
        } catch (Exception RuntimeException) {
            System.out.println("No files added!");
        }
    }

    @FXML
    void deleteVideo(@SuppressWarnings("unused") ActionEvent event) {
        try {
            files.remove(index);
            fileTable.setItems(files);
        } catch (Exception RuntimeException) {
            System.out.println("No videos!");
        }
    }

    @FXML
    void changeOutputFolder(@SuppressWarnings("unused") ActionEvent event) throws IOException {
        try {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File folder = directoryChooser.showDialog(null);
            String newOutputFolder = folder.getAbsolutePath();

            for (VideoConversion videoConversion : conversionQueue) {
                videoConversion.setTargetPath(newOutputFolder);
            }

            outputFolder.setText(newOutputFolder);
        } catch (Exception RuntimeException){
            System.out.println("No folder selected!");
        }
    }

    //this method is executed when the user clicks any of the items on the table
    @FXML
    void getSelectedIndex(@SuppressWarnings("unused") MouseEvent event) {
        try {
            index = fileTable.getSelectionModel().getSelectedIndex();
            System.out.println(conversionQueue.get(index).getVideoCodec());

            String fileOutputName = conversionQueue.get(index).getOutputName();
            //gets the index of the selected item of the comboBoxes in the tabs and then are used to clear and select the chosen option for the video file
            int qualityPreset = files.get(index).getQualityPreset();
            int videoBitRateOption = files.get(index).getVideoBitRateOption();
            int videoFrameRateOption = files.get(index).getVideoFrameRateOption();


            outputFileName.setText(fileOutputName);
            qualityPresets.getSelectionModel().clearAndSelect(qualityPreset);
            videoBitRate.getSelectionModel().clearAndSelect(videoBitRateOption);
            videoFrameRate.getSelectionModel().clearAndSelect(videoFrameRateOption);

        } catch (Exception IndexOutOfBoundsException){
            System.out.println("Empty list!");
        }
    }

    @FXML
    void changeVideoFormat(@SuppressWarnings("unused") MouseEvent event) {
        try {
            String selectedFormat = (fileFormats.getSelectionModel().getSelectedItem().equals("mkv") ? "matroska" : fileFormats.getSelectionModel().getSelectedItem());
            conversionQueue.get(index).setOutputFormat(selectedFormat);

            /*lines 195-197 refresh the output column to show the chosen outputFormat*/

            files.get(index).setOutputFormat(selectedFormat);
            fileTable.setItems(files);
            fileTable.refresh();
        } catch (Exception IndexOutOfBoundsException){
            System.out.println("No videos!");
        }
    }

    @FXML
    void setOutputName(@SuppressWarnings("unused") KeyEvent event) {
        try {
            String newOutputName = outputFileName.getText();
            conversionQueue.get(index).setOutputName(newOutputName);
        } catch (Exception IndexOutOfBoundsException){
            System.out.println("No videos!");
        }
    }

    @FXML
    void setVideoQuality(@SuppressWarnings("unused") ActionEvent event) {
        try {
            String selectedQualityPreset = qualityPresets.getSelectionModel().getSelectedItem();
            int qualityPreset = qualityPresets.getSelectionModel().getSelectedIndex();
            int kilobytesPerSecond;

            if (selectedQualityPreset.equals("Low")) {
                videoBitRate.getSelectionModel().clearAndSelect(0); //it makes the videoBitRate comboBox in the advanced video tab select 0 in order to return NULL
                kilobytesPerSecond = 800; 
                bitRate = Conversions.kpbsToBps(kilobytesPerSecond);
            } else if (selectedQualityPreset.equals("High")) {
                videoBitRate.getSelectionModel().clearAndSelect(0);
                kilobytesPerSecond = 4000;
                bitRate = Conversions.kpbsToBps(kilobytesPerSecond);
            } else {
                bitRate = null;
            }

            files.get(index).setQualityPreset(qualityPreset);
            conversionQueue.get(index).setVideoBitRate(bitRate);
        } catch (Exception IndexOutOfBoundsException){
            System.out.println("No videos!");
        }
    }

    //this method is used on the advanced video settings tab
    @FXML
    void setVideoBitRate(ActionEvent event) {
        try {
            int selectionIndex = videoBitRate.getSelectionModel().getSelectedIndex();
            int kilobytesPerSecond = Integer.parseInt(videoBitRate.getSelectionModel().getSelectedItem());

            if (selectionIndex != 0) {
                bitRate = Conversions.kpbsToBps(kilobytesPerSecond);
            } else {
                bitRate = null;
            }
            files.get(index).setVideoBitRateOption(selectionIndex);
            conversionQueue.get(index).setVideoBitRate(bitRate);

        } catch (Exception IndexOutOfBoundsException){
            System.out.println("No videos!");
        }
    }

    @FXML
    void setVideoCodec(MouseEvent event) throws EncoderException {
        String codec = videoCodecs.getSelectionModel().getSelectedItem();
        conversionQueue.get(index).setVideoCodec(codec);
        System.out.println(conversionQueue.get(index).getVideoCodec());
    }

    @FXML
    void setVideoFrameRate(ActionEvent event){
        int selectedFrameRate = videoFrameRate.getSelectionModel().getSelectedIndex();
        Integer frameRate;

        if (selectedFrameRate != 0){
            frameRate = Integer.valueOf(videoFrameRate.getSelectionModel().getSelectedItem());
        }else{
            frameRate = null;
        }

        files.get(index).setVideoFrameRateOption(selectedFrameRate);
        conversionQueue.get(index).setVideoFrameRate(frameRate);
    }

    Service convertVideos = new Service(){
        protected Task createTask() {
            return new Task() {
                @Override
                protected Void call() throws Exception {
                    for (int index = 0; index < conversionQueue.size(); index++) {     //https://docs.oracle.com/javafx/2/api/javafx/concurrent/Service.html for more information on services;
                        files.get(index).setStatus("Converting");
                        System.out.println(conversionQueue.get(index).showInfo());
                        conversionQueue.get(index).encode();                            //https://stackoverflow.com/questions/16037062/javafx-use-a-thread-more-than-once more information about the thread solution
                        fileTable.setItems(files);
                        fileTable.refresh();

                        if (conversionQueue.get(index).encode()) {
                            files.get(index).setStatus("Finished");
                            fileTable.setItems(files);
                            fileTable.refresh();
                        }
                    }
                    return null;
                }
            };
        }
    };

    @FXML
    void convertVideos(@SuppressWarnings("unused") ActionEvent event) throws EncoderException {
        if(!convertVideos.isRunning()){
            convertVideos.reset();
            convertVideos.start();
        }
    }
}