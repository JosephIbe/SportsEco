package com.jjoey.sportseco.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoey.sportseco.R;
import com.jjoey.sportseco.models.ItemsDrawer;
import com.jjoey.sportseco.models.ItemsHeader;
import com.jjoey.sportseco.viewholders.DrawerHeaderItemViewHolder;
import com.jjoey.sportseco.viewholders.DrawerItemViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemsDrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private List<Object> itemsList;

    public static final int HEADER_VIEW = 0;
    public static final int ITEMS_VIEW = 1;

    public ItemsDrawerAdapter(Context context, List<Object> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADER_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_drawer_layout, parent, false);
            return new DrawerHeaderItemViewHolder(view);
        } else if (viewType == ITEMS_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_menu_items_layout, parent, false);
            return new DrawerItemViewHolder(view);
        }
        throw new RuntimeException("No matching viewTypes");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DrawerHeaderItemViewHolder) {
            ItemsHeader header = (ItemsHeader) itemsList.get(position);
            Picasso.with(context)
                    .load(header.getHeaderIcon())
                    .into(((DrawerHeaderItemViewHolder) holder).drawerHeaderIconImg);

        } else if (holder instanceof DrawerItemViewHolder) {
            ItemsDrawer drawerItems = (ItemsDrawer) itemsList.get(position);
            DrawerItemViewHolder viewHolder = (DrawerItemViewHolder) holder;
            viewHolder.menuTitleTV.setText(drawerItems.getName());
            Picasso.with(context)
                    .load(drawerItems.getIcon())
                    .into(viewHolder.menuIcon);
        }
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return HEADER_VIEW;
        return ITEMS_VIEW;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

}