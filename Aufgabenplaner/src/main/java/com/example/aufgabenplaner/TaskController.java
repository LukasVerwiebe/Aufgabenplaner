
package com.example.aufgabenplaner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Kontroller für die fxml "new-task.fxml" Datei zum Hinzufügen von Task Objekten
 * @author Lukas
 */
public class TaskController implements Initializable {
    
    @FXML
    private Button button_cancel;

    @FXML
    private Button button_ok;

    @FXML
    private ComboBox<String> choice_categories;

    @FXML
    private DatePicker task_date;

    @FXML
    private TextArea txt_details;

    @FXML
    private TextField txt_shortde;
    
    @FXML
    private Label label_userid;

    // Liste für die Status Auswahl
    private String[] categorie = {"In Progress", "Someday", "Important", "Waiting", "Approved"};
    private String fxmlUserID = "logged-in.fxml";

    /**
     * Laden der Task Scene
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Load Task Categories:
        choice_categories.getItems().addAll(categorie);
        choice_categories.setValue("In Progress");
        
        // Set Task Date:
        task_date.setValue(LocalDate.now());
        
        button_cancel.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();                
            }            
        });
        
        button_ok.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                // Get SQL Date:
                java.sql.Date getDate = java.sql.Date.valueOf(task_date.getValue());
                // Write new Task to Database:
                DBUtils.addTask(txt_shortde.getText(), txt_details.getText(),
                        getDate,
                        choice_categories.getValue(),
                        Integer.parseInt(label_userid.getText()));
                // Delete Infos from TextFields:
                emptyFields();                
            }            
        });
        
    }

    // Leeren der Felder nach dem Speichern in der Datenbank
    public void emptyFields() {        
        txt_details.clear();
        txt_shortde.clear();
        task_date.setValue(LocalDate.now());
        choice_categories.getSelectionModel().clearSelection();
        choice_categories.setPromptText("Categories");
    }

    // Schreiben der UserID auf das Task Objekt
    public void writeID(String userid) {
        label_userid.setText(userid);
    }
}
