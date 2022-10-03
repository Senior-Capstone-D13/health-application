package com.example.healthapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChallengesActivityHandler extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges_handler);
        initializeFragments();
        initializeButtons();
    }

    public void initializeFragments() {
        //TODO later mabye handle fragment stack somehow
        Fragment acceptFragment = new ChallengesActivityFragmentAccept();
        Fragment sendFragment = new ChallengesActivityFragmentSend();

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, acceptFragment, null)
                .setReorderingAllowed(true)
                .addToBackStack("name") // name can be null
                .commit();


        //Change Fragment buttons
        Button goSendFragment = findViewById(R.id.goSendFragment);
        goSendFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, sendFragment, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name") // name can be null
                        .commit();
            }
        });


        Button goAcceptFragment = findViewById(R.id.goAcceptFragment);
        goAcceptFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer,acceptFragment, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name") // name can be null
                        .commit();
            }
        });

    }

    public void initializeButtons() {
        Button exit = findViewById(R.id.exitButton);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO change MainActivity.class to actual activity
                Intent intent = new Intent(ChallengesActivityHandler.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}