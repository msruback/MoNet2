package com.mattrubacky.monet2.adapter.RecyclerView;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders.StageViewHolder;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.mattrubacky.monet2.helper.StageStats;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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
    @Override
    public StageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        StageViewHolder viewHolder = new StageViewHolder(inflater,parent,context);
        viewHolder.itemView.setOnClickListener(onClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final StageViewHolder holder, final int position) {
        StageStats stageStats = input.get(position);
        holder.manageHolder(stageStats);

    }

    @Override
    public int getItemCount() {
        return input.size();
    }

}