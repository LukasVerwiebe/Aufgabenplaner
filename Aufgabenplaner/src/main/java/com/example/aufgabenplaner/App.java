
package com.example.aufgabenplaner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Diese Klasse startet die Applikation aus und ruft die start fxml Datei auf.
 * @author Lukas Verwiebe
 */
public class App extends Application {

    /**
     * Die Methode lädt die fxml Datei und zeigt diese dann an.
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sample.fxml")));
        stage.setTitle("Aufgabenplaner");
        stage.getIcons().add(new Image("todo.png")); 
        stage.setScene(new Scene(root));
        //stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    /**
     * Die Methode
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
