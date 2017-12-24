package com.mattrubacky.monet2.adapter.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;

/**
 * Created by mattr on 12/24/2017.
 */

public class UserDetailViewHolder extends RecyclerView.ViewHolder{

    public RecyclerView weaponGearList;
    public RelativeLayout userCard,icon;
    public ImageView user,favoriteStage;
    public TextView name,levelTitle,rankTitle,splatzonesTitle,towerControlTitle,rainmakerTitle,clamBlitzTitle;
    public TextView level,splatzonesRank,towerControlRank,rainmakerRank,clamBlitzRank;;

    public UserDetailViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.item_user_detail, parent, false));

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
}
