package com.example.healthapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
/**
 * A simple {@link Fragment} subclass.
 */
public class ChallengesActivityFragmentSend extends Fragment {

    private TextView name;
    private TextView challengedEmailText;
    private TextView customChallenge;
    private TextView sentChallengesDisplay;
    private TextView challengeToBeSent;
    private TextView sentChallengeText;
    private Button sendButton;

    public ChallengesActivityFragmentSend() {

        super(R.layout.activity_challenges_fragment_send);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_challenges_fragment_send, container, false);

        name = (TextView) view.findViewById(R.id.name);
        challengedEmailText = (TextView) view.findViewById(R.id.challengedEmailText);
        customChallenge = (TextView) view.findViewById(R.id.customChallenge);
        sentChallengesDisplay = (TextView)  view.findViewById(R.id.sentChallengesDisplay);
        challengeToBeSent = (TextView) view.findViewById(R.id.challengeToBeSent);
        sentChallengeText = (TextView) view.findViewById(R.id.sentChallengeText);
        sendButton = (Button) view.findViewById(R.id.sendButton);

        return view;
    }

}