package cnam.smb116.tp3;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.JsonWriter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> listeUE;
    private ArrayAdapter<String> adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ListView listView = (ListView)findViewById(R.id.listView);

        //listeUE = new ArrayList<>();

        //listeUE.add("INA134"); listeUE.add("SAE125"); listeUE.add("MAA131");
        //listeUE.add("ELE102"); listeUE.add("ELA134"); listeUE.add("ELE135");
        //listeUE.add("INA136"); listeUE.add("SAE126"); listeUE.add("MAA136");
        //listeUE.add("ELA136"); listeUE.add("ELE137");

        // On charge la liste depuis les SharedPreferences
        loadUEList();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listeUE);
        listView.setAdapter(adapter);

        // Fonction déclenchée à chaque clic sur un des éléments de la liste
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            String selectedUE = adapter.getItem(i);
            Intent intent = new Intent(MainActivity.this,UEActivity.class);
            intent.putExtra("selectedUE", selectedUE);
            // On démarre l'activité en via l'ActivityResultLauncher pour savoir si l'utilisateur à bien supprimé l'UE
            //startActivity(intent);
            UEActivityResult.launch(intent);
        });
    }

    // Fonction déclenchée à l'appuie du button pour ajouter une UE à la liste
    public void addEU(View view) {
        // On récupère le nom de l'UE à ajouter dans le EditText puis on l'ajoute à la liste
        final EditText textInput = findViewById(R.id.textInput);
        listeUE.add(textInput.getText().toString());
        textInput.getText().clear();
        // On actualise l'adapter pour mettre à jour l'IHM et on enregistre la liste
        adapter.notifyDataSetChanged();
        saveUEList();
    }

    // Supprimant l'UE choisie de la liste
    public void deleteUE(Intent intentResult) {
        // On récupère l'UE à supprimer dans l'intent et on la supprime de la liste
        String deletedUE = intentResult.getStringExtra("deletedUE");
        listeUE.remove(deletedUE);
        // On actualise l'adapter pour mettre à jour l'IHM et on enregistre la liste
        adapter.notifyDataSetChanged();
        saveUEList();
    }

    ActivityResultLauncher<Intent> UEActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // Au retour de l'activité UEActivity, si on a un RESULT_OK (l'utilisateur a choisi de supprimer l'UE de la liste)
                    // on récupère l'intent et on exécute la fonction deleteUE
                    Intent data = result.getData();
                    deleteUE(data);
                }
            });

    // Concatène tous les élèments de la liste en une String et l'enregistre dans les SharedPreferences
    private void saveUEList() {
        StringBuilder csvList = new StringBuilder();
        for(String s : listeUE){
            csvList.append(s);
            csvList.append(",");
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor sEdit = prefs.edit();
        sEdit.putString("listUE", csvList.toString());
        sEdit.apply();
    }

    // Récupère la String contenant les élèments de la liste dans les SharedPreferences et récréer la liste.
    // Si elle n'existe pas, recréer la liste par défaut
    private void loadUEList() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String listUEString = prefs.getString("listUE", null);
        if (listUEString == null || listUEString.length() == 0) {
            this.listeUE = new ArrayList<String>();
            listeUE.add("INA134");
            listeUE.add("SAE125");
            listeUE.add("MAA131");
            listeUE.add("ELE102");
            listeUE.add("ELA134");
            listeUE.add("ELE135");
            listeUE.add("INA136");
            listeUE.add("SAE126");
            listeUE.add("MAA136");
            listeUE.add("ELA136");
            listeUE.add("ELE137");
        } else {
            String[] items = listUEString.split(",");
            this.listeUE = new ArrayList<String>();
            Collections.addAll(listeUE, items);
        }
    }
}