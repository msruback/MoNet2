package com.mattrubacky.monet2.adapter.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders.GearViewHolder;
import com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders.WeaponViewHolder;
import com.mattrubacky.monet2.deserialized.splatoon.Weapon;
import com.mattrubacky.monet2.helper.ClosetHanger;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
                return new WeaponViewHolder(inflater,parent,context);
            default:
                return new GearViewHolder(inflater,parent,context);
        }
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holderAb, final int position) {
        if(holderAb.getItemViewType()==0) {
            WeaponViewHolder holder = (WeaponViewHolder) holderAb;
            holder.manageHolder(weapon);
        }else{
            GearViewHolder holder = (GearViewHolder) holderAb;
            ClosetHanger closetHanger = gear.get(position-1);
            holder.manageHolder(closetHanger);
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

}
