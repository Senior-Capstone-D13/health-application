package com.example.healthapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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

                    //adding user to database
                    Map<String, Object> user = new HashMap<>();
                    user.put("age", age);
                    user.put("height", height);
                    user.put("last", weight);
                    user.put("score",score);
    // Add a new document with a generated ID
                    db.collection("users")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("Users", "User added with ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("Users", "Error adding user", e);
                                }
                            });
                    openHomeScreenActivity(newUser);
                }
                catch (NumberFormatException e){
                }
            }
        });
    }

    public void openHomeScreenActivity(User user) {
        Intent intent = new Intent(this,HomeScreenActivity.class);
        startActivity(intent);
    }

}