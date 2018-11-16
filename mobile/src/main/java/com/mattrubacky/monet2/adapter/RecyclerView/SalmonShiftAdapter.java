package com.mattrubacky.monet2.adapter.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mattrubacky.monet2.BattleInfo;
import com.mattrubacky.monet2.SalmonRunShiftDetail;
import com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders.SalmonJobViewHolder;
import com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders.SalmonShiftViewHolder;
import com.mattrubacky.monet2.deserialized.splatoon.Battle;
import com.mattrubacky.monet2.deserialized.splatoon.CoopResult;
import com.mattrubacky.monet2.deserialized.splatoon.SalmonRunDetail;
import com.mattrubacky.monet2.deserialized.splatoon.Splatfest;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.util.ArrayList;

/**
 * Created by mattr on 11/6/2018.
 */

public class SalmonShiftAdapter extends RecyclerView.Adapter<SalmonShiftViewHolder>{
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<SalmonRunDetail> details;
    RecyclerView listView;

    public SalmonShiftAdapter(Context context, ArrayList<SalmonRunDetail> details, RecyclerView listView){
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.details = details;
        this.listView = listView;
    }

    @Override
    public SalmonShiftViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SalmonShiftViewHolder viewHolder= new SalmonShiftViewHolder(inflater,parent,context);
        viewHolder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = listView.getChildAdapterPosition(v);
                Bundle bundle = new Bundle();
                SalmonRunDetail detail = details.get(itemPosition);
                bundle.putParcelable("shift",detail);
                Intent intent = new Intent(context,SalmonRunShiftDetail.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SalmonShiftViewHolder holder, int position) {
        SalmonRunDetail detail = details.get(position);
        holder.manageHolder(detail);
    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}
