package com.jjoey.sportseco.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.query.Select;
import com.jjoey.sportseco.R;
import com.jjoey.sportseco.adapters.SessionsAdapter;
import com.jjoey.sportseco.models.Batch;
import com.jjoey.sportseco.models.Coach;
import com.jjoey.sportseco.models.ProgramDetails;
import com.jjoey.sportseco.models.Sessions;
import com.jjoey.sportseco.utils.EmptyRecyclerView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateFeedbackFragment extends Fragment {

    private static final String TAG = CreateFeedbackFragment.class.getSimpleName();

    private EmptyRecyclerView createRV;
    private String coachId = null, batchId = null, programUserMapId = null;
    String programId = null, programSessionId = null;

    public CreateFeedbackFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        coachId = getArguments().getString("coach_id");
        Coach coach = new Select()
                .from(Coach.class)
                .orderBy("id ASC")
                .executeSingle();
        coachId = coach.coachId;

        Log.d(TAG, "Coach id:\t" + coachId);

        Batch batch = new Select()
                .from(Batch.class)
                .orderBy("id ASC")
                .executeSingle();
        batchId = batch.batchId;
//        batchId = getArguments().getString("batch_id");

        Log.d(TAG, "Batch id:\t" + batchId);

        List<ProgramDetails> details = new Select()
                .from(ProgramDetails.class)
                .where("coach_id=?", coachId)
                .execute();

        // TODO: 7/11/2018 One Major flaw if coach has multiple programs, this might not work as expected
        for (int i = 0; i < details.size(); i++) {
            programId = details.get(i).getProgId();
            programUserMapId = details.get(i).getProgUserMapId();
        }

        //List of players has to be tied to  session. Any api to get all players under a coach?
        Sessions sessions = new Select()
                .from(Sessions.class)
                .where("coach_id=?", coachId)
                .executeSingle();
        Log.d(TAG, "Prog Sess id:\t" + sessions.getProgramSessionId());

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
