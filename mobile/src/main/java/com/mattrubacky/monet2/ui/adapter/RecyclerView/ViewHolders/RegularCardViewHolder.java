package com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.ui.adapter.RecyclerView.RegularRotationAdapter;
import com.mattrubacky.monet2.data.deserialized.splatoon.TimePeriod;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 1/14/2018.
 */

public class RegularCardViewHolder extends RecyclerView.ViewHolder{

    private RecyclerView TurfPager;
    private TextView turfWarTitle;
    public Context context;

    public RegularCardViewHolder(LayoutInflater inflater, ViewGroup parent,Context context) {
        super(inflater.inflate(R.layout.card_regular, parent, false));

        this.context = context;

        turfWarTitle = itemView.findViewById(R.id.turfWarName);

        TurfPager = itemView.findViewById(R.id.TurfPager);
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