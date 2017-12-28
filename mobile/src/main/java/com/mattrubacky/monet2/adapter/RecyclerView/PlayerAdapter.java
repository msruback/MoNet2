package com.mattrubacky.monet2.adapter.RecyclerView;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.adapter.RecyclerView.ChallengeAdapter;
import com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders.ListViewHolder;
import com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders.UserDetailViewHolder;
import com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders.UserStatsViewHolder;
import com.mattrubacky.monet2.deserialized.Challenge;
import com.mattrubacky.monet2.deserialized.NicknameIcon;
import com.mattrubacky.monet2.deserialized.Record;
import com.mattrubacky.monet2.fragment.SplatfestDetail.SoloMeterFragment;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.mattrubacky.monet2.helper.PlayerStats;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by mattr on 12/24/2017.
 */

public class PlayerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private LayoutInflater inflater;
    private Context context;
    private Record records;
    private NicknameIcon icon;
    private PlayerStats stats;
    private FragmentManager fragmentManager;

    public PlayerAdapter(Context context, Record records, NicknameIcon icon,FragmentManager fragmentManager) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.records = records;
        this.icon = icon;
        this.fragmentManager = fragmentManager;

        stats = new PlayerStats();
        stats.calcStats(context);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case 0:
                return new UserDetailViewHolder(inflater,parent,context);
            default:
                return new ListViewHolder(inflater,parent);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holderAb, final int position) {
        if(holderAb.getItemViewType()==0) {
            UserDetailViewHolder holder = (UserDetailViewHolder) holderAb;
            holder.manageHolder(records,icon);
            holder.userCard.setBackgroundTintList(context.getResources().getColorStateList(R.color.favColorGreen));
        }else if(holderAb.getItemViewType()==1) {
            ListViewHolder holder = (ListViewHolder) holderAb;

            ArrayList<Challenge> challenges = new ArrayList<>();
            challenges.add(records.challenges.nextChallenge);
            challenges.addAll(records.challenges.challenges);
            ChallengeAdapter challengeAdapter = new ChallengeAdapter(context, challenges, records.challenges.totalPaint);
            holder.itemList.setAdapter(challengeAdapter);
            holder.itemList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        }else if(holderAb.getItemViewType()==2){
            ListViewHolder holder = (ListViewHolder) holderAb;
            PlayerStatAdapter playerStatAdapter = new PlayerStatAdapter(context,stats,records);
            holder.itemList.setAdapter(playerStatAdapter);
            holder.itemList.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}