package cm.security.dak;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import cm.security.dak.R;

public class AbsenceDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absence_detail);

        TextView nomVigile = findViewById(R.id.absence_detail_nom_vigile);
        TextView idVigile = findViewById(R.id.absence_detail_id_vigile);
        Log.d("MUANZA", "Nom du vigile : " + nomVigile.getText());
        Log.d("MUANZA", "ID du vigile : " + idVigile.getText());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String nom = extras.getString("nom");
            int id = extras.getInt("id");
            Log.d("MUANZA", "Nom du vigile : " + nom);
            Log.d("MUANZA", "ID du vigile : " + id);
            nomVigile.setText(nom);
            idVigile.setText("ID : " + id);
        }
    }
}