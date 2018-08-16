package com.jjoey.sportseco.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.activeandroid.query.Select;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.jjoey.sportseco.R;
import com.jjoey.sportseco.adapters.AllPlayersAdapter;
import com.jjoey.sportseco.models.AllPlayers;
import com.jjoey.sportseco.models.Batch;
import com.jjoey.sportseco.models.Coach;
import com.jjoey.sportseco.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllPlayersActivity extends AppCompatActivity {

    private static final String TAG = AllPlayersActivity.class.getSimpleName();

    private String coachId = null, batchId = null;

    private Toolbar toolbar;
    private ImageView backIV;
    private RecyclerView allPlayersRV;
    private LinearLayoutManager llm;

    private AllPlayers players;
    private List<AllPlayers> list = new ArrayList<>();
    private AllPlayersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_players);

        getQueryInfo();

        init();
        setSupportActionBar(toolbar);

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startHomeActivity();
            }
        });

    }

    private void startHomeActivity() {
        startActivity(new Intent(AllPlayersActivity.this, HomeActivity.class));
        overridePendingTransition(R.anim.anim_slide_left, R.anim.anim_slide_right);
        finish();
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        backIV = findViewById(R.id.backIV);
        allPlayersRV = findViewById(R.id.allPlayersRv);

        setUpRV();

        if (getList().size() > 0){
            Log.d(TAG, "All Players from DB:\t" + getList().size());
            list = getList();
            adapter = new AllPlayersAdapter(this, list);
            allPlayersRV.setAdapter(adapter);
        } else {
            fetchPlayersUnderCoach(coachId, batchId);
        }

    }

    private void setUpRV() {
        allPlayersRV.setHasFixedSize(true);
        llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        allPlayersRV.setLayoutManager(llm);
    }

    private void fetchPlayersUnderCoach(String coachId, String batchId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("coach_id", coachId);
            jsonObject.put("batch_id", batchId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(Constants.COACH_PLAYER_LIST)
                .addJSONObjectBody(jsonObject)
                .setTag("Get All Players in Batch")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            Log.d(TAG, "All Players Response:\t" + response.toString());

                            try {
                                JSONObject object = new JSONObject(response.toString());
                                JSONArray array = object.getJSONArray("players");
                                for (int m = 0; m < array.length(); m++){
                                    JSONObject jobj = array.getJSONObject(m);

                                    players = new AllPlayers();
                                    players.setUserId(jobj.getString("user_id"));
                                    players.setFirstName(jobj.getString("first_name"));
                                    players.setLastName(jobj.getString("last_name"));
                                    players.setUsername(jobj.getString("username"));
                                    players.setImageURL(jobj.getString("image"));
                                    players.setAddress(jobj.getString("address"));

                                    players.save();

                                    Log.d(TAG, "All Players first time:\t" + getList().size());
                                    list = getList();
                                    adapter = new AllPlayersAdapter(AllPlayersActivity.this, list);
                                    allPlayersRV.setAdapter(adapter);

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

    private List<AllPlayers> getList(){
        return new Select()
                .from(AllPlayers.class)
                .orderBy("id ASC")
                .execute();
    }


    private void getQueryInfo() {
        Coach coach = new Select()
                .from(Coach.class)
                .orderBy("id ASC")
                .executeSingle();
        coachId = coach.coachId;

        Log.d(TAG, "Coach id:\t" + coachId);

        Batch batch = new Select()
                .from(Batch.class)
                .orderBy("id ASC")
                .executeSingle();
        batchId = batch.batchId;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startHomeActivity();
    }
}
