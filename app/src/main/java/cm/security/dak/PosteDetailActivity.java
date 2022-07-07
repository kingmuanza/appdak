package cm.security.dak;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firestore.v1.WriteResult;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import cm.security.dak.services.GPSTracker;

public class PosteDetailActivity extends AppCompatActivity {

    GPSTracker mGPSTracker;
    TextView libelle;
    TextView idposte;
    TextView coordonnees;
    int id;

    LocationManager locationManager;
    int LOCATION_REFRESH_TIME = 15000; // 15 seconds to update
    int LOCATION_REFRESH_DISTANCE = 500; // 500 meters to update

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getLocation(View view) {
        Log.d("MUANZA", "getLocation mGPSTracker : ");
        mGPSTracker = new GPSTracker(PosteDetailActivity.this);
        if (mGPSTracker.canGetLocation()) {
            double latitude = mGPSTracker.getLatitude();
            double longitude = mGPSTracker.getLongitude();
            Log.d("MUANZA", "latitude mGPSTracker : " + latitude);
            Log.d("MUANZA", "longitude mGPSTracker : " + longitude);
            if (longitude != -1 && latitude != -1) {
                updateCoordonnees(latitude, longitude);
            } else {
                Toast.makeText(PosteDetailActivity.this, "Impossible de récupérer les coordonnées", Toast.LENGTH_LONG).show();
            }
        } else {
            mGPSTracker.showSettingsAlert();
        }
    }

    public void updateCoordonnees(double latitude, double longitude) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> update = new HashMap<>();
        update.put("longitude", longitude);
        update.put("latitude", latitude);

        db.collection("poste").document(id + "").set(update, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    TextView coordonnees = findViewById(R.id.poste_detail_coordonnees);
                    coordonnees.setText("Longitude : " + longitude + "; Latitude : " + latitude);
                    Toast.makeText(PosteDetailActivity.this, "Coordonnées géographiques mises à jour", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poste_detail);

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        libelle = findViewById(R.id.poste_detail_libelle);
        idposte = findViewById(R.id.poste_detail_id);
        coordonnees = findViewById(R.id.poste_detail_coordonnees);
        Log.d("MUANZA", "libellé du poste : " + libelle.getText());
        Log.d("MUANZA", "ID du poste : " + idposte.getText());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String nom = extras.getString("nom");
            id = extras.getInt("id");
            double longitude = extras.getDouble("longitude");
            double latitude = extras.getDouble("latitude");

            Log.d("MUANZA", "libellé du poste : " + nom);
            Log.d("MUANZA", "ID du poste : " + id);

            libelle.setText(nom);
            idposte.setText("ID : " + id);
            coordonnees.setText("Longitude : " + longitude + "; Latitude : " + latitude);


            Button ouvrirMapButton = findViewById(R.id.poste_detail_ouvrir_map);
            Button getLocationButton = findViewById(R.id.poste_detail_get_position);

            ouvrirMapButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setPackage("com.google.android.apps.maps");

                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    } else {
                        //Show info
                    }
                }
            });
            getLocationButton.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    getLocation(v);
                }
            });
        }
    }

}