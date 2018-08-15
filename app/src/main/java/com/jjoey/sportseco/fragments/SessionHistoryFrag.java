package com.jjoey.sportseco.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.activeandroid.query.Select;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.jjoey.sportseco.R;
import com.jjoey.sportseco.models.ProgramDetails;
import com.jjoey.sportseco.utils.Constants;
import com.jjoey.sportseco.utils.EmptyRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class SessionHistoryFrag extends Fragment {

    private static final String TAG = SessionHistoryFrag.class.getSimpleName();

    private EmptyRecyclerView historyRV;
    private LinearLayoutManager llm;

    private LinearLayout emptyLayout;

    private ProgramDetails programDetails;
    private String progUserMapId = null;

    public SessionHistoryFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_session_history, container, false);

        emptyLayout = view.findViewById(R.id.emptyLayout);
        historyRV = view.findViewById(R.id.historyRV);

        historyRV.setHasFixedSize(true);
        llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        historyRV.setLayoutManager(llm);
        historyRV.setEmptyView(emptyLayout);

        queryMeta();

        return view;
    }

    private void queryMeta() {
        programDetails = new Select()
                .from(ProgramDetails.class)
                .orderBy("id ASC")
                .executeSingle();
        progUserMapId = programDetails.getProgUserMapId();
        Log.d(TAG, "Prog User Map Id:\t" + progUserMapId);

        fetchHistoryDetails(progUserMapId);

    }

    private void fetchHistoryDetails(String userMapId) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("prg_user_map_id", Integer.parseInt(userMapId));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(Constants.GET_COMPLETED_SESSIONS)
                .addJSONObjectBody(jsonObject)
                .setTag("Get All Completed Sessions")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null){
                            Log.d(TAG, "Session History Response:\t" + response.toString());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (anError != null){
//                            Log.d(TAG, "Sth Went Wrong:\t" + anError.getMessage().toString());
                        }
                    }
                });

    }

}
