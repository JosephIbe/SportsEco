package com.jjoey.sportseco.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoey.sportseco.R;
import com.jjoey.sportseco.models.ItemsDrawer;
import com.jjoey.sportseco.viewholders.DrawerItemViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerItemViewHolder> {

    private final Context context;
    private List<ItemsDrawer> itemsList;

    public DrawerAdapter(Context context, List<ItemsDrawer> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @Override
    public DrawerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_menu_items_layout, parent, false);
        return new DrawerItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DrawerItemViewHolder viewholder, int position) {
        ItemsDrawer drawerItems = itemsList.get(position);
        viewholder.menuTitleTV.setText(drawerItems.getName());
        Picasso.with(context)
                .load(drawerItems.getIcon())
                .into(viewholder.menuIcon);
    }

    @Override
    public int getItemCount() {
        if (itemsList == null) {
            return 0;
        }
        return itemsList.size();
    }
}