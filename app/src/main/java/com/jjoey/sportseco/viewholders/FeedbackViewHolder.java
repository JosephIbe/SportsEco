package com.jjoey.sportseco.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jjoey.sportseco.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeedbackViewHolder extends RecyclerView.ViewHolder {

    public RelativeLayout playersLayoutHolder;
    public CircleImageView circleImageView;
    public CheckBox attendanceCheckBox;
    public TextView playerNameTV;

    public FeedbackViewHolder(View itemView) {
        super(itemView);

        playersLayoutHolder = itemView.findViewById(R.id.playersLayoutHolder);
        circleImageView = itemView.findViewById(R.id.player_img);
        attendanceCheckBox = itemView.findViewById(R.id.attendanceCheckBox);
        playerNameTV = itemView.findViewById(R.id.playerNameTV);

    }
}
