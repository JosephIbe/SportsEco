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
import com.squareup.picasso.Picasso;

public class SessionsActivity extends AppCompatActivity {

    private static final String TAG = SessionsActivity.class.getSimpleName();

    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private TextView sessionNameTxt, sessionDescTxt;
    private ImageView backIV, sessionCoverImg;

    private com.github.clans.fab.FloatingActionButton attendanceFAB, sessionFAB;

    private Intent intent = null;
    private String name_session = null, progSessId = null, desc = null, cover_img = null, focus_points = null, video_link = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);

        init();
        setSupportActionBar(toolbar);

        setMeta();

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SessionsActivity.this, HomeActivity.class));
            }
        });

        attendanceFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(SessionsActivity.this, AttendanceActivity.class);
                intent.putExtra("prg_sess_id", progSessId);
                startActivity(intent);
            }
        });

        sessionFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(SessionsActivity.this, StartSessionActivity.class);
                intent.putExtra("prg_sess_id", progSessId);
                startActivity(intent);
            }
        });

    }

    private void setMeta() {
        progSessId = getIntent().getExtras().getString("prg_sess_id");
        name_session = getIntent().getExtras().getString("session_name");
        desc = getIntent().getExtras().getString("session_desc");
        cover_img = getIntent().getExtras().getString("session_cover_image");
        focus_points = getIntent().getExtras().getString("session_focus_pts");
        video_link = getIntent().getExtras().getString("session_video_link");

        sessionNameTxt.setText(name_session);
        sessionDescTxt.setText(desc);
        Picasso.with(this)
                .load(cover_img)
                .placeholder(R.drawable.basketball)
                .into(sessionCoverImg);

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
