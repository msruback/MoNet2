package com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.Splatfest;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

/**
 * Created by mattr on 12/24/2017.
 */

public class SplatfestViewHolder extends RecyclerView.ViewHolder{

    public ImageView panel;
    public TextView name;
    public RelativeLayout alpha,bravo;
    private Context context;

    public SplatfestViewHolder(LayoutInflater inflater, ViewGroup parent, Context context) {
        super(inflater.inflate(R.layout.item_stage, parent, false));
        this.context = context;

        alpha = (RelativeLayout) itemView.findViewById(R.id.alpha);
        bravo = (RelativeLayout) itemView.findViewById(R.id.bravo);
        panel = (ImageView) itemView.findViewById(R.id.StageImage);
        name = (TextView) itemView.findViewById(R.id.Name);
    }
    public void manageHolder(Splatfest splatfest){
        Typeface font = Typeface.createFromAsset(context.getAssets(),"Splatfont2.ttf");
        ImageHandler imageHandler = new ImageHandler();


        String location = String.valueOf(splatfest.id);
        String url = "https://app.splatoon2.nintendo.net"+splatfest.images.panel;
        if(imageHandler.imageExists("splatfest",location,context)){
            panel.setImageBitmap(imageHandler.loadImage("splatfest",location));
        }else{
            Picasso.with(context).load(url).into(panel);
            imageHandler.downloadImage("splatfest",location,url,context);
        }

        name.setText(splatfest.names.alpha + " VS. "+splatfest.names.bravo);
        name.setTypeface(font);

        alpha.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.alpha.getColor())));
        bravo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));
    }
}
