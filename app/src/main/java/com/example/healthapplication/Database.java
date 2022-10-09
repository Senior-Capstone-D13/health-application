package com.example.healthapplication;

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

    //users will be stored and used as maps
    private static Map<String, Object> user = new HashMap<>();

    // Creates a user with the input Starting data
    // Input: email, height, weight, age
    public static void create_user(String email, int height, int weight, int age){
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
        db.collection("users").document(email).set(user);
    }

    //Grabs the user from the database and stores it into the user field of this class
    //Input: email
    public static void pullUser(String email){
        DocumentReference docRef = db.collection("users").document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        user.put("email", email);
                        user.put("age", document.get("age"));
                        user.put("height", document.get("height"));
                        user.put("weight", document.get("weight"));
                        user.put("Score",document.get("Score"));
                        user.put("friends",document.get("friends"));
                        user.put("notifications",document.get("notifications"));
                        Log.d("TAG", user.toString());
                        return;
                    }
                    else{
                        Log.d("TAG", "No such User");
                        return;
                    }
                }
            }
        });
    }
    //Updates a specific field
    //Input: email, what the user wants to change, the change to be made
    public static void updatefield(String email, String field, Object change){
        DocumentReference docRef = db.collection("users").document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        db.collection("users").document(email)
                                .update(field, change);
                        Log.d("TAG7", user.toString());
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAGS", "get failed with ", task.getException());
                }
            }
        });
    }
    //deletes a user
    //Input email
    public static void deleteUser(String email){
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
    //returns the user-*** SHOULD CALL pullUser BEFORE USING THIS FUNCTION**
    //Output-Map of User
    public static Map<String, Object> getUser(){
        Log.d("TAG3", user.toString());
        return user;
    }

}
