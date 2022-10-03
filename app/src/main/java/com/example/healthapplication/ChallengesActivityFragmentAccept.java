package com.example.healthapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChallengesActivityFragmentAccept extends Fragment {

    public ChallengesActivityFragmentAccept() {
        super(R.layout.activity_challenges_fragment_accept);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_challenges_fragment_accept, container, false);
    }
}