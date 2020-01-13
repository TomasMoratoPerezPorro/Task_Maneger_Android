package com.example.taskmaneger.ui.main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BDSubjects implements Serializable {

    private List<Subject> subjects;

    public BDSubjects() {
        this.subjects = new ArrayList<Subject>();
    }

    public List<Subject> getSubjects() {
        return subjects;
    }



    public static BDSubjects getDummySubjects(){
        BDSubjects bd = new BDSubjects();
        for (int i=1;i<=100; i++){
            bd.getSubjects().add(new Subject("Subject number "+i));
        }
        return bd;
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

    public static BDSubjects getFromFile(FileInputStream fis){
        //de aquest fitxer necessitem llegir un objecte
        //el constructor de object input necessita un fitxer amb el fluxe deentrada
        try {
            ObjectInputStream ois = new ObjectInputStream(fis);
            BDSubjects bd= (BDSubjects) ois.readObject();
            return bd;


        } catch (Exception e) {
            return null;
        }
    }
}
