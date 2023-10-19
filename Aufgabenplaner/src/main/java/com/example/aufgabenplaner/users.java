
package com.example.aufgabenplaner;

/**
 * Klasse User zur Verwaltung von Benutzern im Programm.
 * Beim Aufruf von Benutzern und deren Daten wird ein Benutzerobjekt erstellt um die Daten
 * einfacher verwenden zu können.
 * @author Lukas
 */
public class users {
    
    int id;    
    String username, password, emailadress, type;

    /**
     * Konstruktor für den Benutzer
     * @param id
     * @param username
     * @param password
     * @param emailadress
     * @param type
     */
    public users(int id, String username, String password, String emailadress, String type) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.emailadress = emailadress;
        this.type = type;
    }

    // Leerer Konstruktor
    users() {
        
    }


    // Getter und Setter
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmailadress() {
        return emailadress;
    }
    
    public String getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmailadress(String emailadress) {
        this.emailadress = emailadress;
    }    
    
    public void setType(String type) {
        this.type = type;
    } 
}
