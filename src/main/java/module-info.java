module com.example.videoproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.videoproject to javafx.fxml;
    exports com.example.videoproject;
}