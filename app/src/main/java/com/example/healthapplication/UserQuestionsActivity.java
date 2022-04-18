package com.example.healthapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class UserQuestionsActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_questions);

        Button submitButton = findViewById(R.id.infoSubmit);

        TextView ageText = (TextView) findViewById(R.id.ageInput);
        TextView heightText = (TextView) findViewById(R.id.heightInput);
        TextView weightText = (TextView) findViewById(R.id.weightInput);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int age = Integer.parseInt(ageText.getText().toString().trim());
                    int height = Integer.parseInt(heightText.getText().toString().trim());
                    int weight = Integer.parseInt(weightText.getText().toString().trim());
                    int score = 0;
                    User newUser = new User(age,weight,height,score);
                    Bundle extras = getIntent().getExtras();
                    GoogleSignInAccount account = (GoogleSignInAccount) extras.get("credentials");
                    //adding user to database
                    Map<String, Object> user = new HashMap<>();
                    user.put("email", account.getEmail());
                    user.put("age", age);
                    user.put("height", height);
                    user.put("weight", weight);
                    user.put("score",score);
    // Add a new document with a generated ID
//                    db.collection("users")
//                            .document(account.getEmail())
//                            .set(user)
//                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                @Override
//                                public void onSuccess(DocumentReference documentReference) {
//                                    Log.d("Users", "User added with ID: " + documentReference.getId());
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Log.w("Users", "Error adding user", e);
//                                }
//                            });
                    CollectionReference user_collection = db.collection("users");
                    user_collection.document(account.getEmail()).set(user);
//                    openHomeScreenActivity(user, account);
                    Intent go_back_home = new Intent(UserQuestionsActivity.this, MainActivity.class);
                    startActivity(go_back_home);
                }
                catch (NumberFormatException e){
                }
            }
        });
    }

    public void openHomeScreenActivity(Map user, GoogleSignInAccount account) {
        DocumentReference docRef = db.collection("users").document(account.getEmail());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Intent intent = new Intent(UserQuestionsActivity.this, HomeScreenActivity.class);
                        intent.putExtra("information", (Parcelable) document);
                        startActivity(intent);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

}