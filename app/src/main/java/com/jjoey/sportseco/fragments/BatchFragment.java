package com.jjoey.sportseco.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.jjoey.sportseco.R;
import com.jjoey.sportseco.activities.HomeActivity;
import com.jjoey.sportseco.adapters.HomeMiscAdapter;
import com.jjoey.sportseco.adapters.SessionsAdapter;
import com.jjoey.sportseco.models.ProgramDetails;
import com.jjoey.sportseco.models.Sessions;
import com.jjoey.sportseco.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class BatchFragment extends Fragment {

    private static final String TAG = BatchFragment.class.getSimpleName();

    private RecyclerView sessionsRV, moreRV;
    private TextView moreTV;
    private VideoView videoView;

    private List<Object> sessionsList = new ArrayList<>();
    private SessionsAdapter adapter;

    public BatchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_batch, container, false);

        initViews(view);

        setUpSessionsRV();

        initVideoOfDay();

        setUpMisc();

        return view;
    }

    private void initViews(View view) {
        moreTV = view.findViewById(R.id.moreTV);
        sessionsRV = view.findViewById(R.id.sessionsRV);
        moreRV = view.findViewById(R.id.moreRV);
        videoView = view.findViewById(R.id.videoView);
    }

    private void setUpMisc() {
        moreRV.setHasFixedSize(true);
        LinearLayoutManager horLm = new LinearLayoutManager(getActivity());
        horLm.setOrientation(LinearLayoutManager.HORIZONTAL);
        moreRV.setLayoutManager(horLm);

        List<String> list = new ArrayList<>();
        String misc = "Focal Points";
        list.add(misc);

        String misc2 = "Evaluations";
        list.add(misc2);

        HomeMiscAdapter miscAdapter = new HomeMiscAdapter(getActivity(), list);
        moreRV.setAdapter(miscAdapter);

        moreTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "More Clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setUpSessionsRV() {
        sessionsRV.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        sessionsRV.setLayoutManager(llm);

        ProgramDetails details = new ProgramDetails();
        Log.d(TAG, "Program id:\t" + details.getProgId());

        Sessions bodyItem = new Sessions(R.drawable.ic_launcher_background, "Dribbling", null, null, null);
        sessionsList.add(bodyItem);

        Sessions bodyItem1 = new Sessions(R.drawable.court, "Free Kick", null, null, null);
        sessionsList.add(bodyItem1);

        Sessions bodyItem2 = new Sessions(R.drawable.nav_icon, "Defending", null, null, null);
        sessionsList.add(bodyItem2);

        adapter = new SessionsAdapter(getActivity(), sessionsList);
        sessionsRV.setAdapter(adapter);

    }

    // TODO: 6/27/2018 complete vid of day
    private void initVideoOfDay() {
        videoView.setVideoPath(Constants.VID_URL);
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (videoView.isPlaying()){
                    videoView.pause();
                } else {
                    videoView.start();
                }
            }
        });
    }

}
