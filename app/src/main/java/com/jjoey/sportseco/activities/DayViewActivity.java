package com.jjoey.sportseco.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jjoey.sportseco.R;
import com.jjoey.sportseco.models.Event;
import com.jjoey.sportseco.models.SessionMeeting;
import com.jjoey.sportseco.utils.CalendarConstants;
import com.jjoey.sportseco.utils.MeetingButton;

import java.util.ArrayList;

import static com.jjoey.sportseco.utils.CalendarConstants.EVENTS_DAY_TO_SHOW;

public class DayViewActivity extends AppCompatActivity {

    private ImageView backIV;
    private TextView dayTitleTV;

    private Event event;
    private ArrayList<Event> eventsOfDay;
    private String dateInLongFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_view);

        initViews();
        dateInLongFormat = getIntent().getStringExtra(CalendarConstants.DATE_IN_LONG_FORMAT);
        dayTitleTV.setText(dateInLongFormat);

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DayViewActivity.this, CalendarViewActivity.class));
                finish();
            }
        });

    }

    private void initViews() {
        backIV = findViewById(R.id.backIV);
        dayTitleTV = findViewById(R.id.dayTitleTV);

        eventsOfDay = (ArrayList<Event>) getIntent().getSerializableExtra(EVENTS_DAY_TO_SHOW);
        showDayEvents();

    }

    private void showDayEvents() {
//        LinearLayout addTasksLayout = findViewById(R.id.tasksLinearLayout);
        RelativeLayout addMeetingsLayout = findViewById(R.id.meetings);

        int numTasks = 0;

        for (Event event : eventsOfDay){
            if (event.isMeeting()){
                SessionMeeting meeting = (SessionMeeting) event;
                int startMinute = meeting.getTotalStartMinute();
                int endMinute = meeting.getTotalEndMinute();
                int durationInPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, endMinute - startMinute, getResources().getDisplayMetrics());
                int marginInPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, startMinute, getResources().getDisplayMetrics());
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, durationInPx);
                params.setMargins(0, marginInPx, 0, 0);
                addMeetingsLayout.addView(new MeetingButton(this, meeting.getDescription()), params);
            }
        }

        if (numTasks == 0) findViewById(R.id.secondView).setVisibility(View.GONE);

    }
}
