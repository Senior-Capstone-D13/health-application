package com.example.healthapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*
 This class is used as test intermediate activity between other
 activities for more interesting animations hopefully
 */

public class testIntermediateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_intermediate);

        //Comment out to look at only original
        Intent intent = new Intent(testIntermediateActivity.this,testActivity.class);
        startActivity(intent);
//        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);


        Button backButton = findViewById(R.id.backToTest);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(testIntermediateActivity.this,testActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });


    }
}