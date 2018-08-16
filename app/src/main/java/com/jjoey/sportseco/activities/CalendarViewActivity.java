package com.jjoey.sportseco.activities;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.jjoey.sportseco.R;
import com.jjoey.sportseco.models.Batch;
import com.jjoey.sportseco.models.Coach;
import com.jjoey.sportseco.models.Event;
import com.jjoey.sportseco.models.ProgramDetails;
import com.jjoey.sportseco.models.SessionMeeting;
import com.jjoey.sportseco.models.Sessions;
import com.jjoey.sportseco.models.Task;
import com.jjoey.sportseco.utils.CalendarConstants;
import com.jjoey.sportseco.utils.Constants;
import com.jjoey.sportseco.utils.DateUtils;
import com.jjoey.sportseco.utils.MeetingButton;
import com.jjoey.sportseco.utils.OnSwipeTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.jjoey.sportseco.utils.CalendarConstants.CALENDAR_ID_KEY;
import static com.jjoey.sportseco.utils.CalendarConstants.CALENDAR_NAME;
import static com.jjoey.sportseco.utils.CalendarConstants.CALENDAR_TIME_ZONE;
import static com.jjoey.sportseco.utils.CalendarConstants.DONE_TASK;
import static com.jjoey.sportseco.utils.CalendarConstants.EVENTS_DAY_TO_SHOW;
import static com.jjoey.sportseco.utils.CalendarConstants.FRIDAY;
import static com.jjoey.sportseco.utils.CalendarConstants.MEETING;
import static com.jjoey.sportseco.utils.CalendarConstants.MONDAY;
import static com.jjoey.sportseco.utils.CalendarConstants.MY_ACCOUNT_NAME;
import static com.jjoey.sportseco.utils.CalendarConstants.OWNER_ACCOUNT;
import static com.jjoey.sportseco.utils.CalendarConstants.SATURDAY;
import static com.jjoey.sportseco.utils.CalendarConstants.SETTINGS_VARIABLE_2_KEY;
import static com.jjoey.sportseco.utils.CalendarConstants.SETTINGS_VARIABLE_KEY;
import static com.jjoey.sportseco.utils.CalendarConstants.SUNDAY;
import static com.jjoey.sportseco.utils.CalendarConstants.TASK;
import static com.jjoey.sportseco.utils.CalendarConstants.THURSDAY;
import static com.jjoey.sportseco.utils.CalendarConstants.TOAST_DELETE_MEETING;
import static com.jjoey.sportseco.utils.CalendarConstants.TOAST_ERROR_FIND_ID;
import static com.jjoey.sportseco.utils.CalendarConstants.TOAST_ERROR_FORMAT_DATE;
import static com.jjoey.sportseco.utils.CalendarConstants.TUESDAY;
import static com.jjoey.sportseco.utils.CalendarConstants.WEDNESDAY;

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

    private static boolean hasGranted = false;
    private static final int PERMS_CODE = 1002;

    private Sessions sessions;
    private String programId, programUserMapId;

    private String sessionName, programName, dateStart, dateEnd,
            hour_start, hour_end, min_start, min_end, coachId, batch_id;
    private float downX;
    private float upX;

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
//                overridePendingTransition(R.anim.anim_slide_left, R.anim.anim_slide_right);
                finish();
            }
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            askPerms();
        } else {
            hasGranted = true;
        }

        fetchSessionEvents();

    }

    private void fetchSessionEvents() {

        Coach coach = new Select()
                .from(Coach.class)
                .orderBy("id ASC")
                .executeSingle();
        coachId = coach.coachId;

        Batch batch = new Select()
                .from(Batch.class)
                .orderBy("id ASC")
                .executeSingle();
        batch_id = batch.batchId;

        List<ProgramDetails> details = new Select()
                .from(ProgramDetails.class)
                .where("coach_id=?", coachId)
                .execute();

        // TODO: 7/11/2018 One Major flaw if coach has multiple programs, this might not work as expected
        for (int i = 0; i < details.size(); i++) {
            programId = details.get(i).getProgId();
            programUserMapId = details.get(i).getProgUserMapId();
            fetchSessions(coachId, batch_id, programId, programUserMapId);
        }

    }

    private void fetchSessions(String coachId, String batchId, String programId, String programUserMapId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("coach_id", coachId);
            jsonObject.put("batch_id", batchId);
            jsonObject.put("prg_id", programId);
            jsonObject.put("prg_user_map_id", programUserMapId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(Constants.SESSION_LIST)
                .addJSONObjectBody(jsonObject)
                .setTag("Get Sessions for Program")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try {
                                JSONObject object = new JSONObject(response.toString());
                                JSONArray array = object.getJSONArray("Session_details");
                                for (int m = 0; m < array.length(); m++) {
                                    JSONObject obj = array.getJSONObject(m);

                                    sessions = new Sessions();

                                    String programId = (obj.getString("prg_id"));
                                    sessionName = obj.getString("session_name");
                                    programName = obj.getString("prg_name");
                                    String programSessionId = (obj.getString("prg_session_id"));
                                    String videoLink = (obj.getString("prg_session_video_link"));
                                    String coverImage = obj.getString("prg_session_image");
                                    String desc = obj.getString("prg_session_description");
                                    String focusPoints = obj.getString("prg_session_focus_points");
                                    dateStart = (obj.getString("start_date"));
                                    dateEnd = (obj.getString("end_date"));
                                    hour_start = obj.getString("start_hr");
                                    hour_end = obj.getString("end_hr");
                                    min_start = obj.getString("start_min");
                                    min_end = obj.getString("end_min");
                                    String duration = obj.getString("duration");

                                    try {
                                        addEvents(sessionName, programName, dateStart, dateEnd,
                                                hour_start, hour_end, min_start, min_end);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }

    private void addEvents(String sessionName, String programName, String dateStart, String dateEnd, String hour_start, String hour_end, String min_start, String min_end) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = sdf.parse(dateStart);
        Date endDate;
//        Date endDate = sdf.parse(dateEnd);

//        Log.d(TAG, "Hour Start in method:\t" + hour_start);

        int hourStart_int = DateUtils.getHour(hour_start);
        int hourEnd_int = DateUtils.getHour(hour_end);

        int minStart = DateUtils.getMinute(min_start);
        int minEnd = DateUtils.getMinute(min_end);

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(startDate);
        calendar.set(Calendar.HOUR_OF_DAY, hourStart_int);
        calendar.set(Calendar.MINUTE, minStart);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        startDate = calendar.getTime();

        calendar.set(Calendar.HOUR_OF_DAY, hourEnd_int);
        calendar.set(Calendar.MINUTE, minEnd);
        endDate = calendar.getTime();
        eventAdded = new SessionMeeting(startDate, endDate, "A Session");

        showEvents();

    }

    private void showEvents() {
        if (eventAdded.isMeeting()) {
            SessionMeeting meeting = (SessionMeeting) eventAdded;
            if (!existsMeetingWithSameSchedule(meeting)) {
                addMeeting(meeting.getDescription(), meeting.getDay(), meeting.getMonth(), meeting.getYear(),
                        meeting.getStartHour(), meeting.getStartMinute(), meeting.getEndHour(), meeting.getEndMinute());
            } else {
//                Log.d(TAG, "A Meeting With Same Dates Already Exists");
                return;
            }
        }
    }

    private void askPerms() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR},
                PERMS_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMS_CODE:
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        hasGranted = true;
                    } else {
                        hasGranted = false;
                        Snackbar.make(findViewById(android.R.id.content), "Grant Calendar Permissions to Proceed",
                                Snackbar.LENGTH_LONG).show();
                        askPerms();
                    }
                }
                break;
        }
    }

    private void initProviders() {
        SharedPreferences settings = getSharedPreferences(SETTINGS_VARIABLE_KEY, 0);
        boolean silent = settings.getBoolean(SETTINGS_VARIABLE_2_KEY, false);
        if (!silent) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(SETTINGS_VARIABLE_2_KEY, true);
            editor.commit();
            createNewCalendar();
            calendarId = getCalendarId();
            if (calendarId == -1)
                Toast.makeText(this, TOAST_ERROR_FIND_ID, Toast.LENGTH_LONG).show();
            editor.putLong(CALENDAR_ID_KEY, calendarId);
            editor.commit();
        } else {
            calendarId = settings.getLong(CALENDAR_ID_KEY, -1);
            updateView();
        }
    }

    private void createNewCalendar() {
        // Object that represents the values we want to add
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Calendars.ACCOUNT_NAME, MY_ACCOUNT_NAME);
        values.put(CalendarContract.Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL);
        values.put(CalendarContract.Calendars.NAME, CALENDAR_NAME);
        values.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, CALENDAR_NAME);
        values.put(CalendarContract.Calendars.CALENDAR_COLOR, 0xFFFF0000);
        values.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
        values.put(CalendarContract.Calendars.OWNER_ACCOUNT, OWNER_ACCOUNT);
        values.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, CALENDAR_TIME_ZONE);
        values.put(CalendarContract.Calendars.SYNC_EVENTS, 1);

        // Additional query parameters
        Uri.Builder builder = CalendarContract.Calendars.CONTENT_URI.buildUpon();
        builder.appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, MY_ACCOUNT_NAME);
        builder.appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL);
        builder.appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true");

        Uri uri = getContentResolver().insert(builder.build(), values);

    }

    private long getCalendarId() {
        String[] projection = new String[]{CalendarContract.Calendars._ID};
        String selection = CalendarContract.Calendars.ACCOUNT_NAME + " = ? ";
        String[] selArgs = new String[]{MY_ACCOUNT_NAME};
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            askPerms();
        }
        Cursor cursor =
                getContentResolver().
                        query(
                                CalendarContract.Calendars.CONTENT_URI,
                                projection,
                                selection,
                                selArgs,
                                null);
        if (cursor.moveToFirst()) return cursor.getLong(0);
        return -1;
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

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                Log.d(TAG, "Scrollview onTouch");
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Log.d(TAG, "Scrollview onTouchDown");
                        downX = event.getX();
                    }
                    case MotionEvent.ACTION_UP: {
                        Log.d(TAG, "Scrollview onTouchUp");
                        upX = event.getX();

                        float deltaX = downX - upX;

                        if (Math.abs(deltaX) > 0) {
                            if (deltaX >= 0) {
                                DateUtils.previousWeek();
                                updateView();
                                return true;
                            } else {
                                DateUtils.nextWeek();
                                updateView();
                                return true;
                            }
                        }
                    }
                }

                return false;
            }
        });

