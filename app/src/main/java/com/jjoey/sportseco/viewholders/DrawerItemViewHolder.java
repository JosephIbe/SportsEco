package com.jjoey.sportseco.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jjoey.sportseco.R;

public class DrawerItemViewHolder extends RecyclerView.ViewHolder {

    public TextView menuTitleTV;
    public ImageView menuIcon;
    public Context context;
    public RelativeLayout relativeLayout;

    public DrawerItemViewHolder(View itemView) {
        super(itemView);

        menuIcon = itemView.findViewById(R.id.menuIconIV);
        menuTitleTV = itemView.findViewById(R.id.menuTitleTV);

    }

}
