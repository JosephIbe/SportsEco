package com.jjoey.sportseco.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jjoey.sportseco.R;
import com.jjoey.sportseco.adapters.DrillsAdapter;
import com.jjoey.sportseco.models.Drills;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class StartSessionActivity extends AppCompatActivity {

    private int DURATION = 45; // minutes

    public enum TimerState {
        STOPPED, PAUSED, RUNNING;
    }

    private static final String TAG = StartSessionActivity.class.getSimpleName();

    private Toolbar toolbar;
    private ImageView backIV;

    private ProgressBar progressBar;
    private FloatingActionButton startFAB, pauseFAB, stopFAB;
    private RecyclerView drillsRV;
    private DrillsAdapter adapter;
    private List<Drills> drillsList = new ArrayList<>();

    private TextView sessionTimerTxt;
    private CountDownTimer downTimer;
    private TimerState timerState;
    private long timeCountMilliSecs = 1 * 60000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_session);

        init();
        setSupportActionBar(toolbar);

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 7/3/2018  show session exit dialog
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("Close Session");
//                builder.setMessage("Are You Sure You Want to Quit This Session? Session info will be lost");

            }
        });

        startFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimer();
            }
        });

        pauseFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        stopFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timerState == TimerState.RUNNING){
                    resetTimer();
                }
            }
        });

    }

    private void resetTimer() {
        stopCountDownTimer();
        startCountDownTimer();
    }

    private void startTimer() {
        if (timerState == TimerState.STOPPED) {
            stopFAB.setVisibility(View.GONE);
            pauseFAB.setVisibility(View.GONE);
            setTimerValues();
            setProgressBarValues();
            timerState = TimerState.RUNNING;
            startCountDownTimer();
        } else {
            stopFAB.setVisibility(View.VISIBLE);
            pauseFAB.setVisibility(View.VISIBLE);
            timerState = TimerState.STOPPED;
//            stopCountDownTimer();
        }
    }

    private void stopCountDownTimer() {
        downTimer.cancel();
    }

    private void startCountDownTimer() {
        downTimer = new CountDownTimer(timeCountMilliSecs, 1000) {
            @Override
            public void onTick(long l) {
                sessionTimerTxt.setText(timeFormatter(l));
                progressBar.setProgress((int) (l / 1000));
            }

            @Override
            public void onFinish() {
                sessionTimerTxt.setText(timeFormatter(timeCountMilliSecs));
                setProgressBarValues();
                stopFAB.setVisibility(View.GONE);
                pauseFAB.setVisibility(View.GONE);
                timerState = TimerState.STOPPED;
            }
        }.start();
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

    private void setProgressBarValues() {
        progressBar.setMax((int) (timeCountMilliSecs / 1000));
        progressBar.setProgress((int) (timeCountMilliSecs / 1000));
    }

    private void setTimerValues() {
        int time = DURATION;
        timeCountMilliSecs = time * 60 * 1000;
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        backIV = findViewById(R.id.backIV);
        sessionTimerTxt = findViewById(R.id.sessionTimerTxt);
        startFAB = findViewById(R.id.startSessionFAB);
        pauseFAB = findViewById(R.id.pauseSessionFAB);
        stopFAB = findViewById(R.id.stopSessionFAB);
        progressBar = findViewById(R.id.progressCountDown);
        drillsRV = findViewById(R.id.drillsRV);

        setUpDrillsView();

    }

    private void setUpDrillsView() {
        drillsRV.setHasFixedSize(true);
        LinearLayoutManager vlm = new LinearLayoutManager(this);
        vlm.setOrientation(LinearLayoutManager.VERTICAL);
        drillsRV.setLayoutManager(vlm);
        drillsRV.addItemDecoration(new DividerItemDecoration(this, vlm.getOrientation()));

        Drills drills = new Drills();
        drills.setDrill_Id(1);
        drills.setDrill_Title("Carolina Shooting");
        drills.setDuration(5.00);
        drillsList.add(drills);

        Drills drills1 = new Drills();
        drills1.setDrill_Id(1);
        drills1.setDrill_Title("Beat the Chair");
        drills1.setDuration(5.00);
        drillsList.add(drills1);

        Drills drills2 = new Drills();
        drills2.setDrill_Id(1);
        drills2.setDrill_Title("3 Man Motion Drill");
        drills2.setDuration(5.00);
        drillsList.add(drills2);

        Drills drills3 = new Drills();
        drills3.setDrill_Id(1);
        drills3.setDrill_Title("Angled Shooting");
        drills3.setDuration(5.00);
        drillsList.add(drills3);

        Drills drills4 = new Drills();
        drills4.setDrill_Id(1);
        drills4.setDrill_Title("Banana Cut");
        drills4.setDuration(5.00);
        drillsList.add(drills4);

        Drills drills5 = new Drills();
        drills5.setDrill_Id(1);
        drills5.setDrill_Title("Both Side Shooting");
        drills5.setDuration(5.00);
        drillsList.add(drills5);

        adapter = new DrillsAdapter(this, drillsList);
        drillsRV.setAdapter(adapter);

    }
}
