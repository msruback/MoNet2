package com.mattrubacky.monet2.adapter.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.WeaponLockerDetail;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.mattrubacky.monet2.helper.WeaponStats;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mattr on 11/13/2017.
 */

public class WeaponAdapter extends RecyclerView.Adapter<WeaponAdapter.ViewHolder>{

    private ArrayList<WeaponStats> input = new ArrayList<WeaponStats>();
    private LayoutInflater inflater;
    private Context context;
    private RecyclerView listView;

    public WeaponAdapter(Context context, ArrayList<WeaponStats> input, RecyclerView listView) {
        this.inflater = LayoutInflater.from(context);
        this.input = input;
        this.context = context;
        this.listView = listView;
    }
    @Override
    public WeaponAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_weapon, parent, false);
        WeaponAdapter.ViewHolder viewHolder = new WeaponAdapter.ViewHolder(view);
        view.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = listView.getChildAdapterPosition(v);
                Intent intent = new Intent(context, WeaponLockerDetail.class);
                Bundle bundle = new Bundle();
                WeaponStats weaponStats = input.get(itemPosition);

                weaponStats.calcStats(context);

                bundle.putParcelable("stats",weaponStats);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final WeaponAdapter.ViewHolder holder, final int position) {
        Typeface font = Typeface.createFromAsset(context.getAssets(),"Splatfont2.ttf");
        ImageHandler imageHandler = new ImageHandler();

        WeaponStats weaponStats = input.get(position);

        String url = "https://app.splatoon2.nintendo.net"+weaponStats.weapon.url;
        String location = weaponStats.weapon.name.toLowerCase().replace(" ","_");
        if(imageHandler.imageExists("weapon",location,context)){
            holder.weapon.setImageBitmap(imageHandler.loadImage("weapon",location));
        }else{
            Picasso.with(context).load(url).into(holder.weapon);
            imageHandler.downloadImage("weapon",location,url,context);
        }

        holder.name.setText(weaponStats.weapon.name);
        holder.name.setTypeface(font);

    }

    @Override
    public int getItemCount() {
        return input.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView weapon;
        TextView name;


        public ViewHolder(View itemView) {
            super(itemView);

            weapon = (ImageView) itemView.findViewById(R.id.WeaponImage);
            name = (TextView) itemView.findViewById(R.id.Name);
        }

    }

}