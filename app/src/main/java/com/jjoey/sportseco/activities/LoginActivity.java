package com.jjoey.sportseco.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.activeandroid.query.Select;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.jjoey.sportseco.R;
import com.jjoey.sportseco.models.Batch;
import com.jjoey.sportseco.models.Coach;
import com.jjoey.sportseco.models.ProgramDetails;
import com.jjoey.sportseco.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private EditText emailET, pwdET;
    private Button loginBtn;

    private String email = null, pass = null;
    private String id_coach;
    private Batch batch;
    private List<Object> list = new ArrayList<>();
    private List<ProgramDetails> detailsList = new ArrayList<>();
    private Coach coach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin(email, pass);
//                if (validate()) {
//                    attemptLogin(email, pass);
//                } else {
//                    Snackbar.make(findViewById(android.R.id.content), "Invalid Details", Snackbar.LENGTH_LONG).show();
//                }

            }
        });

    }

    private void attemptLogin(String email, String pass) {

        JSONObject jsonObject = new JSONObject();
        try {
//            jsonObject.put("username", email);
            jsonObject.put("username", "testcoach@gmail.com");
//            jsonObject.put("password", pass);
            jsonObject.put("password", "123@abcd");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // TODO: 7/5/2018 Change values to actual details

        AndroidNetworking.post(Constants.LOGIN_COACH)
                .addJSONObjectBody(jsonObject)
                .setTag("Login CoachResponse")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try {
                                JSONObject object = new JSONObject(response.toString());
                                JSONObject details = object.getJSONObject("coach_details");

                                id_coach = details.getString("coach_id");
                                String academyId = details.getString("academy_id");
                                String username = details.getString("username");
                                String firstName = details.getString("first_name");
                                String lastName = details.getString("last_name");
                                String midName = details.getString("middle_name");
                                String nick = details.getString("nick_name");
                                String gender = details.getString("gender");
                                String mobile = details.getString("mobile");
                                String state = details.getString("state");
                                String email = details.getString("email");

                                saveCoachDetails(id_coach, academyId, username, firstName, lastName, midName, nick, gender, mobile, state, email);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

//                            if (res.getMessage().equals(Constants.SUCCESS_MSG) && id_coach != null) {
                                // TODO: 7/5/2018 Check first login and send to Batch or Home Activity
//                                Intent intent = new Intent(LoginActivity.this, SelectBatchActivity.class);
//                                intent.putExtra("first_name", res.getCoachDetails().getFirstName());
//                                intent.putExtra("id_coach", id_coach);
//                                startActivity(intent);
//                                finish();
//                          }

                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    private void saveCoachDetails(String idCoach, String academyId, String username, String firstName, String lastName, String midName, String nick, String gender, String mobile, String state, String email) {
        coach = new Coach();
        coach.setCoachId(idCoach);
        coach.setAcademyId(academyId);
        coach.setUsername(username);
        coach.setEmailAddr(email);
        coach.setFirstName(firstName);
        coach.setLastName(lastName);
        coach.setMidName(midName);
        coach.setNickName(nick);
        coach.setGender(gender);
        coach.setMobileNum(mobile);
        coach.setOriginState(state);

        coach.save();

        Coach coachQuery = new Select()
                .from(Coach.class)
                .where("coach_id=?", id_coach)
                .executeSingle();
        Log.d(TAG, "Coach id:\t" + coachQuery.coachId);
        id_coach = coachQuery.coachId;
        fetchBatches(idCoach);

    }

    public void fetchBatches(final String coachId) {

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

                                    String name_batch = jsonObject.getString("batch_name");
                                    String id_batch = jsonObject.getString("batch_id");

//                                    fetchProgramList(id_batch, coachId);

                                    batch = new Batch();
                                    batch.setBatchId(id_batch);
                                    batch.setBatchName(name_batch);
                                    batch.save();

                                    list.add(batch);
                                    Log.d(TAG, "Batch Size:\t" + list.size());
                                    if (list.size() > 1){
                                        Intent intent = new Intent(LoginActivity.this, SelectBatchActivity.class);
                                        intent.putExtra("id_coach", coachId);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        fetchProgramList(id_batch, coachId);
                                    }

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

    private void fetchProgramList(final String batch_id, String coachId) {
        JSONObject object = new JSONObject();
        try {
            object.put("coach_id", coachId);
            object.put("batch_id", batch_id);
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
                            try {
                                JSONObject jsonObject = new JSONObject(response.toString());

                                JSONArray detailsArr = jsonObject.getJSONArray("program_details");
                                for (int i = 0; i < detailsArr.length(); i++){
                                    JSONObject obj = detailsArr.getJSONObject(i);
                                    ProgramDetails programDetails = new ProgramDetails();

                                    Batch batch = new Batch();
                                    batch.setBatchName(obj.getString("batch_name"));
                                    batch.setBatchId(obj.getString("batch_id"));

                                    programDetails.setBatch(batch);
                                    programDetails.setCoachId(id_coach);
                                    programDetails.setProgId(obj.getString("prg_id"));
                                    programDetails.setProgramName(obj.getString("prg_name"));
                                    programDetails.setProgUserMapId(obj.getString("prg_user_map_id"));
                                    programDetails.setStartDate(obj.getString("prg_start_date"));
                                    programDetails.setEndDate(obj.getString("prg_end_date"));

                                    programDetails.save();

                                    detailsList.add(programDetails);
                                    Log.d(TAG, "Programs Size:\t" + detailsList.size());

                                    if (detailsList.size() > 1){
//                                        startActivity(new Intent(LoginActivity.this, ProgramsActivity.class));
                                    } else {
                                        Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                                        homeIntent.putExtra("coach_id", id_coach);
                                        homeIntent.putExtra("batch_id", batch_id);
                                        startActivity(homeIntent);
                                    }

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

    private boolean validate() {
        email = emailET.getText().toString();
        pass = pwdET.getText().toString();

        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(pass)) {
            return false;
        } else {
            return true;
        }
    }

    private void initViews() {
        emailET = findViewById(R.id.emailET);
        pwdET = findViewById(R.id.pwdET);
        loginBtn = findViewById(R.id.loginBtn);
    }

}
