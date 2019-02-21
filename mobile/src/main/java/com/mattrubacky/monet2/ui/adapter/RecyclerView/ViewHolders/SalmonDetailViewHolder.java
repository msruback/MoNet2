package com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.data.deserialized.splatoon.SalmonRunDetail;
import com.mattrubacky.monet2.backend.ImageHandler;
import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;

/**
 * Created by mattr on 1/14/2018.
 */

public class SalmonDetailViewHolder extends RecyclerView.ViewHolder{

    private TextView time,stageName;
    private ImageView stageImage,weapon1Image,weapon2Image,weapon3Image,weapon4Image;
    private Context context;

    public SalmonDetailViewHolder(LayoutInflater inflater, ViewGroup parent,Context context) {
        super(inflater.inflate(R.layout.item_salmon_rotation_detail, parent, false));

        this.context = context;

        time = itemView.findViewById(R.id.Time);
        stageName = itemView.findViewById(R.id.StageName);

        stageImage = itemView.findViewById(R.id.StageImage);
        weapon1Image = itemView.findViewById(R.id.Weapon1Image);
        weapon2Image = itemView.findViewById(R.id.Weapon2Image);
        weapon3Image = itemView.findViewById(R.id.Weapon3Image);
        weapon4Image = itemView.findViewById(R.id.Weapon4Image);
    }

    public void manageHolder(SalmonRunDetail detail){

        Typeface font = Typeface.createFromAsset(context.getAssets(), "Splatfont2.ttf");


        time.setTypeface(font);
        stageName.setTypeface(font);

        SimpleDateFormat sdf = new SimpleDateFormat("EEE M/d h a");
        String startText = sdf.format(detail.start*1000);
        String endText = sdf.format(detail.end*1000);
        time.setText(startText + " to " + endText);

        stageName.setText(detail.stage.name);

        ImageHandler imageHandler = new ImageHandler();
        String imageDirName = detail.stage.name.toLowerCase().replace(" ","_");
        String url = "https://app.splatoon2.nintendo.net"+detail.stage.url;

        if(imageHandler.imageExists("salmon_stage",imageDirName,context)){
            stageImage.setImageBitmap(imageHandler.loadImage("salmon_stage",imageDirName));
        }else{
            Picasso.with(context).load(url).resize(1280,720).into(stageImage);
            imageHandler.downloadImage("salmon_stage",imageDirName,url,context);
        }

        if(detail.weapons.get(0).id>=0){
            imageDirName = detail.weapons.get(0).weapon.name.toLowerCase().replace(" ","_");
            url = "https://app.splatoon2.nintendo.net"+detail.weapons.get(0).weapon.url;

            if(imageHandler.imageExists("weapon",imageDirName,context)){
                weapon1Image.setImageBitmap(imageHandler.loadImage("weapon",imageDirName));
            }else{
                Picasso.with(context).load(url).into(weapon1Image);
                imageHandler.downloadImage("weapon",imageDirName,url,context);
            }
        }else if(detail.weapons.get(0).id==-1){
            weapon1Image.setImageDrawable(context.getResources().getDrawable(R.drawable.weapon_mystery));
        }else if(detail.weapons.get(0).id==-2){
            weapon1Image.setImageDrawable(context.getResources().getDrawable(R.drawable.weapon_mystery_grizzco));
        }

        if(detail.weapons.get(1).id>=0){
            imageDirName = detail.weapons.get(1).weapon.name.toLowerCase().replace(" ","_");
            url = "https://app.splatoon2.nintendo.net"+detail.weapons.get(1).weapon.url;

            if(imageHandler.imageExists("weapon",imageDirName,context)){
                weapon2Image.setImageBitmap(imageHandler.loadImage("weapon",imageDirName));
            }else{
                Picasso.with(context).load(url).into(weapon2Image);
                imageHandler.downloadImage("weapon",imageDirName,url,context);
            }
        }else if(detail.weapons.get(1).id==-1){
            weapon2Image.setImageDrawable(context.getResources().getDrawable(R.drawable.weapon_mystery));
        }else if(detail.weapons.get(1).id==-2){
            weapon2Image.setImageDrawable(context.getResources().getDrawable(R.drawable.weapon_mystery_grizzco));
        }

        if(detail.weapons.get(2).id>=0){
            imageDirName = detail.weapons.get(2).weapon.name.toLowerCase().replace(" ","_");
            url = "https://app.splatoon2.nintendo.net"+detail.weapons.get(2).weapon.url;

            if(imageHandler.imageExists("weapon",imageDirName,context)){
                weapon3Image.setImageBitmap(imageHandler.loadImage("weapon",imageDirName));
            }else{
                Picasso.with(context).load(url).into(weapon3Image);
                imageHandler.downloadImage("weapon",imageDirName,url,context);
            }
        }else if(detail.weapons.get(2).id==-1){
            weapon3Image.setImageDrawable(context.getResources().getDrawable(R.drawable.weapon_mystery));
        }else if(detail.weapons.get(2).id==-2){
            weapon3Image.setImageDrawable(context.getResources().getDrawable(R.drawable.weapon_mystery_grizzco));
        }

        if(detail.weapons.get(3).id>=0){
            imageDirName = detail.weapons.get(3).weapon.name.toLowerCase().replace(" ","_");
            url = "https://app.splatoon2.nintendo.net"+detail.weapons.get(3).weapon.url;

            if(imageHandler.imageExists("weapon",imageDirName,context)){
                weapon4Image.setImageBitmap(imageHandler.loadImage("weapon",imageDirName));
            }else{
                Picasso.with(context).load(url).into(weapon4Image);
                imageHandler.downloadImage("weapon",imageDirName,url,context);
            }
        }else if(detail.weapons.get(3).id==-1){
            weapon4Image.setImageDrawable(context.getResources().getDrawable(R.drawable.weapon_mystery));
        }else if(detail.weapons.get(3).id==-2){
            weapon4Image.setImageDrawable(context.getResources().getDrawable(R.drawable.weapon_mystery_grizzco));
        }

    }
}