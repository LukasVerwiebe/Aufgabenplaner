
package com.example.aufgabenplaner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Kontroller für die fxml "update-task.fxml" Datei zum Update von Task Objekten
 * @author Lukas
 */
public class TaskUpdateController implements Initializable {
    
    @FXML
    private Button button_cancel;
    @FXML
    private Button button_ok;
    
    @FXML
    private TextField txt_shortde;    
    @FXML
    private TextArea txt_details;
    
    @FXML
    private ComboBox<String> choice_categories;
    
    @FXML
    private DatePicker task_date;
    
    @FXML
    private Label label_userid;

    // Liste für die Status Auswahl
    private String[] categorie = {"In Progress", "Someday", "Important", "Waiting", "Approved"};
    // Zwischenspeicher für die TaskID
    private int taskID;
    private Tasks task;

    /**
     * Laden der Task Scene
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Load Task Categories:
        choice_categories.getItems().addAll(categorie); 
        
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
                // Update Task to Database:
                DBUtils.updateTask(taskID, 
                        txt_shortde.getText(),
                        txt_details.getText(),                        
                        choice_categories.getValue(),
                        getDate);
                // Close window:
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();  
            }            
        });
        
    }

    // Setzten der TaskID für den Aufruf der Task Daten aus der Datenbank
    public void setTaskId(int taskid) {
        this.taskID = taskid;
        label_userid.setText(Integer.toString(taskid));
    }

    // Task Daten beim Laden des Scene in die Felder laden
    public void setTask(Tasks task) {
        this.task = task;
        txt_shortde.setText(task.getTask_header());
        txt_details.setText(task.getTask_details());
        choice_categories.setValue(task.getTask_categories());
        task_date.setValue(task.getTask_deadline().toLocalDate());
    }
}
