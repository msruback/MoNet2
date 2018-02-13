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
import com.mattrubacky.monet2.deserialized.TimePeriod;

import java.util.ArrayList;

/**
 * Created by mattr on 1/14/2018.
 */

public class RegularCardViewHolder extends RecyclerView.ViewHolder{

    public RecyclerView TurfPager;
    public TabLayout turfDots;
    public TextView turfWarTitle;
    public Context context;

    public RegularCardViewHolder(LayoutInflater inflater, ViewGroup parent,Context context) {
        super(inflater.inflate(R.layout.card_regular, parent, false));

        this.context = context;

        turfWarTitle = (TextView) itemView.findViewById(R.id.turfWarName);

        TurfPager = (RecyclerView) itemView.findViewById(R.id.TurfPager);
        turfDots = (TabLayout) itemView.findViewById(R.id.TurfDots);
    }

    public void manageHolder(ArrayList<TimePeriod> timePeriods){
        Typeface font = Typeface.createFromAsset(context.getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(context.getAssets(), "Paintball.otf");

        turfWarTitle.setTypeface(fontTitle);
        RegularRotationAdapter regularRotationAdapter = new RegularRotationAdapter(context,timePeriods);
        TurfPager.setAdapter(regularRotationAdapter);
        TurfPager.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(TurfPager);
    }
}