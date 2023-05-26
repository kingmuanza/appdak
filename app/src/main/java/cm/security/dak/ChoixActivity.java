package cm.security.dak;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.authentec.java.ptapi.Technocrat.basicsample.SampleActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ChoixActivity extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix);
        toolbar = findViewById(R.id.choix_activity_toolbar);
        setSupportActionBar(toolbar);
        try {
            creerLRepertoirePourLesEmpreintes();
        } catch (IOException e) {

            Log.d("MUANZA", "erreur : " + e.getMessage());
        }
        View controleurBouton = findViewById(R.id.profil_controleur_bouton);
        controleurBouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MUANZA", "controleurBouton");
                Intent mainActivityIntent = new Intent(ChoixActivity.this, MainActivity.class);
                startActivity(mainActivityIntent);
            }
        });

        View adminBouton = findViewById(R.id.profil_admin_bouton);
        adminBouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MUANZA", "adminBouton");
                Intent adminActivityIntent = new Intent(ChoixActivity.this, AdminActivity.class);
                startActivity(adminActivityIntent);
            }
        });

    }

    public void creerLRepertoirePourLesEmpreintes() throws IOException {
        Log.d("MUANZA", "creerLRepertoirePourLesEmpreintes");

        File dossier = getDir("empreintes", Context.MODE_PRIVATE); //Creating an internal dir;

        // File dossier = new File(Environment.getExternalStorageDirectory(), "empreintes");
        if (!dossier.exists()) {
            if (!dossier.mkdirs()) {
                Log.d("MUANZA", "Impossible de créer le dossier");
            } else {
                Log.d("MUANZA", "le dossier vient detre créé");
                creerFicher(dossier);
            }
        } else {
            creerFicher(dossier);
        }
    }

    public void creerFicher(File dossier) throws IOException {
        Log.d("MUANZA", "creerFicher");
        File fichier = new File(dossier, "empreinte.ser"); //Getting a file within the dir.
        // File fichier = new File(dossier + "/empreinte.ser");

        Log.d("MUANZA", "dossier : " + dossier.getAbsolutePath());
        Log.d("MUANZA", "fichier : " + fichier.getAbsolutePath());

        FileOutputStream out = new FileOutputStream(fichier);
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(new byte[] {
                (byte) 12
        });
    }
}