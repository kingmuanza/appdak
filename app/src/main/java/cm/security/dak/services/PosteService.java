package cm.security.dak.services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import cm.security.dak.interfaces.crud;
import cm.security.dak.models.Poste;

public class PosteService implements crud<Poste> {
    List<Poste> resulats = new ArrayList<Poste>();

    @Override
    public Poste get(String id) {
        return null;
    }

    @Override
    public List<Poste> getAll() {
        db.collection("poste")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d("MUANZA", "On transforme en classe JAVA");
                            resulats = task.getResult().toObjects(Poste.class);

                        } else {
                            Log.d("MUANZA", "Error getting documents.", task.getException());
                        }
                    }
                });
        return null;
    }

    @Override
    public void add(Poste element) {

    }

    @Override
    public void update(Poste element) {

    }
}
