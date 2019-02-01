package com.mattrubacky.monet2.adapter.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders.ListViewHolder;
import com.mattrubacky.monet2.deserialized.splatoon.SalmonRunDetail;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 11/6/2018.
 */

public class SalmonRunShiftPagerAdapter extends RecyclerView.Adapter<ListViewHolder>{

    private ArrayList<ArrayList<SalmonRunDetail>> input;
    private LayoutInflater inflater;
    private Context context;

    public SalmonRunShiftPagerAdapter(Context context, ArrayList<ArrayList<SalmonRunDetail>> input) {
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

        final ArrayList<SalmonRunDetail> details = input.get(position);

        holder.itemList.setAdapter(new SalmonShiftAdapter(context,details,holder.itemList));
        holder.itemList.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));

    }

    @Override
    public int getItemCount() {
        return input.size();
    }
}
