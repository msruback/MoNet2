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
import com.mattrubacky.monet2.adapter.RecyclerView.LeagueRotationAdapter;
import com.mattrubacky.monet2.adapter.RecyclerView.RegularRotationAdapter;
import com.mattrubacky.monet2.deserialized.TimePeriod;

import java.util.ArrayList;

/**
 * Created by mattr on 1/14/2018.
 */

public class LeagueCardViewHolder extends RecyclerView.ViewHolder{

    public RecyclerView LeaguePager;
    public TextView leagueTitle;
    public Context context;

    public LeagueCardViewHolder(LayoutInflater inflater, ViewGroup parent, Context context) {
        super(inflater.inflate(R.layout.card_league, parent, false));

        this.context = context;

        leagueTitle = (TextView) itemView.findViewById(R.id.leagueName);

        LeaguePager = (RecyclerView) itemView.findViewById(R.id.LeaguePager);
    }

    public void manageHolder(ArrayList<TimePeriod> timePeriods){
        Typeface font = Typeface.createFromAsset(context.getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(context.getAssets(), "Paintball.otf");

        leagueTitle.setTypeface(fontTitle);
        LeagueRotationAdapter rotationAdapter = new LeagueRotationAdapter(context,timePeriods);
        LeaguePager.setAdapter(rotationAdapter);
        LeaguePager.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(LeaguePager);
    }
}
