package com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.splatoon.CampaignStageInfo;
import com.mattrubacky.monet2.deserialized.splatoon.CampaignWeapon;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by mattr on 12/24/2017.
 */

public class CampaignWeaponViewHolder extends RecyclerView.ViewHolder{

    public ImageView image;
    public TextView weaponTime, completion;
    private Context context;

    public CampaignWeaponViewHolder(LayoutInflater inflater, ViewGroup parent,Context context) {
        super(inflater.inflate(R.layout.item_campaign_weapon, parent, false));

        this.context = context;

        image = (ImageView) itemView.findViewById(R.id.WeaponImage);
        weaponTime = (TextView) itemView.findViewById(R.id.WeaponTime);
        completion = (TextView) itemView.findViewById(R.id.Completion);
    }
    public void manageHolder(CampaignWeapon weapon, ArrayList<CampaignStageInfo> infos){
        Typeface font = Typeface.createFromAsset(context.getAssets(), "Splatfont2.ttf");
        Typeface fontTitle=Typeface.createFromAsset(context.getAssets(),"Paintball.otf");
        ImageHandler imageHandler = new ImageHandler();

        int total = 32;

        long time = 0;
        int completed = 0;
        CampaignStageInfo info;
        for(int i=0;i<infos.size();i++){
            info = infos.get(i);
            if(info.weapons.containsKey(weapon.category)){
                time += info.weapons.get(weapon.category).time;
                completed++;
            }
        }

        int second = (int) time%60;
        int minute = (int) ((time-second)/60)%60;
        int hour = (int) (((time-second)/60)-minute)/60;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,second);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String timeString = sdf.format(calendar.getTimeInMillis());

        weaponTime.setTypeface(font);
        completion.setTypeface(font);

        weaponTime.setText(timeString);
        completion.setText(completed+"/"+total);

        String url = "https://app.splatoon2.nintendo.net"+weapon.url;

        String imageDirName = weapon.category+"-"+weapon.level;
        if(imageHandler.imageExists("campaign_weapon",imageDirName,context)){
            image.setImageBitmap(imageHandler.loadImage("campaign_weapon",imageDirName));
        }else{
            Picasso.with(context).load(url).into(image);
            imageHandler.downloadImage("campaign_weapon",imageDirName,url,context);
        }
    }
}
