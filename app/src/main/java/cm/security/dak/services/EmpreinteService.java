package cm.security.dak.services;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.upek.android.ptapi.struct.PtInputBir;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class EmpreinteService {

    AppCompatActivity appCompatActivity;

    public EmpreinteService(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    public File getFichierEmpreinte(int id) {
        boolean has = false;
        File dossier = appCompatActivity.getDir("empreintes", Context.MODE_PRIVATE);
        if (dossier.exists()) {
            File fichier = new File(dossier, "empreinte-" + id + ".ser");
            if (fichier.exists()) {
                return fichier;
            }
        }
        return null;
    }

    public void sauvegarderEmpreinte(PtInputBir empreinteSerialisee, int id) throws IOException {
        Log.d("MUANZA", "creerLRepertoirePourLesEmpreintes");

        File dossier = appCompatActivity.getDir("empreintes", Context.MODE_PRIVATE); //Creating an internal dir;

        // File dossier = new File(Environment.getExternalStorageDirectory(), "empreintes");
        if (!dossier.exists()) {
            if (!dossier.mkdirs()) {
                Log.d("MUANZA", "Impossible de créer le dossier");
            } else {
                Log.d("MUANZA", "le dossier vient detre créé");
                dossier.setReadable(true);
                dossier.setWritable(true);
                dossier.setExecutable(true);
                creerFicher(dossier, empreinteSerialisee, id);
            }
        } else {
            dossier.setReadable(true);
            dossier.setWritable(true);
            dossier.setExecutable(true);
            creerFicher(dossier, empreinteSerialisee, id);
        }
    }

    public File creerFicher(File dossier, PtInputBir empreinteSerialisee, int id) throws IOException {
        Log.d("MUANZA", "creerFicher");
        File fichier = new File(dossier, "empreinte-" + id + ".ser");

        Log.d("MUANZA", "dossier : " + dossier.getAbsolutePath());
        Log.d("MUANZA", "fichier : " + fichier.getAbsolutePath());
        Log.d("MUANZA", "fichier : " + fichier.getCanonicalPath());

        FileOutputStream out = new FileOutputStream(fichier);
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(empreinteSerialisee);
        Log.d("MUANZA", "creerFicher");

        return fichier;
    }

}
