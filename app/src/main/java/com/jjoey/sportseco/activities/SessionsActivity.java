package com.jjoey.sportseco.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjoey.sportseco.R;

public class SessionsActivity extends AppCompatActivity {

    private static final String TAG = SessionsActivity.class.getSimpleName();

    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private TextView sessionNameTxt, sessionDescTxt;
    private ImageView backIV, sessionCoverImg;

    private com.github.clans.fab.FloatingActionButton attendanceFAB, sessionFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);

        init();
        setSupportActionBar(toolbar);

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SessionsActivity.this, HomeActivity.class));
            }
        });

        attendanceFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SessionsActivity.this, AttendanceActivity.class));
            }
        });

        sessionFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SessionsActivity.this, StartSessionActivity.class));
            }
        });

    }

    private void init() {
        appBarLayout = findViewById(R.id.appBar);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        toolbar = findViewById(R.id.toolbar);
        sessionCoverImg = findViewById(R.id.sessionCoverImg);
        backIV = findViewById(R.id.backIV);
        sessionNameTxt = findViewById(R.id.sessionNameTxt);
        sessionDescTxt = findViewById(R.id.sessionDescTxt);
        attendanceFAB = findViewById(R.id.attendanceFAB);
        sessionFAB = findViewById(R.id.sessionFAB);

        collapsingToolbarLayout.setTitle("");

    }

}
