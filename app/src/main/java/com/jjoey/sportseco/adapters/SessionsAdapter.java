package com.jjoey.sportseco.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.jjoey.sportseco.R;
import com.jjoey.sportseco.activities.SessionsActivity;
import com.jjoey.sportseco.models.Sessions;
import com.jjoey.sportseco.viewholders.SessionsBodyViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SessionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = SessionsAdapter.class.getSimpleName();

    private final Context context;
    private List<Sessions> itemsList;

    private ColorGenerator generator;

    public SessionsAdapter(Context context, List<Sessions> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
        generator= ColorGenerator.MATERIAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.session_body_layout, parent, false);
        return new SessionsBodyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewholder, int position) {
        final Sessions bodyItem = (Sessions) itemsList.get(position);
        SessionsBodyViewHolder holder = (SessionsBodyViewHolder) viewholder;

        holder.sessionNameTV.setText(bodyItem.getSessionName());
        String drawable = String.valueOf(String.valueOf(itemsList.get(position)).charAt(0));
        String drawable1 = String.valueOf(String.valueOf(itemsList.get(position)).charAt(1));

        String letterDrawable = drawable.concat(drawable1);
        TextDrawable textDrawable = TextDrawable.builder().buildRound(letterDrawable, generator.getRandomColor());
        ((SessionsBodyViewHolder) viewholder).session_bodyIV.setImageDrawable(textDrawable);

//            Picasso.with(context)
//                    .load(bodyItem.getSessionIcon())
//                    .placeholder(R.drawable.basketball)
//                    .into(holder.session_bodyIV);

        holder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SessionsActivity.class);
                intent.putExtra("prg_sess_id", bodyItem.getProgramSessionId());
                intent.putExtra("session_name", bodyItem.getSessionName());
                intent.putExtra("session_desc", bodyItem.getSessionDesc());
                intent.putExtra("session_cover_image", bodyItem.getSessionCoverImage());
                intent.putExtra("session_video_link", bodyItem.getSessionVideoLink());
                intent.putExtra("session_focus_pts", bodyItem.getSessionFocusPoints());
                context.startActivity(intent);
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

}