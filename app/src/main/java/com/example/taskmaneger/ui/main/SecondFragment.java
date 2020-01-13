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
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.taskmaneger.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.content.Context.MODE_PRIVATE;

/**
 * A placeholder fragment containing a simple view.
 */
public class SecondFragment extends Fragment {

    private FloatingActionButton fabAdd;
    private ListView listViewSubjects;
    private BDSubjects bd;
    private ArrayAdapter<Subject> adapterSubject;
    private int editingSubject = -1;

    private static int REQUEST_CODE_NEW_SUBJECT = 1;
    private static int REQUEST_CODE_EDIT_SUBJECT = 2;
    private static String FILE_NAME="bdsubjects.obj";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_second, container, false);
        View fabAdd = root.findViewById(R.id.floatingActionButtonAddSubject);

        return root;
    }

    @Override
    public void onActivityCreated( Bundle state) {
        super.onActivityCreated(state);

        listViewSubjects = getView().findViewById(R.id.listViewSubjects);


        //bd= BDSubjects.getDummySubjects();

        try{
            bd = BDSubjects.getFromFile(getActivity().getApplicationContext().openFileInput(FILE_NAME));
            Log.d("FIRST TRY","NOT NULL");
            Log.d("FIRST TRY", bd.toString());


        }
        catch(Exception e){
            //si no es pot obrir el fitxer començem amb una base de dades buida.
            //rn new BDSongs();

            bd = new BDSubjects();
        }

        //per mostrar les dades fa falta un adapter
        adapterSubject = new SubjectAdapter(getActivity().getApplicationContext(),R.layout.subject_item_layout,R.id.textViewName,bd.getSubjects());

        //Passar llista de la bbdd, dirli al list quin es el seu adapter
        listViewSubjects.setAdapter(adapterSubject);

        listViewSubjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Només ens fa falta la posicio, ja que coincideix amb la posició del arraylist
                editSubject(position);
            }
        });

        listViewSubjects.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //deleteSong(position);
                //abans del delete hem de cridar a un mètode per demanar la confirmació, desde aquest metode esborrarem.
                deleteConfirmedSubject(position);
                return true;
            }
        });

        //fabAdd = listViewButton.findViewById(R.id.floatingActionButtonAdd);

        fabAdd = getView().findViewById(R.id.floatingActionButtonAddSubject);
        //hem de passarli un objecte de una clase que implementi un listener (clase anonima)
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSubject();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("songActivityResult","enter");
        //per sobreescriure el metode, primer de tot hem de mirar si el resultat es correcte
        if(resultCode==getActivity().RESULT_OK){
            Log.d("SubjectActivityResult","OK");
            //ara comprovarem si han arribat dades
            //primer de tot com rebrem un objecte song definim la variable
            //hem de saber quin tipus de dades rebrem en aquest cas un objecte de tipus serialitzable
            //en aquest cas rebrem el objecte OUT_SONG_PRAMETER definit a EditSongActivity, com sabem que es una song farem un CASTING (Song)
            Subject subject = (Subject) data.getSerializableExtra(EditSubjectActivity.OUT_SUBJECT_PARAMETER);
            if(subject!=null){
                Log.d("SubjectActivityResult","NOT NULL");
                //ara falta comprobar quin es el objectiu de aquest result, li estavem demanant editar una canço o estavem afegint una nova?
                if(requestCode==REQUEST_CODE_NEW_SUBJECT){
                    Log.d("SubjectActivityResult","NEW SONG");
                    //creem un metode per afegir una canço, farem sobrecarrega sobre addSong per aprofitar el noim del metode
                    //pero podriem crear un metode nou.
                    addSubject(subject);
                }
                else if(requestCode==REQUEST_CODE_EDIT_SUBJECT){
                    modifySubject(subject);
                }
            }


        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void editSubject(int position) {
        Log.d("editSubject",bd.getSubjects().get(position).toString());

        editingSubject = position; //aqui guardem la posició per poderla utilitzar a la funcio editSong

        //Creem el intent passant-li el context i la classe de la qual sera la activitat
        Intent intent = new Intent(getActivity().getApplicationContext(),EditSubjectActivity.class);
        //Per passar dades a l'altre activitat la hem de posar a intent
        //Per passar un objecte ho hem de guardar a la memoria, nomes es poden guardar a la memoria serializables
        //per a que un objecte sigui serializable la seva classe té que implementar una interfícies serializable.
        //la clase de desti (editSong es la que defineix com es diran els parametres que li podem passar.
        intent.putExtra(EditSubjectActivity.SUBJECT_PARAMETER,bd.getSubjects().get(position));

        //ara encenem un nova activitat amb un metode de la classe activity (AFEGIM EL CODI per rebre el resultat final)
        startActivityForResult(intent,REQUEST_CODE_EDIT_SUBJECT);
    }

    private void modifySubject(Subject subject) {
        //per a poder modificar la canço necessitem saber quina canço es la que s'ha editat.
        //per aixo abans de passar a l'altre activity ens hem de guardar la posició desde editSong
        // //hem de declarar un atribut privat i guardar alla la posicio.
        //creem el atribut editingSong

        //abans de utilitzar-lo ens asegurem de que la posicio existeix
        if(editingSubject!=-1){
            bd.getSubjects().set(editingSubject,subject);
            editingSubject=-1;
            //ara notifiquem al adapter que les dades han canviat perque refresqui la vista.
            adapterSubject.notifyDataSetChanged();
            saveToFile();
        }

    }

    private void deleteConfirmedSubject(final int position) {
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
                deleteSubject(position);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        //una vegada esta configurat creem el dialeg
        builder.create().show();
    }

    private void deleteSubject(int position) {
        Log.d("deleteSong",bd.getSubjects().get(position).toString());
        bd.getSubjects().remove(position);
        //Despres de esborrar hem de actualitzar la vista
        //hem de notificar al adapter
        adapterSubject.notifyDataSetChanged();
        //per desar les dades
        saveToFile();
    }


    private void addSubject() {
        Log.d("addSubject","Afegir una Assignatura");
        //Per obrir una nova activity (li hem de passar el context (this) i la classe corresponent a al activitat que volem que obri)
        Intent intent = new Intent(getActivity().getApplicationContext(),EditSubjectActivity.class);
        //ara encenem un nova activitat amb un metode de la classe activity
        //fem servir el ForResult perque esperm un resultat
        //per fer ho necessitem un codi per diferenciar el addSong del editSong-> definirem dos atributs de classe que son enters (REQUEST_CODE_NEW_SONG i REQUEST_CODE_EDIT_SONG)
        startActivityForResult(intent,REQUEST_CODE_NEW_SUBJECT);
        saveToFile();
    }

    private void addSubject(Subject subject) {
        bd.getSubjects().add(subject);
        //igual que fem amb delete song ara hem de comunicar que les dades han canviat.
        adapterSubject.notifyDataSetChanged();
        //ara volem que es fagi un scroll automatic on s'insereix el item.
        listViewSubjects.smoothScrollToPosition(bd.getSubjects().size()-1);
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