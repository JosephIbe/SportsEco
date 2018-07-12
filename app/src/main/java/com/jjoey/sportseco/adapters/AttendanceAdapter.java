package com.jjoey.sportseco.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoey.sportseco.R;
import com.jjoey.sportseco.models.PlayerSession;
import com.jjoey.sportseco.viewholders.AttendanceViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceViewHolder> {

    private final Context context;
//    private List<Attendance> itemsList;
    private List<PlayerSession> itemsList;

    public AttendanceAdapter(Context context, List<PlayerSession> itemsList) {
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
        PlayerSession playerSession = itemsList.get(position);

        String fName = playerSession.getFirstName_player();
        String lName = playerSession.getLastName_player();

        String name = fName.concat("\t" + lName);

        viewholder.playerNameTV.setText(name);
        Picasso.with(context)
                .load(playerSession.getImageURL())
                .placeholder(R.drawable.person_avatar)
                .into(viewholder.circleImageView);

//        Attendance attendance = itemsList.get(position);
//        viewholder.playerNameTV.setText(attendance.getPlayerSession().getFirstName_player());
//        viewholder.attendanceCheckBox.setChecked(attendance.getPlayerSession().isPresent());
    }

    @Override
    public int getItemCount() {
        if (itemsList == null) {
            return 0;
        }
        return itemsList.size();
    }
}