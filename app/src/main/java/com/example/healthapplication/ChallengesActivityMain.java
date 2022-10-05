package com.example.healthapplication;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class ChallengesActivityMain extends AppCompatActivity {

    private FragmentManager fragmentManager;

    private ChallengesActivityFragmentAccept acceptFragment;
    private ChallengesActivityFragmentSend sendFragment;
    private Fragment currentFragment;

    GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges_main);
        initializeFragments();
        initializeButtons();
    }

    public void initializeFragments() {
        acceptFragment = new ChallengesActivityFragmentAccept();
        sendFragment = new ChallengesActivityFragmentSend();

        fragmentManager = getSupportFragmentManager();

        //replace default container from xml with new container
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer,acceptFragment, null)
                .setReorderingAllowed(true)
                .commit();

        //set starting fragment to fragment replaced container with
        currentFragment = acceptFragment;

        //add sendFragment to fragmentManager
        //hide it so acceptFragment only one shown
        fragmentManager.beginTransaction()
                .add(R.id.fragmentContainer,sendFragment, null)
                .setReorderingAllowed(true)
                .hide(sendFragment)
                .commit();

        //Change Fragment buttons
        //updates currentFragment depending on selected fragment
        Button goAcceptFragment = findViewById(R.id.goAcceptFragment);
        goAcceptFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(
                                R.anim.slide_in_left,  //Enter
                                R.anim.slide_out_right //Exit
                        )
                        .hide(currentFragment)
                        .show(acceptFragment)
                        .setReorderingAllowed(true)
                        .commit();
                currentFragment = acceptFragment;
                onStart();
            }
        });

        Button goSendFragment = findViewById(R.id.goSendFragment);
        goSendFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .setCustomAnimations(
                                R.anim.slide_in_right,  //Enter
                                R.anim.slide_out_left   //Exit
                        )
                        .hide(currentFragment)
                        .show(sendFragment)
                        .setReorderingAllowed(true)
                        .commit();
                currentFragment = sendFragment;
                onStart();
            }
        });
    }

    public void initializeButtons() {
        Button exit = findViewById(R.id.exitButton);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChallengesActivityMain.this,HomeScreenActivity.class);
                intent.putExtra("credentials", account);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        TextView name_text_view = acceptFragment.getView().findViewById(R.id.userName);
        //TextView currentChallengeText = findViewById(R.id.currentChallengeText);
        TextView current_challenges = acceptFragment.getView().findViewById(R.id.currentChallengeInfo);
        TextView sent_challenges_display = sendFragment.getView().findViewById(R.id.sentChallengesDisplay);
        TextView sent_challenges = sendFragment.getView().findViewById(R.id.sentChallengeText);

        name_text_view.setText(account.getGivenName());
        //currentChallengeText.setText("Current Challenges");
        //sent_challenges_display.setText("Sent Challenges");
        // Initialize HashMap to use semi-globally
        HashMap<String, String> reference_hashmap = new HashMap<>();
        ArrayList<String> received_challenges_emails = new ArrayList<>();
        ArrayList<String> current_challenges_emails = new ArrayList<>();
        ArrayList<String> sent_challenges_emails = new ArrayList<>();
        CipherHandler ch = new CipherHandler();
        // Scan through the database for initial settings
        String encrypted_email = "default";
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
        DocumentReference docRef = db.collection("users").document(encrypted_email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        // Displays list of received challenges
                        HashMap<String, String> received_challenges = (HashMap<String, String>) document.get("received_challenges");

                        TextView received_challenges_text_view = acceptFragment.getView().findViewById(R.id.receivedChallengeInfo);

                        if(received_challenges.size() != 0) {
                            received_challenges_emails.addAll(received_challenges.keySet());
                            received_challenges_text_view.setText("Email: " + received_challenges_emails.get(0) + " Challenge: "
                                    + received_challenges.get(received_challenges_emails.get(0)));
                            reference_hashmap.put(received_challenges_emails.get(0), received_challenges.get(received_challenges_emails.get(0)));
                        }
                        else{
                            received_challenges_text_view.setText("You currently do not have any received challenges");
                        }

                        // Get initial list of current challenges

                        HashMap<String, String> current_challenges_hash_map = (HashMap<String, String>) document.get("accepted_challenges");
                        if(current_challenges_hash_map.size() != 0){
                            current_challenges_emails.addAll(current_challenges_hash_map.keySet());
                            current_challenges.setText("Email: " + current_challenges_emails.get(0) + " Challenge: "
                                    + current_challenges_hash_map.get(current_challenges_emails.get(0)));
                        }
                        else{
                            current_challenges.setText("You currently do not have any ongoing challenges");
                        }

                        // Get initial list of sent challenges that have not been accepted yet

                        HashMap<String, String> sent_challenges_hash_map = (HashMap<String, String>) document.get("sent_challenges");
                        if(sent_challenges_hash_map.size() != 0){
                            sent_challenges_emails.addAll(sent_challenges_hash_map.keySet());
                            sent_challenges.setText("Email: " + sent_challenges_emails.get(0) + " Challenge: "
                                    + sent_challenges_hash_map.get(sent_challenges_emails.get(0)));
                        }
                        else{
                            sent_challenges.setText("You have not sent any challenges!");
                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        // Add On Button Clickers
        Button accept_challenge_button = acceptFragment.getView().findViewById(R.id.acceptButton);
        String finalEncrypted_email = encrypted_email;
        accept_challenge_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change received challenges to accepted challenges
                CollectionReference user_collection = db.collection("users");
                HashMap<String, String> blank_hash_map = new HashMap<>();
                user_collection.document(finalEncrypted_email).update("accepted_challenges", reference_hashmap);
                user_collection.document(finalEncrypted_email).update("received_challenges", blank_hash_map);
                // On the other person's database, change sent challenges to accepted challenges

                user_collection.document(received_challenges_emails.get(0)).update("sent_challenges", blank_hash_map);
                HashMap<String, String> opposite_hash_map = new HashMap<>();
                opposite_hash_map.put(finalEncrypted_email, reference_hashmap.get(received_challenges_emails.get(0)));
                user_collection.document(received_challenges_emails.get(0)).update("accepted_challenges", opposite_hash_map);
            }
        });

        Button send_challenge_button = sendFragment.getView().findViewById(R.id.sendButton);
        send_challenge_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Capture the current TextBoxes
                EditText challenged_email_edit_text = findViewById(R.id.challengedEmailText);
                EditText challenge_edit_text = findViewById(R.id.customChallenge);
                String email = challenged_email_edit_text.getText().toString();
                try {
                    email = ch.encrypt_message(email);
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
                String challenge = challenge_edit_text.getText().toString();
                HashMap<String, String> sent_challenges_hash_map = new HashMap<>();
                HashMap<String, String> received_challenges_hash_map = new HashMap<>();
                // If the email and challenges are valid, then update sent_challenges of this user and received challenges of other user
                CollectionReference user_collection = db.collection("users");
                DocumentReference docRef = db.collection("users").document(email);
                String finalEmail = email;
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        // If the user exists in the database
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Add sent_challenges to this user
                                sent_challenges_hash_map.put(finalEmail, challenge);
                                db.collection("users").document(account.getEmail()).update("sent_challenges", sent_challenges_hash_map);

                                // Add received_challenges to other user
                                received_challenges_hash_map.put(account.getEmail(), challenge);
                                db.collection("users").document(finalEmail).update("received_challenges", received_challenges_hash_map);
                                challenge_edit_text.setText("Challenge Sent!!");
                            } else {
                                Log.d(TAG, "No such document");
                                challenged_email_edit_text.setText("No such user exists!");
                            }
                        } else {
                            challenged_email_edit_text.setText("No such user exists!");
                        }
                    }
                });

                // Else send an error message
            }
        });
    }



}