package com.jjoey.sportseco.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.jjoey.sportseco.R;
import com.jjoey.sportseco.utils.MonthCalendarView;

import java.util.Date;
import java.util.HashSet;

public class ProgramsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView backIV;
    private MonthCalendarView monthCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programs);

        initViews();
        setSupportActionBar(toolbar);

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startHomeActivity();
            }
        });

        monthCalendarView = findViewById(R.id.monthCalendarView);

        HashSet<Date> events = new HashSet<>();
        events.add(new Date());

        monthCalendarView.updateCalendar(events);

    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        backIV = findViewById(R.id.backIV);
    }

    private void startHomeActivity(){
        startActivity(new Intent(ProgramsActivity.this, HomeActivity.class));
        overridePendingTransition(R.anim.anim_slide_left, R.anim.anim_slide_right);
        finish();
    }

}
