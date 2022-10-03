package com.example.healthapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 */
public class ChallengesActivityFragmentSend extends Fragment {

    public ChallengesActivityFragmentSend() {
        super(R.layout.activity_challenges_fragment_send);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_challenges_fragment_send, container, false);
    }
}