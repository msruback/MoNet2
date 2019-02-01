package com.mattrubacky.monet2.adapter.RecyclerView;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mattrubacky.monet2.BattleInfo;
import com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders.BattleViewHolder;
import com.mattrubacky.monet2.deserialized.splatoon.Battle;
import com.mattrubacky.monet2.deserialized.splatoon.Splatfest;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 10/21/2017.
 *
 * This Adapter fills up the battle list in BattleListFragment
 */

public class BattleListAdapter extends RecyclerView.Adapter<BattleViewHolder>{

    private ArrayList<Battle> input;
    private LayoutInflater inflater;
    private Context context;
    private RecyclerView listView;

    public BattleListAdapter(Context context, ArrayList<Battle> input, RecyclerView listView) {
        this.inflater = LayoutInflater.from(context);
        this.input = input;
        this.context = context;
        this.listView = listView;
    }
    @Override
    @NonNull
    public BattleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BattleViewHolder viewHolder = new BattleViewHolder(inflater,parent,context);
        viewHolder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = listView.getChildAdapterPosition(v);
                Bundle bundle = new Bundle();
                Battle battle = input.get(itemPosition);
                bundle.putParcelable("battle",battle);
                if(battle.type.equals("fes")){
                    SplatnetSQLManager database = new SplatnetSQLManager(context);
                    Splatfest splatfest = database.selectSplatfest(battle.splatfestID).splatfest;
                    bundle.putParcelable("splatfest",splatfest);
                }
                Intent intent = new Intent(context,BattleInfo.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final BattleViewHolder holder, final int position) {

        Battle battle = input.get(position);

        holder.manageHolder(battle);
    }

    @Override
    public int getItemCount() {
        return input.size();
    }

}