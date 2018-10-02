package com.mattrubacky.monet2.adapter.RecyclerView;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mattrubacky.monet2.adapter.ListView.GearPickerAdapter;
import com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders.ListViewViewHolder;
import com.mattrubacky.monet2.deserialized.splatoon.Gear;

import java.util.ArrayList;

/**
 * Created by mattr on 12/18/2017.
 */

public class GearPickerPagerAdapter extends RecyclerView.Adapter<ListViewViewHolder>{

    private ArrayList<ArrayList<Gear>> input = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<ListView> listViews;
    private RecyclerView recyclerView;
    private AdapterView.OnItemClickListener onItemClickListener;

    public GearPickerPagerAdapter(Context context, ArrayList<ArrayList<Gear>> input,RecyclerView recyclerView,AdapterView.OnItemClickListener onItemClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.input = input;
        this.context = context;
        this.recyclerView = recyclerView;
        this.onItemClickListener = onItemClickListener;
        listViews = new ArrayList<>();
    }
    @Override
    public ListViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ListViewViewHolder(inflater,parent);
    }

    @Override
    public void onBindViewHolder(final ListViewViewHolder holder, final int position) {

        ArrayList<Gear> gear = input.get(position);

        listViews.add(position,holder.itemList);

        holder.itemList.setAdapter(new GearPickerAdapter(context,gear));

        holder.itemList.setOnItemClickListener(onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return input.size();
    }

    public Gear getResult(int id){
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int position = linearLayoutManager.findFirstVisibleItemPosition();
        return input.get(position).get(id);
    }

}