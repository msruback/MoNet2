package com.mattrubacky.monet2.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.helper.StageStats;
import com.mattrubacky.monet2.helper.WeaponStats;

import java.util.ArrayList;

/**
 * Created by mattr on 12/17/2017.
 */

public class WeaponPagingListAdapter extends RecyclerView.Adapter<WeaponPagingListAdapter.ViewHolder>{

    private ArrayList<ArrayList<WeaponStats>> input = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private View.OnClickListener onClickListener;

    public WeaponPagingListAdapter(Context context, ArrayList<ArrayList<WeaponStats>> input, View.OnClickListener onClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.input = input;
        this.context = context;
        this.onClickListener = onClickListener;
    }
    @Override
    public WeaponPagingListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_pager_list, parent, false);
        WeaponPagingListAdapter.ViewHolder viewHolder = new WeaponPagingListAdapter.ViewHolder(view);
        view.setOnClickListener(onClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final WeaponPagingListAdapter.ViewHolder holder, final int position) {

        ArrayList<WeaponStats> stats = input.get(position);

        WeaponAdapter weaponAdapter = new WeaponAdapter(context,stats,onClickListener);

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
