package com.jjoey.sportseco.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jjoey.sportseco.R;
import com.jjoey.sportseco.fragments.DrillsFragment;

import java.util.concurrent.TimeUnit;

public class StartSessionActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = StartSessionActivity.class.getSimpleName();

    private Toolbar toolbar;
    private ImageView backIV;

//    private ProgressBar progressTimer1, progressTimer2;
//    private FloatingActionButton startFAB, pauseFAB, stopFAB;
    private ProgressBar fabProgress, resetProgress;
    private FloatingActionButton playFAB, pauseFAB;
    private RelativeLayout resetLayout;

//    private RecyclerView drillsRV;
//    private DrillsAdapter adapter;
//    private List<Drills> drillsList = new ArrayList<>();

    private TextView session_nameTV, timerTxt, endTimerTxt;
    private CountDownTimer downTimer;

    private long START_TIME;
    private long TIME_LEFT = START_TIME;
    private long totalTimeCountInMilliseconds;
    private boolean hasStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_session_layout);

        init();
        setSupportActionBar(toolbar);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.drillsContainer, new DrillsFragment()).commit();

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 7/3/2018  show session exit dialog
            }
        });

//        startFAB.setOnClickListener(this);
//        pauseFAB.setOnClickListener(this);
//        stopFAB.setOnClickListener(this);
        playFAB.setOnClickListener(this);
        pauseFAB.setOnClickListener(this);
        resetLayout.setOnClickListener(this);

    }

    private String timeFormatter(long timeCountMilliSecs) {
        @SuppressLint("DefaultLocale") String HMS = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(timeCountMilliSecs),

                TimeUnit.MILLISECONDS.toMinutes(timeCountMilliSecs) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeCountMilliSecs)),

                TimeUnit.MILLISECONDS.toSeconds(timeCountMilliSecs) -
                        TimeUnit.SECONDS.toSeconds(TimeUnit.MILLISECONDS.toSeconds(timeCountMilliSecs))
        );
        return HMS;
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        backIV = findViewById(R.id.backIV);
        timerTxt = findViewById(R.id.timerTxt);
        endTimerTxt = findViewById(R.id.endTimerTxt);
        playFAB = findViewById(R.id.playFAB);
        pauseFAB = findViewById(R.id.pauseFAB);
//        startFAB = findViewById(R.id.startSessionFAB);
//        pauseFAB = findViewById(R.id.pauseSessionFAB);
//        stopFAB = findViewById(R.id.stopSessionFAB);
//        progressTimer1 = findViewById(R.id.progressTimer1);
//        progressTimer2 = findViewById(R.id.progressTimer2);
        fabProgress = findViewById(R.id.fabProgress);
        resetProgress = findViewById(R.id.resetProgress);
        resetLayout = findViewById(R.id.resetLayout);
//        drillsRV = findViewById(R.id.drillsRV);

//        setUpDrillsView();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            case R.id.startSessionFAB:
//                setTimer();
//
//                startFAB.setVisibility(View.GONE);
//                pauseFAB.setVisibility(View.VISIBLE);
//                stopFAB.setVisibility(View.VISIBLE);
//                progressTimer1.setVisibility(View.GONE);
//
//                startTimer();
//                hasStarted = true;
//                progressTimer2.setVisibility(View.VISIBLE);
//                break;
//            case R.id.pauseSessionFAB:
//                if (hasStarted){
//                    pauseTimer();
//                }
//                break;
//            case R.id.stopSessionFAB:
//                endTimer();
//                break;

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

//    private void pauseTimer() {
//        downTimer.cancel();
//        hasStarted = false;
//        startFAB.setVisibility(View.VISIBLE);
//        pauseFAB.setVisibility(View.GONE);
//        stopFAB.setVisibility(View.GONE);
//        progressTimer2.setVisibility(View.GONE);
//        progressTimer1.setVisibility(View.VISIBLE);
//    }
//
//    private void endTimer() {
//        if (downTimer != null) {
//            downTimer.cancel();
//            downTimer.onFinish();
//            startFAB.setVisibility(View.VISIBLE);
//            pauseFAB.setVisibility(View.GONE);
//            stopFAB.setVisibility(View.GONE);
//            progressTimer2.setVisibility(View.GONE);
//            progressTimer1.setVisibility(View.VISIBLE);
//        }
//
//    }

//    private void setTimer() {
//        START_TIME = 2700;
//        totalTimeCountInMilliseconds = START_TIME * 1000;
//        progressTimer2.setMax((int) (START_TIME * 1000));
//    }

//    private void startTimer() {
//        downTimer = new CountDownTimer(totalTimeCountInMilliseconds, 1) {
//            @Override
//            public void onTick(long millisTillFinish) {
//                long seconds = millisTillFinish / 1000;
//                progressTimer2.setProgress((int) millisTillFinish);
//                timerTxt.setText(String.format("%02d", seconds / 60) + " : " + String.format("%02d", seconds % 60));
//            }
//
//            @Override
//            public void onFinish() {
//                timerTxt.setText("00 : 00");
//                startFAB.setVisibility(View.VISIBLE);
//                stopFAB.setVisibility(View.GONE);
//                progressTimer2.setVisibility(View.GONE);
//                progressTimer1.setVisibility(View.VISIBLE);
//            }
//        }.start();
//    }

}
