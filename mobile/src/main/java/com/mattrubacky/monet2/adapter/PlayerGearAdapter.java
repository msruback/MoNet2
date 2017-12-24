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

public class PlayerGearAdapter extends RecyclerView.Adapter<PlayerGearAdapter.ViewHolder>{

    private LayoutInflater inflater;
    private Context context;
    private Weapon weapon;
    private ArrayList<ClosetHanger> gear;
    int curPos;

    public PlayerGearAdapter(Context context,Weapon weapon,ArrayList<ClosetHanger> gear) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.weapon = weapon;
        this.gear = gear;
        curPos=0;
    }
    @Override
    public PlayerGearAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(curPos==0) {
            view = inflater.inflate(R.layout.item_weapon, parent, false);
        }else{
            view = inflater.inflate(R.layout.item_gear,parent,false);
        }
        PlayerGearAdapter.ViewHolder viewHolder = new PlayerGearAdapter.ViewHolder(view);
        viewHolder.curPos =curPos;
        curPos++;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PlayerGearAdapter.ViewHolder holder, final int position) {
        Typeface font = Typeface.createFromAsset(context.getAssets(),"Splatfont2.ttf");
        ImageHandler imageHandler = new ImageHandler();

        if(position==0) {
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
    public int getItemCount() {
        return gear.size()+1;
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView weapon;
        TextView name;

        RelativeLayout hook,card;
        ImageView gear;

        int curPos;


        public ViewHolder(View itemView) {
            super(itemView);

            if(curPos==0) {
                weapon = (ImageView) itemView.findViewById(R.id.WeaponImage);
                name = (TextView) itemView.findViewById(R.id.Name);
            }else{
                hook = (RelativeLayout) itemView.findViewById(R.id.hook);
                card = (RelativeLayout) itemView.findViewById(R.id.card);
                gear = (ImageView) itemView.findViewById(R.id.GearImage);
                name = (TextView) itemView.findViewById(R.id.Name);
            }
        }

    }

}
