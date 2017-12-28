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
import com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders.CampaignWeaponViewHolder;
import com.mattrubacky.monet2.deserialized.CampaignStageInfo;
import com.mattrubacky.monet2.deserialized.CampaignWeapon;
import com.mattrubacky.monet2.dialog.CampaignWeaponStatsDialog;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by mattr on 12/10/2017.
 */

public class CampaignWeaponAdapter extends RecyclerView.Adapter<CampaignWeaponViewHolder> {

    private ArrayList<CampaignWeapon> input;
    private ArrayList<CampaignStageInfo> infos;
    private LayoutInflater inflater;
    private Context context;
    private Activity activity;

    public CampaignWeaponAdapter(Activity activity, ArrayList<CampaignWeapon> input, ArrayList<CampaignStageInfo> infos ) {
        this.inflater = LayoutInflater.from(activity);
        this.input = input;
        this.activity = activity;
        this.context = activity;
        this.infos = infos;

    }

    @Override
    public CampaignWeaponViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CampaignWeaponViewHolder viewHolder = new CampaignWeaponViewHolder(inflater,parent,context);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerView weapons = (RecyclerView) activity.findViewById(R.id.WeaponList);
                int itemPosition = weapons.indexOfChild(v);
                CampaignWeaponStatsDialog dialog = new CampaignWeaponStatsDialog(activity,input.get(itemPosition),infos);
                dialog.show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CampaignWeaponViewHolder holder, final int position) {

        CampaignWeapon weapon = input.get(position);
        holder.manageHolder(weapon,infos);

    }

    @Override
    public int getItemCount() {
        return input.size();
    }

}