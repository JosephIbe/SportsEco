package com.jjoey.sportseco.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.jjoey.sportseco.R;

public class BatchFooterViewHolder extends RecyclerView.ViewHolder {

    public Button proceedBtn;

    public BatchFooterViewHolder(View itemView) {
        super(itemView);

        proceedBtn = itemView.findViewById(R.id.proceedBtn);

    }
}
