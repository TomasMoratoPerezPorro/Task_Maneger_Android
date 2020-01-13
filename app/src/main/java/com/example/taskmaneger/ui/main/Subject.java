package com.example.taskmaneger.ui.main;

import java.io.Serializable;

public class Subject implements Serializable {
    public String name;

    public Subject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return name;
    }
}
