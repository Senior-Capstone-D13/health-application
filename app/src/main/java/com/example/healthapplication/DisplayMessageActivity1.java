package com.example.healthapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class DisplayMessageActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message1);
        Intent intent = getIntent();
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        CipherHandler ch = new CipherHandler();
        DefaultCalories dc = new DefaultCalories();
        String encrypted_email = null;
        try {
            encrypted_email = ch.encrypt_message(account.getEmail());
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        Button Button1 = (Button) findViewById(R.id.button7);
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DisplayMessageActivity1.this, WorkoutChallenges.class));
            }
        });

        Button submit = findViewById(R.id.button);
        String finalEncrypted_email = encrypted_email;
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView amount_of_time = findViewById(R.id.eidTextTime);
                int minutes = Integer.parseInt(amount_of_time.getText().toString().trim());

                CollectionReference user_ref = db.collection("users");
                try {
                    user_ref.document(finalEncrypted_email).update("score", dc.get_calories("Tennis", minutes));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }
}

