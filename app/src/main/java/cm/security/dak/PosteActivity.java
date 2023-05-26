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

import cm.security.dak.models.Poste;
import cm.security.dak.models.Vigile;

public class PosteActivity extends AppCompatActivity {
    String TAG = "MUANZA";

    List<Poste> mPostes = new ArrayList<Poste>();
    List<Poste> resulats = new ArrayList<Poste>();

    List<String> postesString = new ArrayList<String>();
    List<String> resulatsString = new ArrayList<String>();

    ListView liste;

    List<String> postesToString(List<Poste> vigiles) {
        List<String> postesString = new ArrayList<String>();
        for (Poste v : vigiles) {
            postesString.add(v.getLibelle());
        }
        return postesString;
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
                Log.i("MUANZA", "Y u no see me IGFH  JHHH : ? " + resulats.get(i).getIdposte());

                Intent posteDetailActivityIntent = new Intent(PosteActivity.this, PosteDetailActivity.class);
                posteDetailActivityIntent.putExtra("nom", resulats.get(i).getLibelle());
                posteDetailActivityIntent.putExtra("id", resulats.get(i).getIdposte());

                posteDetailActivityIntent.putExtra("longitude", resulats.get(i).getLongitude());
                posteDetailActivityIntent.putExtra("latitude", resulats.get(i).getLatitude());

                posteDetailActivityIntent.putExtra("longitude1", resulats.get(i).getLongitude1());
                posteDetailActivityIntent.putExtra("latitude1", resulats.get(i).getLatitude1());

                posteDetailActivityIntent.putExtra("longitude2", resulats.get(i).getLongitude2());
                posteDetailActivityIntent.putExtra("latitude2", resulats.get(i).getLatitude2());

                startActivity(posteDetailActivityIntent);

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poste);
        liste = findViewById(R.id.poste_liste);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("poste")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            Log.d(TAG, "On transforme en classe JAVA");

                            mPostes = task.getResult().toObjects(Poste.class);
                            resulats = task.getResult().toObjects(Poste.class);
                            Log.d(TAG, "On a transformé en classe JAVA");
                            postesString = postesToString(mPostes);
                            resulatsString = postesToString(mPostes);;
                            createList();

                            for (Poste p : mPostes) {
                                Log.d(TAG, "ID du vigile : " + p.getIdposte());
                            }

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        EditText rechercher = findViewById(R.id.poste_rechercher);

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
                    resulatsString = postesString.stream().filter(vigile -> vigile.indexOf(editable.toString()) != -1).collect(Collectors.toList());
                } else {
                    resulats = mPostes;
                }
                createList();
            }
        });
    }
}