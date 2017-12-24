package com.mattrubacky.monet2.adapter.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders.CampaignStageViewHolder;
import com.mattrubacky.monet2.deserialized.CampaignStageInfo;
import com.mattrubacky.monet2.deserialized.CampaignWeapon;
import com.mattrubacky.monet2.dialog.CampaignStageStatsDialog;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by mattr on 12/10/2017.
 */

public class CampaignStageAdapter extends RecyclerView.Adapter<CampaignStageViewHolder> {

    private ArrayList<CampaignStageInfo> input;
    private LayoutInflater inflater;
    private Context context;
    private Activity activity;
    private Map<Integer, CampaignWeapon> weaponMap;
    private RecyclerView stage;

    public CampaignStageAdapter(Activity activity, ArrayList<CampaignStageInfo> input,Map<Integer, CampaignWeapon> weaponMap,RecyclerView stage) {
        this.inflater = LayoutInflater.from(activity);
        this.input = input;
        this.activity = activity;
        this.context = activity;
        this.weaponMap = weaponMap;
        this.stage = stage;

    }

    @Override
    public CampaignStageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CampaignStageViewHolder viewHolder = new CampaignStageViewHolder(inflater,parent,context);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = stage.indexOfChild(v);

                ArrayList<CampaignWeapon> weapons = new ArrayList<>();
                Integer[] keys = new Integer[2];
                keys = weaponMap.keySet().toArray(keys);
                for(int i=0;i<keys.length;i++){
                    weapons.add(weaponMap.get(keys[i]));
                }

                CampaignStageStatsDialog dialog = new CampaignStageStatsDialog(activity,input.get(itemPosition),weapons);
                dialog.show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CampaignStageViewHolder holder, final int position) {
        CampaignStageInfo info= input.get(position);
        holder.manageHolder(info,weaponMap);

    }

    @Override
    public int getItemCount() {
        return input.size();
    }


}