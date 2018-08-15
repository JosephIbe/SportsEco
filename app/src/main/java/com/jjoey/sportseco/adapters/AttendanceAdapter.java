package com.jjoey.sportseco.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.jjoey.sportseco.R;
import com.jjoey.sportseco.models.PlayerSession;
import com.jjoey.sportseco.viewholders.AttendanceViewHolder;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceViewHolder> {

    private static final String TAG = AttendanceAdapter.class.getSimpleName();

    private final Context context;
    private List<PlayerSession> itemsList;
    private HashMap<String, String> attendanceMap;
    public static int numChecked = 0;

    public AttendanceAdapter(Context context, List<PlayerSession> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
        attendanceMap = new HashMap<>();
    }

    @Override
    public AttendanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_items_layout, parent, false);
        return new AttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AttendanceViewHolder viewholder, final int position) {
        final PlayerSession playerSession = itemsList.get(position);

        String fName = playerSession.getFirstName_player();
        String lName = playerSession.getLastName_player();

        String name = fName.concat("\t" + lName);

        viewholder.playerNameTV.setText(name);
        Picasso.with(context)
                .load(playerSession.getImageURL())
                .placeholder(R.drawable.person_avatar)
                .into(viewholder.circleImageView);

        viewholder.attendanceCheckBox.setOnCheckedChangeListener(null);
        viewholder.attendanceCheckBox.setChecked(playerSession.isPresent());

        viewholder.attendanceCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    numChecked++;
                    Log.d(TAG, "Num Items Checked:\t" + numChecked);
                    playerSession.setPresent(b);
                    attendanceMap.put(itemsList.get(position).getUserId_player(), "1");
                    sendToActivity();
                } else {
                    numChecked--;
                }
            }
        });

        for (Map.Entry<String, String> viewer : attendanceMap.entrySet()) {
            Log.d(TAG, "Players in map:\t" + viewer.getKey() + " \t and status in map:\t" + viewer.getValue());
        }

    }

    public HashMap<String, String> sendToActivity() {
        return attendanceMap;
    }

    public HashMap<String, String> sendToDialogFragment() {
        return attendanceMap;
    }

    public int getNumChecked(){
        return numChecked;
    }

    @Override
    public int getItemCount() {
        if (itemsList == null) {
            return 0;
        }
        return itemsList.size();
    }

}