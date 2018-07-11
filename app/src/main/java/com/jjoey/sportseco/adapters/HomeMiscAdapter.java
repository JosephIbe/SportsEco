package com.jjoey.sportseco.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.jjoey.sportseco.R;
import com.jjoey.sportseco.viewholders.HomeMiscViewHolder;

import java.util.List;

public class HomeMiscAdapter extends RecyclerView.Adapter<HomeMiscViewHolder> {

    private final Context context;
    private List<String> itemsList;

    private ColorGenerator generator;

    public HomeMiscAdapter(Context context, List<String> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
         generator= ColorGenerator.MATERIAL;
    }

    @Override
    public HomeMiscViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_misc_items_layout, parent, false);
        return new HomeMiscViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeMiscViewHolder viewholder, int position) {

        String misc = itemsList.get(position);
        viewholder.miscTV.setText(misc);

        String letterDrawable = String.valueOf(itemsList.get(position).charAt(0));
        TextDrawable drawable = TextDrawable.builder().buildRound(letterDrawable, generator.getRandomColor());
        viewholder.miscImg.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        if (itemsList == null) {
            return 0;
        }
        return itemsList.size();
    }
}