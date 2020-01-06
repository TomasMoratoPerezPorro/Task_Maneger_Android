package com.example.taskmaneger.ui.main;

import android.content.Context;

import androidx.constraintlayout.widget.Placeholder;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.taskmaneger.R;

import java.util.List;

//COM es un adapter tindra un metode que permet fer el notify canvi.
//Si ho volguessim fer dinamic podriem, (quan s'afegeixi algun item a la llista es podria afegir una pestanya)

public class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;
        //tipus de dades per mostrar a les pestanyes.
        private  int[] titles = {R.string.tab_text_1,R.string.tab_text_2};
        private Context context;
        private Fragment[] fragments ={new FirstFragment(),new SecondFragment()};

        //CONSTRUCTOR
        //Ncessitem un fragment maneger que necessita la classe pare.
        //Generalment tambe necessitarem un altre parametre amb les dades associades al tag (llista tasks per exemple)
        public MyPagerAdapter(Context context,FragmentManager fragmentManager) {
            super(fragmentManager);
            this.context = context;

        }

        // Returns total number of pages
    //Quantes pestanyes hi ha
        @Override
        public int getCount() {
            return titles.length;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return context.getString(titles[position]);
        }

    }

