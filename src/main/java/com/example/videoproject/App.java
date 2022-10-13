package com.example.videoproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("mainGui.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Video Converter");
        stage.getIcons().add(new Image("file:C:\\Users\\roadr\\Desktop\\simple-video-converter\\src\\video_tape.png"));
        //stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}