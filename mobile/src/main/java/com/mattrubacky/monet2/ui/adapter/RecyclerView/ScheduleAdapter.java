package com.mattrubacky.monet2.ui.adapter.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders.LeagueCardViewHolder;
import com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders.RankedCardViewHolder;
import com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders.RegularCardViewHolder;
import com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders.SalmonRunCardViewHolder;
import com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders.SplatfestCardViewHolder;
import com.mattrubacky.monet2.data.deserialized.splatoon.Gear;
import com.mattrubacky.monet2.data.deserialized.splatoon.SalmonSchedule;
import com.mattrubacky.monet2.data.deserialized.splatoon.Schedules;
import com.mattrubacky.monet2.data.deserialized.splatoon.Splatfest;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 1/16/2018.
 */

public class ScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private LayoutInflater inflater;
    private Context context;
    private Schedules schedules;
    private SalmonSchedule salmonSchedule;
    private Gear rewardGear;
    private Splatfest currentSplatfest;
    public ArrayList<String> rotation;

    public ScheduleAdapter(Context context, Schedules schedules, SalmonSchedule salmonSchedule, Gear rewardGear,Splatfest currentSplatfest) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.schedules = schedules;
        this.salmonSchedule = salmonSchedule;
        this.rewardGear = rewardGear;
        this.currentSplatfest = currentSplatfest;
        rotation = new ArrayList<>();
        if(schedules!=null) {
            if (schedules.regular!=null&&schedules.regular.size() > 0) {
                rotation.add("regular");
            }
            if (schedules.ranked!=null&&schedules.ranked.size() > 0) {
                rotation.add("ranked");
            }
            if (schedules.league!=null&&schedules.league.size() > 0) {
                rotation.add("league");
            }
        }
        if(currentSplatfest!=null) {
            if (currentSplatfest!=null && schedules.splatfest.size() > 0) {
                if (schedules.regular.size() == 0 || currentSplatfest.times.start < schedules.regular.get(0).start) {
                    rotation.add(0, "fes");
                } else {
                    rotation.add("fes");
                }
            }
        }
        if(salmonSchedule!=null) {
            if (salmonSchedule.details.size() > 0) {
                rotation.add("salmon");
            }
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case 0:
                return new RegularCardViewHolder(inflater,parent,context);
            case 1:
                return new RankedCardViewHolder(inflater,parent,context);
            case 2:
                return new LeagueCardViewHolder(inflater,parent,context);
            case 3:
                return new SplatfestCardViewHolder(inflater,parent,context);
            case 4:
                return new SalmonRunCardViewHolder(inflater,parent,context);
            default:
                return new SalmonRunCardViewHolder(inflater,parent,context);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holderAb, final int position) {
        if(holderAb.getItemViewType()==0) {
            RegularCardViewHolder holder = (RegularCardViewHolder) holderAb;
            holder.manageHolder(schedules.regular);
        }else if(holderAb.getItemViewType()==1) {
            RankedCardViewHolder holder = (RankedCardViewHolder) holderAb;
            holder.manageHolder(schedules.ranked);
        }else if(holderAb.getItemViewType()==2){
            LeagueCardViewHolder holder = (LeagueCardViewHolder) holderAb;
            holder.manageHolder(schedules.league);
        }else if(holderAb.getItemViewType()==3){
            SplatfestCardViewHolder holder = (SplatfestCardViewHolder) holderAb;
            holder.manageHolder(schedules.splatfest,currentSplatfest);
        }else{
            SalmonRunCardViewHolder holder = (SalmonRunCardViewHolder) holderAb;
            holder.manageHolder(salmonSchedule,rewardGear);
        }

    }

    @Override
    public int getItemCount() {
        return rotation.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch(rotation.get(position)){
            case "regular":
                return 0;
            case "ranked":
                return 1;
            case "league":
                return 2;
            case "fes":
                return 3;
            case "salmon":
                return 4;
            default:
                return -1;
        }
    }

}