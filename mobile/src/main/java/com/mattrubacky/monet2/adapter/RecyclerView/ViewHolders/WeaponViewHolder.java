package com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.mattrubacky.monet2.helper.WeaponStats;
import com.squareup.picasso.Picasso;

/**
 * Created by mattr on 12/24/2017.
 */

public class WeaponViewHolder extends RecyclerView.ViewHolder{

    public ImageView weapon;
    public TextView name;
    private Context context;

    public WeaponViewHolder(LayoutInflater inflater, ViewGroup parent, Context context) {
        super(inflater.inflate(R.layout.item_weapon, parent, false));
        this.context = context;

        weapon = (ImageView) itemView.findViewById(R.id.WeaponImage);
        name = (TextView) itemView.findViewById(R.id.Name);
    }
    public void manageHolder(WeaponStats weaponStats){
        Typeface font = Typeface.createFromAsset(context.getAssets(),"Splatfont2.ttf");
        ImageHandler imageHandler = new ImageHandler();


        String url = "https://app.splatoon2.nintendo.net"+weaponStats.weapon.url;
        String location = weaponStats.weapon.name.toLowerCase().replace(" ","_");
        if(imageHandler.imageExists("weapon",location,context)){
            weapon.setImageBitmap(imageHandler.loadImage("weapon",location));
        }else{
            Picasso.with(context).load(url).into(weapon);
            imageHandler.downloadImage("weapon",location,url,context);
        }

        name.setText(weaponStats.weapon.name);
        name.setTypeface(font);
    }
}
