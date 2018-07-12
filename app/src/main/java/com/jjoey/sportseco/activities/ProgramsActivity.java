package com.jjoey.sportseco.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.jjoey.sportseco.R;

public class ProgramsActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programs);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }
}
