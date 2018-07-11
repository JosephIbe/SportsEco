package com.jjoey.sportseco.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.jjoey.sportseco.R;


public class DrawerHeaderItemViewHolder extends RecyclerView.ViewHolder {

    public ImageView drawerHeaderIconImg;

    public DrawerHeaderItemViewHolder(View itemView) {
        super(itemView);

        drawerHeaderIconImg = itemView.findViewById(R.id.headerIconImg);

    }
}
