package com.mattrubacky.monet2.adapter.ListView;

import android.content.Context;
import android.graphics.Typeface;
import android.provider.ContactsContract;
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

/**
 * Created by mattr on 12/11/2017.
 */

public class CampaignStageStatsAdapter extends ArrayAdapter<CampaignWeapon> {
    private CampaignStageInfo info;

    public CampaignStageStatsAdapter(Context context, ArrayList<CampaignWeapon> input, CampaignStageInfo info) {
        super(context, 0, input);
        this.info = info;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_campaign_stage_stats, parent, false);
        }
        final CampaignWeapon weapon = getItem(position);

        final Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");

        ImageHandler imageHandler = new ImageHandler();

        TextView weaponTime = (TextView) convertView.findViewById(R.id.WeaponTime);
        ImageView weaponImage = (ImageView) convertView.findViewById(R.id.WeaponImage);

        weaponTime.setTypeface(font);

        String url = "https://app.splatoon2.nintendo.net"+weapon.url;

        String imageDirName = weapon.category+"-"+weapon.level;
        if(imageHandler.imageExists("campaign_weapon",imageDirName,getContext())){
            weaponImage.setImageBitmap(imageHandler.loadImage("campaign_weapon",imageDirName));
        }else{
            Picasso.with(getContext()).load(url).into(weaponImage);
            imageHandler.downloadImage("campaign_weapon",imageDirName,url,getContext());
        }

        if(info.weapons.containsKey(weapon.category)){

            weaponTime.setVisibility(View.VISIBLE);
            long time = info.weapons.get(weapon.category).time;

            int second = (int) time%60;
            int minute = (int) ((time-second)/60)%60;
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MINUTE,minute);
            calendar.set(Calendar.SECOND,second);

            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
            String timeString = sdf.format(calendar.getTimeInMillis());

            weaponTime.setText(timeString);
        }else{
            weaponTime.setVisibility(View.GONE);
        }


        return convertView;
    }
}
