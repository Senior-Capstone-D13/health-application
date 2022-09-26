package com.example.healthapplication;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    GoogleSignInAccount account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO remove this
        //for quicker testing to get to wherever
        //Uncomment to regenerate the initial key
//        CipherHandler cipher_handler = new CipherHandler();
//        try {
//            cipher_handler.get_initial_key();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
        Button skipLogin = findViewById(R.id.loginSkip);
        //skipLogin.setVisibility(View.INVISIBLE);
        skipLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // openHomeScreenActivity();
                openUserQuestionsActivity();
            }
        });

        // Initialize the Google Login Feature
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // Initialize the Google Sign In Button
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sign_in_intent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(sign_in_intent, '0');    // 0 is RC code for successful sign in
            }
        });



        // Initialize the text view
        TextView initialTextView = findViewById(R.id.maintextview);
        initialTextView.setText("Welcome to the Health Application!");

        // Hide the sign out button at first
        Button signOutButton = findViewById(R.id.sign_out_button);
        signOutButton.setVisibility(View.INVISIBLE);
    }

    // When the login activity is completed
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == '0') {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignIn(task);
            Intent pass_credentials_to_user_questions = new Intent(MainActivity.this, UserQuestionsActivity.class);
            pass_credentials_to_user_questions.putExtra("credentials", account);
            startActivity(pass_credentials_to_user_questions);
            openUserQuestionsActivity();
            // Check for login
            TextView login_textview = findViewById(R.id.maintextview);
            login_textview.setText("Hello " + task.getResult().getGivenName());
            SignInButton signInButton1 = findViewById(R.id.sign_in_button);
            signInButton1.setVisibility(View.INVISIBLE);
            Button signOutButton = findViewById(R.id.sign_out_button);
            signOutButton.setVisibility(View.VISIBLE);
        }
    }

    // Handle potential exception for the sign in process
    public void handleSignIn(Task<GoogleSignInAccount> completed_task) {
        try {
            account = completed_task.getResult(ApiException.class); // Account contains all account information
        } catch (ApiException e) {
            Log.w(TAG, "Exception code is " + e.getStatusCode());

        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        // Null if no user sign in. Returns account if the user has already signed in
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            // User has already logged in, so go to the next page with his information
//            Intent pass_credentials_to_user_questions = new Intent(MainActivity.this, UserQuestionsActivity.class);
//            pass_credentials_to_user_questions.putExtra("credentials", account);
//            startActivity(pass_credentials_to_user_questions);

            // Access Cipher Class Again
            CipherHandler cipher_handler = new CipherHandler();
            String encrypted_email = null;
            try {
                encrypted_email = cipher_handler.encrypt_message(account.getEmail());

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

            assert encrypted_email != null;
            DocumentReference docRef = db.collection("users").document(encrypted_email);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d(ContentValues.TAG, "DocumentSnapshot data: " + document.getData());
                            Intent intent = new Intent(MainActivity.this, HomeScreenActivity.class);
                            String age = document.get("age").toString();
                            String height = document.get("height").toString();
                            String email = document.get("email").toString();
                            try {
                                email = cipher_handler.decrypt_message(email);
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
                            intent.putExtra("age", age);
                            intent.putExtra("height", height);
                            intent.putExtra("email", email);
                            startActivity(intent);
                        } else {
                            Log.d(ContentValues.TAG, "No such document");
                            Intent pass_credentials_to_user_questions = new Intent(MainActivity.this, UserQuestionsActivity.class);
                            pass_credentials_to_user_questions.putExtra("credentials", account);
                            startActivity(pass_credentials_to_user_questions);
                        }
                    } else {
                        Log.d(ContentValues.TAG, "get failed with ", task.getException());
                    }
                }
            });

            TextView user_text_view = findViewById(R.id.maintextview);
            user_text_view.setText("Hello " + account.getGivenName());
            SignInButton signInButton1 = findViewById(R.id.sign_in_button);
            signInButton1.setVisibility(View.INVISIBLE);
            Button signOutButton = findViewById(R.id.sign_out_button);
            signOutButton.setVisibility(View.VISIBLE);
        }
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Button signOutButton = findViewById(R.id.sign_out_button);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoogleSignInClient.signOut().addOnCompleteListener(MainActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        TextView logout_text_view = findViewById(R.id.maintextview);
                        logout_text_view.setText("Please Log In");
                        SignInButton signInButton1 = findViewById(R.id.sign_in_button);
                        signInButton1.setVisibility(View.VISIBLE);
                        Button signOutButton = findViewById(R.id.sign_out_button);
                        signOutButton.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        onStart();
    }

    //if in database then just go to home
    public void openHomeScreenActivity() {
        Intent intent = new Intent(this,HomeScreenActivity.class);
        startActivity(intent);
    }

    //for first time users or something
    public void openUserQuestionsActivity() {
        Intent intent = new Intent(this,UITestActivity.class);
        startActivity(intent);
    }
}