package com.mattrubacky.monet2.adapter;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.*;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mattr on 10/21/2017.
 */

public class BattleListAdapter extends ArrayAdapter<Battle> {
    public BattleListAdapter(Context context, ArrayList<Battle> input) {
        super(context, 0, input);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_battle, parent, false);
        }
        Battle battle = getItem(position);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Splatfont2.ttf");

        RelativeLayout item = (RelativeLayout) convertView.findViewById(R.id.item);
        item.setClipToOutline(true);

        RelativeLayout fesMode = (RelativeLayout) convertView.findViewById(R.id.FesMode);
        RelativeLayout alpha = (RelativeLayout) convertView.findViewById(R.id.Alpha);
        RelativeLayout bravo = (RelativeLayout) convertView.findViewById(R.id.Bravo);
        RelativeLayout spots = (RelativeLayout) convertView.findViewById(R.id.Spots);

        TextView mode = (TextView) convertView.findViewById(R.id.mode);
        TextView map = (TextView) convertView.findViewById(R.id.map);
        TextView result = (TextView) convertView.findViewById(R.id.result);

        ImageView weapon = (ImageView) convertView.findViewById(R.id.weapon);
        ImageView type = (ImageView) convertView.findViewById(R.id.Type);

        map.setText(battle.stage.name);
        map.setTypeface(font);

        result.setText(battle.result.name);
        result.setTypeface(font);

        String modeString = "";
        switch (battle.rule.name) {
            case "Turf War":
                modeString = "TW";
                break;
            case "Rainmaker":
                modeString = "R";
                break;
            case "Splat Zones":
                modeString = "SZ";
                break;
            case "Tower Control":
                modeString = "TC";
                break;
        }
        mode.setText(modeString);
        mode.setTypeface(font);

        switch (battle.type) {
            case "regular":
                spots.setBackground(getContext().getResources().getDrawable(R.drawable.repeat_spots));
                type.setImageDrawable(getContext().getResources().getDrawable(R.drawable.battle_regular));
                item.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.turf));
                fesMode.setVisibility(View.GONE);
                type.setVisibility(View.VISIBLE);
                break;
            case "gachi":
                spots.setBackground(getContext().getResources().getDrawable(R.drawable.repeat_spots));
                type.setImageDrawable(getContext().getResources().getDrawable(R.drawable.battle_ranked));
                item.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.ranked));
                fesMode.setVisibility(View.GONE);
                type.setVisibility(View.VISIBLE);
                break;
            case "league":
                spots.setBackground(getContext().getResources().getDrawable(R.drawable.repeat_spots));
                type.setImageDrawable(getContext().getResources().getDrawable(R.drawable.battle_league));
                item.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.league));
                fesMode.setVisibility(View.GONE);
                type.setVisibility(View.VISIBLE);
                break;
            case "fes":
                spots.setBackground(getContext().getResources().getDrawable(R.drawable.repeat_spots_splatfest));
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

        ImageHandler imageHandler = new ImageHandler();
        String imageDirName = battle.user.user.weapon.name.toLowerCase().replace(" ", "_");

        if (imageHandler.imageExists("weapon", imageDirName, getContext())) {
            weapon.setImageBitmap(imageHandler.loadImage("weapon", imageDirName));
        } else {
            Picasso.with(getContext()).load(url).into(weapon);
            imageHandler.downloadImage("weapon", imageDirName, url, getContext());
        }

        return convertView;
    }
}
