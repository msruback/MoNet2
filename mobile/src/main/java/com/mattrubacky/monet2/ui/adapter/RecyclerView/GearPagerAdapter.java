package com.mattrubacky.monet2.ui.adapter.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders.ListViewHolder;
import com.mattrubacky.monet2.data.stats.GearStats;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 12/18/2017.
 */

public class GearPagerAdapter extends RecyclerView.Adapter<ListViewHolder>{

    private ArrayList<ArrayList<GearStats>> input;
    private LayoutInflater inflater;
    private Context context;

    public GearPagerAdapter(Context context, ArrayList<ArrayList<GearStats>> input) {
        this.inflater = LayoutInflater.from(context);
        this.input = input;
        this.context = context;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListViewHolder(inflater,parent);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, final int position) {

        ArrayList<GearStats> stats = input.get(position);

        GearAdapter gearAdapter = new GearAdapter(context,stats,holder.itemList);

        holder.itemList.setAdapter(gearAdapter);
        holder.itemList.setLayoutManager(new GridLayoutManager(context,2));
    }

    @Override
    public int getItemCount() {
        return input.size();
    }

}