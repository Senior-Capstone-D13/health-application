package com.example.healthapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        String name = findViewById(R.id.homeScreenName).toString();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        Bundle extras = getIntent().getExtras();
        String age = extras.getString("age");
        String email = extras.getString("email");
        String height = extras.getString("height");
        TextView textview_age = findViewById(R.id.homeScreenName);
        textview_age.setText("Age: " + age + "\n" + "Email: " + email + "\n" + "height: " + height + "inches");
        Button homescreenSignOut = findViewById(R.id.homescreenSignOut);
        homescreenSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoogleSignInClient.signOut().addOnCompleteListener(HomeScreenActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();
                        startActivity(new Intent(HomeScreenActivity.this, MainActivity.class));
                    }
                });
            }

        });

        Button homescreenGoToSteps = findViewById(R.id.homescreenGoToSteps);
        homescreenGoToSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStepsScreen();
            }

        });

        Button go_to_challenges = findViewById(R.id.challenges);
        go_to_challenges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreenActivity.this, ChallengesActivity.class));
                // Go to Challenges Screen
            }
        });

        Button goToChallengeFragments = findViewById(R.id.goChallengeMain);
        goToChallengeFragments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreenActivity.this, ChallengesActivityMain.class));
            }
        });
    }

    //for first time users or something
    public void openStepsScreen() {

        //TODO Implement
    }
}