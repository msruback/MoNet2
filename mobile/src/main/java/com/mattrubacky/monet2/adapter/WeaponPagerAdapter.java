package com.mattrubacky.monet2.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.helper.WeaponStats;

import java.util.ArrayList;

/**
 * Created by mattr on 12/17/2017.
 */

public class WeaponPagerAdapter extends RecyclerView.Adapter<WeaponPagerAdapter.ViewHolder>{

    private ArrayList<ArrayList<WeaponStats>> input = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;

    public WeaponPagerAdapter(Context context, ArrayList<ArrayList<WeaponStats>> input) {
        this.inflater = LayoutInflater.from(context);
        this.input = input;
        this.context = context;
    }
    @Override
    public WeaponPagerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_pager_list, parent, false);
        WeaponPagerAdapter.ViewHolder viewHolder = new WeaponPagerAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final WeaponPagerAdapter.ViewHolder holder, final int position) {

        ArrayList<WeaponStats> stats = input.get(position);

        WeaponAdapter weaponAdapter = new WeaponAdapter(context,stats,holder.listView);

        holder.listView.setAdapter(weaponAdapter);
        holder.listView.setLayoutManager(new GridLayoutManager(context,2));
    }

    @Override
    public int getItemCount() {
        return input.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        RecyclerView listView;


        public ViewHolder(View itemView) {
            super(itemView);

            listView = (RecyclerView) itemView.findViewById(R.id.List);
        }

    }

}
