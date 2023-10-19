
package com.example.aufgabenplaner;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Kontroller für die fxml "logged-in.fxml" Datei für den Start Bildschirm
 * @author Lukas
 */
public class LoggedInController implements Initializable {
    
    @FXML
    private Button button_logout;
    @FXML
    private Button button_adduser;
    @FXML
    private Button button_update;
    @FXML
    private Button button_delete;
    @FXML
    private Button button_pv_update;
    @FXML
    private Button button_newTask;
    @FXML
    private Button button_refresh;
    @FXML
    private Button button_note;
    
    @FXML
    private TextArea txt_note;
    
    @FXML
    private TableView<com.example.aufgabenplaner.users> tv_userlist;
    
    @FXML
    private TableColumn<com.example.aufgabenplaner.users, String> col_email;
    @FXML
    private TableColumn<com.example.aufgabenplaner.users, Integer> col_id;
    @FXML
    private TableColumn<com.example.aufgabenplaner.users, String> col_password;
    @FXML
    private TableColumn<com.example.aufgabenplaner.users, String> col_user;
    @FXML
    private TableColumn<com.example.aufgabenplaner.users, String> col_type;
    
    @FXML
    private TextField txt_id;
    @FXML
    private TextField txt_email;
    @FXML
    private TextField txt_password;
    @FXML
    private TextField txt_type;
    @FXML
    private TextField txt_username;
    @FXML
    private TextField pv_email;
    @FXML
    private TextField pv_password;
    @FXML
    private TextField pv_type;
    @FXML
    private TextField pv_userid;
    @FXML
    private TextField pv_username;    
    @FXML
    private ComboBox chbx_type;
    @FXML
    private TextField txt_search;
    
    @FXML
    private TableView<com.example.aufgabenplaner.Tasks> tv_today;
    @FXML
    private TableView<com.example.aufgabenplaner.Tasks> tv_upcomming;
    @FXML
    private TableView<com.example.aufgabenplaner.Tasks> tv_important;
    @FXML
    private TableView<com.example.aufgabenplaner.Tasks> tv_someday;
    @FXML
    private TableView<com.example.aufgabenplaner.Tasks> tv_trash;
    
    @FXML
    private ListView<String> listview_intent;
    
    @FXML
    private TableColumn<com.example.aufgabenplaner.Tasks, String> col_task_today;
    @FXML
    private TableColumn<com.example.aufgabenplaner.Tasks, String> col_status_today;
    @FXML
    private TableColumn<com.example.aufgabenplaner.Tasks, String> col_task_upcomming;
    @FXML
    private TableColumn<com.example.aufgabenplaner.Tasks, String> col_status_upcomming;
    @FXML
    private TableColumn<com.example.aufgabenplaner.Tasks, String> col_task_important;
    @FXML
    private TableColumn<com.example.aufgabenplaner.Tasks, String> col_status_important;
    @FXML
    private TableColumn<com.example.aufgabenplaner.Tasks, String> col_task_someday;
    @FXML
    private TableColumn<com.example.aufgabenplaner.Tasks, String> col_status_someday;
    @FXML
    private TableColumn<com.example.aufgabenplaner.Tasks, String> col_task_trash;
    @FXML
    private TableColumn<com.example.aufgabenplaner.Tasks, String> col_status_trash;
    
    @FXML
    private Pane pane_today, pane_upcomming, pane_someday, pane_important, pane_trash;    
    
    ObservableList<com.example.aufgabenplaner.Tasks> listTasksToday;
    ObservableList<com.example.aufgabenplaner.Tasks> listTasksUpcomming;
    ObservableList<com.example.aufgabenplaner.Tasks> listTasksImportant;
    ObservableList<com.example.aufgabenplaner.Tasks> listTasksSomeday;
    ObservableList<com.example.aufgabenplaner.Tasks> listTasksTrash;
     
    ObservableList<com.example.aufgabenplaner.users> listA;
    ObservableList<com.example.aufgabenplaner.users> search;
    int index = -1;    
    private String[] type = {"Client", "Admin"};
    private com.example.aufgabenplaner.users newUser;
    
    private ContextMenu contextMenu = new ContextMenu();
    private MenuItem delete = new MenuItem("Delete");
    private MenuItem edit = new MenuItem("Edit");
    private String taskHaed;
    private String taskCategorie;
    private int task_id;
    private com.example.aufgabenplaner.Tasks task;
    private String text;

