package com.example.healthapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
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

import org.w3c.dom.Text;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class ChallengesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges);


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


        TextView name_text_view = findViewById(R.id.userName);
        TextView currentChallengeText = findViewById(R.id.currentChallengeText);
        TextView current_challenges = findViewById(R.id.currentChallengeInfo);
        TextView received_challenges_text_view = findViewById(R.id.receivedChallengeInfo);

        TextView sent_challenges_display = findViewById(R.id.sentChallengesDisplay);
        TextView sent_challenges = findViewById(R.id.sentChallengeText);

        name_text_view.setText(account.getGivenName());
        currentChallengeText.setText("Current Challenges");
        sent_challenges_display.setText("Sent Challenges");
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
                        if(received_challenges.size() != 0) {
                            received_challenges_emails.addAll(received_challenges.keySet());
                            received_challenges_text_view.setText("Email: " + received_challenges_emails.get(0) + " Challenge: "
                                    + received_challenges.get(received_challenges_emails.get(0)));
                            reference_hashmap.put(received_challenges_emails.get(0), received_challenges.get(received_challenges_emails.get(0)));
                        }
                        else{
                            received_challenges_text_view.setText("You currently do not have any receieved challenges");
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
        Button accept_challenge_button = findViewById(R.id.acceptButton);
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

        Button send_challenge_button = findViewById(R.id.sendButton);
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
        ImageView dismiss_button = findViewById(R.id.challengesGoHome);
        dismiss_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //XXX
                //need to change so don't have to go to login screen
                //to get google information
                Intent go_back_to_main = new Intent(ChallengesActivity.this, MainActivity.class);
                startActivity(go_back_to_main);
            }
        });

        setSendVisibility(View.INVISIBLE);
        Button swapButton = findViewById(R.id.swapButton);
        swapButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(accept_challenge_button.getVisibility() == View.VISIBLE){
                    setAcceptVisibility(View.INVISIBLE);
                    setSendVisibility(View.VISIBLE);
                }else{
                    setAcceptVisibility(View.VISIBLE);
                    setSendVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void setAcceptVisibility(int visibility){
        // Keep these visible
        // TextView currentChallengeText = findViewById(R.id.currentChallengeText);
        // TextView current_challenges = findViewById(R.id.currentChallengeInfo);
        TextView name_text_view = findViewById(R.id.userName);
        TextView received_challenges_text_view = findViewById(R.id.receivedChallengeInfo);
        TextView receivedChallenges = findViewById(R.id.receivedChallenges);
        Button acceptButton = findViewById(R.id.acceptButton);
        Button rejectButton = findViewById(R.id.rejectButton);

        name_text_view.setVisibility(visibility);
        received_challenges_text_view.setVisibility(visibility);
        receivedChallenges.setVisibility(visibility);
        acceptButton.setVisibility(visibility);
        rejectButton.setVisibility(visibility);
    }

    public void setSendVisibility(int visibility){
        //Keep these visible
        //TextView sent_challenges_display = findViewById(R.id.sentChallengesDisplay);
        // TextView sent_challenges = findViewById(R.id.sentChallengeText);
        EditText challenged_email_edit_text = findViewById(R.id.challengedEmailText);
        EditText challenge_edit_text = findViewById(R.id.customChallenge);
        TextView name = findViewById(R.id.Name);
        TextView challengeToBeSent = findViewById(R.id.challengeToBeSent);
        Button sendButton = findViewById(R.id.sendButton);

        challenged_email_edit_text.setVisibility(visibility);
        challenge_edit_text.setVisibility(visibility);
        name.setVisibility(visibility);
        challengeToBeSent.setVisibility(visibility);
        sendButton.setVisibility(visibility);
    }

}