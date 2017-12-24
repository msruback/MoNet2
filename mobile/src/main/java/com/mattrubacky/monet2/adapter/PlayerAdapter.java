package com.mattrubacky.monet2.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.adapter.ViewHolders.ListViewHolder;
import com.mattrubacky.monet2.adapter.ViewHolders.UserDetailViewHolder;
import com.mattrubacky.monet2.adapter.ViewHolders.UserStatsViewHolder;
import com.mattrubacky.monet2.deserialized.Challenge;
import com.mattrubacky.monet2.deserialized.NicknameIcon;
import com.mattrubacky.monet2.deserialized.Record;
import com.mattrubacky.monet2.fragment.SplatfestDetail.SoloMeterFragment;
import com.mattrubacky.monet2.helper.ClosetHanger;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.mattrubacky.monet2.helper.PlayerStats;
import com.squareup.picasso.Picasso;

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
    private int curPos;

    public PlayerAdapter(Context context, Record records, NicknameIcon icon,FragmentManager fragmentManager) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.records = records;
        this.icon = icon;
        this.fragmentManager = fragmentManager;

        curPos = 0;
        stats = new PlayerStats();
        stats.calcStats(context);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case 0:
                return new UserDetailViewHolder(inflater,parent);
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
            holder.userCard.setClipToOutline(true);
            holder.icon.setClipToOutline(true);

            holder.name.setTypeface(fontTitle);
            holder.levelTitle.setTypeface(fontTitle);
            holder.rankTitle.setTypeface(fontTitle);
            holder.splatzonesTitle.setTypeface(fontTitle);
            holder.towerControlTitle.setTypeface(fontTitle);
            holder.rainmakerTitle.setTypeface(fontTitle);
            holder.clamBlitzTitle.setTypeface(fontTitle);

            holder.level.setTypeface(font);
            holder.splatzonesRank.setTypeface(font);
            holder.towerControlRank.setTypeface(font);
            holder.rainmakerRank.setTypeface(font);
            holder.clamBlitzRank.setTypeface(font);

            holder.name.setText(records.records.user.name);
            holder.level.setText(String.valueOf(records.records.user.rank));
            if (records.records.user.splatzones.sPlus != null) {
                holder.splatzonesRank.setText(records.records.user.splatzones.rank + "+" + records.records.user.splatzones.sPlus);
            } else {
                holder.splatzonesRank.setText(records.records.user.splatzones.rank);
            }

            if (records.records.user.tower.sPlus != null) {
                holder.towerControlRank.setText(records.records.user.tower.rank + "+" + records.records.user.tower.sPlus);
            } else {
                holder.towerControlRank.setText(records.records.user.tower.rank);
            }

            if (records.records.user.rainmaker.sPlus != null) {
                holder.rainmakerRank.setText(records.records.user.rainmaker.rank + "+" + records.records.user.rainmaker.sPlus);
            } else {
                holder.rainmakerRank.setText(records.records.user.rainmaker.rank);
            }

            if (records.records.user.clam.sPlus != null) {
                holder.clamBlitzRank.setText(records.records.user.clam.rank + "+" + records.records.user.clam.sPlus);
            } else {
                holder.clamBlitzRank.setText(records.records.user.clam.rank);
            }
            if (icon != null) {
                String url = "https://app.splatoon2.nintendo.net" + icon.url;
                String location = icon.nickname.toLowerCase().replace(" ", "_");
                Picasso.with(context).load(url).into(holder.user);
                imageHandler.downloadImage("weapon", location, url, context);
            }

            ArrayList<ClosetHanger> gear = new ArrayList<>();
            ClosetHanger hanger = new ClosetHanger();
            hanger.gear = records.records.user.head;
            hanger.skills = records.records.user.headSkills;
            gear.add(hanger);
            hanger = new ClosetHanger();
            hanger.gear = records.records.user.clothes;
            hanger.skills = records.records.user.clothesSkills;
            gear.add(hanger);
            hanger = new ClosetHanger();
            hanger.gear = records.records.user.shoes;
            hanger.skills = records.records.user.shoeSkills;
            gear.add(hanger);
            PlayerGearAdapter playerGearAdapter = new PlayerGearAdapter(context, records.records.user.weapon, gear);
            holder.weaponGearList.setAdapter(playerGearAdapter);
            holder.weaponGearList.setLayoutManager(new GridLayoutManager(context, 2));
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