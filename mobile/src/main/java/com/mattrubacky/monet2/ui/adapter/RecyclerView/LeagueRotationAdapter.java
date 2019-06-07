package com.mattrubacky.monet2.ui.adapter.RecyclerView;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders.CompetitiveTimePeriodViewHolder;
import com.mattrubacky.monet2.data.deserialized_entities.TimePeriod;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mattr on 1/14/2018.
 */

public class LeagueRotationAdapter extends RecyclerView.Adapter<CompetitiveTimePeriodViewHolder>{

    private List<TimePeriod> input;
    private LayoutInflater inflater;
    private Context context;

    public LeagueRotationAdapter(Context context, List<TimePeriod> input) {
        this.inflater = LayoutInflater.from(context);
        this.input = input;
        this.context = context;
    }
    @NonNull
    @Override
    public CompetitiveTimePeriodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CompetitiveTimePeriodViewHolder(inflater,parent,context);
    }

    @Override
    public void onBindViewHolder(@NonNull final CompetitiveTimePeriodViewHolder holder, final int position) {
        TimePeriod timePeriod = input.get(position);
        holder.manageHolder(timePeriod);
        holder.mode.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.leagueAccent)));
    }

    @Override
    public int getItemCount() {
        return input.size();
    }

}