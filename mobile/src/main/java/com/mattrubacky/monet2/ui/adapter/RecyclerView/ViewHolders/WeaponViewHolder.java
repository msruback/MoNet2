package com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.data.deserialized_entities.Weapon;
import com.mattrubacky.monet2.backend.ImageHandler;
import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 12/24/2017.
 */

public class WeaponViewHolder extends RecyclerView.ViewHolder{

    private ImageView weaponImage;
    private TextView name;
    private Context context;

    public WeaponViewHolder(LayoutInflater inflater, ViewGroup parent, Context context) {
        super(inflater.inflate(R.layout.item_weapon, parent, false));
        this.context = context;

        weaponImage = itemView.findViewById(R.id.WeaponImage);
        name = itemView.findViewById(R.id.Name);
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
