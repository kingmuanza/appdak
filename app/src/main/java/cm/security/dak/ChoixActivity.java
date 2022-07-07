package cm.security.dak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.authentec.java.ptapi.Technocrat.basicsample.SampleActivity;

public class ChoixActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix);

        View controleurBouton = findViewById(R.id.profil_controleur_bouton);
        controleurBouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MUANZA", "controleurBouton");
                Intent mainActivityIntent = new Intent(ChoixActivity.this, MainActivity.class);
                startActivity(mainActivityIntent);
            }
        });

        View adminBouton = findViewById(R.id.profil_admin_bouton);
        adminBouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MUANZA", "adminBouton");
                Intent adminActivityIntent = new Intent(ChoixActivity.this, AdminActivity.class);
                startActivity(adminActivityIntent);
            }
        });

    }
}