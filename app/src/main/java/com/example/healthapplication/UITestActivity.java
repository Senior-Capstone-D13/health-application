package com.example.healthapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Contacts;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class UITestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_uitest);

        Switch lightdark = findViewById(R.id.lightdark);
        lightdark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        Button Theme1 = findViewById(R.id.theme1);
        Theme1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.changeToTheme(UITestActivity.this, 1);
            }

        });
        Button Theme2 = findViewById(R.id.theme2);
        Theme2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.changeToTheme(UITestActivity.this, 2);
            }

        });
        Button Theme3 = findViewById(R.id.theme3);
        Theme3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.changeToTheme(UITestActivity.this, 3);
            }

        });
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UITestActivity.this,MainActivity.class);
                startActivity(intent);
            }

        });

    }

}