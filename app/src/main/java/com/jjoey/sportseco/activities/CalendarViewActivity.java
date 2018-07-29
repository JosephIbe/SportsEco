package com.jjoey.sportseco.activities;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jjoey.sportseco.R;
import com.jjoey.sportseco.models.Event;
import com.jjoey.sportseco.models.Task;
import com.jjoey.sportseco.utils.CalendarConstants;
import com.jjoey.sportseco.utils.DateUtils;
import com.jjoey.sportseco.utils.OnSwipeTouchListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CalendarViewActivity extends AppCompatActivity {

    private static final String TAG = CalendarViewActivity.class.getSimpleName();

    private Toolbar toolbar;
    private ImageView backIV;
    private ScrollView scrollView;
    private LinearLayout mainLayout, sundayLayout, mondayLayout, tuesdayLayout, wednesdayLayout, thursdayLayout, fridayLayout, saturdayLayout;
    private TextView monday, tuesday, wednesday, thursday, friday, saturday, sunday, month, year;
    private String[] days = null;

    private Event eventAdded;
    private String descriptionAssociatedMeeting;

    private long calendarId;
    private ArrayList<Task> undoneTasks;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);

        inflater = LayoutInflater.from(this);
        initViews();
        initProviders();
        setSupportActionBar(toolbar);

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CalendarViewActivity.this, HomeActivity.class));
                finish();
            }
        });

    }

    private void initProviders() {

    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        backIV = findViewById(R.id.backIV);
        scrollView = findViewById(R.id.scrollView);
        mainLayout = findViewById(R.id.mainLayout);
        sundayLayout = findViewById(R.id.sunday);
        mondayLayout = findViewById(R.id.monday);
        tuesdayLayout = findViewById(R.id.tuesday);
        wednesdayLayout = findViewById(R.id.wednesday);
        thursdayLayout = findViewById(R.id.thursday);
        fridayLayout = findViewById(R.id.friday);
        saturdayLayout = findViewById(R.id.saturday);
        monday = findViewById(R.id.mondayText1);
        tuesday = findViewById(R.id.tuesdayText1);
        wednesday = findViewById(R.id.wednesdayText1);
        thursday = findViewById(R.id.thursdayText1);
        friday = findViewById(R.id.fridayText1);
        saturday = findViewById(R.id.saturdayText1);
        sunday = findViewById(R.id.sundayText1);
        month = findViewById(R.id.currentMonthTextView);
        year = findViewById(R.id.currentYearTextView);

        days = DateUtils.getDaysOfWeek();

        sunday.setText(days[0]);
        monday.setText(days[1]);
        tuesday.setText(days[2]);
        wednesday.setText(days[3]);
        thursday.setText(days[4]);
        friday.setText(days[5]);
        saturday.setText(days[6]);
        month.setText(DateUtils.compress(days[7]));
        year.setText(days[8]);

        configureToday();

        mainLayout.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeRight() {
                DateUtils.previousWeek();
                updateView();
            }

            public void onSwipeLeft() {
                DateUtils.nextWeek();
                updateView();
            }

        });

        scrollView.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeRight() {
                DateUtils.previousWeek();
                updateView();
            }

            public void onSwipeLeft() {
                DateUtils.nextWeek();
                updateView();
            }
        });

        sundayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarViewActivity.this, DayViewActivity.class);
                intent.putExtra(CalendarConstants.DATE_IN_LONG_FORMAT,
                        DateUtils.getDateInLongFormat(DateUtils.getDateInStringOfCurrentWeek(0)));
                startActivity(intent);
            }
        });

        mondayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarViewActivity.this, DayViewActivity.class);
                intent.putExtra(CalendarConstants.DATE_IN_LONG_FORMAT,
                        DateUtils.getDateInLongFormat(DateUtils.getDateInStringOfCurrentWeek(1)));
                startActivity(intent);
            }
        });

        tuesdayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarViewActivity.this, DayViewActivity.class);
                intent.putExtra(CalendarConstants.DATE_IN_LONG_FORMAT,
                        DateUtils.getDateInLongFormat(DateUtils.getDateInStringOfCurrentWeek(2)));
                startActivity(intent);
            }
        });

        wednesdayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarViewActivity.this, DayViewActivity.class);
                intent.putExtra(CalendarConstants.DATE_IN_LONG_FORMAT,
                        DateUtils.getDateInLongFormat(DateUtils.getDateInStringOfCurrentWeek(3)));
                startActivity(intent);
            }
        });

        thursdayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarViewActivity.this, DayViewActivity.class);
                intent.putExtra(CalendarConstants.DATE_IN_LONG_FORMAT,
                        DateUtils.getDateInLongFormat(DateUtils.getDateInStringOfCurrentWeek(4)));
                startActivity(intent);
            }
        });

        fridayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarViewActivity.this, DayViewActivity.class);
                intent.putExtra(CalendarConstants.DATE_IN_LONG_FORMAT,
                        DateUtils.getDateInLongFormat(DateUtils.getDateInStringOfCurrentWeek(5)));
                startActivity(intent);
            }
        });

        saturdayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarViewActivity.this, DayViewActivity.class);
                intent.putExtra(CalendarConstants.DATE_IN_LONG_FORMAT,
                        DateUtils.getDateInLongFormat(DateUtils.getDateInStringOfCurrentWeek(6)));
                startActivity(intent);
            }
        });

    }

    private void updateView() {
        days = DateUtils.getDaysOfWeek();

        sunday.setText(days[0]);
        monday.setText(days[1]);
        tuesday.setText(days[2]);
        wednesday.setText(days[3]);
        thursday.setText(days[4]);
        friday.setText(days[5]);
        saturday.setText(days[6]);
        month.setText(DateUtils.compress(days[7]));
        year.setText(days[8]);

        configureToday();
    }

    private void configureToday() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        String today = sdf.format(calendar.getTime());

        if (sunday.getText().equals(today.substring(0,2))){
            sundayLayout.setBackground(getResources().getDrawable(R.drawable.todays_bkg));
        }

        if (monday.getText().equals(today.substring(0,2))){
            mondayLayout.setBackground(getResources().getDrawable(R.drawable.todays_bkg));
        }

        if (tuesday.getText().equals(today.substring(0,2))){
            tuesdayLayout.setBackground(getResources().getDrawable(R.drawable.todays_bkg));
        }

        if (wednesday.getText().equals(today.substring(0,2))){
            wednesdayLayout.setBackground(getResources().getDrawable(R.drawable.todays_bkg));
        }

        if (thursday.getText().equals(today.substring(0,2))){
            thursdayLayout.setBackground(getResources().getDrawable(R.drawable.todays_bkg));
        }

        if (friday.getText().equals(today.substring(0,2))){
            fridayLayout.setBackground(getResources().getDrawable(R.drawable.todays_bkg));
        }

        if (saturday.getText().equals(today.substring(0,2))){
            saturdayLayout.setBackground(getResources().getDrawable(R.drawable.todays_bkg));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.search_day:
                DatePicker picker = (DatePicker) inflater.inflate(R.layout.picker_layout, null);
                picker.updateDate(DateUtils.getCurrentYear(), DateUtils.getCurrentMonth() - 1, DateUtils.getCurrentFirstDay());
                new AlertDialog.Builder(this)
                        .setTitle("Change Current Week")
                        .setMessage("Pick and Date and Press OK")
                        .setView(picker)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        })
                        .show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
