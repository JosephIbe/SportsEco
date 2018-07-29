package com.jjoey.sportseco.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjoey.sportseco.R;
import com.jjoey.sportseco.models.Event;
import com.jjoey.sportseco.utils.CalendarConstants;

public class DayViewActivity extends AppCompatActivity {

    private ImageView backIV;
    private TextView dayTitleTV;

    private Event event;
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
    }
}
