package com.jjoey.sportseco.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jjoey.sportseco.R;

public class BatchItemsViewHolder extends RecyclerView.ViewHolder {

    public RadioButton batchRadioBtn;
    public TextView batchNameTV;

    public BatchItemsViewHolder(View itemView) {
        super(itemView);

        batchRadioBtn = itemView.findViewById(R.id.batchRadioBtn);
        batchNameTV = itemView.findViewById(R.id.batchNameTV);

    }
}
