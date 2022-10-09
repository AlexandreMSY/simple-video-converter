package com.example.videoproject;

import fileDetails.FileDetails;
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
import videoConversion.VideoConversion;
import ws.schild.jave.EncoderException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AppController implements Initializable {
    private final String usersFolder = System.getProperty("user.home");
    private int index;
    private int selectedIndex;
    private Integer bitsPerSecond;

    private String selectedItem;

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

    @FXML
    private ComboBox<String> audioBitRate;

    @FXML
    private ComboBox<String> audioChannels;

    @FXML
    private ComboBox<String> audioSampleRate;

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
            "mpeg1video",
            "mpeg2video",
            "mpeg4",
            "h261",
            "h263",
            "h264");

    ObservableList<String> audioBitRateOptions = FXCollections.observableArrayList(
        "Default",
            "24",
            "32",
            "64",
            "96",
            "128",
            "192",
            "224",
            "256",
            "320"
    );

    ObservableList<String> audioSampleRateOptions = FXCollections.observableArrayList(
            "Default",
            "22050",
            "24000",
            "44100",
            "48000"
    );

    ObservableList<String> audioChannelOptions = FXCollections.observableArrayList(
            "Default",
            "1",
            "2"
    );

    private void updateVideoCodecList(String outputFormat){

        ObservableList<String> mp4Codecs = FXCollections.observableArrayList(
                "Default",
                "mpeg1video",
                "mpeg2video",
                "mpeg4",
                "h261",
                "h263",
                "h264"
        );

        ObservableList<String> oggCodecs = FXCollections.observableArrayList(
                "Default",
                "libtheora"
        );

        ObservableList<String> flvCodecs = FXCollections.observableArrayList(
                "Default",
                "h264",
                "mpeg4",
                "flv"
        );

        ObservableList<String> aviCodecs = FXCollections.observableArrayList(
                "Default",
                "mpeg4",
                "mpeg4 (DIVX)",
                "h261",
                "h263",
                "h264",
                "wmv2",
                "flv",
                "msmpeg4v1",
                "msmpeg4v2"
        );

        ObservableList<String> m4vCodecs = FXCollections.observableArrayList(
                "Default"
        );

        ObservableList<String> movCodecs = FXCollections.observableArrayList(
                "Default",
                "h264",
                "mpeg1video",
                "mpeg2video",
                "mpeg4"
        );

        ObservableList<String> gifCodecs = FXCollections.observableArrayList(
                "Default",
                "gif"
        );


        switch (outputFormat) {
            case "mp4", "matroska" -> videoCodecsOptions.setAll(mp4Codecs);
            case "ogg" -> videoCodecsOptions.setAll(oggCodecs);
            case "flv" -> videoCodecsOptions.setAll(flvCodecs);
            case "avi", "swf" -> videoCodecsOptions.setAll(aviCodecs);
            case "m4v" -> videoCodecsOptions.setAll(m4vCodecs);
            case "mov" -> videoCodecsOptions.setAll(movCodecs);
            case "gif" -> videoCodecsOptions.setAll(gifCodecs);
        }

        videoCodecs.setItems(videoCodecsOptions);
    }

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

        audioBitRate.setItems(audioBitRateOptions);
        audioSampleRate.setItems(audioSampleRateOptions);
        audioChannels.setItems(audioChannelOptions);
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
            String targetPath = outputFolder.getText();
            String readableSize = Conversions.byteConversion(fileSize);
            String status = "In queue";
            int qualityPreset = 1; //Default

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
            conversionQueue.remove(index);
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

            String fileOutputName = conversionQueue.get(index).getOutputName();
            //gets the index of the selected item of the comboBoxes in the tabs and then are used to clear and select the chosen option for the video file
            int qualityPreset = files.get(index).getQualityPreset();

            int videoBitRateSelection = files.get(index).getVideoBitRateOption();
            int videoFrameRateSelection = files.get(index).getVideoFrameRateOption();

            int audioBitRateSelection = files.get(index).getAudioBitRateOption();
            int audioSamplingRateSelection = files.get(index).getAudioSampleRateOption();
            int audioChannelsSelection = files.get(index).getAudioChannelOption();

            outputFileName.setText(fileOutputName);
            qualityPresets.getSelectionModel().clearAndSelect(qualityPreset);

            videoBitRate.getSelectionModel().clearAndSelect(videoBitRateSelection);
            videoFrameRate.getSelectionModel().clearAndSelect(videoFrameRateSelection);

            audioBitRate.getSelectionModel().clearAndSelect(audioBitRateSelection);
            audioSampleRate.getSelectionModel().clearAndSelect(audioSamplingRateSelection);
            audioChannels.getSelectionModel().clearAndSelect(audioChannelsSelection);

        } catch (Exception IndexOutOfBoundsException){
            System.out.println("Empty list!");
        }
    }

    @FXML
    void setOutputFormat(@SuppressWarnings("unused") MouseEvent event) {
        try {
            String selectedFormat = (fileFormats.getSelectionModel().getSelectedItem().equals("mkv") ? "matroska" : fileFormats.getSelectionModel().getSelectedItem());
            conversionQueue.get(index).setOutputFormat(selectedFormat);

            this.updateVideoCodecList(selectedFormat);

            files.get(index).setOutputFormat(selectedFormat); //refresh the output column to show the chosen outputFormat
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
            selectedItem = qualityPresets.getSelectionModel().getSelectedItem();
            int qualityPreset = qualityPresets.getSelectionModel().getSelectedIndex();

            switch (selectedItem) {
                case "Low" -> bitsPerSecond = 800;
                case "High" -> bitsPerSecond = 4000;
                default -> bitsPerSecond = null;
            }

            videoBitRate.getSelectionModel().clearAndSelect(0); //it makes the videoBitRate comboBox in the advanced video tab select 0 in order to return NULL

            bitsPerSecond = Conversions.kbpsToBps(bitsPerSecond);

            files.get(index).setQualityPreset(qualityPreset);
            conversionQueue.get(index).setVideoBitRate(bitsPerSecond);

        } catch (Exception IndexOutOfBoundsException){
            System.out.println("No videos!");
        }
    }

    //this method is used on the advanced video settings tab
    @FXML
    void setVideoBitRate(@SuppressWarnings("unused") ActionEvent event) {
        try {
            selectedIndex = videoBitRate.getSelectionModel().getSelectedIndex();
            selectedItem = videoBitRate.getSelectionModel().getSelectedItem();

            if (selectedIndex != 0) {
                bitsPerSecond = Conversions.kbpsToBps(Integer.parseInt(selectedItem));
            } else {
                bitsPerSecond = null;
            }

            files.get(index).setVideoBitRateOption(selectedIndex);
            conversionQueue.get(index).setVideoBitRate(bitsPerSecond);

        } catch (Exception IndexOutOfBoundsException){
            System.out.println("No videos!");
        }
    }

    @FXML
    void setVideoCodec(@SuppressWarnings("unused") MouseEvent event) throws EncoderException {
        try {
            selectedItem = videoCodecs.getSelectionModel().getSelectedItem();
            String videoTag = "";
            String codec;

            switch (selectedItem) {
                case "Default" -> codec = null;
                case "mpeg4 (DIVX)" -> {
                    codec = "mpeg4";
                    videoTag = "DIVX";
                }
                default -> {
                    codec = selectedItem;
                    videoTag = null;
                }
            }

            conversionQueue.get(index).setVideoCodec(codec);
            conversionQueue.get(index).setVideoTag(videoTag);

            System.out.println(conversionQueue.get(index).getVideoCodec());

        } catch (Exception IndexOutOfBoundsException){
            System.out.println("No videos!");
        }
    }

    @FXML
    void setVideoFrameRate(@SuppressWarnings("unused") ActionEvent event){
        try {
            selectedIndex = videoFrameRate.getSelectionModel().getSelectedIndex();
            selectedItem = videoFrameRate.getSelectionModel().getSelectedItem();
            Integer frameRate;

            if (selectedIndex != 0) {
                frameRate = Integer.valueOf(selectedItem);
            } else {
                frameRate = null;
            }

            files.get(index).setVideoFrameRateOption(selectedIndex);
            conversionQueue.get(index).setVideoFrameRate(frameRate);
        } catch (Exception IndexOutOfBoundsException){
            System.out.println("No videos!");
        }
    }

    @FXML
    void setAudioBitRate(@SuppressWarnings("unused") ActionEvent event) {
        try {
            selectedIndex = audioBitRate.getSelectionModel().getSelectedIndex();
            selectedItem = audioBitRate.getSelectionModel().getSelectedItem();

            if (selectedIndex != 0) {
                bitsPerSecond = Conversions.kbpsToBps(Integer.parseInt(selectedItem));
            } else {
                bitsPerSecond = null;
            }

            files.get(index).setAudioBitRateOption(selectedIndex);
            conversionQueue.get(index).setAudioBitRate(bitsPerSecond);

        } catch (Exception IndexOutOfBoundsException){
            System.out.println("No videos!");
        }
    }

    @FXML
    void setAudioSampleRate(ActionEvent event) {
        try {
            selectedIndex = audioSampleRate.getSelectionModel().getSelectedIndex();
            Integer hertz;
            selectedItem = audioSampleRate.getSelectionModel().getSelectedItem();

            if (selectedIndex != 0) {
                hertz = Integer.valueOf(selectedItem);
            } else {
                hertz = null;
            }

            files.get(index).setAudioSampleRateOption(selectedIndex);
            conversionQueue.get(index).setAudioSamplingRate(hertz);

        }catch (Exception IndexOutOfBoundsException){
            System.out.println("No videos!");
        }
    }

    @FXML
    void setAudioChannels(ActionEvent event) {
        try {
            selectedIndex = audioChannels.getSelectionModel().getSelectedIndex();
            Integer channels;
            selectedItem = audioChannels.getSelectionModel().getSelectedItem();

            if (selectedIndex != 0) {
                channels = Integer.valueOf(selectedItem);
            } else {
                channels = null;
            }

            files.get(index).setAudioChannelOption(selectedIndex);
            conversionQueue.get(index).setAudioChannels(channels);

        }catch (Exception IndexOutOfBoundsException){
            System.out.println("No videos!");
        }
    }

    Service convertVideos = new Service(){
        protected Task createTask() {
            return new Task() {
                @Override
                protected Void call() throws Exception {
                    for (int index = 0; index < conversionQueue.size(); index++) {     //https://docs.oracle.com/javafx/2/api/javafx/concurrent/Service.html for more information on services;
                        files.get(index).setStatus("Converting");
                        conversionQueue.get(index).encode();                            //https://stackoverflow.com/questions/16037062/javafx-use-a-thread-more-than-once more information about the thread solution
                        fileTable.setItems(files);
                        fileTable.refresh();

                        System.out.println(conversionQueue.get(index).showInfo());

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

            for (FileDetails file : files) {
                file.setStatus("In queue");
                fileTable.setItems(files);
                fileTable.refresh();
            }

            convertVideos.reset();
            convertVideos.start();
        }
    }
}