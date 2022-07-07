package cm.security.dak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import cm.security.dak.R;

public class EnregistrerActivity extends AppCompatActivity {

    ListView l;
    String tutorials[] = {"Vigile 1", "VIGILE 2", "VIGILE 3" };
    String resulats[] = {"Vigile 1", "VIGILE 2", "VIGILE 3" };

    void createList() {

        ArrayAdapter<String> arr;
        arr = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                resulats);
        l.setAdapter(arr);

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("MUANZA", "Y u no see me? " + i);
                Log.i("MUANZA", "Y u no see me? " + resulats[i]);
                Intent enregistrerDetailActivityIntent = new Intent(EnregistrerActivity.this, EnregistrerDetailActivity.class);
                enregistrerDetailActivityIntent.putExtra("nom",resulats[i]);
                startActivity(enregistrerDetailActivityIntent);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enregistrer);
        l = findViewById(R.id.enregistrer_list);

        createList();
        EditText rechercher = findViewById(R.id.enregistrer_rechercher);

        rechercher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (!editable.toString().isEmpty()) {

                    int n = 0;
                    for (String tutorial : tutorials) {
                        if (tutorial.indexOf(editable.toString()) != -1) {
                            resulats[n] = tutorial;
                            n++;
                        }
                    }
                    if (n > 0) {
                        String liste[] = new String[n];
                        for (int i = 0; i < n; i++) {
                            liste[i] = resulats[i];
                        }
                        resulats = liste;
                    } else {
                        String liste[] = {};
                        resulats = liste;
                    }
                } else {
                    resulats = tutorials;
                }
                createList();
            }
        });
    }
}