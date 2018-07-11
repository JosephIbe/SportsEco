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
public class FeedbackHistoryFragment extends Fragment {

    private static final String TAG = FeedbackHistoryFragment.class.getSimpleName();

    private EmptyRecyclerView historyRV;

    public FeedbackHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback_history, container, false);

        historyRV = view.findViewById(R.id.historyRV);

        return view;
    }

}
