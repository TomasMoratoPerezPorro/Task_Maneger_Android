package com.example.taskmaneger.ui.main;

import android.icu.util.LocaleData;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task implements Serializable {
    public String title;
    public String description;
    public int subject;
    //public long deadline;
    public LocalDate date;

    public Task(String title,String description, int subject, LocalDate date){
        this.title=title;
        this.description=description;
        this.subject=subject;
        //this.deadline=deadline;
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

   // public long getDeadline() {
        //return deadline;
   // }

    public LocalDate getDate(){
        return date;
    }

    public String toStringDate(){
        String formattedDate = this.date.format(DateTimeFormatter.ofPattern("EEEE, d MMM"));
        return formattedDate;
    }



    public String toString(){
        return title+", "+description+", "+subject+", "+date;
    }
}
