package com.mattrubacky.monet2.adapter.RecyclerView;


import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.BattleInfo;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.*;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mattr on 10/21/2017.
 *
 * This Adapter fills up the battle list in BattleListFragment
 */

public class BattleListAdapter extends RecyclerView.Adapter<BattleListAdapter.ViewHolder>{

    private ArrayList<Battle> input = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    RecyclerView listView;

    public BattleListAdapter(Context context, ArrayList<Battle> input,RecyclerView listView) {
        this.inflater = LayoutInflater.from(context);
        this.input = input;
        this.context = context;
        this.listView = listView;
    }
    @Override
    public BattleListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_battle, parent, false);
        BattleListAdapter.ViewHolder viewHolder = new BattleListAdapter.ViewHolder(view);
        view.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = listView.getChildAdapterPosition(v);
                Bundle bundle = new Bundle();
                Battle battle = input.get(itemPosition);
                bundle.putParcelable("battle",battle);
                if(battle.type.equals("fes")){
                    SplatnetSQLManager database = new SplatnetSQLManager(context);
                    Splatfest splatfest = database.selectSplatfest(battle.splatfestID).splatfest;
                    bundle.putParcelable("splatfest",splatfest);
                }
                Intent intent = new Intent(context,BattleInfo.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final BattleListAdapter.ViewHolder holder, final int position) {
        Typeface font = Typeface.createFromAsset(context.getAssets(),"Splatfont2.ttf");
        ImageHandler imageHandler = new ImageHandler();

        Battle battle = input.get(position);

        holder.item.setClipToOutline(true);

        holder.map.setText(battle.stage.name);
        holder.map.setTypeface(font);

        if(battle.result.key.equals("victory")){
            holder.result.setText(context.getResources().getString(R.string.victory));
        }else{
            holder.result.setText(context.getResources().getString(R.string.defeat));
        }
        holder.result.setTypeface(font);

        String modeString = "";
        switch (battle.rule.key) {
            case "turf_war":
                modeString = context.getResources().getString(R.string.turfWarShort);
                break;
            case "rainmaker":
                modeString = context.getResources().getString(R.string.rainmakerShort);
                break;
            case "splat_zones":
                modeString = context.getResources().getString(R.string.splatzoneShort);
                break;
            case "tower_control":
                modeString = context.getResources().getString(R.string.towerControlShort);
                break;
            case "clam_blitz":
                modeString = context.getResources().getString(R.string.clamBlitzShort);
                break;
        }
        holder.mode.setText(modeString);
        holder.mode.setTypeface(font);

        switch (battle.type) {
            case "regular":
                holder.spots.setBackground(context.getResources().getDrawable(R.drawable.repeat_spots));
                holder.type.setImageDrawable(context.getResources().getDrawable(R.drawable.battle_regular));
                holder.item.setBackgroundTintList(context.getResources().getColorStateList(R.color.turf));
                holder.fesMode.setVisibility(View.GONE);
                holder.type.setVisibility(View.VISIBLE);
                break;
            case "gachi":
                holder.spots.setBackground(context.getResources().getDrawable(R.drawable.repeat_spots));
                holder.type.setImageDrawable(context.getResources().getDrawable(R.drawable.battle_ranked));
                holder.item.setBackgroundTintList(context.getResources().getColorStateList(R.color.ranked));
                holder.fesMode.setVisibility(View.GONE);
                holder.type.setVisibility(View.VISIBLE);
                break;
            case "league":
                holder.spots.setBackground(context.getResources().getDrawable(R.drawable.repeat_spots));
                holder.type.setImageDrawable(context.getResources().getDrawable(R.drawable.battle_league));
                holder.item.setBackgroundTintList(context.getResources().getColorStateList(R.color.league));
                holder.fesMode.setVisibility(View.GONE);
                holder.type.setVisibility(View.VISIBLE);
                break;
            case "fes":
                holder.spots.setBackground(context.getResources().getDrawable(R.drawable.repeat_spots_splatfest));
                holder.mode.setText("SP");
                holder.type.setVisibility(View.GONE);
                holder.fesMode.setVisibility(View.VISIBLE);

                if (battle.myTheme.key.equals("alpha")) {
                    holder.alpha.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(battle.myTheme.color.getColor())));
                    holder.bravo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(battle.otherTheme.color.getColor())));
                    holder.spots.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(battle.myTheme.color.getColor())));
                    holder.item.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(battle.otherTheme.color.getColor())));
                } else {
                    holder.alpha.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(battle.otherTheme.color.getColor())));
                    holder.bravo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(battle.myTheme.color.getColor())));
                    holder.spots.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(battle.otherTheme.color.getColor())));
                    holder.item.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(battle.myTheme.color.getColor())));
                }
                break;
        }
        String url = "https://app.splatoon2.nintendo.net" + battle.user.user.weapon.url;

        String imageDirName = battle.user.user.weapon.name.toLowerCase().replace(" ", "_");

        if (imageHandler.imageExists("weapon", imageDirName, context)) {
            holder.weapon.setImageBitmap(imageHandler.loadImage("weapon", imageDirName));
        } else {
            Picasso.with(context).load(url).into(holder.weapon);
            imageHandler.downloadImage("weapon", imageDirName, url, context);
        }

    }

    @Override
    public int getItemCount() {
        return input.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout item,fesMode,alpha,bravo,spots;
        ImageView weapon,type;
        TextView mode,map,result;


        public ViewHolder(View itemView) {
            super(itemView);

            item = (RelativeLayout) itemView.findViewById(R.id.item);

            fesMode = (RelativeLayout) itemView.findViewById(R.id.FesMode);
            alpha = (RelativeLayout) itemView.findViewById(R.id.Alpha);
            bravo = (RelativeLayout) itemView.findViewById(R.id.Bravo);
            spots = (RelativeLayout) itemView.findViewById(R.id.Spots);

            mode = (TextView) itemView.findViewById(R.id.mode);
            map = (TextView) itemView.findViewById(R.id.map);
            result = (TextView) itemView.findViewById(R.id.result);

            weapon = (ImageView) itemView.findViewById(R.id.weapon);
            type = (ImageView) itemView.findViewById(R.id.Type);
        }

    }

}