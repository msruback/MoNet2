package com.mattrubacky.monet2.ui.adapter.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders.ListViewHolder;
import com.mattrubacky.monet2.data.stats.WeaponStats;

import java.util.ArrayList;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 12/17/2017.
 */

public class WeaponPagerAdapter extends RecyclerView.Adapter<ListViewHolder>{

    private ArrayList<ArrayList<WeaponStats>> input;
    private LayoutInflater inflater;
    private Context context;

    public WeaponPagerAdapter(Context context, ArrayList<ArrayList<WeaponStats>> input) {
        this.inflater = LayoutInflater.from(context);
        this.input = input;
        this.context = context;
    }
    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListViewHolder(inflater,parent);
    }

    @Override
    public void onBindViewHolder(final ListViewHolder holder, final int position) {

        ArrayList<WeaponStats> stats = input.get(position);

        WeaponAdapter weaponAdapter = new WeaponAdapter(context,stats,holder.itemList);

        holder.itemList.setAdapter(weaponAdapter);
        holder.itemList.setLayoutManager(new GridLayoutManager(context,2));
    }

    @Override
    public int getItemCount() {
        return input.size();
    }

}
