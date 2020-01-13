package com.example.taskmaneger.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taskmaneger.R;
import com.example.taskmaneger.ui.main.Subject;
import com.example.taskmaneger.ui.main.Task;

public class EditSubjectActivity extends AppCompatActivity {

    private EditText editTextName;
    private Button buttonSave;

    //definirem atributs de classe estatics i publics per definir quins parametres pot rebre la classe (desde el intent de main activity)
    public static String SUBJECT_PARAMETER = "SUBJECT_PARAMETER";
    public static String OUT_SUBJECT_PARAMETER = "OUT_SUBJECT_PARAMETER";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subject);

        //Per Consultar les dades amb les que hem cridat a la activity
        //Retorna les dades amb les que s'ha creat l'activitat,, get extras retorna un objecte de tipus bundle.
        Bundle bundle = getIntent().getExtras();

        //ara hem de asociar cada atribut amb el objecte de la interfície
        editTextName = findViewById(R.id.editTextName);




        buttonSave = findViewById(R.id.buttonAddSubject);
        //ara afegim el listener
        buttonSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //aqui simplement cridem a un metode que definirem a la classe
                saveTask();
            }
        });


        if(bundle!=null){
            Subject subject = (Subject) bundle.getSerializable(SUBJECT_PARAMETER);
            //Primer mirar si es null, si no es null agafar les dades de aquesta canço i posar-les al edit text que correspon amb el textview by id
            if(subject!=null){
                //ara hem de posar les dades de la canço a cada edittext de la pantalla
                //per cadascun dels edit text definirem un atribut de clase
                //ara hem de posar les dades corresponents a cada atribut

                editTextName.setText(subject.getName());


            }
        }



    }

    private void saveTask() {
        //Hem de desar les dades en un objecte de la classe canço
        //per veure-ho mes clar generarem una variable per a cada dada de la canço:
        String name = editTextName.getText().toString();

        Subject subject = new Subject(name);
        Intent intent = new Intent();
        //definim un nom per als parametres de sortida, que no tenen perque ser els mateixos que d'entrada. "OUT_SONG_PARAMETER"
        intent.putExtra(OUT_SUBJECT_PARAMETER,subject);
        //de alguna forma hemd e dir que aquest parametre es el resultat de aquesta activitat
        this.setResult(RESULT_OK,intent);
        this.finish();
    }




}
