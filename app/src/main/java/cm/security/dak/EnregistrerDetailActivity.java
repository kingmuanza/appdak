package cm.security.dak;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import cm.security.dak.R;

public class EnregistrerDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enregistrer_detail);

        TextView nom = findViewById(R.id.enregistrer_detail_nom_vigile);
        Log.d("MUANZA", "Nom du vigile : " + nom.getText());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("nom");
            Log.d("MUANZA", "Nom du vigile : " + value);
            nom.setText(value);
        }
    }
}