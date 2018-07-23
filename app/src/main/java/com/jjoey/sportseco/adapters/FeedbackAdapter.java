package com.jjoey.sportseco.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoey.sportseco.R;
import com.jjoey.sportseco.models.AllPlayers;
import com.jjoey.sportseco.viewholders.AllPlayersViewHolder;
import com.jjoey.sportseco.viewholders.FeedbackViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackViewHolder> {

    private static final String TAG = FeedbackAdapter.class.getSimpleName();

    private final Context context;
    private List<AllPlayers> itemsList;

    public FeedbackAdapter(Context context, List<AllPlayers> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @Override
    public FeedbackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_players_item_layout, parent, false);
        return new FeedbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FeedbackViewHolder viewholder, int position) {
        AllPlayers players = itemsList.get(position);

        String fName = players.getFirstName();
        String lName = players.getLastName();

        String name = fName.concat("\t" + lName);

        viewholder.playerNameTV.setText(name);
        Picasso.with(context)
                .load(players.getImageURL())
                .placeholder(R.drawable.person_avatar)
                .into(viewholder.circleImageView);

        final String playeruserId = itemsList.get(position).getUserId();

        viewholder.playersLayoutHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "To do:\t start new screen for userId:\t" + playeruserId);
//                Intent intent = new Intent(context, PlayerDetailsActivity.class);
//                intent.putExtra("player_user_id", playeruserId);
//                context.startActivity(intent);
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