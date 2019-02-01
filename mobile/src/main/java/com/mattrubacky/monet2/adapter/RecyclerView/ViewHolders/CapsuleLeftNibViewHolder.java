package com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.splatoon.RewardGear;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 11/10/2018.
 */

public class CapsuleLeftNibViewHolder extends RecyclerView.ViewHolder{
    private Context context;
    ImageView reward;
    TextView title;

    public CapsuleLeftNibViewHolder(LayoutInflater inflater, ViewGroup parent, Context context) {
        super(inflater.inflate(R.layout.nib_capsule_left, parent, false));

        reward = itemView.findViewById(R.id.Capsule);

        title = itemView.findViewById(R.id.Title);
        this.context = context;
    }

    public void manageHolder(RewardGear gear) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "Splatfont2.ttf");

        title.setTypeface(font);
        if(gear.gear==null){
            title.setText("Earned gear!");
            reward.setImageDrawable(context.getDrawable(R.drawable.capsule_gear));
        }
        title.setText("Earned "+gear.gear.name+"!");

        String url = "https://app.splatoon2.nintendo.net" + gear.gear.url;
        ImageHandler imageHandler = new ImageHandler();
        String imageDirName = gear.gear.name.toLowerCase().replace(" ", "_");
        if (imageHandler.imageExists("gear", imageDirName, context)) {
            reward.setImageBitmap(imageHandler.loadImage("gear", imageDirName));
        } else {
            Picasso.with(context).load(url).into(reward);
            imageHandler.downloadImage("gear", imageDirName, url, context);
        }
    }

    public void manageHolder(boolean isSuperBonus) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "Splatfont2.ttf");

        title.setTypeface(font);
        if(isSuperBonus){
            title.setText("Earned Super Bonus!");
        }else {
            title.setText("Earned Capsule!");
        }
        reward.setImageDrawable(context.getDrawable(R.drawable.capsule_mystery));
    }
}
