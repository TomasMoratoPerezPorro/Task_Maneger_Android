package com.example.taskmaneger.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taskmaneger.R;

public class EditTaskActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextDescription,editTextDeadline;
    private Button buttonSave;

    //definirem atributs de classe estatics i publics per definir quins parametres pot rebre la classe (desde el intent de main activity)
    public static String TASK_PARAMETER = "TASK_PARAMETER";
    public static String OUT_TASK_PARAMETER = "OUT_TASK_PARAMETER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        //Per Consultar les dades amb les que hem cridat a la activity
        //Retorna les dades amb les que s'ha creat l'activitat,, get extras retorna un objecte de tipus bundle.
        Bundle bundle = getIntent().getExtras();

        //ara hem de asociar cada atribut amb el objecte de la interfície
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextDeadline = findViewById(R.id.editTextDeadline);

        buttonSave = findViewById(R.id.buttonAdd);


        if(bundle!=null){
            Task task = (Task) bundle.getSerializable(TASK_PARAMETER);
            //Primer mirar si es null, si no es null agafar les dades de aquesta canço i posar-les al edit text que correspon amb el textview by id
            if(task!=null){
                //ara hem de posar les dades de la canço a cada edittext de la pantalla
                //per cadascun dels edit text definirem un atribut de clase
                //ara hem de posar les dades corresponents a cada atribut

                editTextTitle.setText(task.getTitle());
                editTextDescription.setText((task.getDescription()));
                editTextDeadline.setText(Long.toString(task.getDeadline())); ///Tots els elements visuals funcionen amb Strings, tenim que convertir
                buttonSave = findViewById(R.id.buttonAdd);
                //ara afegim el listener
                buttonSave.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        //aqui simplement cridem a un metode que definirem a la classe
                        saveTask();
                    }
                });
            }
        }
    }

    private void saveTask() {
        //Hem de desar les dades en un objecte de la classe canço
        //per veure-ho mes clar generarem una variable per a cada dada de la canço:
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();

        int deadline;
        try{
            deadline = (int) Long.parseLong(editTextDeadline.getText().toString());
        }
        catch(Exception e){
            deadline =2019;
        }

        Task task = new Task(title,description,1,deadline);
        Intent intent = new Intent();
        //definim un nom per als parametres de sortida, que no tenen perque ser els mateixos que d'entrada. "OUT_SONG_PARAMETER"
        intent.putExtra(OUT_TASK_PARAMETER,task);
        //de alguna forma hemd e dir que aquest parametre es el resultat de aquesta activitat
        this.setResult(RESULT_OK,intent);
        this.finish();
    }
}
