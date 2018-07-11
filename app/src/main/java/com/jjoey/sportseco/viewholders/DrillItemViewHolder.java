package com.jjoey.sportseco.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jjoey.sportseco.R;

public class DrillItemViewHolder extends RecyclerView.ViewHolder {

    public TextView drillTitleTV;

    public DrillItemViewHolder(View itemView) {
        super(itemView);

        drillTitleTV = itemView.findViewById(R.id.drillTitle);

    }
}
