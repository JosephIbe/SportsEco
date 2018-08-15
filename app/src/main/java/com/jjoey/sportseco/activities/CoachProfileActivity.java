package com.jjoey.sportseco.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.jjoey.sportseco.R;
import com.jjoey.sportseco.adapters.CoachProfilePagerAdapter;
import com.jjoey.sportseco.fragments.DummyFragment;
import com.jjoey.sportseco.fragments.SessionHistoryFrag;
import com.jjoey.sportseco.models.Coach;
import com.jjoey.sportseco.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class CoachProfileActivity extends AppCompatActivity {

    private static final String TAG = CoachProfileActivity.class.getSimpleName();

    private String coachId = null, position = null, first_name = null, last_name = null,
            mid_name = null, nick_name = null, dob = null, username = null, gender = null,
            mobile_num = null, address = null, pin_code = null, height = null, weight = null,
            exp_bskt_ball = null, exp_coaching = null, image = null, state = null;

    private Toolbar toolbar;
    private ImageView backIV;
    private CircleImageView profileCIV;
    private TextView nameTV, experienceTV, coachingExpTV, stateTV, positionTV;

    private TabLayout tabs_coach;
    private ViewPager pagerCoach;
    private CoachProfilePagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_profile);

        init();
        setSupportActionBar(toolbar);
        getQueryInfo();
        Log.d(TAG, "Coach id:\t" + coachId);

        getProfile(coachId);

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startHomeActivity();
            }
        });

    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        backIV = findViewById(R.id.backIV);
        profileCIV = findViewById(R.id.coachProfileCIV);
//        nameTV = findViewById(R.id.nameTV);
//        stateTV = findViewById(R.id.stateTV);
//        positionTV = findViewById(R.id.positionTV);
        tabs_coach = findViewById(R.id.tabs_coach);
        pagerCoach = findViewById(R.id.pagerCoach);

        setUpTabs();

    }

    private void setUpTabs() {
        tabs_coach.setTabGravity(TabLayout.GRAVITY_FILL);
        tabs_coach.setupWithViewPager(pagerCoach);
        setUpViewPager(pagerCoach);
    }

    private void setUpViewPager(ViewPager viewPager) {
        pagerAdapter = new CoachProfilePagerAdapter(getSupportFragmentManager());
        pagerAdapter.addTabs(new SessionHistoryFrag(), "SESSIONS HISTORY");
        pagerAdapter.addTabs(new DummyFragment(), "DUMMY");
        pagerAdapter.addTabs(new DummyFragment(), "DUMMY");
        viewPager.setAdapter(pagerAdapter);
    }

    private void getProfile(String coachId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("coach_id", coachId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(Constants.PROFILE_COACH)
                .addJSONObjectBody(jsonObject)
                .setTag("Get Coach Profile Info")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            Log.d(TAG, "Profile Response:\t" + response.toString());

                            try {
                                JSONObject object = new JSONObject(response.toString());

                                JSONObject data = object.getJSONObject("data");

                                first_name = data.getString("first_name");
                                last_name = data.getString("last_name");
                                mid_name = data.getString("middle_name");
                                nick_name = data.getString("nick_name");
                                image = data.getString("image");
                                state = data.getString("state");
                                position = data.getString("position");
                                exp_coaching = data.getString("coaching_exp");
                                exp_bskt_ball = data.getString("basketball_exp");
                                pin_code = data.getString("pin_code");
                                gender = data.getString("gender");
                                username = data.getString("username");
                                mobile_num = data.getString("mobile");
                                dob = data.getString("dob");
                                height = data.getString("height");
                                weight = data.getString("weight");
                                address = data.getString("address");

//                                nameTV.setText(first_name + " \t" + last_name);
//                                stateTV.setText(state);
//                                positionTV.setText(position);

                                // TODO: 7/23/2018 save image id, experience, position, pincode,
                                // TODO: home_address, gender, height, weight, dob


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

    private void getQueryInfo() {
        Coach coach = new Select()
                .from(Coach.class)
                .orderBy("id ASC")
                .executeSingle();
        coachId = coach.coachId;
    }

    private void startHomeActivity() {
        startActivity(new Intent(CoachProfileActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startHomeActivity();
    }
}
