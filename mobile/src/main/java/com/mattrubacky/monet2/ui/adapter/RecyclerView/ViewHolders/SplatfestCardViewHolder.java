package com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.ui.adapter.RecyclerView.SplatfestRotationAdapter;
import com.mattrubacky.monet2.data.deserialized.splatoon.Splatfest;
import com.mattrubacky.monet2.data.deserialized.splatoon.TimePeriod;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by mattr on 1/14/2018.
 */

public class SplatfestCardViewHolder extends RecyclerView.ViewHolder{

    private RecyclerView SplatfestPager;
    private TextView splatfestTitle;
    private RelativeLayout fesCard,fesBanner,Alpha,Bravo;
    public Context context;

    public SplatfestCardViewHolder(LayoutInflater inflater, ViewGroup parent, Context context) {
        super(inflater.inflate(R.layout.card_festival, parent, false));

        this.context = context;

        splatfestTitle = itemView.findViewById(R.id.fesName);

        SplatfestPager = itemView.findViewById(R.id.FesPager);
        fesCard = itemView.findViewById(R.id.Festival);
        fesBanner = itemView.findViewById(R.id.fesModeBanner);
        Alpha = itemView.findViewById(R.id.Alpha);
        Bravo = itemView.findViewById(R.id.Bravo);
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