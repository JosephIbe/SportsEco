package com.jjoey.sportseco.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.jjoey.sportseco.R;
import com.jjoey.sportseco.adapters.DrillsAdapter;
import com.jjoey.sportseco.models.Drills;
import com.jjoey.sportseco.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DrillsFragment extends Fragment {

    private static final String TAG = DrillsFragment.class.getSimpleName();

    private String progId, progSessId;

    private RecyclerView drillsRV;
    private DrillsAdapter adapter;
    private List<Drills> drillsList = new ArrayList<>();

    public DrillsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progId = getActivity().getIntent().getStringExtra("prg_id");
        progSessId = getActivity().getIntent().getStringExtra("prg_sess_id");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_drills, container, false);

        drillsRV = view.findViewById(R.id.drillsRV);
        setUpDrillsView();

        fetchDrillsItems(progId, progSessId);

        setUpDrillsList();

        return view;
    }

    private void fetchDrillsItems(String progId, String progSessId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("prg_id", progId);
            jsonObject.put("prg_session_id", progSessId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(Constants.SESSION_DRILLS_API)
                .addJSONObjectBody(jsonObject)
                .setTag("Get Drills for Session")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null){
                            Log.d(TAG, "Drills Response:\t" + response.toString());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }

    private void setUpDrillsView() {
        drillsRV.setHasFixedSize(true);
        LinearLayoutManager vlm = new LinearLayoutManager(getActivity());
        vlm.setOrientation(LinearLayoutManager.VERTICAL);
        drillsRV.setLayoutManager(vlm);
        drillsRV.addItemDecoration(new DividerItemDecoration(getActivity(), vlm.getOrientation()));
    }

    private void setUpDrillsList() {

        Drills drills = new Drills();
        drills.setDrill_Title("Carolina Shooting");
        drillsList.add(drills);

        Drills drills1 = new Drills();
        drills1.setDrill_Title("Beat the Chair");
        drillsList.add(drills1);

        Drills drills2 = new Drills();
        drills2.setDrill_Title("3 Man Motion Drill");
        drillsList.add(drills2);

        Drills drills3 = new Drills();
        drills3.setDrill_Title("Angled Shooting");
        drillsList.add(drills3);

        Drills drills4 = new Drills();
        drills4.setDrill_Title("Banana Cut");
        drillsList.add(drills4);

        Drills drills5 = new Drills();
        drills5.setDrill_Title("Both Side Shooting");
        drillsList.add(drills5);

        adapter = new DrillsAdapter(getActivity(), drillsList);
        drillsRV.setAdapter(adapter);

    }

}
