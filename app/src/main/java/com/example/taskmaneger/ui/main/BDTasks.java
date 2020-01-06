package com.example.taskmaneger.ui.main;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class BDTasks {
    private List<Task> task;

    public BDTasks(){
        this.task=new ArrayList<Task>();
    }

    public List<Task> getTasks(){
        return task;
    }

    public static BDTasks getDummyTasks(){
        BDTasks bd = new BDTasks();
        for (int i=1;i<=100; i++){
            bd.getTasks().add(new Task("Title "+i,"Description "+i, 1,06012020));
        }
        return bd;
    }
}
