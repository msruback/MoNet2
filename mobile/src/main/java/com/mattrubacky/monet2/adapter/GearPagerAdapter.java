package com.mattrubacky.monet2.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.helper.ClosetHanger;

import java.util.ArrayList;

/**
 * Created by mattr on 12/18/2017.
 */

public class GearPagerAdapter extends RecyclerView.Adapter<GearPagerAdapter.ViewHolder>{

    private ArrayList<ArrayList<ClosetHanger>> input = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;

    public GearPagerAdapter(Context context, ArrayList<ArrayList<ClosetHanger>> input) {
        this.inflater = LayoutInflater.from(context);
        this.input = input;
        this.context = context;
    }
    @Override
    public GearPagerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_pager_list, parent, false);
        GearPagerAdapter.ViewHolder viewHolder = new GearPagerAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final GearPagerAdapter.ViewHolder holder, final int position) {

        ArrayList<ClosetHanger> stats = input.get(position);

        GearAdapter gearAdapter = new GearAdapter(context,stats,holder.listView);

        holder.listView.setAdapter(gearAdapter);
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