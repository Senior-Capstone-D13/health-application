package com.example.healthapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class HomeScreenActivity extends AppCompatActivity {

    private ConstraintLayout homeScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        Bundle extras = getIntent().getExtras();
        String age = extras.getString("age");
        String email = extras.getString("email");
        String height = extras.getString("height");


        //Popup menu stuff
        homeScreen = (ConstraintLayout) findViewById(R.id.home_screen);
        Button popupUserInformation = findViewById(R.id.popupUserInformation);
   //     Animation zoomIn = AnimationUtils.loadAnimation(this,R.anim.zoom_in);
   //     Animation popupButtonClicked = AnimationUtils.loadAnimation(this,R.anim.pop_up_zoom_in);



        popupUserInformation.setText(account.getGivenName());

        //Menu stuff
        LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View container = (View) layoutInflater.inflate(
                R.layout.activity_home_screen_popup_user_information,
                null);


        int xStart = 800;
        int windowHeight = 1600;

        PopupWindow window = new PopupWindow(container,xStart,windowHeight,true);

        window.getContentView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
                Log.d("window", "dismissed");
            }
        });

        //TextViews Inside Menu
        TextView popupName = window.getContentView().findViewById(R.id.homeScreenName);
        TextView popupAge = window.getContentView().findViewById(R.id.age);
        TextView popupEmail = window.getContentView().findViewById(R.id.email);
        TextView popupHeight = window.getContentView().findViewById(R.id.height);
        popupUserInformation.setText(account.getGivenName());

        popupName.setText("Name " + account.getGivenName());
        popupAge.setText("Age: " + age);
        popupEmail.setText("Email: " + email);
        popupHeight.setText("Height: " + height + " inches");


        //TextView textview_age = findViewById(R.id.homeScreenName);
        //textview_age.setText("Age: " + age + "\n" + "Email: " + email + "\n" + "height: " + height + "inches");
        //Buttons inside menu
        Button signOut = window.getContentView().findViewById(R.id.signout);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoogleSignInClient.signOut().addOnCompleteListener(HomeScreenActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        window.dismiss();
                        finish();
                        startActivity(new Intent(HomeScreenActivity.this, MainActivity.class));
                    }
                });
            }

        });

        popupUserInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Animation of Button
//                popupUserInformation.startAnimation(popupButtonClicked);

                //Enter animation of popup window
  //              container.startAnimation(zoomIn);
                //CENTER WINDOW
                window.showAtLocation(homeScreen, Gravity.CENTER,0,0);
                window.showAsDropDown(view);
           }
        });

        Button goToChallengeFragments = findViewById(R.id.goChallengeMain);
 //       Animation effect = AnimationUtils.loadAnimation(this,R.anim.slide_out_right);
        goToChallengeFragments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                v.startAnimation(effect);
                startActivity(new Intent(HomeScreenActivity.this, ChallengesActivityMain.class));
//                overridePendingTransition(R.anim.zoom_in,R.anim.expand_effect);
            }
        });
        Button gotoWorkoutFragments = findViewById(R.id.goSelectWorkout);
        gotoWorkoutFragments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreenActivity.this, WorkoutChallenges.class));
            }
        });
    }

}