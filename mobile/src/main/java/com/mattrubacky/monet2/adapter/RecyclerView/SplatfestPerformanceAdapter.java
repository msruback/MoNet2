package com.mattrubacky.monet2.adapter.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders.SplatfestPerformanceViewHolder;
import com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders.SplatfestResultViewHolder;
import com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders.StageViewHolder;
import com.mattrubacky.monet2.deserialized.Splatfest;
import com.mattrubacky.monet2.deserialized.SplatfestResult;
import com.mattrubacky.monet2.helper.SplatfestStats;
import com.mattrubacky.monet2.helper.StageStats;

import java.util.ArrayList;

/**
 * Created by mattr on 12/24/2017.
 */

public class SplatfestPerformanceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private LayoutInflater inflater;
    private Context context;
    private Splatfest splatfest;
    private SplatfestResult result;
    private SplatfestStats stats;

    public SplatfestPerformanceAdapter(Context context, Splatfest splatfest, SplatfestResult result, SplatfestStats stats) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.splatfest = splatfest;
        this.result = result;
        this.stats = stats;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==0){
            return new SplatfestPerformanceViewHolder(inflater,parent,context);
        }else{
            return new SplatfestResultViewHolder(inflater,parent,context);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holderAb, final int position) {
        int type =holderAb.getItemViewType();
        if(type==0){
            SplatfestPerformanceViewHolder holder = (SplatfestPerformanceViewHolder) holderAb;
            holder.manageHolder(stats,splatfest);
        }else{
            SplatfestResultViewHolder holder = (SplatfestResultViewHolder) holderAb;
            holder.manageHolder(splatfest,result);
        }
    }

    @Override
    public int getItemCount() {
        if(result.participants.alpha!=0&&stats!=null){
            return 2;
        }else if(result.participants.alpha==0&&stats==null){
            return 0;
        }
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            if(result.participants.alpha!=0){
                return 1;
            }else{
                return 0;
            }
        }
        return 1;
    }
}