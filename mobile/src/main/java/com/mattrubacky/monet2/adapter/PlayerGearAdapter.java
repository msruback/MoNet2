package com.mattrubacky.monet2.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.WeaponLockerDetail;
import com.mattrubacky.monet2.deserialized.Gear;
import com.mattrubacky.monet2.deserialized.Weapon;
import com.mattrubacky.monet2.helper.ClosetHanger;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.mattrubacky.monet2.helper.WeaponStats;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mattr on 12/24/2017.
 */

public class PlayerGearAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private LayoutInflater inflater;
    private Context context;
    private Weapon weapon;
    private ArrayList<ClosetHanger> gear;

    public PlayerGearAdapter(Context context,Weapon weapon,ArrayList<ClosetHanger> gear) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.weapon = weapon;
        this.gear = gear;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
                return new ViewHolder0(inflater.inflate(R.layout.item_weapon, parent, false));
            default:
                return new ViewHolder1(inflater.inflate(R.layout.item_gear, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holderAb, final int position) {
        Typeface font = Typeface.createFromAsset(context.getAssets(),"Splatfont2.ttf");
        ImageHandler imageHandler = new ImageHandler();

        if(holderAb.getItemViewType()==0) {
            ViewHolder0 holder = (ViewHolder0) holderAb;
            String url = "https://app.splatoon2.nintendo.net" + weapon.url;
            String location = weapon.name.toLowerCase().replace(" ", "_");
            if (imageHandler.imageExists("weapon", location, context)) {
                holder.weapon.setImageBitmap(imageHandler.loadImage("weapon", location));
            } else {
                Picasso.with(context).load(url).into(holder.weapon);
                imageHandler.downloadImage("weapon", location, url, context);
            }

            holder.name.setText(weapon.name);
            holder.name.setTypeface(font);
        }else{
            ViewHolder1 holder = (ViewHolder1) holderAb;
            ClosetHanger closetHanger = gear.get(position-1);

            String url = "https://app.splatoon2.nintendo.net"+closetHanger.gear.url;
            String location = closetHanger.gear.name.toLowerCase().replace(" ","_");
            if(imageHandler.imageExists("gear",location,context)){
                holder.gear.setImageBitmap(imageHandler.loadImage("weapon",location));
            }else{
                Picasso.with(context).load(url).into(holder.gear);
                imageHandler.downloadImage("gear",location,url,context);
            }

            holder.name.setText(closetHanger.gear.name);
            holder.name.setTypeface(font);

            switch(closetHanger.gear.kind){
                case "head":
                    holder.hook.setBackgroundTintList(context.getResources().getColorStateList(R.color.head));
                    holder.card.setBackgroundTintList(context.getResources().getColorStateList(R.color.head));
                    break;
                case "clothes":
                    holder.hook.setBackgroundTintList(context.getResources().getColorStateList(R.color.clothes));
                    holder.card.setBackgroundTintList(context.getResources().getColorStateList(R.color.clothes));
                    break;
                case "shoes":
                    holder.hook.setBackgroundTintList(context.getResources().getColorStateList(R.color.shoes));
                    holder.card.setBackgroundTintList(context.getResources().getColorStateList(R.color.shoes));
                    break;
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return 0;
        }
        return 1;
    }

    @Override
    public int getItemCount() {
        return gear.size()+1;
    }



    public class ViewHolder0 extends RecyclerView.ViewHolder{
        ImageView weapon;
        TextView name;

        public ViewHolder0(View itemView) {
            super(itemView);

            weapon = (ImageView) itemView.findViewById(R.id.WeaponImage);
            name = (TextView) itemView.findViewById(R.id.Name);
        }

    }
    public class ViewHolder1 extends RecyclerView.ViewHolder{

        RelativeLayout hook,card;
        ImageView gear;
        TextView name;

        public ViewHolder1(View itemView) {
            super(itemView);

                hook = (RelativeLayout) itemView.findViewById(R.id.hook);
                card = (RelativeLayout) itemView.findViewById(R.id.card);
                gear = (ImageView) itemView.findViewById(R.id.GearImage);
                name = (TextView) itemView.findViewById(R.id.Name);
        }

    }

}
