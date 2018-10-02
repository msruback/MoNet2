package com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.splatoon.CampaignStageInfo;
import com.mattrubacky.monet2.deserialized.splatoon.CampaignWeapon;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by mattr on 12/24/2017.
 */

public class CampaignStageViewHolder extends RecyclerView.ViewHolder{

    public ImageView image, boss;
    public TextView number, weaponTime, completion;
    private Context context;

    public CampaignStageViewHolder(LayoutInflater inflater, ViewGroup parent, Context context) {
        super(inflater.inflate(R.layout.item_map, parent, false));

        this.context = context;

        image = (ImageView) itemView.findViewById(R.id.WeaponImage);
        boss = (ImageView) itemView.findViewById(R.id.Boss);

        number = (TextView) itemView.findViewById(R.id.Number);
        weaponTime = (TextView) itemView.findViewById(R.id.WeaponTime);
        completion = (TextView) itemView.findViewById(R.id.Completion);;
    }

    public void manageHolder(CampaignStageInfo info,Map<Integer, CampaignWeapon> weaponMap){
        Typeface font = Typeface.createFromAsset(context.getAssets(), "Splatfont2.ttf");
        Typeface fontTitle=Typeface.createFromAsset(context.getAssets(),"Paintball.otf");
        ImageHandler imageHandler = new ImageHandler();

        int total=9;

        Integer[]keys=new Integer[2];
        keys=info.weapons.keySet().toArray(keys);
        CampaignWeapon bestWeapon=info.weapons.get(keys[0]);
        CampaignWeapon weapon;
        for(int i=1;i<keys.length;i++){
            weapon=info.weapons.get(keys[i]);
            if(weapon.time<bestWeapon.time){
                bestWeapon=weapon;
            }
        }

        int second=(int)bestWeapon.time%60;
        int minute=(int)((bestWeapon.time-second)/60)%60;
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,second);

        SimpleDateFormat sdf=new SimpleDateFormat("mm:ss");
        String timeString=sdf.format(calendar.getTimeInMillis());

        number.setTypeface(fontTitle);
        weaponTime.setTypeface(font);
        completion.setTypeface(font);

        number.setText(String.valueOf(info.stage.id));
        weaponTime.setText(timeString);
        completion.setText((keys.length)+"/"+total);

        if(info.stage.isBoss){
            number.setVisibility(View.GONE);
            boss.setVisibility(View.VISIBLE);
        }else{
            number.setVisibility(View.VISIBLE);
            boss.setVisibility(View.GONE);
        }
        weapon=weaponMap.get(bestWeapon.wepcategory);
        bestWeapon.url=weapon.url;

        String url="https://app.splatoon2.nintendo.net"+bestWeapon.url;

        String imageDirName=bestWeapon.wepcategory+"-"+bestWeapon.level;
        if(imageHandler.imageExists("campaign_weapon",imageDirName,context)){
            image.setImageBitmap(imageHandler.loadImage("campaign_weapon",imageDirName));
        }else{
            Picasso.with(context).load(url).into(image);
            imageHandler.downloadImage("campaign_weapon",imageDirName,url,context);
        }
    }
}
