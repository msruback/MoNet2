package com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.adapter.RecyclerView.RegularRotationAdapter;
import com.mattrubacky.monet2.deserialized.Splatfest;
import com.mattrubacky.monet2.deserialized.TimePeriod;

import java.util.ArrayList;

/**
 * Created by mattr on 1/14/2018.
 */

public class SplatfestCardViewHolder extends RecyclerView.ViewHolder{

    public RecyclerView SplatfestPager;
    public TextView splatfestTitle;
    public Context context;

    public SplatfestCardViewHolder(LayoutInflater inflater, ViewGroup parent, Context context) {
        super(inflater.inflate(R.layout.card_regular, parent, false));

        this.context = context;

        splatfestTitle = (TextView) itemView.findViewById(R.id.fesName);

        SplatfestPager = (RecyclerView) itemView.findViewById(R.id.FesPager);
    }

    public void manageHolder(ArrayList<TimePeriod> timePeriods,Splatfest splatfest){
        Typeface font = Typeface.createFromAsset(context.getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(context.getAssets(), "Paintball.otf");

        splatfestTitle.setTypeface(fontTitle);
        RegularRotationAdapter regularRotationAdapter = new RegularRotationAdapter(context,timePeriods);
        SplatfestPager.setAdapter(regularRotationAdapter);
        SplatfestPager.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(SplatfestPager);
    }
}