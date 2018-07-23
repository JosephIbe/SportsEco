package com.jjoey.sportseco.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjoey.sportseco.R;
import com.jjoey.sportseco.adapters.ViewPagerAdapter;
import com.jjoey.sportseco.fragments.CreateFeedbackFragment;
import com.jjoey.sportseco.fragments.FeedbackHistoryFragment;

public class FeedbackActivity extends AppCompatActivity {

    private static final String TAG = FeedbackActivity.class.getSimpleName();

    private Toolbar toolbar;
    private ImageView backIV;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;

    private String coachId = null, batchId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        init();
        setSupportActionBar(toolbar);

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startHomeActivity();
            }
        });

        setUpTabs();

    }

    private void startHomeActivity() {
        startActivity(new Intent(FeedbackActivity.this, HomeActivity.class));
        finish();
    }

    private void setUpTabs() {
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);
        setUpViewPager(viewPager);
    }

    private void setUpViewPager(ViewPager viewPager) {

        Bundle bundle = new Bundle();
        bundle.putString("coach_id", coachId);
        bundle.putString("batch_id", batchId);

        CreateFeedbackFragment cf = new CreateFeedbackFragment();
        cf.setArguments(bundle);

        FeedbackHistoryFragment fhf = new FeedbackHistoryFragment();
        fhf.setArguments(bundle);

        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragTab(cf, "ADD NEW FEEDBACK");
        pagerAdapter.addFragTab(fhf, "FEEDBACK HISTORY");
        viewPager.setAdapter(pagerAdapter);
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        backIV = findViewById(R.id.backIV);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startHomeActivity();
    }
}
