package com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.ui.adapter.RecyclerView.PlayerGearAdapter;
import com.mattrubacky.monet2.data.deserialized.splatoon.NicknameIcon;
import com.mattrubacky.monet2.data.deserialized.splatoon.Record;
import com.mattrubacky.monet2.data.stats.GearStats;
import com.mattrubacky.monet2.backend.ImageHandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 12/24/2017.
 */

public class UserDetailViewHolder extends RecyclerView.ViewHolder{

    private RecyclerView weaponGearList;
    public RelativeLayout userCard,icon;
    private ImageView user;
    private TextView name,levelTitle,rankTitle,splatzonesTitle,towerControlTitle,rainmakerTitle,clamBlitzTitle;
    private TextView level,splatzonesRank,towerControlRank,rainmakerRank,clamBlitzRank;
    private Context context;

    public UserDetailViewHolder(LayoutInflater inflater, ViewGroup parent, Context context) {
        super(inflater.inflate(R.layout.item_user_detail, parent, false));

        this.context = context;

        userCard = itemView.findViewById(R.id.UserCard);
        icon = itemView.findViewById(R.id.icon);

        user = itemView.findViewById(R.id.UserIcon);

        levelTitle = itemView.findViewById(R.id.LevelTitle);
        rankTitle = itemView.findViewById(R.id.RankTitle);
        splatzonesTitle = itemView.findViewById(R.id.Splatzones);
        towerControlTitle = itemView.findViewById(R.id.TowerControl);
        rainmakerTitle = itemView.findViewById(R.id.Rainmaker);
        clamBlitzTitle = itemView.findViewById(R.id.ClamBlitz);

        name = itemView.findViewById(R.id.Name);
        level = itemView.findViewById(R.id.Level);
        splatzonesRank = itemView.findViewById(R.id.SplatzonesRank);
        towerControlRank = itemView.findViewById(R.id.TowerControlRank);
        rainmakerRank = itemView.findViewById(R.id.RainmakerRank);
        clamBlitzRank = itemView.findViewById(R.id.ClamBlitzRank);

        weaponGearList = itemView.findViewById(R.id.WeaponGearList);
    }

    public void manageHolder(Record records, NicknameIcon nicknameIcon){
        Typeface font = Typeface.createFromAsset(context.getAssets(),"Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(context.getAssets(), "Paintball.otf");
        ImageHandler imageHandler = new ImageHandler();

        userCard.setClipToOutline(true);
        icon.setClipToOutline(true);

        name.setTypeface(fontTitle);
        levelTitle.setTypeface(fontTitle);
        rankTitle.setTypeface(fontTitle);
        splatzonesTitle.setTypeface(fontTitle);
        towerControlTitle.setTypeface(fontTitle);
        rainmakerTitle.setTypeface(fontTitle);
        clamBlitzTitle.setTypeface(fontTitle);

        level.setTypeface(font);
        splatzonesRank.setTypeface(font);
        towerControlRank.setTypeface(font);
        rainmakerRank.setTypeface(font);
        clamBlitzRank.setTypeface(font);

        name.setText(records.records.user.name);
        level.setText(String.valueOf(records.records.user.rank));
        if (records.records.user.splatzones.sPlus != null) {
            splatzonesRank.setText(records.records.user.splatzones.rank + "+" + records.records.user.splatzones.sPlus);
        } else {
            splatzonesRank.setText(records.records.user.splatzones.rank);
        }

        if (records.records.user.tower.sPlus != null) {
            towerControlRank.setText(records.records.user.tower.rank + "+" + records.records.user.tower.sPlus);
        } else {
            towerControlRank.setText(records.records.user.tower.rank);
        }

        if (records.records.user.rainmaker.sPlus != null) {
            rainmakerRank.setText(records.records.user.rainmaker.rank + "+" + records.records.user.rainmaker.sPlus);
        } else {
            rainmakerRank.setText(records.records.user.rainmaker.rank);
        }

        if (records.records.user.clam.sPlus != null) {
            clamBlitzRank.setText(records.records.user.clam.rank + "+" + records.records.user.clam.sPlus);
        } else {
            clamBlitzRank.setText(records.records.user.clam.rank);
        }
        if (nicknameIcon != null) {
            String url = "https://app.splatoon2.nintendo.net" + nicknameIcon.url;
            String location = nicknameIcon.nickname.toLowerCase().replace(" ", "_");
            Picasso.with(context).load(url).into(user);
            imageHandler.downloadImage("weapon", location, url, context);
        }

        ArrayList<GearStats> gear = new ArrayList<>();
        GearStats hanger = new GearStats();

        hanger.gear = records.records.user.head;
        hanger.skills = records.records.user.headSkills;
        gear.add(hanger);
        hanger = new GearStats();
        hanger.gear = records.records.user.clothes;
        hanger.skills = records.records.user.clothesSkills;
        gear.add(hanger);
        hanger = new GearStats();
        hanger.gear = records.records.user.shoes;
        hanger.skills = records.records.user.shoeSkills;
        gear.add(hanger);

        PlayerGearAdapter playerGearAdapter = new PlayerGearAdapter(context, records.records.user.weapon, gear);
        weaponGearList.setAdapter(playerGearAdapter);
        weaponGearList.setLayoutManager(new GridLayoutManager(context, 2));
    }
}
