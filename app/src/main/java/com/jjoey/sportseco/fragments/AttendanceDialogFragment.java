package com.jjoey.sportseco.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;

import com.activeandroid.query.Select;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.jjoey.sportseco.R;
import com.jjoey.sportseco.activities.HomeActivity;
import com.jjoey.sportseco.activities.StartSessionActivity;
import com.jjoey.sportseco.adapters.AttendanceAdapter;
import com.jjoey.sportseco.models.Batch;
import com.jjoey.sportseco.models.Coach;
import com.jjoey.sportseco.models.PlayerSession;
import com.jjoey.sportseco.models.ProgramDetails;
import com.jjoey.sportseco.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceDialogFragment extends DialogFragment {

    private static final String TAG = AttendanceDialogFragment.class.getSimpleName();

    private Toolbar toolbar;
    private ImageView checkAttendance;

    private RecyclerView attendanceRV;
    private List<PlayerSession> arrayList = new ArrayList<>();
    private AttendanceAdapter adapter;

    private String coachId = null, batchId = null, academyId = null,
            progUserMapId = null, progSessId = null;
    private ProgramDetails programDetails;

    private PlayerSession playerSession;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance_dialog, container, false);

        init(view);

        setUpAttendanceList();

        checkAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getNumChecked() > 0){
                    submitAttendanceBkg();
                    markSessionComplete(progSessId, progUserMapId);
                    startHomeActivity();
                } else {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "Mark Attendance For Students Present in Session",
                            Snackbar.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    private void markSessionComplete(String progSessId, String mapId) {
        Log.d(TAG, "Prog Sess Id in method:\t" + progSessId);
        Log.d(TAG, "Prog User Map Id in method:\t" + mapId);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("prg_session_id", progSessId);
            jsonObject.put("prg_user_map_id", mapId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(Constants.MARK_SESSION_COMPLETE)
                .addJSONObjectBody(jsonObject)
                .setTag("Mark Session Complete")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null){
                            Log.d(TAG, "Mark Session Complete Response:\t" + response.toString());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }

    private void startHomeActivity() {
        startActivity(new Intent(getActivity(), HomeActivity.class));
        getActivity().overridePendingTransition(R.anim.anim_slide_left, R.anim.anim_slide_right);
        getActivity().finish();
    }

    private void submitAttendanceBkg() {
        HashMap<String, String> map = adapter.sendToDialogFragment();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("prg_user_map_id", progUserMapId);
            jsonObject.put("prg_session_id", progSessId);
            jsonObject.put("batch_id", batchId);
            jsonObject.put("coach_id", coachId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringBuilder keyBuilder = new StringBuilder();
        StringBuilder valueBuilder = new StringBuilder();
        for (Map.Entry<String, String> viewer : map.entrySet()) {
            Log.d(TAG, "Players in map:\t" + viewer.getKey() + " \t and status in map:\t" + viewer.getValue());
            keyBuilder.append(viewer.getKey());
            valueBuilder.append(viewer.getValue());
        }
        try {
            jsonObject.put("player_id", keyBuilder.toString());
            jsonObject.put("att_status", valueBuilder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(Constants.PLAYER_ATTENDANCE)
//                .addJSONObjectBody(object)
                .addJSONObjectBody(jsonObject)
                .setTag("Send Player Attendance")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            Log.d(TAG, "Attendance Response:\t" + response.toString());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }

    private void setUpAttendanceList() {
        attendanceRV.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        attendanceRV.setLayoutManager(llm);

        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_anim_fall_down);
        attendanceRV.setLayoutAnimation(controller);

        Coach coach = new Select()
                .from(Coach.class)
                .orderBy("id ASC")
                .executeSingle();
        coachId = coach.coachId;
        academyId = coach.academyId;

        Batch batch = new Select()
                .from(Batch.class)
                .orderBy("id ASC")
                .executeSingle();
        batchId = batch.batchId;

        programDetails = new Select()
                .from(ProgramDetails.class)
                .where("coach_id=?", coachId)
                .executeSingle();
        progUserMapId = programDetails.getProgUserMapId();
        progSessId = getArguments().getString("prg_sess_id");

        if (playerList().size() > 0) {
            arrayList = playerList();
            Log.d(TAG, "PlayerSession List Size DB:\t" + arrayList.size());
            adapter = new AttendanceAdapter(getActivity(), arrayList);
            attendanceRV.setAdapter(adapter);
        } else {
            fetchPlayersForSession(coachId, batchId, progSessId, progUserMapId, academyId);
        }
//            fetchPlayersForSession(coachId, batchId, progSessId, progUserMapId, academyId);

    }

    private List<PlayerSession> playerList() {
        return new Select()
                .from(PlayerSession.class)
                .orderBy("id ASC")
                .execute();
    }

    private void fetchPlayersForSession(String coachId, String batchId, String progSessId, String progUserMapId, String academyId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("coach_id", coachId);
            jsonObject.put("batch_id", batchId);
            jsonObject.put("prg_session_id", progSessId);
            jsonObject.put("prg_user_map_id", progUserMapId);
            jsonObject.put("academy_id", academyId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(Constants.COACH_PLAYER_LIST_SESSION)
                .addJSONObjectBody(jsonObject)
                .setTag("Get Players List for Session")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            Log.d(TAG, "Attendance Resp:\t" + response.toString());
                            try {
                                JSONObject object = new JSONObject(response.toString());
                                JSONArray arr = object.getJSONArray("players");
                                for (int m = 0; m < arr.length(); m++) {
                                    JSONObject info = arr.getJSONObject(m);

                                    playerSession = new PlayerSession();
                                    playerSession.setUserId_player(info.getString("user_id"));
                                    playerSession.setImageURL(info.getString("image"));
                                    playerSession.setFirstName_player(info.getString("first_name"));
                                    playerSession.setLastName_player(info.getString("last_name"));
                                    playerSession.setUsername(info.getString("username"));
                                    playerSession.setAddress_player(info.getString("address"));
                                    playerSession.setBatchName_player(info.getString("batch_name"));
                                    playerSession.setBatchId_player(info.getString("batch_id"));
                                    playerSession.setProgramName_player(info.getString("prg_name"));
                                    playerSession.setProgramIdPlayer(info.getString("prg_id"));
                                    playerSession.setProgramUserMapId_player(info.getString("prg_user_map_id"));
                                    playerSession.setStartDate_player(info.getString("prg_start_date"));
                                    playerSession.setEndDate_player(info.getString("prg_end_date"));
                                    playerSession.setAttendanceStatus_player(info.getString("att_status"));

                                    playerSession.save();

//                                    arrayList = playerList();
                                    arrayList.add(playerSession);
                                    adapter = new AttendanceAdapter(getActivity(), arrayList);
                                    Log.d(TAG, "Player List Size:\t" + arrayList.size());
                                    attendanceRV.setAdapter(adapter);

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

    private void init(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        checkAttendance = view.findViewById(R.id.checkAttendance);
        attendanceRV = view.findViewById(R.id.attendanceRV);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

}
