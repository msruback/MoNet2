package com.mattrubacky.monet2.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.CampaignStage;
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

public class CampaignWorldAdapter extends ArrayAdapter<ArrayList<CampaignStageInfo>> {

    float totalHeight;
    Map<Integer,CampaignWeapon> weaponMap;

    public CampaignWorldAdapter(Context context, ArrayList<ArrayList<CampaignStageInfo>> input,Map<Integer,CampaignWeapon> weaponMap) {
        super(context, 0, input);
        totalHeight = 0;
        this.weaponMap = weaponMap;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_world, parent, false);
        }
        ArrayList<CampaignStageInfo> infos = getItem(position);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(getContext().getAssets(), "Paintball.otf");

        TextView world = (TextView) convertView.findViewById(R.id.World);
        ListView list = (ListView) convertView.findViewById(R.id.List);

        world.setTypeface(fontTitle);

        world.setText("World "+(position+1));

        ViewGroup.LayoutParams layoutParams = list.getLayoutParams();
        float height = (infos.size()*50)+5;
        layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, getContext().getResources().getDisplayMetrics());
        list.setLayoutParams(layoutParams);
        totalHeight +=(height + 15);

        CampaignStageAdapter campaignStageAdapter = new CampaignStageAdapter(getContext(),infos,weaponMap);
        list.setAdapter(campaignStageAdapter);

        return convertView;
    }
    public float getHeight(){
        return totalHeight;
    }
}
