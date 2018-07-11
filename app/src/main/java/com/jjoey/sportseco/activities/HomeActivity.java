package com.jjoey.sportseco.activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.jjoey.sportseco.R;
import com.jjoey.sportseco.adapters.BatchAdapter;
import com.jjoey.sportseco.adapters.HomeMiscAdapter;
import com.jjoey.sportseco.adapters.ItemsDrawerAdapter;
import com.jjoey.sportseco.adapters.SessionsAdapter;
import com.jjoey.sportseco.fragments.BatchFragment;
import com.jjoey.sportseco.interfaces.RecyclerClickListener;
import com.jjoey.sportseco.models.Batch;
import com.jjoey.sportseco.models.BatchFooter;
import com.jjoey.sportseco.models.CoachResponse;
import com.jjoey.sportseco.models.ItemsDrawer;
import com.jjoey.sportseco.models.ItemsHeader;
import com.jjoey.sportseco.models.Sessions;
import com.jjoey.sportseco.utils.Constants;
import com.jjoey.sportseco.utils.RecyclerItemTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();

    private Toolbar toolbar;
    private ImageView drawerIcon;
    private Spinner batchSpinner;
    private DrawerLayout drawerLayout;
    private RecyclerView drawerRV;

    private List<Object> itemsDrawerList = new ArrayList<>();
    private ItemsDrawerAdapter drawerAdapter;

    private Batch batch;
    private List<Object> spinnerList = new ArrayList<>();
    private String coachId = null, name_batch, id_batch;
    private ArrayAdapter<Object> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();
        setSupportActionBar(toolbar);

        setUpDrawer();

        //setBatchSpinner();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameBatches, new BatchFragment()).commit();

    }

    private void setBatchSpinner() {
        coachId = getIntent().getExtras().getString("coach_id");
        Log.d(TAG,"Coach id Home:\t" + coachId);
        if (coachId != null){
            fetchBatches(coachId);
        }
    }

    private void fetchBatches(final String coachId) {

        JSONObject batchObj = new JSONObject();
        try {
            batchObj.put("coach_id", Integer.parseInt(coachId));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(Constants.COACH_BATCH_LIST)
                .addJSONObjectBody(batchObj)
                .setTag("Fetch Batch List")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            Log.d(TAG, "Batches Response:\t" + response.toString());
                            try {
                                JSONObject object = new JSONObject(response.toString());

                                JSONArray array = object.getJSONArray("batch_details");
                                for (int m = 0; m < array.length(); m++) {

                                    JSONObject jsonObject = array.getJSONObject(m);

                                    name_batch = jsonObject.getString("batch_name");
                                    id_batch = jsonObject.getString("batch_id");

                                    batch = new Batch();
                                    batch.setBatchId(id_batch);
                                    batch.setBatchName(name_batch);

                                    spinnerList.add(batch.getBatchName());
                                    arrayAdapter = new ArrayAdapter<>(HomeActivity.this, android.R.layout.simple_spinner_dropdown_item, spinnerList);
                                    arrayAdapter.setDropDownViewResource(R.layout.batch_spinner_items_layout);
                                    batchSpinner.setAdapter(arrayAdapter);

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

    private void setUpDrawer() {
        drawerRV.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        drawerRV.setLayoutManager(llm);
        drawerRV.addItemDecoration(new DividerItemDecoration(this, llm.getOrientation()));

        ItemsHeader header = new ItemsHeader(R.drawable.court);
        itemsDrawerList.add(header);

        ItemsDrawer drawer = new ItemsDrawer(R.drawable.basketball, "Sessions");
        itemsDrawerList.add(drawer);

        ItemsDrawer drawer1 = new ItemsDrawer(R.drawable.basketball, "Feedback");
        itemsDrawerList.add(drawer1);

        ItemsDrawer drawer2 = new ItemsDrawer(R.drawable.basketball, "Batches");
        itemsDrawerList.add(drawer2);

        ItemsDrawer drawer3 = new ItemsDrawer(R.drawable.basketball, "Programs");
        itemsDrawerList.add(drawer3);

        ItemsDrawer drawer4 = new ItemsDrawer(R.drawable.basketball, "Attendance Records");
        itemsDrawerList.add(drawer4);

        ItemsDrawer drawer5 = new ItemsDrawer(R.drawable.basketball, "Player Stats");
        itemsDrawerList.add(drawer5);

        drawerAdapter = new ItemsDrawerAdapter(this, itemsDrawerList);
        drawerRV.setAdapter(drawerAdapter);

        handleDrawerEvents();

    }

    private void handleDrawerEvents() {
        setDrawerClickListener();
        drawerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                    drawerLayout.closeDrawer(GravityCompat.END);
                } else {
                    drawerLayout.openDrawer(GravityCompat.END);
                }
            }
        });
    }

    private void setDrawerClickListener() {
        drawerRV.addOnItemTouchListener(new RecyclerItemTouchListener(this, drawerRV, new RecyclerClickListener() {
            @Override
            public void onClick(View view, int position) {
                switch (position){
                    case 1:
                        Toast.makeText(HomeActivity.this, "Sessions Clicked", Toast.LENGTH_SHORT).show();
                        break;

                    case 2:
                        startActivity(new Intent(HomeActivity.this, FeedbackActivity.class));
                        break;

                    case 3:
                        Toast.makeText(HomeActivity.this, "Batches Clicked", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(HomeActivity.this, SelectBatchActivity.class));
                        break;

                    case 4:
                        Toast.makeText(HomeActivity.this, "Programs Clicked", Toast.LENGTH_SHORT).show();
                        break;

                    case 5:
                        Toast.makeText(HomeActivity.this, "Attendance Clicked", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(HomeActivity.this, HistoryAttendanceActivity.class));
                        break;

                    case 6:
                        Toast.makeText(HomeActivity.this, "Player Stats Clicked", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(HomeActivity.this, PlayerStatsActivity.class));
                        break;
                }
            }
        }));
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        batchSpinner = findViewById(R.id.batchSpinner);
        drawerIcon = findViewById(R.id.drawerIcon);
        drawerLayout = findViewById(R.id.drawerLayout);
        drawerRV = findViewById(R.id.drawerRV);
    }

}
