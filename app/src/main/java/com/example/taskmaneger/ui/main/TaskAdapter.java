package com.example.taskmaneger.ui.main;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task>{
    private int idResourceTitle,idResourceDescription,idResourceDeadline;


    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView textViewTitle = view.findViewById(idResourceTitle);
        TextView textViewDescription = view.findViewById(idResourceDescription);
        TextView textViewDeadline = view.findViewById(idResourceDeadline);

        //position corresponent a la vista que estem creant per a cada canço

        Task task = this.getItem(position);

        //Assignar contingut a cadascun dels elements de la vista:

        textViewTitle.setText(task.getTitle());
        textViewDescription.setText(task.getDescription());

        //la classe integer te un metode to string
        textViewDeadline.setText(Long.toString(task.getDeadline()));

        return view;

    }

    //El constructor ha de tenir el context
    public TaskAdapter(Context context, int resource, int idResourceTitle,
                       List<Task> objects, int idResourceDescription, int idResourceDeadline) {
        super(context, resource, idResourceTitle, objects);
        this.idResourceTitle = idResourceTitle;
        this.idResourceDescription = idResourceDescription;
        this.idResourceDeadline= idResourceDeadline;


    }


}
