package com.example.taskmaneger.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.taskmaneger.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class FirstFragment extends Fragment {

    /*// Store instance variables
    private String title;
    private int page;*/
    private ListView listViewTasks;
    private BDTasks bd;
    private ArrayAdapter<Task> adapter;

    public FirstFragment(){}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Genera una vista a partir del xml del fragment
        listViewTasks = listViewTasks.findViewById(R.id.listViewTasks);

        adapter = new TaskAdapter(this,R.layout.task_item_layout,R.layout.textViewTitle)


        View root = inflater.inflate(R.layout.fragment_first, container, false);


        return root;
    }
}