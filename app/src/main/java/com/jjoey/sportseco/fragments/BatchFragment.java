package com.jjoey.sportseco.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.activeandroid.query.Select;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.jjoey.sportseco.R;
import com.jjoey.sportseco.adapters.HomeMiscAdapter;
import com.jjoey.sportseco.adapters.SessionsAdapter;
import com.jjoey.sportseco.models.ProgramDetails;
import com.jjoey.sportseco.models.Sessions;
import com.jjoey.sportseco.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BatchFragment extends Fragment {

    private static final String TAG = BatchFragment.class.getSimpleName();

    private RecyclerView sessionsRV, moreRV;
    private TextView moreTV;
    private VideoView videoView;

    private String coachId = null, batchId = null;

    private List<Sessions> sessionsList = new ArrayList<>();
    private SessionsAdapter adapter;
    private String programId, programUserMapId;
    private Sessions sessions;
    private String videoURL;

    public BatchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        coachId = getArguments().getString("coach_id");
        batchId = getArguments().getString("batch_id");

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
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getActivity()
                , R.anim.layout_anim_fall_down);
        sessionsRV.setLayoutAnimation(controller);

        List<ProgramDetails> details = new Select()
                .from(ProgramDetails.class)
                .where("coach_id=?", coachId)
                .execute();

        // TODO: 7/11/2018 One Major flaw if coach has multiple programs, this might not work as expected
        for (int i = 0; i < details.size(); i++) {
            programId = details.get(i).getProgId();
            programUserMapId = details.get(i).getProgUserMapId();
            if (getAllSessions().size() > 0){
                // do nothing
                sessionsList = getAllSessions();
                adapter = new SessionsAdapter(getActivity(), sessionsList);
                sessionsRV.setAdapter(adapter);
            } else {
                fetchSessions(coachId, batchId, programId, programUserMapId);
            }
        }
        Log.d(TAG, "Sessions DB size:\t" + getAllSessions().size());

    }

    private List<Sessions> getAllSessions() {
        return new Select()
                .from(Sessions.class)
                .orderBy("id ASC")
                .execute();
    }

    private void fetchSessions(String coachId, String batchId, String programId, String programUserMapId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("coach_id", coachId);
            jsonObject.put("batch_id", batchId);
            jsonObject.put("prg_id", programId);
            jsonObject.put("prg_user_map_id", programUserMapId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(Constants.SESSION_LIST)
                .addJSONObjectBody(jsonObject)
                .setTag("Get Sessions for Program")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            Log.d(TAG, "Program Session List Response:\t" + response.toString());
                            try {
                                JSONObject object = new JSONObject(response.toString());
                                JSONArray array = object.getJSONArray("Session_details");
                                for (int m = 0; m < array.length(); m++) {
                                    JSONObject obj = array.getJSONObject(m);

                                    sessions = new Sessions();

                                    String programId =(obj.getString("prg_id"));
                                    String sessionName = obj.getString("session_name");
                                    String programName = obj.getString("prg_name");
                                    String programSessionId = (obj.getString("prg_session_id"));
                                    String videoLink = (obj.getString("prg_session_video_link"));
                                    String coverImage = obj.getString("prg_session_image");
                                    String desc = obj.getString("prg_session_description");
                                    String focusPoints = obj.getString("prg_session_focus_points");
                                    String dateTime = (obj.getString("date_time"));

                                    saveSessionsInfo(programId, programName, sessionName, programSessionId, videoLink, coverImage, desc, focusPoints, dateTime);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    private void saveSessionsInfo(String programId, String programName, String sessionName, String programSessionId, String videoLink, String coverImage, String desc, String focusPoints,  String dateTime) {
        sessions.setProgramId(programId);
        sessions.setSessionName(sessionName);
        sessions.setProgramName(programName);
        sessions.setProgramSessionId(programSessionId);
        sessions.setSessionVideoLink(videoLink);
        sessions.setSessionCoverImage(coverImage);
        sessions.setSessionDesc(desc);
        sessions.setSessionFocusPoints(focusPoints);
        sessions.setDateTime(dateTime);
        sessions.setCoachId(coachId);

        sessions.save();

        sessionsList = getAllSessions();
        Log.d(TAG, "DB Sessions size:\t" + getAllSessions().size());
        adapter = new SessionsAdapter(getActivity(), sessionsList);
        sessionsRV.setAdapter(adapter);

    }

    // TODO: 6/27/2018 complete vid of day
    private void initVideoOfDay() {
        AndroidNetworking.post(Constants.VID_OF_DAY_URL)
                .setTag("Get Video of the Day")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null){
                            Log.d(TAG, "Vid_day response:\t" + response.toString());
                            try {
                                JSONObject jsonObject = new JSONObject(response.toString());
                                videoURL = jsonObject.getString("url");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

        // TODO: 7/12/2018 Show Video from url

    }

}
