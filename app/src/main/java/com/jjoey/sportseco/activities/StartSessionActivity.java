package com.jjoey.sportseco.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jjoey.sportseco.R;
import com.jjoey.sportseco.fragments.DrillsFragment;

public class StartSessionActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = StartSessionActivity.class.getSimpleName();

    private String progId, progSessId, name_session;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_session);

        progId = getIntent().getExtras().getString("prg_id");
        Log.d(TAG, "Prog id:\t" + progId);
        progSessId = getIntent().getExtras().getString("prg_sess_id");
        Log.d(TAG, "Session Prog id:\t" + progSessId);
        name_session = getIntent().getExtras().getString("session_name");

        if (name_session != null) {
            Log.d(TAG, "Session Name:\t" + name_session);
//            session_nameTV.setText(name_session);
        }

        init();
        setSupportActionBar(toolbar);

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showExitSessionDialog();
            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        DrillsFragment fragment = new DrillsFragment();

        Bundle bundle = new Bundle();
        bundle.putString("prg_id", progId);
        bundle.putString("prg_sess_id", progSessId);

        fragment.setArguments(bundle);
        transaction.replace(R.id.drillsContainer, fragment).commit();

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 7/3/2018  show session exit dialog
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
                endTimer();
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
        START_TIME = 180;
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
            }
        }.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showExitSessionDialog();
//        startHomeActivity();
    }

    private void showExitSessionDialog() {

    }

    private void startHomeActivity() {
        startActivity(new Intent(StartSessionActivity.this, HomeActivity.class));
        finish();
    }

}
