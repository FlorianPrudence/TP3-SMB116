package cnam.smb116.tp3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class UEActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // On récupère le nom de l'UE dans l'intent
        String selectedUE = getIntent().getStringExtra("selectedUE");
        // On affiche le toast
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, selectedUE, duration);
        toast.show();

        // On créer la boîte de dialogue servant à supprimer l'UE de la liste
        AlertDialog alertDialog = new AlertDialog.Builder(UEActivity.this).create();
        alertDialog.setTitle("Suppression d'une UE ");
        alertDialog.setMessage("Souhaitez vous supprimer l'UE " + selectedUE + " de la liste ?");

        // On créer les listeners pour les buttons
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Oui",
                new DialogInterface.OnClickListener() {
                    // Si l'utilisateur choisi de supprimer l'UE de la liste
                    public void onClick(DialogInterface dialog, int which) {
                        // On ferme la boîte de dialogue
                        dialog.dismiss();
                        // On déclare un RESULT_OK, on créer un intent pour le retour contenant le nom de l'UE à supprimer puis on termine l'activité
                        Intent intent = new Intent();
                        intent.putExtra("deletedUE", selectedUE);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Non",
                new DialogInterface.OnClickListener() {
                    // Si l'utilisateur refuse de supprimer l'UE de la liste
                    public void onClick(DialogInterface dialog, int which) {
                        // On ferme la boîte de dialogue
                        dialog.dismiss();
                        // On déclare un RESULT_CANCELED et on termine l'activité
                        setResult(RESULT_CANCELED);
                        finish();
                    }
                });
        // On affiche la boîte de dialogue
        alertDialog.show();
    }
}