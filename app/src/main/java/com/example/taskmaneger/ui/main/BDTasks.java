package com.example.taskmaneger.ui.main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class BDTasks implements Serializable {
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
            bd.getTasks().add(new Task("Title "+i,"Description "+i, 1, LocalDate.now(), LocalTime.now()));
        }
        return bd;
    }

    //Necessitem un metode per carregar el fitxer amb les dades, retornara un objecte
    //necessitem un file input stream, stream perque es un fluxe, del qual puc anar llegint coses.
    public static BDTasks getFromFile(FileInputStream fis){
        //de aquest fitxer necessitem llegir un objecte
        //el constructor de object input necessita un fitxer amb el fluxe deentrada
        try {
            ObjectInputStream ois = new ObjectInputStream(fis);
            BDTasks bd= (BDTasks) ois.readObject();
            return bd;


        } catch (Exception e) {
            return null;
        }
    }

    public boolean writeToFile(FileOutputStream fos){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
