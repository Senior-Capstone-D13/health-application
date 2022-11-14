package com.example.healthapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayMessageActivity4 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message4);
        Intent intent = getIntent();
        Button Button1 = (Button) findViewById(R.id.button7);
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DisplayMessageActivity4.this, WorkoutChallenges.class));
            }
        });
    }
}
