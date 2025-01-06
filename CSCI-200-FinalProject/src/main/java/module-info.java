module com.example.csci200finalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.csci200finalproject to javafx.fxml;
    exports com.example.csci200finalproject;
}