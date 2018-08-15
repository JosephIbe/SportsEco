package com.jjoey.sportseco;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jjoey.sportseco.activities.CalendarViewActivity;
import com.jjoey.sportseco.activities.LoginActivity;
import com.jjoey.sportseco.models.SessionMeeting;
import com.jjoey.sportseco.utils.CalendarConstants;
import com.jjoey.sportseco.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // TODO: 6/27/2018 check logged in status and send accordingly

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                overridePendingTransition(R.anim.anim_slide_right, R.anim.anim_slide_left);
                finish();
            }
        }, 3000);

    }

}
