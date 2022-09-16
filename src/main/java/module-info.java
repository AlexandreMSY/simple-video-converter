module com.example.videoproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.apache.commons.io;


    opens com.example.videoproject to javafx.fxml;
    exports com.example.videoproject;
}