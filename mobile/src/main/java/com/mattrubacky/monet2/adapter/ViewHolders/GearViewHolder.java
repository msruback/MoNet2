package com.mattrubacky.monet2.adapter.ViewHolders;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.helper.ClosetHanger;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

/**
 * Created by mattr on 12/24/2017.
 */

public class GearViewHolder extends RecyclerView.ViewHolder{

    public RelativeLayout hook,card;
    public ImageView gear;
    public TextView name;
    private Context context;

    public GearViewHolder(LayoutInflater inflater, ViewGroup parent, Context context) {
        super(inflater.inflate(R.layout.item_gear, parent, false));

        this.context = context;

        hook = (RelativeLayout) itemView.findViewById(R.id.hook);
        card = (RelativeLayout) itemView.findViewById(R.id.card);
        gear = (ImageView) itemView.findViewById(R.id.GearImage);
        name = (TextView) itemView.findViewById(R.id.Name);
    }

    public void manageHolder(ClosetHanger closetHanger){
        Typeface font = Typeface.createFromAsset(context.getAssets(),"Splatfont2.ttf");
        ImageHandler imageHandler = new ImageHandler();

        String url = "https://app.splatoon2.nintendo.net"+closetHanger.gear.url;
        String location = closetHanger.gear.name.toLowerCase().replace(" ","_");
        if(imageHandler.imageExists("gear",location,context)){
            gear.setImageBitmap(imageHandler.loadImage("weapon",location));
        }else{
            Picasso.with(context).load(url).into(gear);
            imageHandler.downloadImage("gear",location,url,context);
        }

        name.setText(closetHanger.gear.name);
        name.setTypeface(font);

        switch(closetHanger.gear.kind){
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
