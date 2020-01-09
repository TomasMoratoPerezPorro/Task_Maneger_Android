package com.example.taskmaneger.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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


    private ListView listViewTasks;
    private BDTasks bd;
    private ArrayAdapter<Task> adapter;

    private int editingTask = -1;
    private static int REQUEST_CODE_NEW_TASK = 1;
    private static int REQUEST_CODE_EDIT_TASK = 2;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_first, container, false);


        return root;
    }

    @Override
    public void onActivityCreated( Bundle state) {
        super.onActivityCreated(state);

        listViewTasks = getView().findViewById(R.id.listViewTasks);

        bd= BDTasks.getDummyTasks();

        //per mostrar les dades fa falta un adapter
        adapter = new TaskAdapter(getActivity().getApplicationContext(),R.layout.task_item_layout,R.id.textViewTitle,bd.getTasks(),R.id.textViewDescription,R.id.textViewDeadline);

        //Passar llista de la bbdd, dirli al list quin es el seu adapter
        listViewTasks.setAdapter(adapter);

        listViewTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Només ens fa falta la posicio, ja que coincideix amb la posició del arraylist
                editTask(position);
            }
        });


    }

    private void editTask(int position) {
        Log.d("editSong",bd.getTasks().get(position).toString());

        editingTask = position; //aqui guardem la posició per poderla utilitzar a la funcio editSong

        //Creem el intent passant-li el context i la classe de la qual sera la activitat
        Intent intent = new Intent(getActivity().getApplicationContext(),EditTaskActivity.class);
        //Per passar dades a l'altre activitat la hem de posar a intent
        //Per passar un objecte ho hem de guardar a la memoria, nomes es poden guardar a la memoria serializables
        //per a que un objecte sigui serializable la seva classe té que implementar una interfícies serializable.
        //la clase de desti (editSong es la que defineix com es diran els parametres que li podem passar.
        intent.putExtra(EditTaskActivity.TASK_PARAMETER,bd.getTasks().get(position));

        //ara encenem un nova activitat amb un metode de la classe activity (AFEGIM EL CODI per rebre el resultat final)
        startActivityForResult(intent,REQUEST_CODE_EDIT_TASK);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("songActivityResult","enter");
        //per sobreescriure el metode, primer de tot hem de mirar si el resultat es correcte
        if(resultCode==getActivity().RESULT_OK){
            Log.d("songActivityResult","OK");
            //ara comprovarem si han arribat dades
            //primer de tot com rebrem un objecte song definim la variable
            //hem de saber quin tipus de dades rebrem en aquest cas un objecte de tipus serialitzable
            //en aquest cas rebrem el objecte OUT_SONG_PRAMETER definit a EditSongActivity, com sabem que es una song farem un CASTING (Song)
            Task task = (Task)data.getSerializableExtra(EditTaskActivity.OUT_TASK_PARAMETER);
            if(task!=null){
                Log.d("songActivityResult","NOT NULL");
                //ara falta comprobar quin es el objectiu de aquest result, li estavem demanant editar una canço o estavem afegint una nova?
                if(requestCode==REQUEST_CODE_NEW_TASK){
                    Log.d("songActivityResult","NEW SONG");
                    //creem un metode per afegir una canço, farem sobrecarrega sobre addSong per aprofitar el noim del metode
                    //pero podriem crear un metode nou.
                    //addTask(task);
                }
                else if(requestCode==REQUEST_CODE_EDIT_TASK){
                    modifyTask(task);
                }
            }


        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void modifyTask(Task task) {
        //per a poder modificar la canço necessitem saber quina canço es la que s'ha editat.
        //per aixo abans de passar a l'altre activity ens hem de guardar la posició desde editSong
        // //hem de declarar un atribut privat i guardar alla la posicio.
        //creem el atribut editingSong

        //abans de utilitzar-lo ens asegurem de que la posicio existeix
        if(editingTask!=-1){
            bd.getTasks().set(editingTask,task);
            editingTask=-1;
            //ara notifiquem al adapter que les dades han canviat perque refresqui la vista.
            adapter.notifyDataSetChanged();
            //saveToFile();
        }

    }
}