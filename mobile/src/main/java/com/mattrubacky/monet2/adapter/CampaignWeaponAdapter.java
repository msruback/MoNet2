package com.mattrubacky.monet2.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
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
import com.mattrubacky.monet2.dialog.CampaignWeaponStatsDialog;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by mattr on 12/10/2017.
 */

public class CampaignWeaponAdapter extends RecyclerView.Adapter<CampaignWeaponAdapter.ViewHolder> {

    private ArrayList<CampaignWeapon> input;
    private ArrayList<CampaignStageInfo> infos;
    private LayoutInflater inflater;
    private Context context;
    private Activity activity;

    public CampaignWeaponAdapter(Activity activity, ArrayList<CampaignWeapon> input, ArrayList<CampaignStageInfo> infos ) {
        this.inflater = LayoutInflater.from(activity);
        this.input = input;
        this.activity = activity;
        this.context = activity;
        this.infos = infos;

    }

    @Override
    public CampaignWeaponAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_campaign_weapon, parent, false);
        CampaignWeaponAdapter.ViewHolder viewHolder = new CampaignWeaponAdapter.ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerView weapons = (RecyclerView) activity.findViewById(R.id.WeaponList);
                int itemPosition = weapons.indexOfChild(v);
                CampaignWeaponStatsDialog dialog = new CampaignWeaponStatsDialog(activity,input.get(itemPosition),infos);
                dialog.show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CampaignWeaponAdapter.ViewHolder holder, final int position) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "Splatfont2.ttf");
        Typeface fontTitle=Typeface.createFromAsset(context.getAssets(),"Paintball.otf");
        ImageHandler imageHandler = new ImageHandler();

        CampaignWeapon weapon = input.get(position);
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

        holder.weaponTime.setTypeface(font);
        holder.completion.setTypeface(font);

        holder.weaponTime.setText(timeString);
        holder.completion.setText(completed+"/"+total);

        String url = "https://app.splatoon2.nintendo.net"+weapon.url;

        String imageDirName = weapon.category+"-"+weapon.level;
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
        ImageView image;
        TextView weaponTime, completion;


        public ViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.WeaponImage);
            weaponTime = (TextView) itemView.findViewById(R.id.WeaponTime);
            completion = (TextView) itemView.findViewById(R.id.Completion);

        }

    }

}