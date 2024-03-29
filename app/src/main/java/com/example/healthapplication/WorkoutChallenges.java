package com.example.healthapplication;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class WorkoutChallenges extends AppCompatActivity {
    ImageButton androidImageButton1;
    ImageButton androidImageButton2;
    ImageButton androidImageButton3;
    ImageButton androidImageButton4;
    ImageButton androidImageButton5;
    ImageButton androidImageButton6;
    Button androidButton1;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_acitivity_challeneges);
        androidImageButton1 = (ImageButton) findViewById(R.id.imageButton4);
        androidImageButton2 = (ImageButton) findViewById(R.id.imageButton5);
        androidImageButton3 = (ImageButton) findViewById(R.id.imageButton7);
        androidImageButton4 = (ImageButton) findViewById(R.id.imageButton8);
        androidImageButton5 = (ImageButton) findViewById(R.id.imageButton9);
        androidImageButton6 = (ImageButton) findViewById(R.id.imageButton10);
        androidButton1 = (Button) findViewById((R.id.button4));
        androidImageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(view);
            }
        });
        androidImageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage1(view);
            }
        });
        androidImageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage2(view);
            }
        });
        androidImageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage5(view);
            }
        });
        androidImageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage6(view);
            }
        });
        androidImageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage7(view);
            }
        });
       androidButton1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               sendMessage3(view);
           }
       });
    }
    public void sendMessage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        startActivity(intent);
    }
    public void sendMessage1(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, DisplayMessageActivity1.class);
        startActivity(intent);
    }
    public void sendMessage2(View view){
        Intent intent = new Intent(this, DisplayMessageActivity2.class);
        startActivity(intent);
    }
    public void sendMessage5(View view){
        Intent intent = new Intent(this, DisplayMessageActivity3.class);
        startActivity(intent);
    }
    public void sendMessage6(View view){
        Intent intent = new Intent(this, DisplayMessageActivity4.class);
        startActivity(intent);
    }
    public void sendMessage7(View view){
        Intent intent = new Intent(this, DisplayMessageActivity5.class);
        startActivity(intent);
    }
    public void sendMessage3(View view){
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
    }
}
