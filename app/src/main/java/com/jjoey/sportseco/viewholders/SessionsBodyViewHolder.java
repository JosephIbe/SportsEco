package com.jjoey.sportseco.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoey.sportseco.R;

public class SessionsBodyViewHolder extends RecyclerView.ViewHolder {

    public ImageView session_bodyIV;
    public TextView sessionNameTV;
    public LinearLayout rowLayout;

    public SessionsBodyViewHolder(View itemView) {
        super(itemView);
        session_bodyIV = itemView.findViewById(R.id.session_bodyIV);
        sessionNameTV = itemView.findViewById(R.id.sessionNameTV);
        rowLayout = itemView.findViewById(R.id.rowLayout);
    }
}
