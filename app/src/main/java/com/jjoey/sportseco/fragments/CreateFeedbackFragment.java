package com.jjoey.sportseco.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoey.sportseco.R;
import com.jjoey.sportseco.utils.EmptyRecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateFeedbackFragment extends Fragment {

    private static final String TAG = CreateFeedbackFragment.class.getSimpleName();

    private EmptyRecyclerView createRV;

    public CreateFeedbackFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_create_feedback, container, false);

        createRV = v.findViewById(R.id.createRV);

        return v;
    }

}
