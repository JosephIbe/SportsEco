package com.jjoey.sportseco.activities;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.jjoey.sportseco.R;
import com.jjoey.sportseco.fragments.AttendanceDialogFragment;
import com.jjoey.sportseco.fragments.DrillsFragment;
import com.jjoey.sportseco.models.ProgramDetails;
import com.jjoey.sportseco.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class StartSessionActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = StartSessionActivity.class.getSimpleName();

    private String progId, progSessId, progUserMapId, name_session,
            start_time = null, end_time = null, duration = null;

    private Toolbar toolbar;
    private ImageView backIV;

    private ProgressBar fabProgress, resetProgress;
    private FloatingActionButton playFAB, pauseFAB;
    private RelativeLayout resetLayout;

    private TextView session_nameTV, timerTxt, endTimerTxt;
    private CountDownTimer downTimer, resumeTimer;

    private long START_TIME;
    private long TIME_LEFT = START_TIME;
    private long totalTimeCountInMilliseconds, resumeCount;
    private boolean hasStarted = false, isPaused = false;
    private boolean hasMarkedAttendance = false;

    private ProgramDetails programDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_session);

        progId = getIntent().getExtras().getString("prg_id");
        progSessId = getIntent().getExtras().getString("prg_sess_id");
        name_session = getIntent().getExtras().getString("session_name");
        start_time = getIntent().getExtras().getString("start_time");
        end_time = getIntent().getExtras().getString("end_time");
        duration = getIntent().getExtras().getString("duration");
        // TODO: 8/14/2018 Set Session Name

        hasMarkedAttendance = getIntent().getExtras().getBoolean("has_marked_attendance");

        init();
        setSupportActionBar(toolbar);

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showExitSessionDialog();
            }
        });

        programDetails = new Select()
                .from(ProgramDetails.class)
                .orderBy("id ASC")
                .executeSingle();
        progUserMapId = programDetails.getProgUserMapId();
        Log.d(TAG, "Prog User Map Id:\t" + progUserMapId);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        DrillsFragment fragment = new DrillsFragment();

        Bundle bundle = new Bundle();
        bundle.putString("prg_id", progId);
        bundle.putString("prg_sess_id", progSessId);
        bundle.putString("sess_duration", duration);

        fragment.setArguments(bundle);
        transaction.replace(R.id.drillsContainer, fragment).commit();

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showExitSessionDialog();
            }
        });

        playFAB.setOnClickListener(this);
        pauseFAB.setOnClickListener(this);
        resetLayout.setOnClickListener(this);

    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        backIV = findViewById(R.id.backIV);
        timerTxt = findViewById(R.id.timerTxt);
        endTimerTxt = findViewById(R.id.endTimerTxt);
        playFAB = findViewById(R.id.playFAB);
        pauseFAB = findViewById(R.id.pauseFAB);
        fabProgress = findViewById(R.id.fabProgress);
        resetProgress = findViewById(R.id.resetProgress);
        resetLayout = findViewById(R.id.resetLayout);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.playFAB:
                setTimer();

                playFAB.setVisibility(View.GONE);
                pauseFAB.setVisibility(View.VISIBLE);
                fabProgress.setVisibility(View.VISIBLE);
                resetLayout.setVisibility(View.VISIBLE);

                hasStarted = true;
                startTimer();

                break;
            case R.id.pauseFAB:
                pauseTimer();
                break;
            case R.id.resetLayout:
                showExitSessionDialog();
                break;
        }
    }

    private void pauseTimer() {
        downTimer.cancel();
        hasStarted = false;
        isPaused = true;
        playFAB.setVisibility(View.VISIBLE);
        pauseFAB.setVisibility(View.GONE);
        resetLayout.setVisibility(View.GONE);
        fabProgress.setVisibility(View.GONE);
        resetProgress.setVisibility(View.VISIBLE);
    }

    private void endTimer() {
        if (downTimer != null) {
            downTimer.cancel();
            downTimer.onFinish();
            endTimerTxt.setVisibility(View.VISIBLE);
            endTimerTxt.setText("Session Over");
        }
    }

    private void setTimer() {
//        START_TIME = 2700;
        START_TIME = 180; // TODO: 8/14/2018 Change to session duration
//        START_TIME = Long.parseLong(duration);
        totalTimeCountInMilliseconds = START_TIME * 1000;
        fabProgress.setMax((int) (START_TIME * 1000));
        resetProgress.setMax((int) (START_TIME * 1000));
    }

    private void startTimer() {
        downTimer = new CountDownTimer(totalTimeCountInMilliseconds, 1) {
            @Override
            public void onTick(long millisTillFinish) {
                long seconds = millisTillFinish / 1000;
                fabProgress.setProgress((int) millisTillFinish);
                resetProgress.setProgress((int) millisTillFinish);
                timerTxt.setText(String.format("%02d", seconds / 60) + " : " + String.format("%02d", seconds % 60));
            }

            @Override
            public void onFinish() {
                timerTxt.setText("00 : 00");
                endTimerTxt.setVisibility(View.VISIBLE);
                playFAB.setVisibility(View.GONE);
                pauseFAB.setVisibility(View.GONE);
                resetLayout.setVisibility(View.GONE);
                fabProgress.setVisibility(View.GONE);
                resetProgress.setVisibility(View.GONE);
                if (hasMarkedAttendance){
                    markSessionComplete(progSessId, progUserMapId);
                    startHomeActivity();
                } else {
                    showAttendanceDialog();
                }
            }
        }.start();
    }

    private void showAttendanceDialog() {
        AttendanceDialogFragment dialogFragment = new AttendanceDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("prg_sess_id", progSessId);
        dialogFragment.setArguments(bundle);
        dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_NoActionBar_Fullscreen);
        dialogFragment.setCancelable(false);
        dialogFragment.show(getFragmentManager(), "AttendanceDialogFragment");
    }

    private void showExitSessionDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this)
                .setTitle("Quit Session")
                .setMessage("This Session Will Be Marked As Cancelled. Do You Wish To Proceed?")
                .setIcon(R.drawable.warning)
                .setPositiveButton("Yes, QUIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        markSessionComplete(progSessId, progUserMapId);
                        endTimer();
                        startHomeActivity();
                    }
                })
                .setNegativeButton("CANCEL", null);
        AlertDialog dialog = alertDialog.create();
        dialog.show();

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
        startActivity(new Intent(StartSessionActivity.this, HomeActivity.class));
        overridePendingTransition(R.anim.anim_slide_left, R.anim.anim_slide_right);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.ACTION_DOWN) {
            super.onKeyDown(keyCode, event);
            return true;
        }
        return false;
    }

}
