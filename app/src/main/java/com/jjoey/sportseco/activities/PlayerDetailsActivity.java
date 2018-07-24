package com.jjoey.sportseco.activities;

import android.content.Intent;
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
import com.jjoey.sportseco.models.AllPlayers;
import com.jjoey.sportseco.utils.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlayerDetailsActivity extends AppCompatActivity {

    private static final String TAG = PlayerDetailsActivity.class.getSimpleName();

    private String player_user_id = null;

    private Toolbar toolbar;
    private ImageView backIV;
    private CircleImageView civ_player;
    private TextView nameTV_player, emailTV, ageTV, stateTV;

    private AllPlayers allPlayers;
    private String firstName, lastName, username, height, weight,
            imageURL, mobile, dob, gender, pinCode, position, state, experience;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_details);

        init();
        setSupportActionBar(toolbar);
        allPlayers = new AllPlayers();

        player_user_id = getIntent().getStringExtra("player_user_id");
        if (player_user_id != null) {
            allPlayers = new Select()
                    .from(AllPlayers.class)
                    .where("user_id_player=?", player_user_id)
                    .executeSingle();
            if (allPlayers != null) {
                Log.d(TAG, "smtn:\t" + allPlayers.getAddress());
                getFromDB();
            } else {
                fetchPlayerProfile(player_user_id);
            }
        }

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

    }

    private void getFromDB() {

        firstName = allPlayers.firstName;
        lastName = allPlayers.lastName;
        username = allPlayers.username;
        height = allPlayers.heightPlayer;
        weight = allPlayers.weightPlayer;
        imageURL = allPlayers.imageURL;
        dob = allPlayers.dobPlayer;
        Log.d(TAG, "DOB from db:\t" + dob);
        gender = allPlayers.genderPlayer;
        mobile = allPlayers.mobilePlayer;
        position = allPlayers.positionPlayer;
        state = allPlayers.statePlayer;
        pinCode = allPlayers.pinCodePlayer;
        experience = allPlayers.bsktballExpPlayer;

        updateUI(firstName, lastName, imageURL, dob, experience, position, height, weight, username, state);

    }

    private void goBack() {
        startActivity(new Intent(PlayerDetailsActivity.this, AllPlayersActivity.class));
        finish();
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        backIV = findViewById(R.id.backIV);
        civ_player = findViewById(R.id.playerCIV);
        nameTV_player = findViewById(R.id.playerNameTV);
        emailTV = findViewById(R.id.emailTV);
        ageTV = findViewById(R.id.ageTV);
        stateTV = findViewById(R.id.stateTV);
    }

    private void fetchPlayerProfile(String player_uid) {
        JSONObject playerObject = new JSONObject();
        try {
            playerObject.put("player_id", player_uid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(Constants.GET_PLAYER_PROFILE)
                .addJSONObjectBody(playerObject)
                .setTag("Get player info")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.toString());

                                JSONObject data = jsonObject.getJSONObject("data");

                                firstName = data.getString("first_name");
                                lastName = data.getString("last_name");

                                username = data.getString("username");
                                height = data.getString("height");
                                weight = data.getString("weight");

//                                String address = data.getString("address");

                                imageURL = data.getString("image");
                                mobile = data.getString("mobile");
                                dob = data.getString("dob");
                                gender = data.getString("gender");
                                pinCode = data.getString("pin_code");
                                position = data.getString("position");
                                experience = data.getString("basketball_exp");
                                state = data.getString("state");

                                Log.d(TAG, "DOB from response:\t" + dob);

                                saveDetails(height, weight, mobile, gender, pinCode, position, dob, experience, state);
                                updateUI(firstName, lastName, imageURL, dob, experience, position, height, weight, username, state);

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

    private void saveDetails(String height, String weight, String mobile, String gender,
                             String pinCode, String position, String dob_db, String experience, String state) {

        allPlayers.setBsktballExpPlayer(experience);
        allPlayers.setPositionPlayer(position);
        allPlayers.setPinCodePlayer(pinCode);
        allPlayers.setGenderPlayer(gender);
        allPlayers.setMobilePlayer(mobile);
        allPlayers.setHeightPlayer(height);
        allPlayers.setWeightPlayer(weight);
        allPlayers.setDobPlayer(dob_db);
        Log.d(TAG, "DOB to db:\t" + dob_db);
        allPlayers.setStatePlayer(state);

        allPlayers.save();

    }

    private void updateUI(String firstName, String lastName, String imageURL, String dob,
                          String experience, String position, String height, String weight,
                          String username, String state) {

//        String year = dob.substring(0, 4);
//        int mm = Integer.parseInt(year);
//        Calendar calendar = Calendar.getInstance();
//        int age_year = calendar.get(Calendar.YEAR) - mm;
//        ageTV.setText(age_year + "\t years");

        nameTV_player.setText(firstName + " \t" + lastName);
        emailTV.setText(username);
        stateTV.setText(state);

        Picasso.with(this)
                .load(imageURL)
                .placeholder(R.drawable.person_avatar)
                .into(civ_player);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goBack();
    }

}
