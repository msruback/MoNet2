package com.mattrubacky.monet2.adapter.RecyclerView;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders.SplatfestViewHolder;
import com.mattrubacky.monet2.deserialized.Splatfest;
import com.mattrubacky.monet2.deserialized.SplatfestDatabase;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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
