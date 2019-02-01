package com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.splatoon.Battle;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 12/24/2017.
 */

public class BattleViewHolder extends RecyclerView.ViewHolder{
    private RelativeLayout item,fesMode,alpha,bravo,spots;

    private ImageView weapon,type;
    private TextView mode,map,result;
    private Context context;


    public BattleViewHolder(LayoutInflater inflater, ViewGroup parent, Context context) {
        super(inflater.inflate(R.layout.item_battle, parent, false));

        this.context = context;

        item = itemView.findViewById(R.id.item);

        fesMode = itemView.findViewById(R.id.FesMode);
        alpha = itemView.findViewById(R.id.Alpha);
        bravo = itemView.findViewById(R.id.Bravo);
        spots = itemView.findViewById(R.id.Spots);

        mode = itemView.findViewById(R.id.mode);
        map = itemView.findViewById(R.id.map);
        result = itemView.findViewById(R.id.result);

        weapon = itemView.findViewById(R.id.weapon);
        type = itemView.findViewById(R.id.Type);
    }

    public void manageHolder(Battle battle){

        Typeface font = Typeface.createFromAsset(context.getAssets(),"Splatfont2.ttf");
        ImageHandler imageHandler = new ImageHandler();

        item.setClipToOutline(true);

        map.setText(battle.stage.name);
        map.setTypeface(font);

        if(battle.result.key.equals("victory")){
            result.setText(context.getResources().getString(R.string.victory));
        }else{
            result.setText(context.getResources().getString(R.string.defeat));
        }
        result.setTypeface(font);

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
        mode.setText(modeString);
        mode.setTypeface(font);

        switch (battle.type) {
            case "regular":
                spots.setBackground(context.getResources().getDrawable(R.drawable.repeat_spots));
                type.setImageDrawable(context.getResources().getDrawable(R.drawable.battle_regular));
                item.setBackgroundTintList(context.getResources().getColorStateList(R.color.turf));
                fesMode.setVisibility(View.GONE);
                type.setVisibility(View.VISIBLE);
                break;
            case "gachi":
                spots.setBackground(context.getResources().getDrawable(R.drawable.repeat_spots));
                type.setImageDrawable(context.getResources().getDrawable(R.drawable.battle_ranked));
                item.setBackgroundTintList(context.getResources().getColorStateList(R.color.ranked));
                fesMode.setVisibility(View.GONE);
                type.setVisibility(View.VISIBLE);
                break;
            case "league":
                spots.setBackground(context.getResources().getDrawable(R.drawable.repeat_spots));
                type.setImageDrawable(context.getResources().getDrawable(R.drawable.battle_league));
                item.setBackgroundTintList(context.getResources().getColorStateList(R.color.league));
                fesMode.setVisibility(View.GONE);
                type.setVisibility(View.VISIBLE);
                break;
            case "fes":
                spots.setBackground(context.getResources().getDrawable(R.drawable.repeat_spots_splatfest));
                mode.setText("SP");
                type.setVisibility(View.GONE);
                fesMode.setVisibility(View.VISIBLE);

                if (battle.myTheme.key.equals("alpha")) {
                    alpha.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(battle.myTheme.color.getColor())));
                    bravo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(battle.otherTheme.color.getColor())));
                    spots.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(battle.myTheme.color.getColor())));
                    item.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(battle.otherTheme.color.getColor())));
                } else {
                    alpha.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(battle.otherTheme.color.getColor())));
                    bravo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(battle.myTheme.color.getColor())));
                    spots.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(battle.otherTheme.color.getColor())));
                    item.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(battle.myTheme.color.getColor())));
                }
                break;
        }
        String url = "https://app.splatoon2.nintendo.net" + battle.user.user.weapon.url;

        String imageDirName = battle.user.user.weapon.name.toLowerCase().replace(" ", "_");

        if (imageHandler.imageExists("weapon", imageDirName, context)) {
            weapon.setImageBitmap(imageHandler.loadImage("weapon", imageDirName));
        } else {
            Picasso.with(context).load(url).into(weapon);
            imageHandler.downloadImage("weapon", imageDirName, url, context);
        }
    }

}
