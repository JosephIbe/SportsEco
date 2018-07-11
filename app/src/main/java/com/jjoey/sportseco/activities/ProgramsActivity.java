package com.jjoey.sportseco.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.jjoey.sportseco.R;

import java.util.Calendar;

public class ProgramsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    //    private CalendarView calendarView;
    private CompactCalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programs);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        calendarView = (CompactCalendarView) findViewById(R.id.calView);
        calendarView.setShouldDrawDaysHeader(true);
        calendarView.setFirstDayOfWeek(Calendar.SUNDAY);

        Event event = new Event(Color.YELLOW, 1531188500);
        calendarView.addEvent(event);

    }
}
