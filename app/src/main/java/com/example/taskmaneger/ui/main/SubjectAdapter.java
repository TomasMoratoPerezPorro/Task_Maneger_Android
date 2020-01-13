package com.example.taskmaneger.ui.main;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SubjectAdapter extends ArrayAdapter<Subject> {

    private int idResourceName;


    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView textViewName = view.findViewById(idResourceName);


        //position corresponent a la vista que estem creant per a cada canço

        Subject subject = this.getItem(position);

        //Assignar contingut a cadascun dels elements de la vista:

        textViewName.setText(subject.getName());


        return view;

    }

    //El constructor ha de tenir el context

    public SubjectAdapter(Context context, int resource, int idResourceName, List<Subject> objects) {
        super(context, resource, idResourceName, objects);
        this.idResourceName = idResourceName;

    }
}
