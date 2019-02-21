package com.mattrubacky.monet2.ui.adapter.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders.StageViewHolder;
import com.mattrubacky.monet2.data.stats.StageStats;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 11/13/2017.
 */

public class StageAdapter extends RecyclerView.Adapter<StageViewHolder>{

    private ArrayList<StageStats> input;
    private LayoutInflater inflater;
    private Context context;
    private View.OnClickListener onClickListener;

    public StageAdapter(Context context, ArrayList<StageStats> input, View.OnClickListener onClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.input = input;
        this.context = context;
        this.onClickListener = onClickListener;
    }
    @NonNull
    @Override
    public StageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StageViewHolder viewHolder = new StageViewHolder(inflater,parent,context);
        viewHolder.itemView.setOnClickListener(onClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final StageViewHolder holder, final int position) {
        StageStats stageStats = input.get(position);
        holder.manageHolder(stageStats);

    }

    @Override
    public int getItemCount() {
        return input.size();
    }

}