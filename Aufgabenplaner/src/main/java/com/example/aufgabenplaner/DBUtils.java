
package com.example.aufgabenplaner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

/**
 * Klasse ist für den Zugriff auf die MySQL Datenbank zuständig.
 * Die Methoden Schreiben Daten und entnommen Sie aus der Datenbank.
 * @author Lukas
 */
public class DBUtils {    
    
    public static ObservableList<com.example.aufgabenplaner.users> userDataList = FXCollections.observableArrayList();
    public static ObservableList<com.example.aufgabenplaner.Tasks> tasksDataListToday = FXCollections.observableArrayList();
    public static ObservableList<com.example.aufgabenplaner.Tasks> tasksDataListUpcomming = FXCollections.observableArrayList();
    public static ObservableList<com.example.aufgabenplaner.Tasks> tasksDataListImportant = FXCollections.observableArrayList();
    public static ObservableList<com.example.aufgabenplaner.Tasks> tasksDataListSomeday = FXCollections.observableArrayList();
    public static ObservableList<com.example.aufgabenplaner.Tasks> tasksDataListTrash = FXCollections.observableArrayList();
    public static String queryNote;

    /**
     * Die Methode ist für den Wechsel der zwischen den fxml Dateien zuständig.
     * Dabei wird das momentan offene Fenster zwischengespeichert und das neue geladen, vor dem Aufruf werden die
     * Daten erneut aus der Datenbank ausgelesen und damit ebenfalls aktualisiert.
     * @param event
     * @param fxmlFile
     * @param title
     * @param username
     */
    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username) {
        Parent root = null;
        
        if(username != null) {
            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();

                com.example.aufgabenplaner.LoggedInController loggedInController = loader.getController();
                loggedInController.fillUserData(username);
                loggedInController.getUserData();
                loggedInController.showTasksToday();
                loggedInController.showTasksUpcomming();
                loggedInController.showTasksImportant();
                loggedInController.showTasksSomeday();
                loggedInController.showTasksTrash();
                loggedInController.showTasksIntent();
                loggedInController.getNote();
            } catch(IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(Objects.requireNonNull(DBUtils.class.getResource(fxmlFile)));
            } catch(IOException e) {
                e.printStackTrace();
            }            
        }
        // Das aktuell aufgerufene Fenster Speichern
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle(title);
        assert root != null;
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Öffnen der ersten fxml Datei beim Ausführen des Programms
     * @param event
     * @param fxmlFile
     * @param title
     * @param userid
     */
    public static void openScene(ActionEvent event, String fxmlFile, String title, String userid) {
        try {
            Stage stage = new Stage();
            Parent root = null;             
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile)); 
            root = loader.load();
            
            com.example.aufgabenplaner.TaskController taskController = loader.getController();
            // Speichern der UserID für die Zuordnung von neuen Einträgen in der Datenbank
            taskController.writeID(userid);
            
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode zum Update eines Tasks, zum Update wird eine neue fxml Datei geöffnet.
     * Dabei wird mittels der TaskID die Daten aus der Datenbank ausgelesen und in der Update
     * fxml Angezeigt.
     * @param event
     * @param fxmlFile
     * @param taskid
     * @param task
     */
    public static void openSceneTaskUpdate(ActionEvent event, String fxmlFile, int taskid, com.example.aufgabenplaner.Tasks task) {
        try {
            Stage stage = new Stage();
            Parent root = null;             
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile)); 
            root = loader.load();
            
            com.example.aufgabenplaner.TaskUpdateController update = loader.getController();
            // Zwischenspeichern der Aktuellen TaskID für Änderungen in der Datenbank
            update.setTaskId(taskid);           
            update.setTask(task);
            
            stage.setTitle("Aufgabenplaner");
            stage.setScene(new Scene(root));
            stage.show();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode zum Aufbau einer Verbindung zu der Datenbank
     * @return
     */
    public static Connection dbConnect() {   
        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafxdb", "root", "root");
            return dbConnection;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Methode zum Hinzufügen eines neuen Benutzers für den Administrator
     * @param username
     * @param password
     * @param email
     * @param type
     */
    public static void addUser(String username, String password, String email, String type) {
        Connection connection = null;        
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;
        
        try {
            // Datenbank Verbindung:
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafxdb", "root", "root");
            // SQL Statement:
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();

            // Prüfen ob der Benutzername bereits Vergeben wurde
            if(resultSet.isBeforeFirst()) {
                System.out.println("User already exists!");            
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username.");
                alert.show();
            } else {
                // Hinzufügen des neuen Benutzers
                psInsert = connection.prepareStatement("INSERT INTO users (username, password, emailadress, type) VALUES (?, ?, ?, ?)");        
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.setString(3, email);
                psInsert.setString(4, type);
                psInsert.execute();
            
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("User add sucessful.");
                alert.show();               
            }               
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            // Schließen der Verbindung und der SQL Anweisung
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
            if(psCheckUserExists != null) {
                try {
                    psCheckUserExists.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
            if(psInsert != null) {
                try {
                    psInsert.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
            if(connection != null) {
                try {
                    connection.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
        }
    }

    /**
     * Methode zum hinzufügen eines neuen Benutzers für den Benutzer (Registrierung)
     * @param event
     * @param username
     * @param password
     * @throws SQLException
     */
    public static void signUpUser(ActionEvent event, String username, String password) throws SQLException {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;
        
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafxdb", "root", "root");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();
            
            if(resultSet.isBeforeFirst()) {
                System.out.println("User already exists!");            
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username.");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO users (username, password, type) VALUES (?, ?, ?)");                
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.setString(3, "Client");  
                psInsert.executeUpdate();

                // Wechseln der Scene zum Login Bildschirm
                changeScene(event, "logged-in.fxml", "Aufgabenplaner", username);                
            }               
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
            if(psCheckUserExists != null) {
                try {
                    psCheckUserExists.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
            if(psInsert != null) {
                try {
                    psInsert.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
            if(connection != null) {
                try {
                    connection.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
        }
    }

    /**
     * Methode für den Login
     * @param event
     * @param username
     * @param password
     */
    public static void logInUser(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafxdb", "root", "root");
            preparedStatement = connection.prepareStatement("SELECT password FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            
            if(!resultSet.isBeforeFirst()) {
                System.out.println("User not found in Database!");            
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect!");
                alert.show();
            } else {
                while(resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    if(retrievedPassword.equals(password)) {
                        changeScene(event, "logged-in.fxml", "Aufgabenplaner", username);
                    } else {
                        System.out.println("Passwords did not match!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("The provided credentials are incorrect!");
                        alert.show();
                    }
                }                              
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
            if(preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
            if(connection != null) {
                try {
                    connection.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
        }
    }
    
    public static ObservableList<com.example.aufgabenplaner.users> getUserList() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ObservableList<com.example.aufgabenplaner.users> list = FXCollections.observableArrayList();
        
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafxdb", "root", "root");
            preparedStatement = connection.prepareStatement("SELECT * FROM users");
            resultSet = preparedStatement.executeQuery();
            
            if(!resultSet.isBeforeFirst()) {
                System.out.println("No Data found in Database!");            
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("There are no user data in the database.");
                alert.show();
            } else {
                while (resultSet.next()) {
                    list.add(new com.example.aufgabenplaner.users(Integer.parseInt(resultSet.getString("user_id")),
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            resultSet.getString("emailadress"),
                            resultSet.getString("type")));
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
            if(preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
            if(connection != null) {
                try {
                    connection.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
        }
        return list;
    }
    
    public static void editUser(String id, String username, String password, String email, String type) {
        Connection connection = null;        
        PreparedStatement psUpdate = null;
        
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafxdb", "root", "root");
            psUpdate = connection.prepareStatement("UPDATE users SET user_id=?, username=?, password=?, emailadress=?, type=? WHERE user_id=?");        
            psUpdate.setString(1, id);
            psUpdate.setString(2, username);
            psUpdate.setString(3, password);
            psUpdate.setString(4, email);
            psUpdate.setString(5, type);
            psUpdate.setString(6, id);
            psUpdate.execute();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("User update sucessful.");
            alert.show();
            
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(psUpdate != null) {
                try {
                    psUpdate.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
            if(connection != null) {
                try {
                    connection.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
        }
    }
    
    public static void deleteUser(String id) {
        Connection connection = null;        
        PreparedStatement psUpdate = null;
        
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafxdb", "root", "root");
            psUpdate = connection.prepareStatement("DELETE FROM users WHERE user_id=?");        
            psUpdate.setString(1, id);
            psUpdate.execute();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("User delete sucessful.");
            alert.show();
            
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(psUpdate != null) {
                try {
                    psUpdate.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
            if(connection != null) {
                try {
                    connection.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
        }
    }
    
    public static void loadUserData(String username) {
        Connection connection = null;        
        PreparedStatement pst = null;
        ResultSet resultSet = null;
        
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafxdb", "root", "root");
            pst = connection.prepareStatement("SELECT * FROM users WHERE username=?");        
            pst.setString(1, username);
            resultSet = pst.executeQuery();
            
            while (resultSet.next()) {
                userDataList.add(new com.example.aufgabenplaner.users(Integer.parseInt(resultSet.getString("user_id")),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("emailadress"),
                        resultSet.getString("type")));
            }
            
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(pst != null) {
                try {
                    pst.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
            if(connection != null) {
                try {
                    connection.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
        }        
    }
    
    public static void addTask(String shortde, String details, Date date, String categorie, int owner) {
        Connection connection = null;        
        PreparedStatement psInsert = null;
        ResultSet resultSet = null;
        
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafxdb", "root", "root");
            psInsert = connection.prepareStatement("INSERT INTO task (task_header, task_details, task_deadline, task_categorie, task_owner) "
                    + "VALUES (?, ?, ?, ?, ?)");        
            psInsert.setString(1, shortde);
            psInsert.setString(2, details);
            psInsert.setDate(3, date);
            psInsert.setString(4, categorie);
            psInsert.setInt(5, owner);
            psInsert.execute();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("The task was added successfully.");
            alert.show();     
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
            if(psInsert != null) {
                try {
                    psInsert.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
            if(connection != null) {
                try {
                    connection.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
        }
    }
    
    public static ObservableList<com.example.aufgabenplaner.Tasks> getTaskListToday(int userid, String categorie) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ObservableList<com.example.aufgabenplaner.Tasks> list = FXCollections.observableArrayList();
        
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafxdb", "root", "root");
            preparedStatement = connection.prepareStatement("SELECT * FROM task WHERE task_owner=? AND task_categorie=?");
            preparedStatement.setInt(1, userid);
            preparedStatement.setString(2, categorie);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                list.add(new com.example.aufgabenplaner.Tasks(Integer.parseInt(resultSet.getString("task_id")),
                        Integer.parseInt(resultSet.getString("task_owner")),
                        resultSet.getString("task_header"),
                        resultSet.getString("task_details"),
                        resultSet.getDate("task_deadline"),
                        resultSet.getString("task_categorie")));
            }
            
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
            if(preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
            if(connection != null) {
                try {
                    connection.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
        }
        return list;
    }
    
    public static ObservableList<com.example.aufgabenplaner.Tasks> getTaskListUpcomming(int userid, String categorie) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ObservableList<com.example.aufgabenplaner.Tasks> list = FXCollections.observableArrayList();
        
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafxdb", "root", "root");
            preparedStatement = connection.prepareStatement("SELECT * FROM task WHERE task_owner=? AND task_categorie=?");
            preparedStatement.setInt(1, userid);
            preparedStatement.setString(2, categorie);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                list.add(new com.example.aufgabenplaner.Tasks(Integer.parseInt(resultSet.getString("task_id")),
                        Integer.parseInt(resultSet.getString("task_owner")),
                        resultSet.getString("task_header"),
                        resultSet.getString("task_details"),
                        resultSet.getDate("task_deadline"),
                        resultSet.getString("task_categorie")));
            }
            
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
            if(preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
            if(connection != null) {
                try {
                    connection.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
        }
        return list;
    }
    
    public static ObservableList<com.example.aufgabenplaner.Tasks> getTaskListImportant(int userid, String categorie) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ObservableList<com.example.aufgabenplaner.Tasks> list = FXCollections.observableArrayList();
        
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafxdb", "root", "root");
            preparedStatement = connection.prepareStatement("SELECT * FROM task WHERE task_owner=? AND task_categorie=?");
            preparedStatement.setInt(1, userid);
            preparedStatement.setString(2, categorie);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                list.add(new com.example.aufgabenplaner.Tasks(Integer.parseInt(resultSet.getString("task_id")),
                        Integer.parseInt(resultSet.getString("task_owner")),
                        resultSet.getString("task_header"),
                        resultSet.getString("task_details"),
                        resultSet.getDate("task_deadline"),
                        resultSet.getString("task_categorie")));
            }
           
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
            if(preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
            if(connection != null) {
                try {
                    connection.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
        }
        return list;
    }
    
    public static ObservableList<com.example.aufgabenplaner.Tasks> getTaskListSomeday(int userid, String categorie) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ObservableList<com.example.aufgabenplaner.Tasks> list = FXCollections.observableArrayList();
        
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafxdb", "root", "root");
            preparedStatement = connection.prepareStatement("SELECT * FROM task WHERE task_owner=? AND task_categorie=?");
            preparedStatement.setInt(1, userid);
            preparedStatement.setString(2, categorie);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                list.add(new com.example.aufgabenplaner.Tasks(Integer.parseInt(resultSet.getString("task_id")),
                        Integer.parseInt(resultSet.getString("task_owner")),
                        resultSet.getString("task_header"),
                        resultSet.getString("task_details"),
                        resultSet.getDate("task_deadline"),
                        resultSet.getString("task_categorie")));
            }
            
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
            if(preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
            if(connection != null) {
                try {
                    connection.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
        }
        return list;
    }
    
    public static ObservableList<com.example.aufgabenplaner.Tasks> getTaskListTrash(int userid, String categorie) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ObservableList<com.example.aufgabenplaner.Tasks> list = FXCollections.observableArrayList();
        
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafxdb", "root", "root");
            preparedStatement = connection.prepareStatement("SELECT * FROM task WHERE task_owner=? AND task_categorie=?");
            preparedStatement.setInt(1, userid);
            preparedStatement.setString(2, categorie);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                list.add(new com.example.aufgabenplaner.Tasks(Integer.parseInt(resultSet.getString("task_id")),
                        Integer.parseInt(resultSet.getString("task_owner")),
                        resultSet.getString("task_header"),
                        resultSet.getString("task_details"),
                        resultSet.getDate("task_deadline"),
                        resultSet.getString("task_categorie")));
            }        
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
            if(preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
            if(connection != null) {
                try {
                    connection.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
        }
        return list;
    }
    
    public static void deleteTask(int taskID) {
        Connection connection = null;        
        PreparedStatement psUpdate = null;
        
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafxdb", "root", "root");
            psUpdate = connection.prepareStatement("DELETE FROM task WHERE task_id=?");        
            psUpdate.setInt(1, taskID);
            psUpdate.execute();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Task delete sucessful.");
            alert.show();
            
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(psUpdate != null) {
                try {
                    psUpdate.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
            if(connection != null) {
                try {
                    connection.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
        }
    }
    
    public static void updateTask(int taskID, String header, String details, String categorie, Date date) {
        Connection connection = null;        
        PreparedStatement psUpdate = null;
        
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafxdb", "root", "root");
            psUpdate = connection.prepareStatement("UPDATE task SET task_header=?, task_details=?, task_deadline=?, task_categorie=? WHERE task_id=?");        
            psUpdate.setString(1, header);
            psUpdate.setString(2, details);
            psUpdate.setDate(3, date);
            psUpdate.setString(4, categorie);
            psUpdate.setInt(5, taskID);
            psUpdate.executeUpdate();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Task Update sucessful.");
            alert.show();
            
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(psUpdate != null) {
                try {
                    psUpdate.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
            if(connection != null) {
                try {
                    connection.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
        }
    }
    
    public static void saveNotes(int userID, String details) {
        Connection connection = null;        
        PreparedStatement psCount = null;
        ResultSet resultSet = null;
        
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafxdb", "root", "root");
            psCount = connection.prepareStatement("SELECT COUNT(note_owner) AS noteCount FROM notes WHERE note_owner=?");        
            psCount.setInt(1, userID);            
            resultSet = psCount.executeQuery();
            resultSet.next();
                    
            if(resultSet.getInt("noteCount") == 1) {
                psCount = connection.prepareStatement("UPDATE notes SET note_details=? WHERE note_owner=?");  
                psCount.setString(1, details);
                psCount.setInt(2, userID); 
                psCount.executeUpdate();
            } else {
                psCount = connection.prepareStatement("INSERT INTO notes (note_details, note_owner) VALUES (?, ?)");  
                psCount.setString(1, details);
                psCount.setInt(2, userID);                
                psCount.execute();
            }
            
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(psCount != null) {
                try {
                    psCount.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
            if(connection != null) {
                try {
                    connection.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
        }
    }
    
    public static void loadNotes(int userID, String details) {
        Connection connection = null;        
        PreparedStatement psCount = null;
        ResultSet resultSet = null;
        
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafxdb", "root", "root");
            psCount = connection.prepareStatement("SELECT note_details FROM notes WHERE note_owner=?");        
            psCount.setInt(1, userID);            
            resultSet = psCount.executeQuery();
            resultSet.next();
                    
            queryNote = resultSet.getString(1);
            
            
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(psCount != null) {
                try {
                    psCount.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
            if(connection != null) {
                try {
                    connection.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }                
            }
        }
    }
    
    
}
