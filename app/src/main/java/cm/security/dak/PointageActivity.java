package cm.security.dak;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cm.security.dak.R;

import java.util.Date;

public class PointageActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pointage);

        TextView titre = findViewById(R.id.pointage_titre);
        TextView description = findViewById(R.id.pointage_description);

        Button simuler = findViewById(R.id.pointage_bouton);

        simuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("MUANZA", "Simuler le pointage");
                Log.d("MUANZA", "titre : " + titre.getText());
                Log.d("MUANZA", "description : " + description.getText());

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        titre.setText("Pointage réussi !");
                        description.setText("Henri Camara \nAgente de sécurité\nDate : " + new Date().toString() + "\nImpossible de récupérer les coordonnées");
                        simuler.setText("Simuler un autre poointage");
                    }
                }, 2_000); // LENGTH_SHORT is usually 2 second long
            }
        });
    }
}