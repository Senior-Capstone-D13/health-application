package com.example.healthapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserQuestionsActivity extends AppCompatActivity {

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