//        scrollView.setOnTouchListener(new OnSwipeTouchListener(this) {
//            public void onSwipeRight() {
//                DateUtils.previousWeek();
//                updateView();
//            }
//
//            public void onSwipeLeft() {
//                DateUtils.nextWeek();
//                updateView();
//            }
//        });

        sundayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Event> dayEvents = findByDay(DateUtils.getDateInStringOfCurrentWeek(0));
                Intent intent = new Intent(CalendarViewActivity.this, DayViewActivity.class);
                intent.putExtra(CalendarConstants.DATE_IN_LONG_FORMAT,
                        DateUtils.getDateInLongFormat(DateUtils.getDateInStringOfCurrentWeek(0)));
                intent.putExtra(EVENTS_DAY_TO_SHOW, dayEvents);
                startActivityForResult(intent, 0);
            }
        });

        mondayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Event> dayEvents = findByDay(DateUtils.getDateInStringOfCurrentWeek(1));
                Intent intent = new Intent(CalendarViewActivity.this, DayViewActivity.class);
                intent.putExtra(CalendarConstants.DATE_IN_LONG_FORMAT,
                        DateUtils.getDateInLongFormat(DateUtils.getDateInStringOfCurrentWeek(1)));
                intent.putExtra(EVENTS_DAY_TO_SHOW, dayEvents);
                startActivityForResult(intent, 0);
            }
        });

        tuesdayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Event> dayEvents = findByDay(DateUtils.getDateInStringOfCurrentWeek(2));
                Intent intent = new Intent(CalendarViewActivity.this, DayViewActivity.class);
                intent.putExtra(CalendarConstants.DATE_IN_LONG_FORMAT,
                        DateUtils.getDateInLongFormat(DateUtils.getDateInStringOfCurrentWeek(2)));
                intent.putExtra(EVENTS_DAY_TO_SHOW, dayEvents);
                startActivityForResult(intent, 0);
            }
        });

        wednesdayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Event> dayEvents = findByDay(DateUtils.getDateInStringOfCurrentWeek(3));
                Intent intent = new Intent(CalendarViewActivity.this, DayViewActivity.class);
                intent.putExtra(CalendarConstants.DATE_IN_LONG_FORMAT,
                        DateUtils.getDateInLongFormat(DateUtils.getDateInStringOfCurrentWeek(3)));
                intent.putExtra(EVENTS_DAY_TO_SHOW, dayEvents);
                startActivityForResult(intent, 0);
            }
        });

        thursdayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Event> dayEvents = findByDay(DateUtils.getDateInStringOfCurrentWeek(4));
                Intent intent = new Intent(CalendarViewActivity.this, DayViewActivity.class);
                intent.putExtra(CalendarConstants.DATE_IN_LONG_FORMAT,
                        DateUtils.getDateInLongFormat(DateUtils.getDateInStringOfCurrentWeek(4)));
                intent.putExtra(EVENTS_DAY_TO_SHOW, dayEvents);
                startActivityForResult(intent, 0);
            }
        });

        fridayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Event> dayEvents = findByDay(DateUtils.getDateInStringOfCurrentWeek(5));
                Intent intent = new Intent(CalendarViewActivity.this, DayViewActivity.class);
                intent.putExtra(CalendarConstants.DATE_IN_LONG_FORMAT,
                        DateUtils.getDateInLongFormat(DateUtils.getDateInStringOfCurrentWeek(5)));
                intent.putExtra(EVENTS_DAY_TO_SHOW, dayEvents);
                startActivityForResult(intent, 0);
            }
        });

        saturdayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Event> dayEvents = findByDay(DateUtils.getDateInStringOfCurrentWeek(6));
                Intent intent = new Intent(CalendarViewActivity.this, DayViewActivity.class);
                intent.putExtra(CalendarConstants.DATE_IN_LONG_FORMAT,
                        DateUtils.getDateInLongFormat(DateUtils.getDateInStringOfCurrentWeek(6)));
                intent.putExtra(EVENTS_DAY_TO_SHOW, dayEvents);
                startActivityForResult(intent, 0);
            }
        });

    }

    private ArrayList<Event> findByDay(String dateInStringOfCurrentWeek) {
        ArrayList<Event> allEvents = list();
        ArrayList<Event> result = new ArrayList<>();
        for (Event event : allEvents) {
            if (event.isMeeting()) {
                SessionMeeting meeting = (SessionMeeting) event;
                if (DateUtils.dateToString(meeting.getDate()).equals(dateInStringOfCurrentWeek)) {
                    result.add(meeting);
                }
            }
        }
        return result;
    }

    private ArrayList<Event> findByWeek(String firstDayString, String lastDayString) {
        ArrayList<Event> result = new ArrayList<>();
        ArrayList<Event> events = list();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date firstDay = dateFormat.parse(firstDayString);
            Date lastDay = dateFormat.parse(lastDayString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(lastDay);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            lastDay = calendar.getTime();
            for (Event e : events) {
                if (e.isMeeting()) {
                    SessionMeeting m = (SessionMeeting) e;
                    if (DateUtils.compareTo(m.getDate(), firstDay) == 1 && DateUtils.compareTo(m.getDate(), lastDay) == -1) {
                        result.add(m);
                    }
                }
            }
        } catch (ParseException pe) {
            Toast.makeText(this, TOAST_ERROR_FORMAT_DATE, Toast.LENGTH_LONG).show();
        }
        return result;

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

        String startDay = DateUtils.getFirstDayOfTheCurrentWeek();
        String lastDay = DateUtils.getLastDayOfTheCurrentWeek();

        ArrayList<Event> weekEvents = findByWeek(startDay, lastDay);
        for (Event event : weekEvents) {
            if (event.isMeeting()) {
                SessionMeeting meeting = (SessionMeeting) event;
                String day = DateUtils.getDayOfWeekInString(meeting.getDate());
                String text = meeting.getDescription();
                int startMinute = meeting.getTotalStartMinute();
                int endMinute = meeting.getTotalEndMinute();
                createMeetingButton(day, text, startMinute, endMinute, meeting.getId());
            }
        }

    }

    // TODO: 8/15/2018 Make Meeting Drawable back-compatible
    private void createMeetingButton(String day, String text, int startTime, int endTime, final long id) {
        RelativeLayout dayLayout;
        if (day.equals(MONDAY)) dayLayout = (RelativeLayout) findViewById(R.id.mondayEvents);
        else if (day.equals(TUESDAY)) dayLayout = (RelativeLayout) findViewById(R.id.tuesdayEvents);
        else if (day.equals(WEDNESDAY))
            dayLayout = (RelativeLayout) findViewById(R.id.wednesdayEvents);
        else if (day.equals(THURSDAY))
            dayLayout = (RelativeLayout) findViewById(R.id.thursdayEvents);
        else if (day.equals(FRIDAY)) dayLayout = (RelativeLayout) findViewById(R.id.fridayEvents);
        else if (day.equals(SATURDAY))
            dayLayout = (RelativeLayout) findViewById(R.id.saturdayEvents);
        else if (day.equals(SUNDAY)) dayLayout = (RelativeLayout) findViewById(R.id.sundayEvents);
        else return;

        int duration = endTime - startTime;
        int durationInPx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, duration, getResources().getDisplayMetrics());
        int marginInPx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, startTime, getResources().getDisplayMetrics());

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, durationInPx);
        params.setMargins(0, marginInPx, 0, 0);

        MeetingButton mButton = new MeetingButton(this, text);
        dayLayout.addView(mButton, params);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new android.app.AlertDialog.Builder(CalendarViewActivity.this)
                        .setTitle("Delete a Meeting")
                        .setMessage("Are you sure you want to delete this meeting?")
                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                deleteEventById(id);
                                updateView();
                                Toast.makeText(CalendarViewActivity.this, TOAST_DELETE_MEETING, Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(getResources().getDrawable(R.drawable.clear))
                        .show();
            }
        });
    }

    private void deleteEventById(long id) {
        String[] selArgs =
                new String[]{Long.toString(id)};
        String selection = CalendarContract.Events._ID + " = ? ";
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            askPerms();
        }
        int deleted =
                getContentResolver().
                        delete(
                                CalendarContract.Events.CONTENT_URI,
                                selection,
                                selArgs);
    }

    private void configureToday() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        String today = sdf.format(calendar.getTime());

        if (sunday.getText().equals(today.substring(0, 2))) {
            sundayLayout.setBackground(getResources().getDrawable(R.drawable.todays_bkg));
        }

        if (monday.getText().equals(today.substring(0, 2))) {
            mondayLayout.setBackground(getResources().getDrawable(R.drawable.todays_bkg));
        }

        if (tuesday.getText().equals(today.substring(0, 2))) {
            tuesdayLayout.setBackground(getResources().getDrawable(R.drawable.todays_bkg));
        }

        if (wednesday.getText().equals(today.substring(0, 2))) {
            wednesdayLayout.setBackground(getResources().getDrawable(R.drawable.todays_bkg));
        }

        if (thursday.getText().equals(today.substring(0, 2))) {
            thursdayLayout.setBackground(getResources().getDrawable(R.drawable.todays_bkg));
        }

        if (friday.getText().equals(today.substring(0, 2))) {
            fridayLayout.setBackground(getResources().getDrawable(R.drawable.todays_bkg));
        }

        if (saturday.getText().equals(today.substring(0, 2))) {
            saturdayLayout.setBackground(getResources().getDrawable(R.drawable.todays_bkg));
        }

    }

    private long addMeeting(String description, int day, int month, int year, int startHour,
                            int startMinute, int endHour,
                            int endMinute) {
        // SET start date
        long startTime = DateUtils.createDate(day, month, year, startHour, startMinute);
        // SET end date
        long endTime = DateUtils.createDate(day, month, year, endHour, endMinute);

        // Initialize the object that represents the values we want to add
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, startTime);
        values.put(CalendarContract.Events.DTEND, endTime);
        values.put(CalendarContract.Events.RRULE, "FREQ=DAILY;COUNT=20;BYDAY=MO,TU,WE,TH,FR;WKST=MO");
        values.put(CalendarContract.Events.TITLE, description);
        values.put(CalendarContract.Events.CALENDAR_ID, calendarId);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, CALENDAR_TIME_ZONE);
        values.put(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE);
        values.put(CalendarContract.Events.SELF_ATTENDEE_STATUS, CalendarContract.Events.STATUS_CONFIRMED);
        values.put(CalendarContract.Events.ALL_DAY, 0);
        values.put(CalendarContract.Events.ORGANIZER, OWNER_ACCOUNT);
        values.put(CalendarContract.Events.GUESTS_CAN_INVITE_OTHERS, MEETING);
        values.put(CalendarContract.Events.GUESTS_CAN_MODIFY, 1);
        values.put(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            askPerms();
        }
        Uri uri = getContentResolver().insert(CalendarContract.Events.CONTENT_URI, values);
        return new Long(uri.getLastPathSegment());
    }

    private boolean existsMeetingWithSameSchedule(SessionMeeting meeting) {
        ArrayList<Event> all = list();
        for (Event e : all) {
            if (e.isMeeting()) {
                SessionMeeting m = (SessionMeeting) e;
                if (DateUtils.isSameDay(m.getDate(), meeting.getDate()) &&
                        ((DateUtils.compareTo(m.getHourEnd(), meeting.getHourStart()) >= 0 && DateUtils.compareTo(m.getHourEnd(), meeting.getHourEnd()) <= 0) ||
                                (DateUtils.compareTo(m.getHourStart(), meeting.getHourEnd()) <= 0 && DateUtils.compareTo(m.getHourStart(), meeting.getHourStart()) >= 0)))
                    return true;
            }
        }
        return false;
    }

    private ArrayList<Event> list() {
        ArrayList<Event> events = new ArrayList<>();
        String[] projection = new String[]{
                CalendarContract.Events.GUESTS_CAN_INVITE_OTHERS,
                CalendarContract.Events._ID,
                CalendarContract.Events.TITLE,
                CalendarContract.Events.DTSTART,
                CalendarContract.Events.DTEND,
                CalendarContract.Events.GUESTS_CAN_MODIFY,
                CalendarContract.Events.DESCRIPTION
        };
        String selection = CalendarContract.Events.CALENDAR_ID + " = ? ";
        String[] selArgs = new String[]{Long.toString(calendarId)};
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            askPerms();
        }
        Cursor cursor =
                getContentResolver().
                        query(
                                CalendarContract.Events.CONTENT_URI,
                                projection,
                                selection,
                                selArgs,
                                null);
        while (cursor.moveToNext()) {
            int type = cursor.getInt(0);
            if (type == MEETING)
                events.add(new SessionMeeting(cursor.getLong(1), new Date(cursor.getLong(3)), new Date(cursor.getLong(4)), cursor.getString(2)));
            else if (type == TASK) {
                boolean done = (cursor.getInt(5) == DONE_TASK);
                events.add(new Task(cursor.getLong(1), cursor.getString(2), new Date(cursor.getLong(3)), new Date(cursor.getLong(4)), done, cursor.getString(6)));
            }
        }
        cursor.close();
        return events;
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
                final DatePicker datePicker = (DatePicker) inflater.inflate(getResources().getLayout(R.layout.picker_layout), null);
                datePicker.updateDate(DateUtils.getCurrentYear(), DateUtils.getCurrentMonth() - 1, DateUtils.getCurrentFirstDay());
                new android.app.AlertDialog.Builder(CalendarViewActivity.this)
                        .setTitle("Change week")
                        .setMessage("Select the day you want to go")
                        .setView(datePicker)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d(TAG, "Date Selected:\t" + datePicker.getCalendarView().getDate());
                                DateUtils.setDaysOfWeek(datePicker.getCalendarView().getDate());
                                updateView();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .show();
        }
        return super.onOptionsItemSelected(item);
    }

}
