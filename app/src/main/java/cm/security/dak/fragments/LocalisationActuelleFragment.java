package cm.security.dak.fragments;

import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import cm.security.dak.R;
import cm.security.dak.services.GPSTracker;

public class LocalisationActuelleFragment extends Fragment {

    GPSTracker mGPSTracker;
    TextView libelle;
    TextView coordonnees;
    TextView heure;
    LocationManager locationManager;
    int intervalle = 30;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public LocalisationActuelleFragment() {
        // Required empty public constructor
    }

    public static LocalisationActuelleFragment newInstance(String param1, String param2) {
        LocalisationActuelleFragment fragment = new LocalisationActuelleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_localisation_actuelle, container, false);
        coordonnees = view.findViewById(R.id.fragment_localisation_actuelle_coordonnees);
        heure = view.findViewById(R.id.fragment_localisation_actuelle_heure);
        final Handler handler = new Handler();
        boolean condition = true;

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String texte = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    texte = getLocation(view);
                    setTextViewCoordonnees(texte);
                    // Log.d("MUANZA", "récupération des coordonnées... : ");
                }
                handler.postDelayed(this, 10000);

            }
        });
        return view;
    }

    public void actualiser(View view) {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Log.i("tag", "On récupère la localisation toutes les 30 secondes");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    String texte = getLocation(view);
                    setTextViewCoordonnees(texte);
                }
            }
        }, 0, intervalle * 1000);
    }

    public void setTextViewCoordonnees(String texte) {
        coordonnees.setText(texte);
        Date date = new Date();
        heure.setText(date.toString());
        heure.setText("Dernière actualisation : " + date);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public String getLocation(View view) {
        // Log.d("MUANZA", "getLocation mGPSTracker : ");
        mGPSTracker = new GPSTracker(view.getContext());
        double latitude = -1;
        double longitude = -1;
        if (mGPSTracker.canGetLocation()) {
            latitude = mGPSTracker.getLatitude();
            longitude = mGPSTracker.getLongitude();
            // Log.d("MUANZA", "latitude mGPSTracker : " + latitude);
            // Log.d("MUANZA", "longitude mGPSTracker : " + longitude);
            if (longitude != -1 && latitude != -1) {
            } else {
                // Toast.makeText(view.getContext(), "Impossible de récupérer les coordonnées", Toast.LENGTH_LONG).show();
            }

        } else {
            mGPSTracker.showSettingsAlert();
        }
        return "Latitude : " + latitude + "; Longitude : " + longitude;
    }

}