package cm.security.dak;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cm.security.dak.models.Vigile;

public class VigileActivity extends AppCompatActivity {

    String TAG = "MUANZA";

    List<Vigile> vigiles = new ArrayList<Vigile>();
    List<Vigile> resulats = new ArrayList<Vigile>();

    List<String> vigilesString = new ArrayList<String>();
    List<String> resulatsString = new ArrayList<String>();

    ListView liste;

    List<String> vigilesToString(List<Vigile> vigiles) {
        List<String> vigilesString = new ArrayList<String>();
        for (Vigile v : vigiles) {
            vigilesString.add(v.getNoms());
        }
        return vigilesString;
    }

    void createList() {

        ArrayAdapter<String> arr;
        arr = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                resulatsString);
        liste.setAdapter(arr);


        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("MUANZA", "Y u no see me? " + i);
                Log.i("MUANZA", "Y u no see me? " + resulatsString.get(i));
                Log.i("MUANZA", "Y u no see me IGFH  JHHH : ? " + resulats.get(i).getIdvigile());
                Intent vigileDetailActivityIntent = new Intent(VigileActivity.this, VigileDetailActivity.class);
                vigileDetailActivityIntent.putExtra("nom", resulats.get(i).getNoms());
                vigileDetailActivityIntent.putExtra("id", resulats.get(i).getIdvigile());
                startActivity(vigileDetailActivityIntent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vigile);

        liste = findViewById(R.id.vigile_list);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("vigile")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "On transforme en classe JAVA");
                            vigiles = task.getResult().toObjects(Vigile.class);
                            resulats = task.getResult().toObjects(Vigile.class);
                            Log.d(TAG, "On a transformé en classe JAVA");
                            vigilesString = vigilesToString(vigiles);
                            resulatsString = vigilesToString(vigiles);
                            createList();

                            for (Vigile v : vigiles) {
                                Log.d(TAG, "ID du vigile : " + v.getIdvigile());
                            }

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        EditText rechercher = findViewById(R.id.vigile_rechercher);

        rechercher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void afterTextChanged(Editable editable) {

                if (!editable.toString().isEmpty()) {
                    resulatsString = vigilesString.stream().filter(vigile -> vigile.indexOf(editable.toString()) != -1).collect(Collectors.toList());
                } else {
                    resulats = vigiles;
                }
                createList();
            }
        });
    }
}