package com.mattrubacky.monet2.adapter.RecyclerView;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mattrubacky.monet2.adapter.RecyclerView.GearAdapter;
import com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders.ListViewHolder;
import com.mattrubacky.monet2.helper.ClosetHanger;

import java.util.ArrayList;

/**
 * Created by mattr on 12/18/2017.
 */

public class GearPagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<ArrayList<ClosetHanger>> input = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;

    public GearPagerAdapter(Context context, ArrayList<ArrayList<ClosetHanger>> input) {
        this.inflater = LayoutInflater.from(context);
        this.input = input;
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListViewHolder(inflater,parent);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holderAb, final int position) {

        ListViewHolder holder = (ListViewHolder) holderAb;
        ArrayList<ClosetHanger> stats = input.get(position);

        GearAdapter gearAdapter = new GearAdapter(context,stats,holder.itemList);

        holder.itemList.setAdapter(gearAdapter);
        holder.itemList.setLayoutManager(new GridLayoutManager(context,2));
    }

    @Override
    public int getItemCount() {
        return input.size();
    }

}