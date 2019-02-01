package com.mattrubacky.monet2.adapter.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders.SplatfestViewHolder;
import com.mattrubacky.monet2.deserialized.splatoon.Splatfest;
import com.mattrubacky.monet2.deserialized.splatoon.SplatfestDatabase;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 11/20/2017.
 */

public class SplatfestAdapter extends RecyclerView.Adapter<SplatfestViewHolder>{

    private ArrayList<SplatfestDatabase> input;
    private LayoutInflater inflater;
    private Context context;
    private View.OnClickListener onClickListener;

    public SplatfestAdapter(Context context, ArrayList<SplatfestDatabase> splatfests, View.OnClickListener onClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.input = splatfests;
        this.context = context;
        this.onClickListener = onClickListener;
    }
    @Override
    public SplatfestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SplatfestViewHolder viewHolder = new SplatfestViewHolder(inflater,parent,context);
        viewHolder.itemView.setOnClickListener(onClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SplatfestViewHolder holder, final int position) {
        Splatfest splatfest = input.get(position).splatfest;
        holder.manageHolder(splatfest);
    }

    @Override
    public int getItemCount() {
        return input.size();
    }

}
