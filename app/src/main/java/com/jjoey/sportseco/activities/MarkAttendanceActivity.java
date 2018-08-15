package com.jjoey.sportseco.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.jjoey.sportseco.R;
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

public class MarkAttendanceActivity extends AppCompatActivity {

    private static final String TAG = MarkAttendanceActivity.class.getSimpleName();

    private Toolbar toolbar;
    private TextView sessionNameTxt;
    private ImageView backIV, checkAttendance;

    //    private List<Attendance> arrayList = new ArrayList<>();
    private RecyclerView attendanceRV;
    private List<PlayerSession> arrayList = new ArrayList<>();
    private AttendanceAdapter adapter;

    private String coachId = null, batchId = null, academyId = null,
            progUserMapId = null, progSessId = null;
    private ProgramDetails programDetails;

    private PlayerSession playerSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        init();
        setSupportActionBar(toolbar);

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startHomeActivity();
            }
        });

        setUpAttendanceList();

        checkAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitAttendanceBkg();
                if (adapter.getNumChecked() > 0){
                    Intent intent = new Intent(MarkAttendanceActivity.this, StartSessionActivity.class);
                    intent.putExtra("prg_sess_id", progSessId);
                    intent.putExtra("prg_id", getIntent().getExtras().getString("prg_id"));
                    intent.putExtra("session_name", getIntent().getExtras().getString("session_name"));
                    intent.putExtra("start_time", getIntent().getExtras().getString("start_time"));
                    intent.putExtra("end_time", getIntent().getExtras().getString("end_timed"));
                    intent.putExtra("duration", getIntent().getExtras().getString("duration"));
                    intent.putExtra("has_marked_attendance", true);
                    PlayerSession playerSession = new PlayerSession();
                    playerSession.setPresent(false);
                    startActivity(intent);
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Mark Attendance For Students Present in Session", Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }

    private void submitAttendanceBkg() {
        HashMap<String, String> map = adapter.sendToActivity();

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

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        attendanceRV.setLayoutManager(llm);

        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_anim_fall_down);
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
        progSessId = getIntent().getExtras().getString("prg_sess_id");

        if (playerList().size() > 0) {
            arrayList = playerList();
            Log.d(TAG, "PlayerSession List Size DB:\t" + arrayList.size());
            adapter = new AttendanceAdapter(MarkAttendanceActivity.this, arrayList);
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
                                    Log.d(TAG, "PlayerSession List Size:\t" + arrayList.size());
                                    adapter = new AttendanceAdapter(MarkAttendanceActivity.this, arrayList);
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

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        sessionNameTxt = findViewById(R.id.sessionNameTxt);
        backIV = findViewById(R.id.backIV);
        checkAttendance = findViewById(R.id.checkAttendance);
        attendanceRV = findViewById(R.id.attendanceRV);
    }

    private void startHomeActivity() {
        startActivity(new Intent(MarkAttendanceActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startHomeActivity();
    }

}
