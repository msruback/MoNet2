package com.mattrubacky.monet2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.Battle;
import com.mattrubacky.monet2.deserialized.NicknameIcon;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mattr on 11/16/2017.
 */

public class SplatfestBattleAdapter extends ArrayAdapter<Battle> {
    public SplatfestBattleAdapter(Context context, ArrayList<Battle> input) {
        super(context, 0, input);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_splatfest_battle, parent, false);
        }
        Battle battle = getItem(position);

        ImageView image = (ImageView) convertView.findViewById(R.id.WeaponImage);
        TextView battleNumber = (TextView) convertView.findViewById(R.id.BattleNumber);

        String name = "#"+battle.id;
        battleNumber.setText(name);

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
