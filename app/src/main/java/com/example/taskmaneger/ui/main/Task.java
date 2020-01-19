package com.example.taskmaneger.ui.main;

import android.icu.util.LocaleData;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Task implements Serializable {
    public String title;
    public String description;
    public int subject;
    //public long deadline;
    public LocalDate date;
    public LocalTime hour;

    public Task(String title,String description, int subject, LocalDate date, LocalTime hour){
        this.title=title;
        this.description=description;
        this.subject=subject;

        this.date=date;
        this.hour=hour;

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



    public LocalDate getDate(){
        return date;
    }

    public String toStringDate(){
        String formattedDate = this.date.format(DateTimeFormatter.ofPattern("EEEE, d MMM"));
        return formattedDate;
    }

    public String toStringDateSmall(){
        String formattedDate = this.date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return formattedDate;
    }

    public LocalTime getHour(){
        return hour;
    }

    public String toStringHour(){
        DateTimeFormatter timeFormatter1 = DateTimeFormatter.ofPattern("HH:mm a");
        return this.hour.format(timeFormatter1);
    }

    public String toStringHourSmall(){
        DateTimeFormatter timeFormatter1 = DateTimeFormatter.ofPattern("HH:mm");
        return this.hour.format(timeFormatter1);
    }



    public String toString(){
        return title+", "+description+", "+subject+", "+date+", "+hour;
    }
}
