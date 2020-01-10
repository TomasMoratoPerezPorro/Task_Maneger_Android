package com.example.taskmaneger.ui.main;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taskmaneger.R;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditTaskActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextTitle, editTextDescription,editTextDeadline,editTextDate;
    private Button buttonSave;

    //definirem atributs de classe estatics i publics per definir quins parametres pot rebre la classe (desde el intent de main activity)
    public static String TASK_PARAMETER = "TASK_PARAMETER";
    public static String OUT_TASK_PARAMETER = "OUT_TASK_PARAMETER";



    //CALENDARI
    private static final String CERO = "0";
    private static final String DOS_PUNTOS = ":";
    private static final String BARRA = "/";
    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    //Hora
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);

    private String date;

    //Widgets
    EditText etFecha, etHora;
    ImageButton ibObtenerFecha, ibObtenerHora;






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
        editTextDate = findViewById(R.id.et_mostrar_fecha_picker);

        buttonSave = findViewById(R.id.buttonAdd);
        //ara afegim el listener
        buttonSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //aqui simplement cridem a un metode que definirem a la classe
                saveTask();
            }
        });


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

            }
        }


        //CALENDARI
        etFecha = (EditText) findViewById(R.id.et_mostrar_fecha_picker);
        etHora = (EditText) findViewById(R.id.et_mostrar_hora_picker);

        ibObtenerFecha = (ImageButton) findViewById(R.id.ib_obtener_fecha);
        ibObtenerHora = (ImageButton) findViewById(R.id.ib_obtener_hora);

        ibObtenerFecha.setOnClickListener(this);
        ibObtenerHora.setOnClickListener(this);
    }

    private void saveTask() {
        //Hem de desar les dades en un objecte de la classe canço
        //per veure-ho mes clar generarem una variable per a cada dada de la canço:
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();

        //SAVE AND FORMAT DATE
        String date = editTextDate.getText().toString();
        Log.d("editTextDate",date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "dd/MM/yyyy" );
        LocalDate ld = LocalDate.parse( "23/01/2016" , formatter );




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


    //CALENDARI
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_obtener_fecha:
                obtenerFecha();
                break;
            case R.id.ib_obtener_hora:
                obtenerHora();
                break;
        }
    }

    private void obtenerFecha(){

        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                final int mesActual = month + 1;

                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);

                etFecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);


            }
        },anio, mes, dia);

        recogerFecha.show();

    }

    private void obtenerHora(){
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                String horaFormateada =  (hourOfDay < 9)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                String minutoFormateado = (minute < 9)? String.valueOf(CERO + minute):String.valueOf(minute);

                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }

                etHora.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
            }

        }, hora, minuto, false);

        recogerHora.show();
    }







}
