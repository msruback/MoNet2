package com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.adapter.RecyclerView.CampaignStageAdapter;
import com.mattrubacky.monet2.deserialized.CampaignStageInfo;
import com.mattrubacky.monet2.deserialized.CampaignWeapon;
import com.mattrubacky.monet2.helper.ImageHandler;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by mattr on 12/24/2017.
 */

public class CampaignWorldViewHolder extends RecyclerView.ViewHolder{

    public TextView world;
    public RecyclerView list;
    private Context context;

    public CampaignWorldViewHolder(LayoutInflater inflater, ViewGroup parent,Context context) {
        super(inflater.inflate(R.layout.item_world, parent, false));
        this.context = context;

        world = (TextView) itemView.findViewById(R.id.World);
        list = (RecyclerView) itemView.findViewById(R.id.List);
    }
    public void manageHolder(int worldNum, Activity activity, ArrayList<CampaignStageInfo> infos, Map<Integer, CampaignWeapon> weaponMap){
        Typeface font = Typeface.createFromAsset(context.getAssets(), "Splatfont2.ttf");
        Typeface fontTitle=Typeface.createFromAsset(context.getAssets(),"Paintball.otf");
        ImageHandler imageHandler = new ImageHandler();

        world.setTypeface(fontTitle);

        world.setText("World "+(worldNum));

        CampaignStageAdapter campaignStageAdapter = new CampaignStageAdapter(activity,infos,weaponMap,list);
        list.setAdapter(campaignStageAdapter);
        list.setLayoutManager(new LinearLayoutManager(context));
    }
}