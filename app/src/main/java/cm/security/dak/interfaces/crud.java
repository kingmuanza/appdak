package cm.security.dak.interfaces;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import cm.security.dak.models.Poste;

public interface crud<T> {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public T get(String id);
    public List<T> getAll();
    public void add(T element);
    public void update(T element);
}
