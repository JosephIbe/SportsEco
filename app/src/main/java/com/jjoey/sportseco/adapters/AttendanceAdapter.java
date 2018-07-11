package com.jjoey.sportseco.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoey.sportseco.R;
import com.jjoey.sportseco.models.Attendance;
import com.jjoey.sportseco.viewholders.AttendanceViewHolder;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceViewHolder> {

    private final Context context;
    private List<Attendance> itemsList;

    public AttendanceAdapter(Context context, List<Attendance> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @Override
    public AttendanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_items_layout, parent, false);
        return new AttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AttendanceViewHolder viewholder, int position) {
        Attendance attendance = itemsList.get(position);
        viewholder.playerNameTV.setText(attendance.getPlayer().getPlayerName());
        viewholder.attendanceCheckBox.setChecked(attendance.getPlayer().isPresent());
    }

    @Override
    public int getItemCount() {
        if (itemsList == null) {
            return 0;
        }
        return itemsList.size();
    }
}