    /**
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Load User Type:
        chbx_type.getItems().addAll(type);
        chbx_type.setValue("Client");
        
        // Load the User Table:
        showUsers();
        serche_uer();
        
        // Context Menu:
        contextMenu.getItems().add(edit);
        contextMenu.getItems().add(delete);
        tv_today.setContextMenu(contextMenu);
        tv_upcomming.setContextMenu(contextMenu);
        tv_important.setContextMenu(contextMenu);
        tv_someday.setContextMenu(contextMenu);
        tv_trash.setContextMenu(contextMenu);
        
        delete.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                DBUtils.deleteTask(getTaskID());
                showTasksToday();
                showTasksImportant();
                showTasksSomeday();
                showTasksUpcomming();
                showTasksTrash();
                showTasksIntent();
            }            
        });
        
        edit.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                DBUtils.openSceneTaskUpdate(event, "update-task.fxml", task_id, task);
            }            
        });
        
//        button_logout.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                DBUtils.changeScene(event, "sample.fxml", "Aufgabenplaner", null);
//                
//            }                
//        }); 
        
//        EventHandler<WindowEvent> event = new EventHandler<WindowEvent>() {
//            public void handle(WindowEvent e)
//            {
//                if (contextMenu.isShowing()) {
//                    contextMenu.DBUtils.deleteTask(, );
//                } else {
//                    
//                }
//            }
//        };
        
        button_adduser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { 
                if(!txt_username.getText().trim().isEmpty() && !txt_password.getText().trim().isEmpty()) {
                    try {
                        DBUtils.addUser(txt_username.getText(), txt_password.getText(), txt_email.getText(), chbx_type.getValue().toString());
                        showUsers();
                        emptyFields();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Please fill in all information.");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all information to sign up!");
                    alert.show();                    
                }
            }                
        });
        
        button_update.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { 
                if(!txt_username.getText().trim().isEmpty() && !txt_password.getText().trim().isEmpty()) {
                    try {
                        DBUtils.editUser(txt_id.getText(), txt_username.getText(), txt_password.getText(), txt_email.getText(), chbx_type.getValue().toString());
                        showUsers();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Please fill in all information.");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all information to sign up!");
                    alert.show();                    
                }
            }                
        });
        
        button_delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    DBUtils.deleteUser(txt_id.getText());
                    showUsers();
                    emptyFields();
                } catch (Exception e) {
                    e.printStackTrace();
                }             
            }                
        });
        
        button_pv_update.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { 
                if(!pv_username.getText().trim().isEmpty() && !pv_password.getText().trim().isEmpty()) {
                    try {
                        DBUtils.editUser(pv_userid.getText(), pv_username.getText(), pv_password.getText(), pv_email.getText(), pv_type.getText());
                        showUsers();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Please fill in all information.");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all information to sign up!");
                    alert.show();                    
                }
            }                
        });
        
        button_newTask.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                DBUtils.openScene(event, "new_task.fxml", "Aufgabenplaner", pv_userid.getText());                
            }            
        });
        
        button_refresh.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                showTasksToday();
                showTasksImportant();
                showTasksSomeday();
                showTasksUpcomming();
                showTasksTrash();
                showTasksIntent();
            }            
        });
        
        button_note.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                DBUtils.saveNotes(Integer.parseInt(pv_userid.getText()), txt_note.getText());
            }            
        });
    } 
    
    // Select User //
    @FXML
    void getSelected(MouseEvent event) {
        index = tv_userlist.getSelectionModel().getSelectedIndex();
        if(index <= -1) {
            return;
        }
        txt_id.setText(col_id.getCellData(index).toString());
        txt_username.setText(col_user.getCellData(index));
        txt_password.setText(col_password.getCellData(index));
        txt_email.setText(col_email.getCellData(index));
        chbx_type.setValue(col_type.getCellData(index));
    }
    
    // Select Task //
    @FXML
    void getSelectedTask(MouseEvent event) {
        index = tv_today.getSelectionModel().getSelectedIndex();
        if(index <= -1) {
            return;
        }
        taskHaed = col_task_today.getCellData(index).toString();
        taskCategorie = col_status_today.getCellData(index).toString();
        for(int i = 0; i < listTasksToday.size(); i++) {
            if(listTasksToday.get(i).getTask_header().equals(col_task_today.getCellData(index).toString())) {
                task_id = listTasksToday.get(i).getTask_id();
                task = listTasksToday.get(i);                
            }            
        }  
    }
    
    // Select Task //
    @FXML
    void getSelectedTaskToday(MouseEvent event) {
        index = tv_today.getSelectionModel().getSelectedIndex();
        if(index <= -1) {
            return;
        }
        taskHaed = col_task_today.getCellData(index).toString();
        taskCategorie = col_status_today.getCellData(index).toString();
        for(int i = 0; i < listTasksToday.size(); i++) {
            if(listTasksToday.get(i).getTask_header().equals(col_task_today.getCellData(index).toString())) {
                task_id = listTasksToday.get(i).getTask_id();
                task = listTasksToday.get(i);                
            }            
        }  
    }
    
    // Select Task //
    @FXML
    void getSelectedTaskUpcomming(MouseEvent event) {
        index = tv_upcomming.getSelectionModel().getSelectedIndex();
        if(index <= -1) {
            return;
        }
        taskHaed = col_task_upcomming.getCellData(index).toString();
        taskCategorie = col_status_upcomming.getCellData(index).toString();
        for(int i = 0; i < listTasksToday.size(); i++) {
            if(listTasksUpcomming.get(i).getTask_header().equals(col_task_upcomming.getCellData(index).toString())) {
                task_id = listTasksUpcomming.get(i).getTask_id();
                task = listTasksUpcomming.get(i);                
            }            
        }  
    }
    
    // Select Task //
    @FXML
    void getSelectedTaskImportant(MouseEvent event) {
        index = tv_important.getSelectionModel().getSelectedIndex();
        if(index <= -1) {
            return;
        }
        taskHaed = col_task_important.getCellData(index).toString();
        taskCategorie = col_status_important.getCellData(index).toString();
        for(int i = 0; i < listTasksImportant.size(); i++) {
            if(listTasksImportant.get(i).getTask_header().equals(col_task_important.getCellData(index).toString())) {
                task_id = listTasksImportant.get(i).getTask_id();
                task = listTasksImportant.get(i);                
            }            
        }  
    }
    
    // Select Task //
    @FXML
    void getSelectedTaskSomeday(MouseEvent event) {
        index = tv_someday.getSelectionModel().getSelectedIndex();
        if(index <= -1) {
            return;
        }
        taskHaed = col_task_someday.getCellData(index).toString();
        taskCategorie = col_status_someday.getCellData(index).toString();
        for(int i = 0; i < listTasksSomeday.size(); i++) {
            if(listTasksSomeday.get(i).getTask_header().equals(col_task_someday.getCellData(index).toString())) {
                task_id = listTasksSomeday.get(i).getTask_id();
                task = listTasksSomeday.get(i);                
            }            
        }  
    }
    
    // Select Task //
    @FXML
    void getSelectedTaskTrash(MouseEvent event) {
        index = tv_trash.getSelectionModel().getSelectedIndex();
        if(index <= -1) {
            return;
        }
        taskHaed = col_task_trash.getCellData(index).toString();
        taskCategorie = col_status_trash.getCellData(index).toString();
        for(int i = 0; i < listTasksTrash.size(); i++) {
            if(listTasksTrash.get(i).getTask_header().equals(col_task_trash.getCellData(index).toString())) {
                task_id = listTasksTrash.get(i).getTask_id();
                task = listTasksTrash.get(i);                
            }            
        }  
    }
            
    public void showUsers() {
        col_id.setCellValueFactory(new PropertyValueFactory<com.example.aufgabenplaner.users, Integer>("id"));
        col_user.setCellValueFactory(new PropertyValueFactory<com.example.aufgabenplaner.users, String>("username"));
        col_password.setCellValueFactory(new PropertyValueFactory<com.example.aufgabenplaner.users, String>("password"));
        col_email.setCellValueFactory(new PropertyValueFactory<com.example.aufgabenplaner.users, String>("emailadress"));
        col_type.setCellValueFactory(new PropertyValueFactory<com.example.aufgabenplaner.users, String>("type"));
        
        listA = DBUtils.getUserList();
        tv_userlist.setItems(listA);        
    }
    
    
    
    public void getNote() {
        DBUtils.loadNotes(Integer.parseInt(pv_userid.getText()), txt_note.getText());
        text = DBUtils.queryNote;
        txt_note.setText(text);
    }
    
    public void emptyFields() {
        txt_username.clear();
        txt_password.clear();
        txt_email.clear();
        chbx_type.setValue("Client");
    }
    
    public void fillUserData(String username) {
        pv_username.setText(username);
    }
    
    public void getUserData() {        
        DBUtils.loadUserData(pv_username.getText());
        pv_password.setText(DBUtils.userDataList.get(0).password);
        pv_userid.setText(Integer.toString(DBUtils.userDataList.get(0).getId()));
        pv_email.setText(DBUtils.userDataList.get(0).emailadress);
        pv_type.setText(DBUtils.userDataList.get(0).type);
    }
    
    public void showTasksToday() {
        col_task_today.setCellValueFactory(new PropertyValueFactory<com.example.aufgabenplaner.Tasks, String>("task_header"));
        col_status_today.setCellValueFactory(new PropertyValueFactory<com.example.aufgabenplaner.Tasks, String>("task_categories"));
        listTasksToday = DBUtils.getTaskListToday(Integer.parseInt(pv_userid.getText()), "In Progress");
        tv_today.setItems(listTasksToday);         
    }
    
    public void showTasksUpcomming() {
        col_task_upcomming.setCellValueFactory(new PropertyValueFactory<com.example.aufgabenplaner.Tasks, String>("task_header"));
        col_status_upcomming.setCellValueFactory(new PropertyValueFactory<com.example.aufgabenplaner.Tasks, String>("task_categories"));
        listTasksUpcomming = DBUtils.getTaskListUpcomming(Integer.parseInt(pv_userid.getText()), "Waiting");
        tv_upcomming.setItems(listTasksUpcomming);         
    }
    
    public void showTasksImportant() {
        col_task_important.setCellValueFactory(new PropertyValueFactory<com.example.aufgabenplaner.Tasks, String>("task_header"));
        col_status_important.setCellValueFactory(new PropertyValueFactory<com.example.aufgabenplaner.Tasks, String>("task_categories"));
        listTasksImportant = DBUtils.getTaskListImportant(Integer.parseInt(pv_userid.getText()), "Important");
        tv_important.setItems(listTasksImportant);         
    }
    
    public void showTasksSomeday() {
        col_task_someday.setCellValueFactory(new PropertyValueFactory<com.example.aufgabenplaner.Tasks, String>("task_header"));
        col_status_someday.setCellValueFactory(new PropertyValueFactory<com.example.aufgabenplaner.Tasks, String>("task_categories"));
        listTasksSomeday = DBUtils.getTaskListSomeday(Integer.parseInt(pv_userid.getText()), "Someday");
        tv_someday.setItems(listTasksSomeday);      
    }
    
    public void showTasksTrash() {
        col_task_trash.setCellValueFactory(new PropertyValueFactory<com.example.aufgabenplaner.Tasks, String>("task_header"));
        col_status_trash.setCellValueFactory(new PropertyValueFactory<com.example.aufgabenplaner.Tasks, String>("task_categories"));
        listTasksTrash = DBUtils.getTaskListTrash(Integer.parseInt(pv_userid.getText()), "Approved");
        tv_trash.setItems(listTasksTrash);         
    }
    
    public void showTasksIntent() {
        listTasksSomeday = DBUtils.getTaskListSomeday(Integer.parseInt(pv_userid.getText()), "Someday");
        ArrayList<String> intent = new ArrayList<>();
        for(int i = 0; i < listTasksSomeday.size(); i++) {
            intent.add(listTasksSomeday.get(i).task_header);
        }
        listview_intent.getItems().removeAll(intent);        
        listview_intent.getItems().addAll(intent);         
    }      
    
    public void tabToday(ActionEvent event) { 
        pane_today.toFront();
    }
    
    public void tabUpcomming(ActionEvent event) { 
        pane_upcomming.toFront();
    }
    
    public void tabImportant(ActionEvent event) { 
        pane_important.toFront();
    }
    
    public void tabSomeday(ActionEvent event) { 
        pane_someday.toFront();
    }
    
    public void tabTrash(ActionEvent event) { 
        pane_trash.toFront();
    }
    
    public String getUsername() {
        return this.pv_username.getText();
    }
    
    public int getUserID() {
        return Integer.parseInt(this.pv_userid.getText());
    }
    
    public void setUser(com.example.aufgabenplaner.users user) {
        this.newUser = user;
    }
    
    public com.example.aufgabenplaner.users getUser() {
        return this.newUser;
    }
    
    public int getTaskID() {
        return this.task_id;
    }
    
    @FXML
    void serche_uer() {
        col_id.setCellValueFactory(new PropertyValueFactory<com.example.aufgabenplaner.users, Integer>("id"));
        col_user.setCellValueFactory(new PropertyValueFactory<com.example.aufgabenplaner.users, String>("username"));
        col_password.setCellValueFactory(new PropertyValueFactory<com.example.aufgabenplaner.users, String>("password"));
        col_email.setCellValueFactory(new PropertyValueFactory<com.example.aufgabenplaner.users, String>("emailadress"));
        col_type.setCellValueFactory(new PropertyValueFactory<com.example.aufgabenplaner.users, String>("type"));
        
        search = DBUtils.getUserList();
        tv_userlist.setItems(search); 
        FilteredList<com.example.aufgabenplaner.users> filteredData = new FilteredList<>(search, b -> true);
        txt_search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                
                if(person.getUsername().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(person.getPassword().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(String.valueOf(person.getEmailadress()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<com.example.aufgabenplaner.users> sortedData  = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tv_userlist.comparatorProperty());
        tv_userlist.setItems(sortedData);
    }
    
}
