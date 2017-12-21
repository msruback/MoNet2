package com.mattrubacky.monet2.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mattrubacky.monet2.BattleInfo;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.Battle;
import com.mattrubacky.monet2.deserialized.Gear;
import com.mattrubacky.monet2.deserialized.Splatfest;
import com.mattrubacky.monet2.dialog.LoadingDialog;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.util.ArrayList;

/**
 * Created by mattr on 12/21/2017.
 */

public class BattleListPagerAdapter extends RecyclerView.Adapter<BattleListPagerAdapter.ViewHolder>{

    private ArrayList<ArrayList<Battle>> input = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;

    public BattleListPagerAdapter(Context context, ArrayList<ArrayList<Battle>> input) {
        this.inflater = LayoutInflater.from(context);
        this.input = input;
        this.context = context;
    }
    @Override
    public BattleListPagerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_pager_list, parent, false);
        BattleListPagerAdapter.ViewHolder viewHolder = new BattleListPagerAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final BattleListPagerAdapter.ViewHolder holder, final int position) {

        final ArrayList<Battle> battles = input.get(position);

        holder.listView.setAdapter(new BattleListAdapter(context,battles,holder.listView));
        holder.listView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));

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
