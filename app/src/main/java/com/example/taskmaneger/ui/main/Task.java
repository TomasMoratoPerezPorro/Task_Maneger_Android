package com.example.taskmaneger.ui.main;

import android.icu.util.LocaleData;

import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable {
    public String title;
    public String description;
    public int subject;
    public long deadline;
    public LocalDate date;

    public Task(String title,String description, int subject, long deadline){
        this.title=title;
        this.description=description;
        this.subject=subject;
        this.deadline=deadline;

    }

    public Task(String title, String description, int subject, long deadline, LocalDate date){
        this.title=title;
        this.description=description;
        this.subject=subject;
        this.deadline=deadline;
        this.date=date;
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

    public LocalDate getDate(){
        return date;
    }





    public String toString(){
        return title+", "+description+", "+subject+", "+deadline;
    }
}
