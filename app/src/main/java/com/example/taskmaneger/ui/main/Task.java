package com.example.taskmaneger.ui.main;

import java.io.Serializable;

public class Task implements Serializable {
    public String title;
    public String description;
    public int subject;
    public long deadline;

    public Task(String title,String description, int subject, long deadline){
        this.title=title;
        this.description=description;
        this.subject=subject;
        this.deadline=deadline;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public int getSubject() {
        return subject;
    }

    public long getDeadline() {
        return deadline;
    }

    public String toString(){
        return title+", "+description+", "+subject+", "+deadline;
    }
}
