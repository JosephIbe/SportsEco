package com.jjoey.sportseco.utils;

import android.app.DialogFragment;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jjoey.sportseco.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;


public class MonthCalendarView extends LinearLayout {

    private static final String TAG = MonthCalendarView.class.getSimpleName();

    private static final int DAYS_COUNT = 42;
    private static final String DATE_FORMAT = "MMM yyyy";
    private String dateFormat;
    private Calendar currentDate = Calendar.getInstance();

    private EventHandler eventHandler = null;

    private LinearLayout headerLayout;
    private ImageView nextIV, prevIV;
    private TextView dateTxt;
    private GridView gridView;

    int[] rainbow = new int[]{
            R.color.summer,
            R.color.fall,
            R.color.winter,
            R.color.spring
    };

    int[] monthSeason = new int[]{2, 2, 3, 3, 3, 0, 0, 0, 1, 1, 1, 2};

    public MonthCalendarView(Context context) {
        super(context);
    }

    public MonthCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl(context, attrs);
    }

    public MonthCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context, attrs);
    }

    private void initControl(final Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.month_calendar_control_layout, this);

        loadDateFormat(attrs);
        assignUiElements();
        assignClickHandlers();

        updateCalendar();

    }

    private void loadDateFormat(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.MonthCalendarView);
        try {
            dateFormat = ta.getString(R.styleable.MonthCalendarView_dateFormat);
            if (dateFormat == null)
                dateFormat = DATE_FORMAT;
        } finally {
            ta.recycle();
        }
    }

    private void assignUiElements() {
        headerLayout = findViewById(R.id.monthDaysLayout);
        nextIV = findViewById(R.id.nextIV);
        prevIV = findViewById(R.id.prevIV);
        dateTxt = findViewById(R.id.dateTxtDisplayTV);
        gridView = findViewById(R.id.calendarGrid);
    }

    private void assignClickHandlers() {
        nextIV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate.add(Calendar.MONTH, 1);
                updateCalendar();
            }
        });

        prevIV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate.add(Calendar.MONTH, -1);
                updateCalendar();
            }
        });

        final LinearLayout mainMonthLayout = findViewById(R.id.mainMonthView);
        RelativeLayout rootLayout = findViewById(R.id.rootLayout);

        rootLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mainMonthLayout.getVisibility() == VISIBLE){
                    mainMonthLayout.setVisibility(GONE);
                } else {
                    mainMonthLayout.setVisibility(VISIBLE);
                }
            }
        });

    }

    public void updateCalendar() {
        updateCalendar(null);
    }

    public void updateCalendar(HashSet<Date> events) {
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar) currentDate.clone();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        while (cells.size() < DAYS_COUNT) {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        gridView.setAdapter(new CalendarAdapter(getContext(), cells, events));

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        dateTxt.setText(sdf.format(currentDate.getTime()));

        int month = currentDate.get(Calendar.MONTH);
        int season = monthSeason[month];
        int color = rainbow[season];

        headerLayout.setBackgroundColor(getResources().getColor(color));

    }

    private class CalendarAdapter extends ArrayAdapter<Date> {

        private HashSet<Date> eventDays;
        private LayoutInflater inflater;

        public CalendarAdapter(Context context, ArrayList<Date> days, HashSet<Date> eventDays) {
            super(context, R.layout.month_calendar_day, days);
            this.eventDays = eventDays;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            Date date = getItem(position);
            int day = date.getDate();
            int month = date.getMonth();
            int year = date.getYear();

            Date today = new Date();

            if (view == null) {
                view = inflater.inflate(R.layout.month_calendar_day, parent, false);
                RelativeLayout dateHolder = view.findViewById(R.id.dateCellHolder);

                TextView monthDate = view.findViewById(R.id.monthDate);

                // if this day has an event, specify event image
//            view.setBackgroundResource(0);
                if (eventDays != null) {
                    for (Date eventDate : eventDays) {
                        if (eventDate.getDate() == day &&
                                eventDate.getMonth() == month && eventDate.getYear() == year) {
                            // mark this day for event
                            dateHolder.setBackgroundResource(R.drawable.circle_date_cell_event_filled);
//                        view.setBackgroundResource(R.drawable.reminder);
                            break;
                        }
                    }
                }

                monthDate.setTypeface(null, Typeface.NORMAL);
                if (month != today.getMonth() || year != today.getYear()) {
                    monthDate.setTextColor(getResources().getColor(R.color.greyed_out));
                } else if (day == today.getDate()) {
                    dateHolder.setBackgroundResource(R.drawable.circle_date_today);
                    monthDate.setTypeface(null, Typeface.BOLD);
                    monthDate.setTextColor(getResources().getColor(R.color.today));
                }

                monthDate.setText(String.valueOf(date.getDate()));

            }
            return view;
        }
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public interface EventHandler {
        void onDayLongPress(Date date);
    }

}
