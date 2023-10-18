module com.example.aufgabenplaner {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.aufgabenplaner to javafx.fxml;
    exports com.example.aufgabenplaner;
}