package com.mattrubacky.monet2.ui.adapter.ListView;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.data.deserialized.splatoon.CampaignStageInfo;
import com.mattrubacky.monet2.data.deserialized.splatoon.CampaignWeapon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by mattr on 12/11/2017.
 */

public class CampaignWeaponStatsAdapter extends ArrayAdapter<CampaignStageInfo> {
    private CampaignWeapon weapon;
    public CampaignWeaponStatsAdapter(Context context, ArrayList<CampaignStageInfo> input, CampaignWeapon weapon) {
        super(context, 0, input);
        this.weapon = weapon;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_campaign_weapon_stats, parent, false);
        }
        final CampaignStageInfo info = getItem(position);

        final Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");

        TextView number = convertView.findViewById(R.id.Number);
        TextView weaponTime = convertView.findViewById(R.id.WeaponTime);
        ImageView boss = convertView.findViewById(R.id.Boss);

        number.setTypeface(font);
        weaponTime.setTypeface(font);

        if(info.stage.isBoss){
            number.setText(info.stage.area+" -");
            boss.setVisibility(View.VISIBLE);
        }else {
            number.setText(info.stage.area + " - " + info.stage.id);
            boss.setVisibility(View.GONE);
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
            weaponTime.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }
}