package com.jjoey.sportseco.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.query.Select;
import com.jjoey.sportseco.R;
import com.jjoey.sportseco.adapters.AllPlayersAdapter;
import com.jjoey.sportseco.models.AllPlayers;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DummyFragment extends Fragment {

    private RecyclerView allPlayersRV;
    private LinearLayoutManager llm;

    private AllPlayers players;
    private List<AllPlayers> list = new ArrayList<>();
    private AllPlayersAdapter adapter;

    public DummyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dummy, container, false);

        allPlayersRV = view.findViewById(R.id.allPlayersRv);
        allPlayersRV.setHasFixedSize(true);
        llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        allPlayersRV.setLayoutManager(llm);

        list = getList();
        adapter = new AllPlayersAdapter(getActivity(), list);
        allPlayersRV.setAdapter(adapter);

        return view;
    }

    private List<AllPlayers> getList(){
        return new Select()
                .from(AllPlayers.class)
                .orderBy("id ASC")
                .execute();
    }

}
