package com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.adapter.RecyclerView.PlayerGearAdapter;
import com.mattrubacky.monet2.deserialized.splatoon.NicknameIcon;
import com.mattrubacky.monet2.deserialized.splatoon.Record;
import com.mattrubacky.monet2.helper.ClosetHanger;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mattr on 12/24/2017.
 */

public class UserDetailViewHolder extends RecyclerView.ViewHolder{

    public RecyclerView weaponGearList;
    public RelativeLayout userCard,icon;
    public ImageView user,favoriteStage;
    public TextView name,levelTitle,rankTitle,splatzonesTitle,towerControlTitle,rainmakerTitle,clamBlitzTitle;
    public TextView level,splatzonesRank,towerControlRank,rainmakerRank,clamBlitzRank;;
    private Context context;

    public UserDetailViewHolder(LayoutInflater inflater, ViewGroup parent, Context context) {
        super(inflater.inflate(R.layout.item_user_detail, parent, false));

        this.context = context;

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
        weaponGearList.setAdapter(playerGearAdapter);
        weaponGearList.setLayoutManager(new GridLayoutManager(context, 2));
    }
}
