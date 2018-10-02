package com.mattrubacky.monet2.adapter.RecyclerView;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders.CompetitiveTimePeriodViewHolder;
import com.mattrubacky.monet2.deserialized.splatoon.TimePeriod;

import java.util.ArrayList;

/**
 * Created by mattr on 1/14/2018.
 */

public class RankedRotationAdapter extends RecyclerView.Adapter<CompetitiveTimePeriodViewHolder>{

    private ArrayList<TimePeriod> input;
    private LayoutInflater inflater;
    private Context context;

    public RankedRotationAdapter(Context context, ArrayList<TimePeriod> input) {
        this.inflater = LayoutInflater.from(context);
        this.input = input;
        this.context = context;
    }
    @Override
    public CompetitiveTimePeriodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CompetitiveTimePeriodViewHolder viewHolder = new CompetitiveTimePeriodViewHolder(inflater,parent,context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CompetitiveTimePeriodViewHolder holder, final int position) {
        TimePeriod timePeriod = input.get(position);
        holder.manageHolder(timePeriod);
        holder.mode.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.rankAccent)));
    }

    @Override
    public int getItemCount() {
        return input.size();
    }

}