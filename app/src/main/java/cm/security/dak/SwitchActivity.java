package cm.security.dak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cm.security.dak.models.Changement;
import cm.security.dak.models.Vigile;

public class SwitchActivity extends AppCompatActivity {
    String TAG = "MUANZA";
    ListView liste;
    List<Changement> changements = new ArrayList<Changement>();
    List<String> changementsString = new ArrayList<String>();
    ArrayList<HashMap<String,String>> changementsToString(List<Changement> changements) {
        ArrayList<HashMap<String,String>> liste = new ArrayList<HashMap<String,String>>();

        for (Changement v : changements) {
            String e = v.getIdvigileBase().getNoms() + " <-> " + v.getIdvigileSwitch().getNoms();
            HashMap<String,String> item;
            item = new HashMap<String,String>();
            item.put( "title", v.getIdaffectation().getIdposte().getLibelle());
            item.put( "subtitle", e);
            item.put( "complement", v.getDate());
            liste.add( item );
        }
        return liste;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);
        liste = findViewById(R.id.switch_list);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("switch")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "On transforme en classe JAVA");
                            changements = task.getResult().toObjects(Changement.class);
                            Log.d(TAG, "On a transformé en classe JAVA");

                            createList();

                            for (Changement v : changements) {
                                Log.d(TAG, "ID du vigile : " + v.getIdvigileBase().getNoms() );
                                Log.d(TAG, "ID du vigile : " + v.getIdvigileSwitch().getNoms() );
                            }

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
    void createList() {
        ArrayList<HashMap<String,String>> list = this.changementsToString(this.changements);
        SimpleAdapter arr;
        arr = new SimpleAdapter(
                this,
                list,
                R.layout.list_tile,
                new String[] { "title","subtitle", "complement" },
                new int[] {R.id.list_tile_title, R.id.list_tile_subtitle, R.id.list_tile_complement});
        liste.setAdapter(arr);
        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("MUANZA", "Y u no see me? " + i);
                Log.i("MUANZA", "Y u no see me? " + changementsString.get(i));
                Log.i("MUANZA", "Y u no see me IGFH  JHHH : ? " + changements.get(i).getIdvigileBase().getNoms());
                /* Intent absenceDetailActivityIntent = new Intent(SwitchActivity.this, AbsenceDetailActivity.class);
                absenceDetailActivityIntent.putExtra("nom", changements.get(i).getIdvigileBase().getNoms());
                absenceDetailActivityIntent.putExtra("id", changements.get(i).getIdvigileBase().getIdvigile());
                startActivity(absenceDetailActivityIntent); */
            }
        });
    }
}