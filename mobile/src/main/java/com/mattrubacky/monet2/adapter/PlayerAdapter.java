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

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder>{

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
    public PlayerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(curPos==0) {
            view = inflater.inflate(R.layout.item_user_detail, parent, false);
        }else if(curPos==1){
            view = inflater.inflate(R.layout.item_pager_list, parent, false);
        }else{
            view = inflater.inflate(R.layout.item_user_stats, parent, false);
        }
        PlayerAdapter.ViewHolder viewHolder = new PlayerAdapter.ViewHolder(view);
        viewHolder.curPos = curPos;
        curPos++;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PlayerAdapter.ViewHolder holder, final int position) {
        Typeface font = Typeface.createFromAsset(context.getAssets(),"Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(context.getAssets(), "Paintball.otf");
        ImageHandler imageHandler = new ImageHandler();
        switch(position){
            case 0:
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
                if(records.records.user.splatzones.sPlus!=null){
                    holder.splatzonesRank.setText(records.records.user.splatzones.rank+"+"+records.records.user.splatzones.sPlus);
                }else {
                    holder.splatzonesRank.setText(records.records.user.splatzones.rank);
                }

                if(records.records.user.tower.sPlus!=null){
                    holder.towerControlRank.setText(records.records.user.tower.rank+"+"+records.records.user.tower.sPlus);
                }else{
                    holder.towerControlRank.setText(records.records.user.tower.rank);
                }

                if(records.records.user.rainmaker.sPlus!=null){
                    holder.rainmakerRank.setText(records.records.user.rainmaker.rank+"+"+records.records.user.rainmaker.sPlus);
                }else{
                    holder.rainmakerRank.setText(records.records.user.rainmaker.rank);
                }

                if(records.records.user.clam.sPlus!=null){
                    holder.clamBlitzRank.setText(records.records.user.clam.rank+"+"+records.records.user.clam.sPlus);
                }else{
                    holder.clamBlitzRank.setText(records.records.user.clam.rank);
                }

                String url = "https://app.splatoon2.nintendo.net" + icon.url;
                String location = icon.nickname.toLowerCase().replace(" ", "_");
                Picasso.with(context).load(url).into(holder.user);
                imageHandler.downloadImage("weapon", location, url, context);

                ArrayList<ClosetHanger> gear = new ArrayList<>();
                ClosetHanger hanger = new ClosetHanger();
                hanger.gear = records.records.user.head;
                hanger.skills = records.records.user.headSkills;
                gear.add(hanger);
                hanger.gear = records.records.user.clothes;
                hanger.skills = records.records.user.clothesSkills;
                gear.add(hanger);
                hanger.gear = records.records.user.shoes;
                hanger.skills = records.records.user.shoeSkills;
                gear.add(hanger);
                PlayerGearAdapter playerGearAdapter = new PlayerGearAdapter(context,records.records.user.weapon,gear);
                holder.weaponGearList.setAdapter(playerGearAdapter);
                holder.weaponGearList.setLayoutManager(new GridLayoutManager(context,2));
                break;
            case 1:
                ArrayList<Challenge> challenges = new ArrayList<>();
                challenges.add(records.challenges.nextChallenge);
                challenges.addAll(records.challenges.challenges);
                ChallengeAdapter challengeAdapter = new ChallengeAdapter(context,challenges,records.challenges.totalPaint);
                holder.itemList.setAdapter(challengeAdapter);
                holder.itemList.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
                break;
            case 2:
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
                float total = records.records.wins+records.records.losses;
                float width = records.records.wins/total;

                width *= 250;
                layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
                holder.wins.setLayoutParams(layoutParams);

                layoutParams = holder.losses.getLayoutParams();
                width = records.records.losses/total;
                width *= 250;
                layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
                holder.losses.setLayoutParams(layoutParams);

                boolean hasStats = false;

                //Ink card
                float range = stats.inkStats[4] - stats.inkStats[0];
                if(range!=0) {
                    Bundle bundle = new Bundle();
                    bundle.putIntArray("stats",stats.inkStats);
                    Fragment ink = new SoloMeterFragment();
                    ink.setArguments(bundle);
                    fragmentManager.beginTransaction()
                            .replace(R.id.InkMeter,ink)
                            .commit();
                    hasStats = true;
                }else{
                    holder.inkCard.setVisibility(View.GONE);
                }

                //Kill card
                range = stats.killStats[4] - stats.killStats[0];
                if(range!=0) {
                    Bundle bundle = new Bundle();
                    bundle.putIntArray("stats",stats.killStats);
                    Fragment kill = new SoloMeterFragment();
                    kill.setArguments(bundle);
                    fragmentManager.beginTransaction()
                            .replace(R.id.KillMeter,kill)
                            .commit();
                    hasStats = true;
                }else{
                    holder.killCard.setVisibility(View.GONE);
                }

                //Death Card

                range = stats.deathStats[4] - stats.deathStats[0];
                if(range!=0) {
                    Bundle bundle = new Bundle();
                    bundle.putIntArray("stats",stats.deathStats);
                    Fragment death = new SoloMeterFragment();
                    death.setArguments(bundle);
                    fragmentManager.beginTransaction()
                            .replace(R.id.DeathMeter,death)
                            .commit();
                    hasStats = true;
                }else{
                    holder.deathCard.setVisibility(View.GONE);
                }

                //Special Card

                range = stats.specialStats[4] - stats.specialStats[0];
                if(range!=0) {
                    Bundle bundle = new Bundle();
                    bundle.putIntArray("stats",stats.specialStats);
                    Fragment special = new SoloMeterFragment();
                    special.setArguments(bundle);
                    fragmentManager.beginTransaction()
                            .replace(R.id.SpecialMeter,special)
                            .commit();
                    hasStats = true;
                }else{
                    holder.specialCard.setVisibility(View.GONE);
                }
                if(hasStats){
                    holder.noCard.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        //User Detail
        RecyclerView weaponGearList;
        RelativeLayout userCard,icon;
        ImageView user,favoriteStage;
        TextView name,levelTitle,rankTitle,splatzonesTitle,towerControlTitle,rainmakerTitle,clamBlitzTitle;
        TextView level,splatzonesRank,towerControlRank,rainmakerRank,clamBlitzRank;
        //Challenges
        RecyclerView itemList;
        //User Stats
        RelativeLayout generalCard,inkCard,killCard,deathCard,specialCard,noCard;
        RelativeLayout winLossMeter,wins,losses,inkMeter,killMeter,deathMeter,specialMeter;
        TextView totalInkTitle,firstPlayedTitle,lastPlayedTitle,inkTitle,killTitle,deathTitle,specialTitle,noStats;
        TextView winText,lossText,inkedText,firstPlayedText,lastPlayedText;


        int curPos;


        public ViewHolder(View itemView) {
            super(itemView);
            if(curPos==0){

                userCard = (RelativeLayout) itemView.findViewById(R.id.UserCard);
                icon = (RelativeLayout) itemView.findViewById(R.id.icon);

                user = (ImageView) itemView.findViewById(R.id.UserIcon);
                favoriteStage = (ImageView) itemView.findViewById(R.id.Stage);

                levelTitle = (TextView) itemView.findViewById(R.id.LevelTitle);
                rankTitle = (TextView) itemView.findViewById(R.id.RankTitle);
                splatzonesTitle = (TextView) itemView.findViewById(R.id.Splatzones);
                towerControlTitle = (TextView) itemView.findViewById(R.id.TowerControl);
                rainmakerTitle = (TextView) itemView.findViewById(R.id.Rainmaker);
                clamBlitzTitle = (TextView) itemView.findViewById(R.id.ClamBlitz);

                name = (TextView) itemView.findViewById(R.id.Name);
                level = (TextView) itemView.findViewById(R.id.Level);
                splatzonesRank = (TextView) itemView.findViewById(R.id.SplatzonesRank);
                towerControlRank = (TextView) itemView.findViewById(R.id.TowerControlRank);
                rainmakerRank = (TextView) itemView.findViewById(R.id.RainmakerRank);
                clamBlitzRank = (TextView) itemView.findViewById(R.id.ClamBlitzRank);

                weaponGearList = (RecyclerView) itemView.findViewById(R.id.WeaponGearList);
            }else if(curPos ==1){
                itemList = (RecyclerView) itemView.findViewById(R.id.List);
            }else {

                generalCard = (RelativeLayout) itemView.findViewById(R.id.generalStats);
                inkCard = (RelativeLayout) itemView.findViewById(R.id.inkStats);
                killCard = (RelativeLayout) itemView.findViewById(R.id.killStats);
                deathCard = (RelativeLayout) itemView.findViewById(R.id.deathStats);
                specialCard = (RelativeLayout) itemView.findViewById(R.id.specialStats);
                noCard = (RelativeLayout) itemView.findViewById(R.id.noStats);

                winLossMeter = (RelativeLayout) itemView.findViewById(R.id.WinLossMeter);
                wins = (RelativeLayout) itemView.findViewById(R.id.Wins);
                losses = (RelativeLayout) itemView.findViewById(R.id.Losses);
                inkMeter = (RelativeLayout) itemView.findViewById(R.id.InkMeter);
                killMeter = (RelativeLayout) itemView.findViewById(R.id.KillMeter);
                deathMeter = (RelativeLayout) itemView.findViewById(R.id.DeathMeter);
                specialMeter = (RelativeLayout) itemView.findViewById(R.id.SpecialMeter);

                totalInkTitle = (TextView) itemView.findViewById(R.id.InkedTitleText);
                firstPlayedTitle = (TextView) itemView.findViewById(R.id.FirstTitleText);
                lastPlayedTitle = (TextView) itemView.findViewById(R.id.LastTitleText);
                inkTitle = (TextView) itemView.findViewById(R.id.InkTitle);
                killTitle = (TextView) itemView.findViewById(R.id.KillTitle);
                deathTitle = (TextView) itemView.findViewById(R.id.DeathTitle);
                specialTitle = (TextView) itemView.findViewById(R.id.SpecialTitle);
                noStats = (TextView) itemView.findViewById(R.id.NoStatsText);

                winText = (TextView) itemView.findViewById(R.id.WinText);
                lossText = (TextView) itemView.findViewById(R.id.LossText);
                inkedText = (TextView) itemView.findViewById(R.id.InkedText);
                firstPlayedText = (TextView) itemView.findViewById(R.id.FirstText);
                lastPlayedText = (TextView) itemView.findViewById(R.id.LastText);
            }

        }

    }

}