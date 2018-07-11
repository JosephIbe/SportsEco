package com.jjoey.sportseco.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjoey.sportseco.R;

public class HomeMiscViewHolder extends RecyclerView.ViewHolder {

    public ImageView miscImg;
    public TextView miscTV, moreTV;

    public HomeMiscViewHolder(View itemView) {
        super(itemView);

        miscImg = itemView.findViewById(R.id.miscImg);
        miscTV = itemView.findViewById(R.id.miscTV);
//        moreTV = itemView.findViewById(R.id.moreTV);

    }
}
