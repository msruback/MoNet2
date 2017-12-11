package com.mattrubacky.monet2.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.CampaignStage;
import com.mattrubacky.monet2.deserialized.CampaignStageInfo;
import com.mattrubacky.monet2.deserialized.CampaignWeapon;
import com.mattrubacky.monet2.deserialized.Gear;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by mattr on 12/10/2017.
 */

public class CampaignWeaponAdapter extends ArrayAdapter<CampaignWeapon> {

    ArrayList<CampaignStageInfo> infos;

    public CampaignWeaponAdapter(Context context, ArrayList<CampaignWeapon> input, ArrayList<CampaignStageInfo> infos) {
        super(context, 0, input);
        this.infos = infos;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_campaign_weapon, parent, false);
        }
        CampaignWeapon weapon = getItem(position);

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


        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(getContext().getAssets(), "Paintball.otf");

        int second = (int) time%60;
        int minute = (int) ((time-second)/60)%60;
        int hour = (int) (((time-second)/60)-minute)/60;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,second);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String timeString = sdf.format(calendar.getTimeInMillis());

        ImageView image = (ImageView) convertView.findViewById(R.id.WeaponImage);
        TextView weaponTime = (TextView) convertView.findViewById(R.id.WeaponTime);
        TextView completion = (TextView) convertView.findViewById(R.id.Completion);

        weaponTime.setTypeface(font);
        completion.setTypeface(font);

        weaponTime.setText(timeString);
        completion.setText(completed+"/"+total);

        String url = "https://app.splatoon2.nintendo.net"+weapon.url;

        ImageHandler imageHandler = new ImageHandler();
        String imageDirName = weapon.category+"-"+weapon.level;
        if(imageHandler.imageExists("campaign_weapon",imageDirName,getContext())){
            image.setImageBitmap(imageHandler.loadImage("campaign_weapon",imageDirName));
        }else{
            Picasso.with(getContext()).load(url).into(image);
            imageHandler.downloadImage("campaign_weapon",imageDirName,url,getContext());
        }

        return convertView;
    }
}