package com.jjoey.sportseco.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.jjoey.sportseco.R;
import com.jjoey.sportseco.activities.HomeActivity;
import com.jjoey.sportseco.models.Batch;
import com.jjoey.sportseco.viewholders.BatchFooterViewHolder;
import com.jjoey.sportseco.viewholders.BatchItemsViewHolder;

import java.util.List;

public class BatchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = BatchAdapter.class.getSimpleName();

    public static final int ITEMS_VIEW = 1;
    public static final int FOOTER_VIEW = 2;

    private final Context context;
    private List<Object> itemsList;

    private String coachId = null, batchId = null, msg = null;

    public BatchAdapter(Context context, List<Object> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
        coachId = ((AppCompatActivity)context).getIntent().getExtras().getString("coachId");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case ITEMS_VIEW:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.batch_items_layout, parent, false);
                return new BatchItemsViewHolder(view);
            case FOOTER_VIEW:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.batch_footer_layout, parent, false);
                return new BatchFooterViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BatchItemsViewHolder){
            BatchItemsViewHolder viewHolder = (BatchItemsViewHolder) holder;
            final Batch batch = (Batch) itemsList.get(position);
            viewHolder.batchNameTV.setText(batch.getBatchName());
            viewHolder.batchRadioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        Log.d(TAG, "Batch_id:\t" + batch.getBatchId());
                        batchId = batch.getBatchId();
                    } else {
                        batchId = null;
                    }
                }
            });
        } else if (holder instanceof BatchFooterViewHolder){
            BatchFooterViewHolder footerViewHolder = (BatchFooterViewHolder) holder;
            footerViewHolder.proceedBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (batchId != null && getMessageValue() != null){
                        Intent intent = new Intent(context, HomeActivity.class);
                        intent.putExtra("coach_id", coachId);
                        intent.putExtra("batch_id", batchId);
                        context.startActivity(intent);
                        ((Activity)context).finish();
                    } else {
                        Snackbar.make(((AppCompatActivity)context).findViewById(android.R.id.content), "Select a Batch to Continue", Snackbar.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (itemsList == null) {
            return 0;
        }
        return itemsList.size();
    }

    private boolean isPositionFooter(int position){
        return position == itemsList.size() - 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionFooter(position)){
            return FOOTER_VIEW;
        }
        return ITEMS_VIEW;
    }

    public void setMessageValue(String message) {
        Log.d(TAG, "Message Value:\t" + message);
        if (message != null){
            msg = message;
        }
    }

    public String getMessageValue(){
        return msg;
    }

}