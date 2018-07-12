package com.jjoey.sportseco.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.query.Select;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.jjoey.sportseco.R;
import com.jjoey.sportseco.adapters.AllPlayersAdapter;
import com.jjoey.sportseco.adapters.SessionsAdapter;
import com.jjoey.sportseco.models.AllPlayers;
import com.jjoey.sportseco.models.Batch;
import com.jjoey.sportseco.models.Coach;
import com.jjoey.sportseco.models.ProgramDetails;
import com.jjoey.sportseco.models.Sessions;
import com.jjoey.sportseco.utils.Constants;
import com.jjoey.sportseco.utils.EmptyRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateFeedbackFragment extends Fragment {

    private static final String TAG = CreateFeedbackFragment.class.getSimpleName();

    private EmptyRecyclerView createRV;
    private String coachId = null, batchId = null;

    private AllPlayers players;
    private List<AllPlayers> list = new ArrayList<>();
    private AllPlayersAdapter adapter;

    public CreateFeedbackFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                                    adapter = new AllPlayersAdapter(getActivity(), list);
                                    createRV.setAdapter(adapter);

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_create_feedback, container, false);

        createRV = v.findViewById(R.id.createRV);

        createRV.setHasFixedSize(true);
        LinearLayoutManager vlm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        createRV.setLayoutManager(vlm);

        if (getList().size() > 0){
            Log.d(TAG, "All Players from DB:\t" + getList().size());
            list = getList();
            adapter = new AllPlayersAdapter(getActivity(), list);
            createRV.setAdapter(adapter);
        } else {
            fetchPlayersUnderCoach(coachId, batchId);
        }

        return v;
    }

}
