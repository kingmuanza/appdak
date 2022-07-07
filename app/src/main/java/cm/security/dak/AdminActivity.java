package cm.security.dak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.authentec.java.ptapi.Technocrat.basicsample.SampleActivity;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        View zones = findViewById(R.id.admin_postes);
        View vigiles = findViewById(R.id.admin_enregistrer_vigile);

        zones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MUANZA", "Je vais effectuer un pointage");
                Intent posteActivityIntent = new Intent(AdminActivity.this, PosteActivity.class);
                startActivity(posteActivityIntent);
            }
        });

        vigiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MUANZA", "Je vais effectuer un pointage");
                Intent vigileActivityIntent = new Intent(AdminActivity.this, VigileActivity.class);
                startActivity(vigileActivityIntent);
            }
        });
    }
}