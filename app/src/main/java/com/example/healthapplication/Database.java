package com.example.database;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
public class Database {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static void create_user(String email, int height, int weight, int age){
        Map<String, Object> user = new HashMap<>();
        List friends= new ArrayList();
        List notifications= new ArrayList();
        int score=0;
        user.put("email", email);
        user.put("age", age);
        user.put("height", height);
        user.put("weight", weight);
        user.put("Score", score);
        user.put("friends",friends);
        user.put("notifications",notifications);
        db.collection("user").document(email).set(user);
    }
    public Map<String,Object> getUser(String email){
        DocumentReference docRef = db.collection("users").document(email);
        Map<String, Object> user = new HashMap<>();
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        user.put("email", email);
                        user.put("age", document.get("age"));
                        user.put("height", document.get("age"));
                        user.put("weight", document.get("weight"));
                        user.put("Score",document.get("Score"));
                        user.put("friends",document.get("friends"));
                        user.put("notifications",document.get("notifications"));
                    }
                    else{
                        Log.d("TAG", "No such User");
                    }
                }
            }
        });
        return user;
    }
    public void updatefield(String email,String field, Object change){
        DocumentReference docRef = db.collection("users").document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        db.collection("users").document("Dave")
                                .update(field, change);
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAGS", "get failed with ", task.getException());
                }
            }
        });
    }
    public void deleteUser(String email){
        DocumentReference docRef = db.collection("users").document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        db.collection("users").document(email).delete();
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAGS", "get failed with ", task.getException());
                }
            }
        });
    }

}

