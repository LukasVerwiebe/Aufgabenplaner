
package com.example.aufgabenplaner;

import java.sql.Date;

/**
 * Klasse zur Verwaltung von Tasks im Programm.
 * Beim Aufruf von Tasks und deren Daten wird ein Task-objekt erstellt um die Daten
 * einfacher verwenden zu können.
 * @author Lukas
 */
public class Tasks {
    
    int task_id, owner_id;
    String task_header, task_details, task_categories;
    Date task_deadline;

    // Task Konstruktor:
    public Tasks(int task_id, int owner_id, String task_header, String task_details, Date task_deadline, String task_categories) {
        this.task_id = task_id;
        this.owner_id = owner_id;
        this.task_header = task_header;
        this.task_details = task_details;
        this.task_deadline = task_deadline;
        this.task_categories = task_categories;
    }


    // Getter und Setter
    public int getTask_id() {
        return task_id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public String getTask_header() {
        return task_header;
    }

    public String getTask_details() {
        return task_details;
    }

    public Date getTask_deadline() {
        return task_deadline;
    }

    public String getTask_categories() {
        return task_categories;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public void setTask_header(String task_header) {
        this.task_header = task_header;
    }

    public void setTask_details(String task_details) {
        this.task_details = task_details;
    }

    public void setTask_deadline(Date task_deadline) {
        this.task_deadline = task_deadline;
    }

    public void setTask_categories(String task_categories) {
        this.task_categories = task_categories;
    }
    
    public String getDateString() {
        return task_deadline.toString();
    }
    
}
