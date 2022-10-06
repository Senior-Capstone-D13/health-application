package com.example.healthapplication;

import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChallengesActivityFragmentAccept extends Fragment {


    //XMl STUFF
    private TextView receivedChallenges;
    private TextView userName;
    private TextView receivedChallengeInfo;
    private Button rejectButton;
    private Button acceptButton;
    private TextView currentChallengeText;
    private TextView currentChallengeInfo;

    public ChallengesActivityFragmentAccept() {
        super(R.layout.activity_challenges_fragment_accept);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_challenges_fragment_accept, container, false);
        receivedChallenges = (TextView) view.findViewById(R.id.receivedChallenges);
        userName = (TextView) view.findViewById(R.id.userName);
        receivedChallengeInfo = (TextView) view.findViewById(R.id.receivedChallengeInfo);
        rejectButton = (Button) view.findViewById(R.id.rejectButton);
        acceptButton = (Button) view.findViewById(R.id.acceptButton);
        currentChallengeText = (TextView) view.findViewById(R.id.currentChallengeText);
        currentChallengeInfo = (TextView) view.findViewById(R.id.currentChallengeInfo);
        return view;
    }

}