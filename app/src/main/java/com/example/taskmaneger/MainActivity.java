package com.example.taskmaneger;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.taskmaneger.ui.main.BDTasks;
import com.example.taskmaneger.ui.main.FirstFragment;
import com.example.taskmaneger.ui.main.MyPagerAdapter;
import com.example.taskmaneger.ui.main.SecondFragment;
import com.example.taskmaneger.ui.main.Subject;
import com.example.taskmaneger.ui.main.Task;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //carrego les dades (dummy)
    //private BDTasks bd;


    private ArrayAdapter<Task> adapter;
    private ArrayAdapter<Subject> adapterSubject;
    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*tab_titles = new ArrayList<String>();
        for(int i=1; i<3;i++){
            tab_titles.add("Tab "+i);
        }*/

        //bd= BDTasks.getDummyTasks();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        MyPagerAdapter sectionsPagerAdapter = new MyPagerAdapter(this,getSupportFragmentManager());

        ViewPager vpPager = findViewById(R.id.view_pager);


        vpPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabs= findViewById(R.id.tabs);
        tabs.setupWithViewPager(vpPager);



    }

}