package com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.tables.Weapon;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

/**
 * Created by mattr on 12/24/2017.
 */

public class WeaponViewHolder extends RecyclerView.ViewHolder{

    public ImageView weaponImage;
    public TextView name;
    private Context context;

    public WeaponViewHolder(LayoutInflater inflater, ViewGroup parent, Context context) {
        super(inflater.inflate(R.layout.item_weapon, parent, false));
        this.context = context;

        weaponImage = (ImageView) itemView.findViewById(R.id.WeaponImage);
        name = (TextView) itemView.findViewById(R.id.Name);
    }
    public void manageHolder(Weapon weapon){
        Typeface font = Typeface.createFromAsset(context.getAssets(),"Splatfont2.ttf");
        ImageHandler imageHandler = new ImageHandler();


        String url = "https://app.splatoon2.nintendo.net"+weapon.url;
        String location = weapon.name.toLowerCase().replace(" ","_");
        if(imageHandler.imageExists("weapon",location,context)){
            weaponImage.setImageBitmap(imageHandler.loadImage("weapon",location));
        }else{
            Picasso.with(context).load(url).into(weaponImage);
            imageHandler.downloadImage("weapon",location,url,context);
        }

        name.setText(weapon.name);
        name.setTypeface(font);
    }
}
