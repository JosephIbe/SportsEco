package com.jjoey.sportseco.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.jjoey.sportseco.R;
import com.jjoey.sportseco.adapters.BatchAdapter;
import com.jjoey.sportseco.models.BatchFooter;
import com.jjoey.sportseco.models.Batch;
import com.jjoey.sportseco.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SelectBatchActivity extends AppCompatActivity {

    private static final String TAG = SelectBatchActivity.class.getSimpleName();

    private String coachName = null, coachId = null;

    private TextView nameTV;
    private ProgressBar progress;
    private RecyclerView recyclerView;

    private Batch batch;
    private List<Object> list = new ArrayList<>();
    private BatchAdapter adapter;
    private String name_batch, id_batch;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_batch);

        initViews();

        coachName = getIntent().getExtras().getString("first_name");
        coachId = getIntent().getExtras().getString("coachId");
        if (coachName != null && coachId != null) {
            nameTV.setText(coachName);
            fetchBatches(coachId);
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void fetchProgramList(String batch_id, String coachId) {
        JSONObject object = new JSONObject();
        Log.d(TAG, "Coach id for program list:\t" + Integer.parseInt(coachId));
        Log.d(TAG, "Batch id for program list:\t" + Integer.parseInt(batch_id));
        try {
            object.put("coach_id", 1556);
            object.put("batch_id", 50);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(Constants.COACH_PROGRAM_LIST)
                .addJSONObjectBody(object)
                .setTag("Get ProgramDetails for Coach")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null){
                            Log.d(TAG, "Program List Response:\t" + response.toString());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

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

                                message = object.getString("message");

                                JSONArray array = object.getJSONArray("batch_details");
                                for (int m = 0; m < array.length(); m++) {

                                    JSONObject jsonObject = array.getJSONObject(m);

                                    name_batch = jsonObject.getString("batch_name");
                                    id_batch = jsonObject.getString("batch_id");

                                    fetchProgramList(id_batch, coachId);

                                    batch = new Batch();
                                    batch.setBatchId(id_batch);
                                    batch.setBatchName(name_batch);

                                    list.add(batch);
                                    BatchFooter footer = new BatchFooter();
                                    list.add(footer.getButton());

                                    adapter = new BatchAdapter(SelectBatchActivity.this, list);
                                    adapter.setMessageValue(message);
                                    recyclerView.setAdapter(adapter);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            progress.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    private void initViews() {
        nameTV = findViewById(R.id.coachNameTV);
        progress = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.batchRV);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.ACTION_DOWN){
            super.onKeyDown(keyCode, event);
            return true;
        }
        return false;

    }


}
