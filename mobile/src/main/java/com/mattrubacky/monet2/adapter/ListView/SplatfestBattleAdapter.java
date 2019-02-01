package com.mattrubacky.monet2.adapter.ListView;

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
import com.mattrubacky.monet2.deserialized.splatoon.Battle;
import com.mattrubacky.monet2.deserialized.splatoon.SplatfestColor;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mattr on 11/16/2017.
 */

public class SplatfestBattleAdapter extends ArrayAdapter<Battle> {
    SplatfestColor color;
    public SplatfestBattleAdapter(Context context, ArrayList<Battle> input, SplatfestColor color) {
        super(context, 0, input);
        this.color = color;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_splatfest_battle, parent, false);
        }
        Battle battle = getItem(position);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(getContext().getAssets(), "Paintball.otf");

        RelativeLayout weaponLayout = convertView.findViewById(R.id.weaponLayout);

        ImageView image = convertView.findViewById(R.id.WeaponImage);
        TextView battleNumber = convertView.findViewById(R.id.BattleNumber);
        TextView stage = convertView.findViewById(R.id.Stage);

        weaponLayout.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color.getColor())));

        battleNumber.setTypeface(font);
        stage.setTypeface(font);

        String name = "#"+battle.id;
        battleNumber.setText(name);
        stage.setText(battle.stage.name);

        String url = "https://app.splatoon2.nintendo.net"+battle.user.user.weapon.url;

        ImageHandler imageHandler = new ImageHandler();
        String imageDirName = battle.user.user.weapon.name.toLowerCase().replace(" ", "_");
        if(imageHandler.imageExists("weapon",imageDirName,getContext())){
            image.setImageBitmap(imageHandler.loadImage("weapon",imageDirName));
        }else{
            Picasso.with(getContext()).load(url).into(image);
            imageHandler.downloadImage("weapon",imageDirName,url,getContext());
        }

        return convertView;
    }
}
