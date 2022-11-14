package com.example.healthapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

public class Friendslist extends AppCompatActivity {
    MultiAutoCompleteTextView multiAutoCompleteTextViewDefault;
    String[] fewRandomSuggestedText = {"deepsusanta@gmail.com", "eyobat@gmail.com", "bjeong16@utexas.edu", "davidborges537@gmail.com","jflotildes@utexas.edu","prathik.srinivasan@utexas.edu"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        multiAutoCompleteTextViewDefault = findViewById(R.id.multiAutoCompleteTextView);
        ArrayAdapter<String> randomArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fewRandomSuggestedText);
        multiAutoCompleteTextViewDefault.setAdapter(randomArrayAdapter);
        multiAutoCompleteTextViewDefault.setThreshold(1);
        multiAutoCompleteTextViewDefault.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

    }
}
