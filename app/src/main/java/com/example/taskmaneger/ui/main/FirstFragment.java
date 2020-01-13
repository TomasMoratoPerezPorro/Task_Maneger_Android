package com.example.taskmaneger.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.taskmaneger.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.content.Context.MODE_PRIVATE;

/**
 * A placeholder fragment containing a simple view.
 */
public class FirstFragment extends Fragment {


    private FloatingActionButton fab;
    private ListView listViewTasks;
    private View listViewButton;
    private BDTasks bd;
    private ArrayAdapter<Task> adapter;
    private FloatingActionButton fabAdd;
    private int editingTask = -1;
    private static int REQUEST_CODE_NEW_TASK = 1;
    private static int REQUEST_CODE_EDIT_TASK = 2;
    private static String FILE_NAME="bdsongs.obj";





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_first, container, false);
        fabAdd =root.findViewById(R.id.floatingActionButtonAdd);

        return root;
    }

    @Override
    public void onActivityCreated( Bundle state) {
        super.onActivityCreated(state);

        listViewTasks = getView().findViewById(R.id.listViewTasks);


        //bd= BDTasks.getDummyTasks();

        try{
            bd = BDTasks.getFromFile(getActivity().getApplicationContext().openFileInput(FILE_NAME));

        }
        catch(Exception e){
            //si no es pot obrir el fitxer començem amb una base de dades buida.

            bd = new BDTasks();
        }

        //per mostrar les dades fa falta un adapter
        adapter = new TaskAdapter(getActivity().getApplicationContext(),R.layout.task_item_layout,R.id.textViewTitle,bd.getTasks(),R.id.textViewDescription,R.id.textViewDate,R.id.textViewHour);

        //Passar llista de la bbdd, dirli al list quin es el seu adapter
        listViewTasks.setAdapter(adapter);

        listViewTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Només ens fa falta la posicio, ja que coincideix amb la posició del arraylist
                editTask(position);
            }
        });

        listViewTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //deleteSong(position);
                //abans del delete hem de cridar a un mètode per demanar la confirmació, desde aquest metode esborrarem.
                deleteConfirmedTask(position);
                return true;
            }
        });

        //fabAdd = listViewButton.findViewById(R.id.floatingActionButtonAdd);

        //fabAdd = getView().findViewById(R.id.floatingActionButtonAdd);
        //hem de passarli un objecte de una clase que implementi un listener (clase anonima)
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
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
                    addTask(task);
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
            saveToFile();
        }

    }

    private void deleteConfirmedTask(final int position) {
        //Farem servir una classe pròpia de Android AlertDialogue.
        //en comptes de crear un objecte de la classe, (dins te una altre classe que te un builder de dialegs per defecte amb un title, confirmar cancelar...)
        //Context: dins de quin context es mopstrara el alert
       // AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Light_Dialog);
        builder.setTitle(R.string.titleAlertDialogDeleteTask);
        builder.setMessage(R.string.messageAlertDialogueDeleteTask);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //per poder utilitzar position, aquesta variable té que ser final
                deleteTask(position);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        //una vegada esta configurat creem el dialeg
        builder.create().show();
    }

    private void deleteTask(int position) {
        Log.d("deleteSong",bd.getTasks().get(position).toString());
        bd.getTasks().remove(position);
        //Despres de esborrar hem de actualitzar la vista
        //hem de notificar al adapter
        adapter.notifyDataSetChanged();
        //per desar les dades
        saveToFile();
    }

    private void addTask() {
        Log.d("addSong","Afegir una canço");
        //Per obrir una nova activity (li hem de passar el context (this) i la classe corresponent a al activitat que volem que obri)
        Intent intent = new Intent(getActivity().getApplicationContext(),EditTaskActivity.class);
        //ara encenem un nova activitat amb un metode de la classe activity
        //fem servir el ForResult perque esperm un resultat
        //per fer ho necessitem un codi per diferenciar el addSong del editSong-> definirem dos atributs de classe que son enters (REQUEST_CODE_NEW_SONG i REQUEST_CODE_EDIT_SONG)
        startActivityForResult(intent,REQUEST_CODE_NEW_TASK);
        saveToFile();

    }

    private void addTask(Task task) {
        bd.getTasks().add(task);
        //igual que fem amb delete song ara hem de comunicar que les dades han canviat.
        adapter.notifyDataSetChanged();
        //ara volem que es fagi un scroll automatic on s'insereix el item.
        listViewTasks.smoothScrollToPosition(bd.getTasks().size()-1);
        saveToFile();
    }


    private void saveToFile(){
        try{
            bd.writeToFile(getActivity().getApplicationContext().openFileOutput(FILE_NAME,MODE_PRIVATE));

        }
        catch(Exception e){

        }
    }
}