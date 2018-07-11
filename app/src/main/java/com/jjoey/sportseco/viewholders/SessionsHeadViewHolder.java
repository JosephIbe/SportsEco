package com.jjoey.sportseco.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjoey.sportseco.R;

public class SessionsHeadViewHolder extends RecyclerView.ViewHolder {

    public ImageView session_headImg;
    public TextView createSessionTV;

    public SessionsHeadViewHolder(View itemView) {
        super(itemView);

        session_headImg = itemView.findViewById(R.id.session_headImg);
        createSessionTV = itemView.findViewById(R.id.createSessionTV);

    }
}
