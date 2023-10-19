
package com.example.aufgabenplaner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Kontroller für die fxml "sign-up.fxml" Datei zum Registrieren von neuen Benutzern
 * @author Lukas
 */
public class SignUpController implements Initializable {
    
    @FXML
    private Button button_log_in;
    @FXML
    private Button button_signup;
    
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;

    /**
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        

        // Einloggen Funktion:
        button_log_in.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "sample.fxml", "Aufgabenplaner", null);
            }            
        });

        // Registrierungs-Funktion:
        button_signup.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if(!tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty()) {
                    try {
                        DBUtils.signUpUser(event, tf_username.getText(), tf_password.getText());
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    System.out.println("Please fill in all information.");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all information to sign up!");
                    alert.show();
                }
            }            
        });
    }
    
}
