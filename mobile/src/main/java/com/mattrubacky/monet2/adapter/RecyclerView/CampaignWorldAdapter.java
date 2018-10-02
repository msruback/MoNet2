package com.mattrubacky.monet2.adapter.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders.CampaignWorldViewHolder;
import com.mattrubacky.monet2.deserialized.splatoon.CampaignStageInfo;
import com.mattrubacky.monet2.deserialized.splatoon.CampaignWeapon;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by mattr on 12/10/2017.
 */

public class CampaignWorldAdapter extends RecyclerView.Adapter<CampaignWorldViewHolder> {

    private ArrayList<ArrayList<CampaignStageInfo>> input;
    private LayoutInflater inflater;
    private Context context;
    private Activity activity;
    private Map<Integer, CampaignWeapon> weaponMap;

    public CampaignWorldAdapter(Activity activity, ArrayList<ArrayList<CampaignStageInfo>> input, Map<Integer, CampaignWeapon> weaponMap ) {
        this.inflater = LayoutInflater.from(activity);
        this.input = input;
        this.activity = activity;
        this.context = activity;
        this.weaponMap = weaponMap;

    }

    @Override
    public CampaignWorldViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CampaignWorldViewHolder viewHolder = new CampaignWorldViewHolder(inflater,parent,context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CampaignWorldViewHolder holder, final int position) {
        final ArrayList<CampaignStageInfo> infos = input.get(position);

        holder.manageHolder(position+1,activity,infos,weaponMap);
    }

    @Override
    public int getItemCount() {
        return input.size();
    }

}