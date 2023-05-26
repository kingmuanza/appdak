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

        View absence = findViewById(R.id.absence_bouton);
        View changements = findViewById(R.id.changements);

        changements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MUANZA", "Je vais effectuer un pointage");
                Intent switchActivityIntent = new Intent(MainActivity.this, SwitchActivity.class);
                startActivity(switchActivityIntent);
            }
        });


        pointage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MUANZA", "Je vais effectuer un pointage");
                Intent pointageActivityIntent = new Intent(MainActivity.this, PointageActivity.class);
                startActivity(pointageActivityIntent);
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