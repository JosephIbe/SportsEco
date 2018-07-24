package com.jjoey.sportseco.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoey.sportseco.R;
import com.jjoey.sportseco.activities.PlayerDetailsActivity;
import com.jjoey.sportseco.models.AllPlayers;
import com.jjoey.sportseco.viewholders.AllPlayersViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AllPlayersAdapter extends RecyclerView.Adapter<AllPlayersViewHolder> {

    private static final String TAG = AllPlayersAdapter.class.getSimpleName();

    private final Context context;
    private List<AllPlayers> itemsList;

    public AllPlayersAdapter(Context context, List<AllPlayers> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @Override
    public AllPlayersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_players_item_layout, parent, false);
        return new AllPlayersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AllPlayersViewHolder viewholder, int position) {
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
                Intent intent = new Intent(context, PlayerDetailsActivity.class);
                intent.putExtra("player_user_id", playeruserId);
                context.startActivity(intent);
//                ((AppCompatActivity)context).finish();
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