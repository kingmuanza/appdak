package cm.security.dak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.authentec.java.ptapi.Technocrat.basicsample.FPDisplay;
import com.authentec.java.ptapi.Technocrat.basicsample.SampleActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import cm.security.dak.R;
import cm.security.dak.models.Vigile;
import cm.security.dak.services.GPSTracker;

public class AbsenceDetailActivity extends AppCompatActivity {

    String nom;
    String raisonAbsence;
    int id;
    double latitude;
    double longitude;
    GPSTracker mGPSTracker;
    EditText commentaire;
    Spinner staticSpinner;
    Button pointer;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absence_detail);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mGPSTracker = new GPSTracker(AbsenceDetailActivity.this);
            if (mGPSTracker.canGetLocation()) {
                latitude = mGPSTracker.getLatitude();
                longitude = mGPSTracker.getLongitude();
                Log.d("MUANZA", "latitude mGPSTracker : " + latitude);
                Log.d("MUANZA", "longitude mGPSTracker : " + longitude);

            } else {
                mGPSTracker.showSettingsAlert();
            }
        }

        commentaire = findViewById(R.id.absence_commentaire);
        toolbar = findViewById(R.id.absence_detail_activity_toolbar);
        staticSpinner = findViewById(R.id.static_spinner);
        pointer = findViewById(R.id.absence_detail_button);

        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.absence_motifs_array,android.R.layout.simple_spinner_item);
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        staticSpinner.setAdapter(staticAdapter);
        staticSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                raisonAbsence = (String) parent.getItemAtPosition(position);
                Log.d("MUANZA", raisonAbsence);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        pointer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creerPointage();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nom = extras.getString("nom");
            id = extras.getInt("id");
            Log.d("MUANZA", "Nom du vigile : " + nom);
            Log.d("MUANZA", "ID du vigile : " + id);
            toolbar.setSubtitle(nom);
        }
    }


    public void creerPointage() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());

        Map<String, Object> update = new HashMap<>();
        update.put("date", new Date());
        update.put("id", timeStamp);
        update.put("idvigile", id);
        update.put("nomsVigile", nom);
        update.put("empreinte", false);
        update.put("absence", true);
        update.put("commentaire", commentaire.getText().toString());
        update.put("raison", raisonAbsence);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mGPSTracker = new GPSTracker(AbsenceDetailActivity.this);
            if (mGPSTracker.canGetLocation()) {
                latitude = mGPSTracker.getLatitude();
                longitude = mGPSTracker.getLongitude();
                update.put("longitude", longitude);
                update.put("latitude", latitude);
                Log.d("MUANZA", "latitude mGPSTracker : " + latitude);
                Log.d("MUANZA", "longitude mGPSTracker : " + longitude);

            } else {
                mGPSTracker.showSettingsAlert();
            }
        }
        update.put("longitude", longitude);
        update.put("latitude", latitude);

        db.collection("pointage").document(timeStamp).set(update, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AbsenceDetailActivity.this, "Pointage mis à jour sur le serveur", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

}