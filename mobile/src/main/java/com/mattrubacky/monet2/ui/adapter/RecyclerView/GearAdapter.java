package com.mattrubacky.monet2.ui.adapter.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mattrubacky.monet2.ui.activities.ClosetDetail;
import com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders.GearViewHolder;
import com.mattrubacky.monet2.data.stats.GearStats;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 11/13/2017.
 */

public class GearAdapter extends RecyclerView.Adapter<GearViewHolder>{

    private ArrayList<GearStats> input;
    private LayoutInflater inflater;
    private Context context;
    private RecyclerView listView;

    public GearAdapter(Context context, ArrayList<GearStats> input, RecyclerView listView) {
        this.inflater = LayoutInflater.from(context);
        this.input = input;
        this.context = context;
        this.listView = listView;
    }
    @NonNull
    @Override
    public GearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GearViewHolder viewHolder = new GearViewHolder(inflater,parent,context);
        viewHolder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = listView.getChildAdapterPosition(v);
                Intent intent = new Intent(context, ClosetDetail.class);
                Bundle bundle = new Bundle();
                GearStats hanger = input.get(itemPosition);

                hanger.calcStats(context);

                bundle.putParcelable("stats",hanger);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final GearViewHolder holder, final int position) {
        GearStats gearStats = input.get(position);
        holder.manageHolder(gearStats);

    }

    @Override
    public int getItemCount() {
        return input.size();
    }

}
