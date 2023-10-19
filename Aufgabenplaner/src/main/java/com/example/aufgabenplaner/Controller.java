
package com.example.aufgabenplaner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Kontroller für den Login Bildschirm "sample.fxml"
 * @author Lukas
 */
public class Controller implements Initializable {
    
    @FXML
    private Button button_login;
    @FXML
    private Button button_sign_up;
    
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;
    
    private Parent root;

    /**
     * Funktionen zum Login und der Registrierung
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        

        // Login Button: Übergibt die beim Login Vorgang eingegebenen Daten an die Methode logInUser in der Datei DBUtils
        button_login.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                com.example.aufgabenplaner.DBUtils.logInUser(event, tf_username.getText(), tf_password.getText());
            }            
        });

        // Sign-up Button: Wechsel zu der fxml Datei mit dem Regestrierungs-Formular
        button_sign_up.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                com.example.aufgabenplaner.DBUtils.changeScene(event, "sign-up.fxml", "Aufgabenplaner", null);
            }            
        });
    }
}
