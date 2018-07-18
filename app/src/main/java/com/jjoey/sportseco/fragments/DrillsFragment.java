package com.jjoey.sportseco.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoey.sportseco.R;
import com.jjoey.sportseco.adapters.DrillsAdapter;
import com.jjoey.sportseco.models.Drills;

import java.util.ArrayList;
import java.util.List;

public class DrillsFragment extends Fragment {

    private static final String TAG = DrillsFragment.class.getSimpleName();

    private RecyclerView drillsRV;
    private DrillsAdapter adapter;
    private List<Drills> drillsList = new ArrayList<>();

    public DrillsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_drills, container, false);

        drillsRV = view.findViewById(R.id.drillsRV);
        setUpDrillsView();

        return view;
    }

    private void setUpDrillsView() {
        drillsRV.setHasFixedSize(true);
        LinearLayoutManager vlm = new LinearLayoutManager(getActivity());
        vlm.setOrientation(LinearLayoutManager.VERTICAL);
        drillsRV.setLayoutManager(vlm);
        drillsRV.addItemDecoration(new DividerItemDecoration(getActivity(), vlm.getOrientation()));

        Drills drills = new Drills();
        drills.setDrill_Id(1);
        drills.setDrill_Title("Carolina Shooting");
        drills.setDuration(5.00);
        drillsList.add(drills);

        Drills drills1 = new Drills();
        drills1.setDrill_Id(1);
        drills1.setDrill_Title("Beat the Chair");
        drills1.setDuration(5.00);
        drillsList.add(drills1);

        Drills drills2 = new Drills();
        drills2.setDrill_Id(1);
        drills2.setDrill_Title("3 Man Motion Drill");
        drills2.setDuration(5.00);
        drillsList.add(drills2);

        Drills drills3 = new Drills();
        drills3.setDrill_Id(1);
        drills3.setDrill_Title("Angled Shooting");
        drills3.setDuration(5.00);
        drillsList.add(drills3);

        Drills drills4 = new Drills();
        drills4.setDrill_Id(1);
        drills4.setDrill_Title("Banana Cut");
        drills4.setDuration(5.00);
        drillsList.add(drills4);

        Drills drills5 = new Drills();
        drills5.setDrill_Id(1);
        drills5.setDrill_Title("Both Side Shooting");
        drills5.setDuration(5.00);
        drillsList.add(drills5);

        adapter = new DrillsAdapter(getActivity(), drillsList);
        drillsRV.setAdapter(adapter);

    }

}
