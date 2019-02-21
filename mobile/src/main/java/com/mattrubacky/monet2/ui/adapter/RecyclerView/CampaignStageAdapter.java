package com.mattrubacky.monet2.ui.adapter.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders.CampaignStageViewHolder;
import com.mattrubacky.monet2.data.deserialized.splatoon.CampaignStageInfo;
import com.mattrubacky.monet2.data.deserialized.splatoon.CampaignWeapon;
import com.mattrubacky.monet2.ui.dialog.CampaignStageStatsDialog;

import java.util.ArrayList;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

    @NonNull
    @Override
    public CampaignStageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CampaignStageViewHolder viewHolder = new CampaignStageViewHolder(inflater,parent,context);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = stage.indexOfChild(v);

                ArrayList<CampaignWeapon> weapons = new ArrayList<>(weaponMap.values());

                CampaignStageStatsDialog dialog = new CampaignStageStatsDialog(activity,input.get(itemPosition),weapons);
                dialog.show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CampaignStageViewHolder holder, final int position) {
        CampaignStageInfo info= input.get(position);
        holder.manageHolder(info,weaponMap);

    }

    @Override
    public int getItemCount() {
        return input.size();
    }


}