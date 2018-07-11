package com.jjoey.sportseco.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jjoey.sportseco.R;

public class AttendanceViewHolder extends RecyclerView.ViewHolder {

    public CheckBox attendanceCheckBox;
    public TextView playerNameTV;

    public AttendanceViewHolder(View itemView) {
        super(itemView);

        attendanceCheckBox = itemView.findViewById(R.id.attendanceCheckBox);
        playerNameTV = itemView.findViewById(R.id.playerNameTV);

    }
}
