package com.mattrubacky.monet2.adapter.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.CampaignStageInfo;
import com.mattrubacky.monet2.deserialized.CampaignWeapon;
import com.mattrubacky.monet2.dialog.CampaignStageStatsDialog;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by mattr on 12/10/2017.
 */

public class CampaignStageAdapter extends RecyclerView.Adapter<CampaignStageAdapter.ViewHolder> {

    private ArrayList<CampaignStageInfo> input;
    private LayoutInflater inflater;
    private Context context;
    private Activity activity;
    private Map<Integer, CampaignWeapon> weaponMap;
    private RecyclerView stage;

    public CampaignStageAdapter(Activity activity, ArrayList<CampaignStageInfo> input,Map<Integer, CampaignWeapon> weaponMap,RecyclerView stage) {
        this.inflater = LayoutInflater.from(activity);
        this.input = input;
        this.activity = activity;
        this.context = activity;
        this.weaponMap = weaponMap;
        this.stage = stage;

    }

    @Override
    public CampaignStageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_map, parent, false);
        CampaignStageAdapter.ViewHolder viewHolder = new CampaignStageAdapter.ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = stage.indexOfChild(v);

                ArrayList<CampaignWeapon> weapons = new ArrayList<>();
                Integer[] keys = new Integer[2];
                keys = weaponMap.keySet().toArray(keys);
                for(int i=0;i<keys.length;i++){
                    weapons.add(weaponMap.get(keys[i]));
                }

                CampaignStageStatsDialog dialog = new CampaignStageStatsDialog(activity,input.get(itemPosition),weapons);
                dialog.show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CampaignStageAdapter.ViewHolder holder, final int position) {
        final CampaignStageInfo info= input.get(position);
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

        holder.number.setTypeface(fontTitle);
        holder.weaponTime.setTypeface(font);
        holder.completion.setTypeface(font);

        holder.number.setText(String.valueOf(info.stage.id));
        holder.weaponTime.setText(timeString);
        holder.completion.setText((keys.length)+"/"+total);

        if(info.stage.isBoss){
            holder.number.setVisibility(View.GONE);
            holder.boss.setVisibility(View.VISIBLE);
        }else{
            holder.number.setVisibility(View.VISIBLE);
            holder.boss.setVisibility(View.GONE);
        }
        weapon=weaponMap.get(bestWeapon.wepcategory);
        bestWeapon.url=weapon.url;

        String url="https://app.splatoon2.nintendo.net"+bestWeapon.url;

        String imageDirName=bestWeapon.wepcategory+"-"+bestWeapon.level;
        if(imageHandler.imageExists("campaign_weapon",imageDirName,context)){
            holder.image.setImageBitmap(imageHandler.loadImage("campaign_weapon",imageDirName));
        }else{
            Picasso.with(context).load(url).into(holder.image);
            imageHandler.downloadImage("campaign_weapon",imageDirName,url,context);
        }
    }

    @Override
    public int getItemCount() {
        return input.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image, boss;
        TextView number, weaponTime, completion;


        public ViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.WeaponImage);
            boss = (ImageView) itemView.findViewById(R.id.Boss);

            number = (TextView) itemView.findViewById(R.id.Number);
            weaponTime = (TextView) itemView.findViewById(R.id.WeaponTime);
            completion = (TextView) itemView.findViewById(R.id.Completion);
        }

    }

}