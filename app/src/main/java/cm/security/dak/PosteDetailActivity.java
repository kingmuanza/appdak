package cm.security.dak;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
    TextView latitude;
    TextView longitude;
    Toolbar toolbar;
    int id;

    int step = 0;

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
                updateCoordonnees(latitude, longitude, view);
            } else {
                Toast.makeText(PosteDetailActivity.this, "Impossible de récupérer les coordonnées", Toast.LENGTH_LONG).show();
            }
        } else {
            mGPSTracker.showSettingsAlert();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getLocation1(View view) {
        Log.d("MUANZA", "getLocation mGPSTracker : ");
        mGPSTracker = new GPSTracker(PosteDetailActivity.this);
        if (mGPSTracker.canGetLocation()) {
            double latitude = mGPSTracker.getLatitude();
            double longitude = mGPSTracker.getLongitude();
            Log.d("MUANZA", "latitude mGPSTracker : " + latitude);
            Log.d("MUANZA", "longitude mGPSTracker : " + longitude);
            if (longitude != -1 && latitude != -1) {
                updateCoordonnees1(latitude, longitude, view);
            } else {
                Toast.makeText(PosteDetailActivity.this, "Impossible de récupérer les coordonnées", Toast.LENGTH_LONG).show();
            }
        } else {
            mGPSTracker.showSettingsAlert();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getLocation2(View view) {
        Log.d("MUANZA", "getLocation mGPSTracker : ");
        mGPSTracker = new GPSTracker(PosteDetailActivity.this);
        if (mGPSTracker.canGetLocation()) {
            double latitude = mGPSTracker.getLatitude();
            double longitude = mGPSTracker.getLongitude();
            Log.d("MUANZA", "latitude mGPSTracker : " + latitude);
            Log.d("MUANZA", "longitude mGPSTracker : " + longitude);
            if (longitude != -1 && latitude != -1) {
                updateCoordonnees2(latitude, longitude, view);
            } else {
                Toast.makeText(PosteDetailActivity.this, "Impossible de récupérer les coordonnées", Toast.LENGTH_LONG).show();
            }
        } else {
            mGPSTracker.showSettingsAlert();
        }
    }

    public void updateCoordonnees(double latitude, double longitude, View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> update = new HashMap<>();
        update.put("longitude", longitude);
        update.put("latitude", latitude);

        db.collection("poste").document(id + "").set(update, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    TextView latitudeTextView = findViewById(R.id.poste_detail_latitude);
                    TextView longitudeTextView = findViewById(R.id.poste_detail_longitude);
                    latitudeTextView.setText(latitude + "");
                    longitudeTextView.setText(longitude + "");
                    Toast.makeText(PosteDetailActivity.this, "Premières Coordonnées géographiques mises à jour", Toast.LENGTH_LONG).show();
                    step = 1;
                }
            }
        });
    }

    public void updateCoordonnees1(double latitude, double longitude, View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> update = new HashMap<>();
        update.put("longitude1", longitude);
        update.put("latitude1", latitude);

        db.collection("poste").document(id + "").set(update, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    TextView latitudeTextView = findViewById(R.id.poste_detail_latitude1);
                    TextView longitudeTextView = findViewById(R.id.poste_detail_longitude1);
                    latitudeTextView.setText(latitude + "");
                    longitudeTextView.setText(longitude + "");
                    Toast.makeText(PosteDetailActivity.this, "Deuxièmes Coordonnées géographiques mises à jour", Toast.LENGTH_LONG).show();
                    step = 2;
                }
            }
        });
    }

    public void updateCoordonnees2(double latitude, double longitude, View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> update = new HashMap<>();
        update.put("longitude2", longitude);
        update.put("latitude2", latitude);

        db.collection("poste").document(id + "").set(update, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    TextView latitudeTextView = findViewById(R.id.poste_detail_latitude2);
                    TextView longitudeTextView = findViewById(R.id.poste_detail_longitude2);
                    latitudeTextView.setText(latitude + "");
                    longitudeTextView.setText(longitude + "");
                    Toast.makeText(PosteDetailActivity.this, "Troisièmes Coordonnées géographiques mises à jour", Toast.LENGTH_LONG).show();
                    step = 0;
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poste_detail);
        toolbar = findViewById(R.id.poste_detail_activity_toolbar);
        setSupportActionBar(toolbar);

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView latitudeTextView = findViewById(R.id.poste_detail_latitude);
        TextView longitudeTextView = findViewById(R.id.poste_detail_longitude);

        TextView latitudeTextView1 = findViewById(R.id.poste_detail_latitude1);
        TextView longitudeTextView1 = findViewById(R.id.poste_detail_longitude1);

        TextView latitudeTextView2 = findViewById(R.id.poste_detail_latitude2);
        TextView longitudeTextView2 = findViewById(R.id.poste_detail_longitude2);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String nom = extras.getString("nom");
            id = extras.getInt("id");

            double longitude = extras.getDouble("longitude");
            double latitude = extras.getDouble("latitude");

            double longitude1 = extras.getDouble("longitude1");
            double latitude1 = extras.getDouble("latitude1");

            double longitude2 = extras.getDouble("longitude2");
            double latitude2 = extras.getDouble("latitude2");

            Log.d("MUANZA", "libellé du poste : " + nom);
            Log.d("MUANZA", "ID du poste : " + id);

            toolbar.setTitle(nom);
            latitudeTextView.setText(latitude + "");
            longitudeTextView.setText(longitude + "");

            latitudeTextView1.setText(latitude1 + "");
            longitudeTextView1.setText(longitude1 + "");

            latitudeTextView2.setText(latitude2 + "");
            longitudeTextView2.setText(longitude2 + "");

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
                    if (step == 0) {
                        getLocation(v);
                    }
                    if (step == 1) {
                        getLocation1(v);
                    }
                    if (step == 2) {
                        getLocation2(v);
                    }
                }
            });
        }
    }

}