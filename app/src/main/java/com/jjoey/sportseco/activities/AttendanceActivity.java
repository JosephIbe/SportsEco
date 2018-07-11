package com.jjoey.sportseco.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjoey.sportseco.R;
import com.jjoey.sportseco.adapters.AttendanceAdapter;
import com.jjoey.sportseco.models.Attendance;
import com.jjoey.sportseco.models.Player;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AttendanceActivity extends AppCompatActivity {

    private static final String TAG = AttendanceActivity.class.getSimpleName();

    private Toolbar toolbar;
    private TextView sessionNameTxt;
    private ImageView backIV, checkAttendance;

    private RecyclerView attendanceRV;
    private List<Attendance> arrayList = new ArrayList<>();
    private AttendanceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        init();
        setSupportActionBar(toolbar);

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AttendanceActivity.this, SessionsActivity.class));
            }
        });

        checkAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AttendanceActivity.this, SessionsActivity.class));
            }
        });

        setUpAttendanceList();

    }

    private void setUpAttendanceList() {
        attendanceRV.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        attendanceRV.setLayoutManager(llm);
        attendanceRV.addItemDecoration(new DividerItemDecoration(this, llm.getOrientation()));

        Attendance attendance = new Attendance();
        attendance.setAttendanceDate(new Date());
        Player player = new Player();
        player.setPlayerName("Michael Jordan");
        player.setPresent(true);
        attendance.setPlayer(player);
        arrayList.add(attendance);

        Attendance attendance1 = new Attendance();
        attendance1.setAttendanceDate(new Date());
        Player player1 = new Player();
        player1.setPlayerName("Lebron James");
        player1.setPresent(true);
        attendance1.setPlayer(player1);
        arrayList.add(attendance1);

        Attendance attendance2 = new Attendance();
        attendance2.setAttendanceDate(new Date());
        Player player2 = new Player();
        player2.setPlayerName("Michael Phelps");
        player2.setPresent(false);
        attendance2.setPlayer(player2);
        arrayList.add(attendance2);

        Attendance attendance3 = new Attendance();
        attendance3.setAttendanceDate(new Date());
        Player player3 = new Player();
        player3.setPlayerName("Steph Curry");
        player3.setPresent(true);
        attendance3.setPlayer(player3);
        arrayList.add(attendance3);

        adapter = new AttendanceAdapter(this, arrayList);
        attendanceRV.setAdapter(adapter);

    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        sessionNameTxt = findViewById(R.id.sessionNameTxt);
        backIV = findViewById(R.id.backIV);
        checkAttendance = findViewById(R.id.checkAttendance);
        attendanceRV = findViewById(R.id.attendanceRV);
    }

}
