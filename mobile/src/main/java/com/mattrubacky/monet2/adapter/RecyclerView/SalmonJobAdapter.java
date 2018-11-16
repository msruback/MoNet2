package com.mattrubacky.monet2.adapter.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders.SalmonJobViewHolder;
import com.mattrubacky.monet2.deserialized.splatoon.CoopResult;
import com.mattrubacky.monet2.deserialized.splatoon.RewardGear;

import java.util.ArrayList;

/**
 * Created by mattr on 11/6/2018.
 */

public class SalmonJobAdapter extends RecyclerView.Adapter<SalmonJobViewHolder>{
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<CoopResult> results;
    private RewardGear rewardGear;
    private int money;

    public SalmonJobAdapter(Context context, ArrayList<CoopResult> results, RewardGear rewardGear){
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.results = results;
        this.rewardGear = rewardGear;
        money = 0;
    }

    @Override
    public SalmonJobViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SalmonJobViewHolder(inflater,parent,context);
    }

    @Override
    public void onBindViewHolder(SalmonJobViewHolder holder, int position) {
        CoopResult result = results.get(position);

        holder.manageHolder(result,rewardGear,money);
        money += result.money;
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}
