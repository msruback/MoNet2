package com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.ui.adapter.RecyclerView.SplatfestPerformanceAdapter;
import com.mattrubacky.monet2.data.deserialized.splatoon.Splatfest;
import com.mattrubacky.monet2.data.deserialized.splatoon.SplatfestResult;
import com.mattrubacky.monet2.data.stats.SplatfestStats;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 12/24/2017.
 */

public class SplatfestGeneralStatsViewHolder extends RecyclerView.ViewHolder{
    public RelativeLayout card,zigzag;
    public RecyclerView pager;
    private Context context;

    public SplatfestGeneralStatsViewHolder(LayoutInflater inflater, ViewGroup parent,Context context) {
        super(inflater.inflate(R.layout.item_pager_list, parent, false));
        this.context = context;
        card = itemView.findViewById(R.id.generalStats);
        zigzag = itemView.findViewById(R.id.zigzag);
        pager = itemView.findViewById(R.id.GeneralStatsPager);
    }
    public void manageHolder(Splatfest splatfest, SplatfestResult result, SplatfestStats stats){
        card.setClipToOutline(true);
        SplatfestPerformanceAdapter splatfestPerformanceAdapter = new SplatfestPerformanceAdapter(context,splatfest,result,stats);
        pager.setAdapter(splatfestPerformanceAdapter);
        pager.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(pager);

        zigzag.setBackground(context.getResources().getDrawable(R.drawable.repeat_zigzag_splatfest));
        zigzag.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));
        card.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.alpha.getColor())));
    }
}
