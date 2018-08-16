package com.jjoey.sportseco.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.jjoey.sportseco.R;
import com.jjoey.sportseco.utils.MonthCalendarView;

import java.util.Date;
import java.util.HashSet;

public class ProgramsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private MonthCalendarView monthCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programs);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        monthCalendarView = findViewById(R.id.monthCalendarView);

        HashSet<Date> events = new HashSet<>();
        events.add(new Date());

        monthCalendarView.updateCalendar(events);

    }

    private void startHomeActivity(){
        startActivity(new Intent(ProgramsActivity.this, HomeActivity.class));
        overridePendingTransition(R.anim.anim_slide_left, R.anim.anim_slide_right);
        finish();
    }

}
