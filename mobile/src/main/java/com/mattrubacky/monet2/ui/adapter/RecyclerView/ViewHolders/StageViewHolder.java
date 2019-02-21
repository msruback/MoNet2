package com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.backend.ImageHandler;
import com.mattrubacky.monet2.data.stats.StageStats;
import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 12/24/2017.
 */

public class StageViewHolder extends RecyclerView.ViewHolder{

    public ImageView stage;
    public TextView name;
    private Context context;

    public StageViewHolder(LayoutInflater inflater, ViewGroup parent, Context context) {
        super(inflater.inflate(R.layout.item_stage, parent, false));
        this.context = context;

        stage = itemView.findViewById(R.id.StageImage);
        name = itemView.findViewById(R.id.Name);
    }
    public void manageHolder(StageStats stageStats){
        Typeface font = Typeface.createFromAsset(context.getAssets(),"Splatfont2.ttf");
        ImageHandler imageHandler = new ImageHandler();


        String url = "https://app.splatoon2.nintendo.net"+stageStats.stage.url;
        String location = stageStats.stage.name.toLowerCase().replace(" ","_");
        if(imageHandler.imageExists("stage",location,context)){
            stage.setImageBitmap(imageHandler.loadImage("stage",location));
        }else{
            Picasso.with(context).load(url).resize(1280,720).into(stage);
            imageHandler.downloadImage("stage",location,url,context);
        }

        name.setText(stageStats.stage.name);
        name.setTypeface(font);
    }
}
