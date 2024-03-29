package com.authentec.java.ptapi.Technocrat.basicsample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import cm.security.dak.PosteDetailActivity;
import cm.security.dak.R;
import cm.security.dak.services.Comparateur;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FPDisplay extends Activity {
    public static final int FPDR_NONE = 0;
    public static final int FPDR_PASS = 1;
    private static final String METHOD_NAME = "ThumbExists";
    private static final String NAMESPACE = "http://tempuri.org/";
    private static final String SOAP_ACTION = "http://tempuri.org/ThumbExists";
    private static final String URL = "http://10.10.10.204/voteregister/Service.asmx";
    public static Bitmap mImage = null;
    public static String msTitle = null;
    private Intent fingerintent;
    private ImageView mImgv = null;
    public boolean shownotify;

    TextView description;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fpdisplay);

        Button passButton = (Button) findViewById(R.id.pass);
        this.mImgv = (ImageView) findViewById(R.id.img);
        description = findViewById(R.id.fpdisplay_description);

        if (mImage != null) {
            this.mImgv.setImageBitmap(mImage);
        }
        if (msTitle != null) {
            setTitle(msTitle);
        }
        try {
            new File(Environment.getExternalStorageDirectory() + "/Android/finger.bmp");
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Variable Error  " + ex.toString(), Toast.LENGTH_SHORT).show();
        }

        description.setText("On va commencer la comparaison");

        if (!(Comparateur.empreinteScannee != null)) {
           // description.setText( description.getText() + "\nAucune empreinte n'a été scannée");
        } else {
            String str = new String(Comparateur.empreinteScannee, StandardCharsets.UTF_8);
            // description.setText( description.getText() + "\n" + str);
        }
        if (!(Comparateur.empreinteEnrollee != null)) {
            // description.setText( description.getText() + "\nAucune empreinte n'a été enrollée");
        } else {
            String str = new String(Comparateur.empreinteEnrollee, StandardCharsets.UTF_8);
            // description.setText( description.getText() + "\n" + str);
        }

        Comparateur comparateur = new Comparateur();
        if (Comparateur.empreinteScannee != null && Comparateur.empreinteEnrollee !=null) {
            // description.setText( description.getText() + "\nOn a toutes les données");
            double pourcentage = comparateur.compareByteArrays(Comparateur.empreinteScannee, Comparateur.empreinteEnrollee);
            // description.setText( description.getText() + "\npourcentage : " + pourcentage + "%");

        }
        passButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                FPDisplay.this.setResult(1);
                FPDisplay.this.finish();
                System.exit(1);
            }
        });
    }


    public void pointer(double latitude, double longitude) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> update = new HashMap<>();
        update.put("date", new Date());
        update.put("latitude", latitude);

        db.collection("pointage").document( "145").set(update, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    TextView coordonnees = findViewById(R.id.fpdisplay_description);
                    coordonnees.setText("Longitude : " + longitude + "; Latitude : " + latitude);
                    Toast.makeText(FPDisplay.this, "Pointage validé et enregistré", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
