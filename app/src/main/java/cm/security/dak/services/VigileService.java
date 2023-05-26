package cm.security.dak.services;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.authentec.java.ptapi.Technocrat.basicsample.SampleActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import cm.security.dak.models.Affectation;
import cm.security.dak.models.Vigile;

public abstract class VigileService {

    public List<Vigile> vigiles = new ArrayList<Vigile>();
    public List<Affectation> affectations = new ArrayList<Affectation>();

    public abstract void actionOnFinish();

    public void updateVigile(int id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> update = new HashMap<>();
        update.put("empreinte", true);

        db.collection("vigile").document(id + "").set(update, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    actionOnFinish();
                }
            }
        });
    }

    public void getVigiles() {
        Log.d("MUANZA", "VigileService getVigiles");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("vigile")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.d("MUANZA", "VigileService getVigiles onComplete");
                        if (task.isSuccessful()) {
                            Log.d("MUANZA", "VigileService getVigiles isSuccessful");
                            // Log.d(TAG, "On transforme en classe JAVA");
                            vigiles = task.getResult().toObjects(Vigile.class);
                            Log.d("MUANZA", "VigileService getVigiles isSuccessful vigiles");
                            actionOnFinish();
                        } else {
                            Log.d("MUANZA", "VigileService getVigiles raté");

                        }
                    }
                });

    }

    public void getAffectations(int idvigile) {
        Log.d("MUANZA", "VigileService getAffectations");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("affectation")
                .whereEqualTo("idvigile.idvigile", idvigile)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.d("MUANZA", "VigileService getAffectations onComplete");
                        if (task.isSuccessful()) {
                            Log.d("MUANZA", "VigileService getAffectations isSuccessful");
                            // Log.d(TAG, "On transforme en classe JAVA");
                            affectations = task.getResult().toObjects(Affectation.class);
                            Log.d("MUANZA", "VigileService getAffectations isSuccessful affectations");
                            actionOnFinish();
                        } else {
                            Log.d("MUANZA", "VigileService getAffectations raté");

                        }
                    }
                });
    }

    public Affectation getAffectationEnCours() {
        if (!affectations.isEmpty()) {
            for (int i = 0; i < affectations.size(); i++) {
                Affectation affectation = affectations.get(i);
                if (affectation.getArret() == null) {
                    return affectation;
                }
            }
        }
        return affectations.isEmpty() ? null : affectations.get(0);
    }


    public void creerPointage(Vigile v, double longitude, double latitude) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());

        Map<String, Object> update = new HashMap<>();
        update.put("date", new Date());
        update.put("idvigile", v.getIdvigile());
        update.put("nomsVigile", v.getNoms());
        update.put("empreinte", true);

        update.put("longitude", longitude);
        update.put("latitude", latitude);

        db.collection("pointage").document(timeStamp).set(update, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    actionOnFinish();
                }
            }
        });
    }

}
