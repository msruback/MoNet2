package com.mattrubacky.monet2.adapter.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders.RegularTimePeriodViewHolder;
import com.mattrubacky.monet2.deserialized.splatoon.TimePeriod;

import java.util.ArrayList;

/**
 * Created by mattr on 1/14/2018.
 */

public class RegularRotationAdapter extends RecyclerView.Adapter<RegularTimePeriodViewHolder>{

    private ArrayList<TimePeriod> input;
    private LayoutInflater inflater;
    private Context context;

    public RegularRotationAdapter(Context context, ArrayList<TimePeriod> input) {
        this.inflater = LayoutInflater.from(context);
        this.input = input;
        this.context = context;
    }
    @Override
    public RegularTimePeriodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RegularTimePeriodViewHolder viewHolder = new RegularTimePeriodViewHolder(inflater,parent,context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RegularTimePeriodViewHolder holder, final int position) {
        TimePeriod timePeriod = input.get(position);
        holder.manageHolder(timePeriod);
    }

    @Override
    public int getItemCount() {
        return input.size();
    }

}
