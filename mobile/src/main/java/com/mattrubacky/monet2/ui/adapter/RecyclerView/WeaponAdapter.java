package com.mattrubacky.monet2.ui.adapter.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mattrubacky.monet2.ui.activities.WeaponLockerDetail;
import com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders.WeaponViewHolder;
import com.mattrubacky.monet2.data.stats.WeaponStats;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 11/13/2017.
 */

public class WeaponAdapter extends RecyclerView.Adapter<WeaponViewHolder>{

    private ArrayList<WeaponStats> input;
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
    public WeaponViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WeaponViewHolder viewHolder = new WeaponViewHolder(inflater,parent,context);
        viewHolder.itemView.setOnClickListener( new View.OnClickListener() {
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
    public void onBindViewHolder(final WeaponViewHolder holder, final int position) {
        WeaponStats weaponStats = input.get(position);
        holder.manageHolder(weaponStats.weapon);
    }

    @Override
    public int getItemCount() {
        return input.size();
    }

}