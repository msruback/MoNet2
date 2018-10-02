package com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.adapter.RecyclerView.SplatfestRotationAdapter;
import com.mattrubacky.monet2.deserialized.splatoon.Splatfest;
import com.mattrubacky.monet2.deserialized.splatoon.TimePeriod;

import java.util.ArrayList;

/**
 * Created by mattr on 1/14/2018.
 */

public class SplatfestCardViewHolder extends RecyclerView.ViewHolder{

    public RecyclerView SplatfestPager;
    public TextView splatfestTitle;
    RelativeLayout fesCard,fesBanner,Alpha,Bravo;
    public Context context;

    public SplatfestCardViewHolder(LayoutInflater inflater, ViewGroup parent, Context context) {
        super(inflater.inflate(R.layout.card_festival, parent, false));

        this.context = context;

        splatfestTitle = (TextView) itemView.findViewById(R.id.fesName);

        SplatfestPager = (RecyclerView) itemView.findViewById(R.id.FesPager);
        fesCard = (RelativeLayout) itemView.findViewById(R.id.Festival);
        fesBanner = (RelativeLayout) itemView.findViewById(R.id.fesModeBanner);
        Alpha = (RelativeLayout) itemView.findViewById(R.id.Alpha);
        Bravo = (RelativeLayout) itemView.findViewById(R.id.Bravo);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(SplatfestPager);
    }

    public void manageHolder(ArrayList<TimePeriod> timePeriods,Splatfest splatfest){
        Typeface font = Typeface.createFromAsset(context.getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(context.getAssets(), "Paintball.otf");

        splatfestTitle.setTypeface(fontTitle);
        SplatfestRotationAdapter rotationAdapter = new SplatfestRotationAdapter(context,timePeriods,splatfest);
        SplatfestPager.setAdapter(rotationAdapter);
        SplatfestPager.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));

        fesCard.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.alpha.getColor())));
        fesBanner.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));

        Alpha.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.alpha.getColor())));
        Bravo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));

    }
}