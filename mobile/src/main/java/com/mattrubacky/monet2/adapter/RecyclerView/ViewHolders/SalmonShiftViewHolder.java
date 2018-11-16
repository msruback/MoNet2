package com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.splatoon.CoopResult;
import com.mattrubacky.monet2.deserialized.splatoon.SalmonRunDetail;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

/**
 * Created by mattr on 11/6/2018.
 */

public class SalmonShiftViewHolder extends RecyclerView.ViewHolder{
    private Context context;
    ImageView stageImage,weapon1Image,weapon2Image,weapon3Image,weapon4Image;
    TextView stageName,time;

    public SalmonShiftViewHolder(LayoutInflater inflater, ViewGroup parent, Context context) {
        super(inflater.inflate(R.layout.item_shift, parent, false));

        this.context = context;
        stageImage = (ImageView) itemView.findViewById(R.id.StageImage);
        weapon1Image = (ImageView) itemView.findViewById(R.id.Weapon1Image);
        weapon2Image = (ImageView) itemView.findViewById(R.id.Weapon2Image);
        weapon3Image = (ImageView) itemView.findViewById(R.id.Weapon3Image);
        weapon4Image = (ImageView) itemView.findViewById(R.id.Weapon4Image);

        stageName = (TextView) itemView.findViewById(R.id.StageName);
        time = (TextView) itemView.findViewById(R.id.Time);

    }

    public void manageHolder(SalmonRunDetail shift) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "Splatfont2.ttf");

        stageName.setTypeface(font);
        time.setTypeface(font);


        SimpleDateFormat sdf = new SimpleDateFormat("EEE M/d h a");
        String startText = sdf.format(shift.start*1000);
        String endText = sdf.format(shift.end*1000);
        time.setText(startText + " to " + endText);

        stageName.setText(shift.stage.name);

        ImageHandler imageHandler = new ImageHandler();
        String imageDirName = shift.stage.name.toLowerCase().replace(" ","_");
        String url = "https://app.splatoon2.nintendo.net"+shift.stage.url;

        if(imageHandler.imageExists("salmon_stage",imageDirName,context)){
            stageImage.setImageBitmap(imageHandler.loadImage("salmon_stage",imageDirName));
        }else{
            Picasso.with(context).load(url).resize(1280,720).into(stageImage);
            imageHandler.downloadImage("salmon_stage",imageDirName,url,context);
        }

        if(shift.weapons.get(0).id>=0){
            imageDirName = shift.weapons.get(0).weapon.name.toLowerCase().replace(" ","_");
            url = "https://app.splatoon2.nintendo.net"+shift.weapons.get(0).weapon.url;

            if(imageHandler.imageExists("weapon",imageDirName,context)){
                weapon1Image.setImageBitmap(imageHandler.loadImage("weapon",imageDirName));
            }else{
                Picasso.with(context).load(url).into(weapon1Image);
                imageHandler.downloadImage("weapon",imageDirName,url,context);
            }
        }else if(shift.weapons.get(0).id==-1){
            weapon1Image.setImageDrawable(context.getResources().getDrawable(R.drawable.weapon_mystery));
        }else if(shift.weapons.get(0).id==-2){
            weapon1Image.setImageDrawable(context.getResources().getDrawable(R.drawable.weapon_mystery_grizzco));
        }

        if(shift.weapons.get(1).id>=0){
            imageDirName = shift.weapons.get(1).weapon.name.toLowerCase().replace(" ","_");
            url = "https://app.splatoon2.nintendo.net"+shift.weapons.get(1).weapon.url;

            if(imageHandler.imageExists("weapon",imageDirName,context)){
                weapon2Image.setImageBitmap(imageHandler.loadImage("weapon",imageDirName));
            }else{
                Picasso.with(context).load(url).into(weapon2Image);
                imageHandler.downloadImage("weapon",imageDirName,url,context);
            }
        }else if(shift.weapons.get(1).id==-1){
            weapon2Image.setImageDrawable(context.getResources().getDrawable(R.drawable.weapon_mystery));
        }else if(shift.weapons.get(1).id==-2){
            weapon2Image.setImageDrawable(context.getResources().getDrawable(R.drawable.weapon_mystery_grizzco));
        }

        if(shift.weapons.get(2).id>=0){
            imageDirName = shift.weapons.get(2).weapon.name.toLowerCase().replace(" ","_");
            url = "https://app.splatoon2.nintendo.net"+shift.weapons.get(2).weapon.url;

            if(imageHandler.imageExists("weapon",imageDirName,context)){
                weapon3Image.setImageBitmap(imageHandler.loadImage("weapon",imageDirName));
            }else{
                Picasso.with(context).load(url).into(weapon3Image);
                imageHandler.downloadImage("weapon",imageDirName,url,context);
            }
        }else if(shift.weapons.get(2).id==-1){
            weapon3Image.setImageDrawable(context.getResources().getDrawable(R.drawable.weapon_mystery));
        }else if(shift.weapons.get(2).id==-2){
            weapon3Image.setImageDrawable(context.getResources().getDrawable(R.drawable.weapon_mystery_grizzco));
        }

        if(shift.weapons.get(3).id>=0){
            imageDirName = shift.weapons.get(3).weapon.name.toLowerCase().replace(" ","_");
            url = "https://app.splatoon2.nintendo.net"+shift.weapons.get(3).weapon.url;

            if(imageHandler.imageExists("weapon",imageDirName,context)){
                weapon4Image.setImageBitmap(imageHandler.loadImage("weapon",imageDirName));
            }else{
                Picasso.with(context).load(url).into(weapon4Image);
                imageHandler.downloadImage("weapon",imageDirName,url,context);
            }
        }else if(shift.weapons.get(3).id==-1){
            weapon4Image.setImageDrawable(context.getResources().getDrawable(R.drawable.weapon_mystery));
        }else if(shift.weapons.get(3).id==-2){
            weapon4Image.setImageDrawable(context.getResources().getDrawable(R.drawable.weapon_mystery_grizzco));
        }
    }
}
