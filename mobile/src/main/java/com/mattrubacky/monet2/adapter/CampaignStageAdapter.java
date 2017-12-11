package com.mattrubacky.monet2.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.CampaignStageInfo;
import com.mattrubacky.monet2.deserialized.CampaignWeapon;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by mattr on 12/10/2017.
 */

public class CampaignStageAdapter extends ArrayAdapter<CampaignStageInfo> {

    private Map<Integer,CampaignWeapon> weaponMap;


    public CampaignStageAdapter(Context context, ArrayList<CampaignStageInfo> input,Map<Integer,CampaignWeapon> weaponMap) {
        super(context, 0, input);
        this.weaponMap = weaponMap;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_map, parent, false);
        }
        CampaignStageInfo info = getItem(position);

        int total = 9;

        Integer[] keys = new Integer[2];
        keys = info.weapons.keySet().toArray(keys);
        CampaignWeapon bestWeapon = info.weapons.get(keys[0]);
        CampaignWeapon weapon;
        for(int i=1;i<keys.length;i++){
            weapon = info.weapons.get(keys[i]);
            if(weapon.time<bestWeapon.time){
                bestWeapon = weapon;
            }
        }


        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(getContext().getAssets(), "Paintball.otf");

        int second = (int) bestWeapon.time%60;
        int minute = (int) ((bestWeapon.time-second)/60)%60;
        int hour = (int) (((bestWeapon.time-second)/60)-minute)/60;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,second);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String timeString = sdf.format(calendar.getTimeInMillis());

        ImageView image = (ImageView) convertView.findViewById(R.id.WeaponImage);
        ImageView boss = (ImageView) convertView.findViewById(R.id.Boss);

        TextView number = (TextView) convertView.findViewById(R.id.Number);
        TextView weaponTime = (TextView) convertView.findViewById(R.id.WeaponTime);
        TextView completion = (TextView) convertView.findViewById(R.id.Completion);

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
        weapon = weaponMap.get(bestWeapon.category);
        bestWeapon.url = weapon.url;

        String url = "https://app.splatoon2.nintendo.net"+bestWeapon.url;

        ImageHandler imageHandler = new ImageHandler();
        String imageDirName = bestWeapon.category+"-"+bestWeapon.level;
        if(imageHandler.imageExists("campaign_weapon",imageDirName,getContext())){
            image.setImageBitmap(imageHandler.loadImage("campaign_weapon",imageDirName));
        }else{
            Picasso.with(getContext()).load(url).into(image);
            imageHandler.downloadImage("campaign_weapon",imageDirName,url,getContext());
        }

        return convertView;
    }
}
