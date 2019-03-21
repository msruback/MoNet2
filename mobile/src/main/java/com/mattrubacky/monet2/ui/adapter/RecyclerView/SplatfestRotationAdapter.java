package com.mattrubacky.monet2.ui.adapter.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders.SplatfestTimePeriodViewHolder;
import com.mattrubacky.monet2.data.deserialized.splatoon.Splatfest;
import com.mattrubacky.monet2.data.deserialized.splatoon.TimePeriod;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by mattr on 1/14/2018.
 */

public class SplatfestRotationAdapter extends RecyclerView.Adapter<SplatfestTimePeriodViewHolder>{

    private ArrayList<TimePeriod> input;
    private LayoutInflater inflater;
    private Splatfest splatfest;
    private Context context;

    public SplatfestRotationAdapter(Context context, ArrayList<TimePeriod> input, Splatfest splatfest) {
        this.inflater = LayoutInflater.from(context);
        this.input = input;
        this.splatfest = splatfest;
        this.context = context;
    }
    @NonNull
    @Override
    public SplatfestTimePeriodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SplatfestTimePeriodViewHolder(inflater,parent,context);
    }

    @Override
    public void onBindViewHolder(final SplatfestTimePeriodViewHolder holder, final int position) {
        TimePeriod timePeriod = input.get(position);
        holder.manageHolder(timePeriod,splatfest);
    }

    @Override
    public int getItemCount() {
        return input.size();
    }

}