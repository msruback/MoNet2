package com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.ui.adapter.RecyclerView.CampaignStageAdapter;
import com.mattrubacky.monet2.data.deserialized.splatoon.CampaignStageInfo;
import com.mattrubacky.monet2.data.deserialized.splatoon.CampaignWeapon;

import java.util.ArrayList;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 12/24/2017.
 */

public class CampaignWorldViewHolder extends RecyclerView.ViewHolder{

    private TextView world;
    public RecyclerView list;
    private Context context;

    public CampaignWorldViewHolder(LayoutInflater inflater, ViewGroup parent,Context context) {
        super(inflater.inflate(R.layout.item_world, parent, false));
        this.context = context;

        world = itemView.findViewById(R.id.World);
        list = itemView.findViewById(R.id.List);
    }
    public void manageHolder(int worldNum, Activity activity, ArrayList<CampaignStageInfo> infos, Map<Integer, CampaignWeapon> weaponMap){
        Typeface fontTitle=Typeface.createFromAsset(context.getAssets(),"Paintball.otf");

        world.setTypeface(fontTitle);

        world.setText("World "+(worldNum));

        CampaignStageAdapter campaignStageAdapter = new CampaignStageAdapter(activity,infos,weaponMap,list);
        list.setAdapter(campaignStageAdapter);
        list.setLayoutManager(new LinearLayoutManager(context));
    }
}