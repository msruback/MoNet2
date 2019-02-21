package com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.data.stats.GearStats;
import com.mattrubacky.monet2.backend.ImageHandler;
import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 12/24/2017.
 */

public class GearViewHolder extends RecyclerView.ViewHolder{

    private RelativeLayout hook;
    private RelativeLayout card;
    private ImageView gear;
    private TextView name;
    private Context context;

    public GearViewHolder(LayoutInflater inflater, ViewGroup parent, Context context) {
        super(inflater.inflate(R.layout.item_gear, parent, false));

        this.context = context;

        hook = itemView.findViewById(R.id.hook);
        card = itemView.findViewById(R.id.card);
        gear = itemView.findViewById(R.id.GearImage);
        name = itemView.findViewById(R.id.Name);
    }

    public void manageHolder(GearStats gearStats){
        Typeface font = Typeface.createFromAsset(context.getAssets(),"Splatfont2.ttf");
        ImageHandler imageHandler = new ImageHandler();

        String url = "https://app.splatoon2.nintendo.net"+ gearStats.gear.url;
        String location = gearStats.gear.name.toLowerCase().replace(" ","_");
        if(imageHandler.imageExists("gear",location,context)){
            gear.setImageBitmap(imageHandler.loadImage("weapon",location));
        }else{
            Picasso.with(context).load(url).into(gear);
            imageHandler.downloadImage("gear",location,url,context);
        }

        name.setText(gearStats.gear.name);
        name.setTypeface(font);

        switch(gearStats.gear.kind){
            case "head":
                hook.setBackgroundTintList(context.getResources().getColorStateList(R.color.head));
                card.setBackgroundTintList(context.getResources().getColorStateList(R.color.head));
                break;
            case "clothes":
                hook.setBackgroundTintList(context.getResources().getColorStateList(R.color.clothes));
                card.setBackgroundTintList(context.getResources().getColorStateList(R.color.clothes));
                break;
            case "shoes":
                hook.setBackgroundTintList(context.getResources().getColorStateList(R.color.shoes));
                card.setBackgroundTintList(context.getResources().getColorStateList(R.color.shoes));
                break;
        }
    }
}
