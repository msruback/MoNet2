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
            case 1:
                return new ListViewHolder(inflater,parent);
            default:
                return new UserStatsViewHolder(inflater,parent);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holderAb, final int position) {
        Typeface font = Typeface.createFromAsset(context.getAssets(),"Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(context.getAssets(), "Paintball.otf");
        ImageHandler imageHandler = new ImageHandler();
        if(holderAb.getItemViewType()==0) {
            UserDetailViewHolder holder = (UserDetailViewHolder) holderAb;
            holder.manageHolder(records,icon);
        }else if(holderAb.getItemViewType()==1) {
            ListViewHolder holder = (ListViewHolder) holderAb;

            ArrayList<Challenge> challenges = new ArrayList<>();
            challenges.add(records.challenges.nextChallenge);
            challenges.addAll(records.challenges.challenges);
            ChallengeAdapter challengeAdapter = new ChallengeAdapter(context, challenges, records.challenges.totalPaint);
            holder.itemList.setAdapter(challengeAdapter);
            holder.itemList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        }else if(holderAb.getItemViewType()==2){
            UserStatsViewHolder holder = (UserStatsViewHolder) holderAb;

            holder.generalCard.setClipToOutline(true);
            holder.inkCard.setClipToOutline(true);
            holder.killCard.setClipToOutline(true);
            holder.deathCard.setClipToOutline(true);
            holder.specialCard.setClipToOutline(true);
            holder.noCard.setClipToOutline(true);

            holder.totalInkTitle.setTypeface(fontTitle);
            holder.firstPlayedTitle.setTypeface(fontTitle);
            holder.lastPlayedTitle.setTypeface(fontTitle);
            holder.inkTitle.setTypeface(fontTitle);
            holder.killTitle.setTypeface(fontTitle);
            holder.deathTitle.setTypeface(fontTitle);
            holder.specialTitle.setTypeface(fontTitle);
            holder.noStats.setTypeface(fontTitle);

            holder.winText.setTypeface(font);
            holder.lossText.setTypeface(font);
            holder.inkedText.setTypeface(font);
            holder.firstPlayedText.setTypeface(font);
            holder.lastPlayedText.setTypeface(font);

            holder.winText.setText(String.valueOf(records.records.wins));
            holder.lossText.setText(String.valueOf(records.records.losses));
            holder.inkedText.setText(String.valueOf(records.challenges.totalPaint));

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyy h:mm:ss a");

            String time = sdf.format(records.records.startTime);

            holder.firstPlayedText.setText(time);

            time = sdf.format(stats.lastPlayed);

            holder.lastPlayedText.setText(time);

            ViewGroup.LayoutParams layoutParams = holder.wins.getLayoutParams();
            float total = records.records.wins + records.records.losses;
            float width = records.records.wins / total;

            width *= 250;
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
            holder.wins.setLayoutParams(layoutParams);

            layoutParams = holder.losses.getLayoutParams();
            width = records.records.losses / total;
            width *= 250;
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
            holder.losses.setLayoutParams(layoutParams);

            boolean hasStats = false;

            //Ink card
            float range = stats.inkStats[4] - stats.inkStats[0];
            if (range != 0) {
                Bundle bundle = new Bundle();
                bundle.putIntArray("stats", stats.inkStats);
                Fragment ink = new SoloMeterFragment();
                ink.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(holder.inkMeter.getId(), ink)
                        .commit();
                hasStats = true;
            } else {
                holder.inkCard.setVisibility(View.GONE);
            }

            //Kill card
            range = stats.killStats[4] - stats.killStats[0];
            if (range != 0) {
                Bundle bundle = new Bundle();
                bundle.putIntArray("stats", stats.killStats);
                Fragment kill = new SoloMeterFragment();
                kill.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(holder.killMeter.getId(), kill)
                        .commit();
                hasStats = true;
            } else {
                holder.killCard.setVisibility(View.GONE);
            }

            //Death Card

            range = stats.deathStats[4] - stats.deathStats[0];
            if (range != 0) {
                Bundle bundle = new Bundle();
                bundle.putIntArray("stats", stats.deathStats);
                Fragment death = new SoloMeterFragment();
                death.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(holder.deathMeter.getId(), death)
                        .commit();
                hasStats = true;
            } else {
                holder.deathCard.setVisibility(View.GONE);
            }

            //Special Card

            range = stats.specialStats[4] - stats.specialStats[0];
            if (range != 0) {
                Bundle bundle = new Bundle();
                bundle.putIntArray("stats", stats.specialStats);
                Fragment special = new SoloMeterFragment();
                special.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(holder.specialMeter.getId(), special)
                        .commit();
                hasStats = true;
            } else {
                holder.specialCard.setVisibility(View.GONE);
            }
            if (hasStats) {
                holder.noCard.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}