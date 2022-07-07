package cm.security.dak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.authentec.java.ptapi.Technocrat.basicsample.SampleActivity;
import cm.security.dak.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View pointage = findViewById(R.id.pointage_bouton);
        View enregistrer = findViewById(R.id.enregistrer_bouton);
        View absence = findViewById(R.id.absence_bouton);

        pointage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MUANZA", "Je vais effectuer un pointage");
                Intent pointageActivityIntent = new Intent(MainActivity.this, SampleActivity.class);
                startActivity(pointageActivityIntent);
            }
        });

        enregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MUANZA", "Je vais enregistrer des empreintes");
                Intent enregistrerActivityIntent = new Intent(MainActivity.this, EnregistrerActivity.class);
                startActivity(enregistrerActivityIntent);
            }
        });

        absence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MUANZA", "Je vais enregistrer des empreintes");
                Intent absenceActivityIntent = new Intent(MainActivity.this, AbsenceActivity.class);
                startActivity(absenceActivityIntent);
            }
        });
    }
}