package com.jjoey.sportseco.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jjoey.sportseco.R;
import com.jjoey.sportseco.activities.SessionsActivity;
import com.jjoey.sportseco.models.Sessions;
import com.jjoey.sportseco.viewholders.SessionsBodyViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SessionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private List<Object> itemsList;

    public static final int HEADER_VIEW = 0;
    public static final int ITEMS_VIEW = 1;

    public SessionsAdapter(Context context, List<Object> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.session_body_layout, parent, false);
        return new SessionsBodyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewholder, int position) {
            Sessions bodyItem = (Sessions) itemsList.get(position);
            SessionsBodyViewHolder holder = (SessionsBodyViewHolder) viewholder;

            holder.sessionNameTV.setText(bodyItem.getSessionName());
            Picasso.with(context)
                    .load(bodyItem.getSessionIcon())
                    .placeholder(R.drawable.basketball)
                    .into(holder.session_bodyIV);

            holder.rowLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, SessionsActivity.class));
                }
            });
    }

    @Override
    public int getItemCount() {
        if (itemsList == null) {
            return 0;
        }
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