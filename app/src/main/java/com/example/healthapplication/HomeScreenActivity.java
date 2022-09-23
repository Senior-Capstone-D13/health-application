package com.example.healthapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class HomeScreenActivity extends AppCompatActivity {
    private ConstraintLayout homeScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        initializeGoogle();
        initializeMenu();


    }

    void initializeGoogle(){
        TextView homeScreenName = findViewById(R.id.homeScreenName);
        String name = homeScreenName.toString();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        Bundle extras = getIntent().getExtras();
        String age = extras.getString("age");
        String email = extras.getString("email");
        String height = extras.getString("height");

        homeScreenName.setText("Age: " + age + "\n" + "Email: " + email + "\n" + "height: " + height + "inches");

        Button homescreenSignOut = findViewById(R.id.homescreenSignOut);
        homescreenSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoogleSignInClient.signOut().addOnCompleteListener(HomeScreenActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();
                        startActivity(new Intent(HomeScreenActivity.this, MainActivity.class));
                        //overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    }
                });
            }
        });

    }

    void initializeMenu(){

        homeScreen = (ConstraintLayout) findViewById(R.id.home_screen);

        Button createPopUpWindow = findViewById(R.id.homescreenMenuButton);
        createPopUpWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Menu stuff
                LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.home_menu_popup,null);
                int xStart = 400;
                int height = 1650;
                PopupWindow window = new PopupWindow(container,xStart,height,true);

                window.setAnimationStyle(-1);//-1 default
                window.showAtLocation(homeScreen, Gravity.LEFT,0,500);


                //Buttons inside menu
                Button go_to_challenges = window.getContentView().findViewById(R.id.challenges);
                //Button go_to_challenges = findViewById(R.id.challenges);
                go_to_challenges.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(HomeScreenActivity.this, ChallengesActivity.class));
                        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                        // Go to Challenges Screen
                    }
                });

            }
        });
    }

    //for first time users or something
    public void setGoToSteps() {
        //TODO Implement
    }

}