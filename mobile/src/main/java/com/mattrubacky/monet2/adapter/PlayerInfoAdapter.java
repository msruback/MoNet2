package com.mattrubacky.monet2.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.BattleInfo;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.Battle;
import com.mattrubacky.monet2.deserialized.Player;
import com.mattrubacky.monet2.deserialized.Splatfest;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mattr on 11/1/2017.
 */

    public class PlayerInfoAdapter extends RecyclerView.Adapter<PlayerInfoAdapter.ViewHolder>{

    private ArrayList<Player> input = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private RecyclerView listView;
    private boolean isAlly;
    private Battle battle;

    public PlayerInfoAdapter(Context context, ArrayList<Player> input,RecyclerView listView,Battle battle,boolean isAlly) {
        this.inflater = LayoutInflater.from(context);
        this.input = input;
        this.context = context;
        this.listView = listView;
        this.isAlly = isAlly;
        this.battle = battle;
    }
    @Override
    public PlayerInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_player, parent, false);
        PlayerInfoAdapter.ViewHolder viewHolder = new PlayerInfoAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PlayerInfoAdapter.ViewHolder holder, final int position) {
        Typeface font = Typeface.createFromAsset(context.getAssets(),"Splatfont2.ttf");
        ImageHandler imageHandler = new ImageHandler();

        Player player = input.get(position);

        holder.rank.setTypeface(font);
        holder.name.setTypeface(font);
        holder.fesGrade.setTypeface(font);
        holder.points.setTypeface(font);
        holder.killsText.setTypeface(font);
        holder.deathsText.setTypeface(font);
        holder.specialText.setTypeface(font);

        holder.name.setText(player.user.name);
        String point = player.points + "p";
        holder.points.setText(point);
        String kills;
        if (player.assists == 0) {
            kills = String.valueOf(player.kills);
        } else {
            kills = (player.kills+player.assists) + "(" + player.assists + ")";
        }
        holder.killsText.setText(kills);
        String deaths = String.valueOf(player.deaths);
        holder.deathsText.setText(deaths);
        String specials = String.valueOf(player.special);
        holder.specialText.setText(specials);

        String url = "https://app.splatoon2.nintendo.net" + player.user.weapon.url;

        String imageDirName = player.user.weapon.name.toLowerCase().replace(" ", "_");
        if (imageHandler.imageExists("weapon", imageDirName, context)) {
            holder.weapon.setImageBitmap(imageHandler.loadImage("weapon", imageDirName));
        } else {
            Picasso.with(context).load(url).into(holder.weapon);
            imageHandler.downloadImage("weapon", imageDirName, url, context);
        }
        //Set default colors
        if (isAlly) {
            holder.specialIconLayout.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            holder.killsIconLayout.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            holder.deathsIconLayout.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.specialIconLayout.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            holder.killsIconLayout.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            holder.deathsIconLayout.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        }

        String rankString;
        switch (battle.type) {
            case "regular":
                rankString = String.valueOf(player.user.rank);
                holder.rank.setText(rankString);
                holder.fesGrade.setVisibility(View.GONE);
                break;
            case "gachi":
                if (player.user.udamae.sPlus == null) {
                    holder.rank.setText(player.user.udamae.rank);
                } else {
                    rankString = player.user.udamae.rank + player.user.udamae.sPlus;
                    holder.rank.setText(rankString);
                }
                holder.fesGrade.setVisibility(View.GONE);
                break;
            case "league":
                if (player.user.udamae.sPlus == null) {
                    holder.rank.setText(player.user.udamae.rank);
                } else {
                    rankString = player.user.udamae.rank + player.user.udamae.sPlus;
                    holder.rank.setText(rankString);
                }
                holder.fesGrade.setVisibility(View.GONE);
                break;
            case "fes":
                if (isAlly) {
                    holder.killsIconLayout.setBackgroundColor(Color.parseColor(battle.myTheme.color.getColor()));
                    holder.deathsIconLayout.setBackgroundColor(Color.parseColor(battle.myTheme.color.getColor()));
                    holder.specialIcon.setBackgroundColor(Color.parseColor(battle.myTheme.color.getColor()));
                } else {
                    holder.killsIconLayout.setBackgroundColor(Color.parseColor(battle.otherTheme.color.getColor()));
                    holder.deathsIconLayout.setBackgroundColor(Color.parseColor(battle.otherTheme.color.getColor()));
                    holder.specialIcon.setBackgroundColor(Color.parseColor(battle.otherTheme.color.getColor()));
                }
                rankString = String.valueOf(player.user.rank);
                holder.rank.setText(rankString);
                holder.fesGrade.setText(player.user.grade.name);
                break;
        }

        switch (player.user.weapon.special.id) {
            case 0://tentamissiles
                holder.specialIcon.setImageDrawable(context.getDrawable(R.drawable.special_tentamissles));
                break;
            case 1://ink armor
                holder.specialIcon.setImageDrawable(context.getDrawable(R.drawable.special_inkarmor));
                break;
            case 2://splat bombs
                holder.specialIcon.setImageDrawable(context.getDrawable(R.drawable.special_bombrush_splatbombs));
                break;
            case 3://suction bombs
                holder.specialIcon.setImageDrawable(context.getDrawable(R.drawable.special_bombrush_suctionbombs));
                break;
            case 4:
                holder.killsIconLayout.setBackgroundColor(context.getResources().getColor(R.color.grey));

                url = "https://app.splatoon2.nintendo.net" + player.user.weapon.special.url;

                imageHandler = new ImageHandler();
                imageDirName = player.user.weapon.special.name.toLowerCase().replace(" ", "_");
                if (imageHandler.imageExists("special", imageDirName, context)) {
                    holder.specialIcon.setImageBitmap(imageHandler.loadImage("special", imageDirName));
                } else {
                    Picasso.with(context).load(url).into(holder.specialIcon);
                    imageHandler.downloadImage("special", imageDirName, url, context);
                }
                break;
            case 5://curling bombs
                holder.specialIcon.setImageDrawable(context.getDrawable(R.drawable.special_bombrush_curlingbombs));
                break;
            case 6:
                holder.killsIconLayout.setBackgroundColor(context.getResources().getColor(R.color.grey));

                url = "https://app.splatoon2.nintendo.net" + player.user.weapon.special.url;

                imageHandler = new ImageHandler();
                imageDirName = player.user.weapon.special.name.toLowerCase().replace(" ", "_");
                if (imageHandler.imageExists("special", imageDirName, context)) {
                    holder.specialIcon.setImageBitmap(imageHandler.loadImage("special", imageDirName));
                } else {
                    Picasso.with(context).load(url).into(holder.specialIcon);
                    imageHandler.downloadImage("special", imageDirName, url, context);
                }
                break;
            case 7://stingray
                holder.specialIcon.setImageDrawable(context.getDrawable(R.drawable.special_stingray));
                break;
            case 8://inkjet
                holder.specialIcon.setImageDrawable(context.getDrawable(R.drawable.special_inkjet));
                break;
            case 9://splashdown
                holder.specialIcon.setImageDrawable(context.getDrawable(R.drawable.special_splashdown));
                break;
            case 10://ink storm
                holder.specialIcon.setImageDrawable(context.getDrawable(R.drawable.special_inkstorm));
                break;
            case 11://baller
                holder.specialIcon.setImageDrawable(context.getDrawable(R.drawable.special_baller));
                break;
            case 12://bubble blower
                holder.specialIcon.setImageDrawable(context.getDrawable(R.drawable.special_bubbleblower));
                break;
        }

        holder.child.setClipToOutline(true);

        url = "https://app.splatoon2.nintendo.net" + player.user.head.url;
        imageDirName = player.user.head.name.toLowerCase().replace(" ", "_");
        if (imageHandler.imageExists("gear", imageDirName, context)) {
            holder.head.setImageBitmap(imageHandler.loadImage("gear", imageDirName));
        } else {
            Picasso.with(context).load(url).into(holder.head);
            imageHandler.downloadImage("gear", imageDirName, url, context);
        }

        url = "https://app.splatoon2.nintendo.net" + player.user.headSkills.main.url;
        imageHandler = new ImageHandler();
        imageDirName = player.user.headSkills.main.name.toLowerCase().replace(" ", "_");
        if (imageHandler.imageExists("ability", imageDirName, context)) {
            holder.headMain.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
        } else {
            Picasso.with(context).load(url).into(holder.headMain);
            imageHandler.downloadImage("ability", imageDirName, url, context);
        }

        if (player.user.headSkills.subs.size() > 0 && player.user.headSkills.subs.get(0) != null) {
            url = "https://app.splatoon2.nintendo.net" + player.user.headSkills.subs.get(0).url;
            imageHandler = new ImageHandler();
            imageDirName = player.user.headSkills.subs.get(0).name.toLowerCase().replace(" ", "_");
            if (imageHandler.imageExists("ability", imageDirName, context)) {
                holder.headSub1.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
            } else {
                Picasso.with(context).load(url).into(holder.headSub1);
                imageHandler.downloadImage("ability", imageDirName, url, context);
            }
            if (player.user.headSkills.subs.size() > 1 && player.user.headSkills.subs.get(1) != null) {
                url = "https://app.splatoon2.nintendo.net" + player.user.headSkills.subs.get(1).url;
                imageHandler = new ImageHandler();
                imageDirName = player.user.headSkills.subs.get(1).name.toLowerCase().replace(" ", "_");
                if (imageHandler.imageExists("ability", imageDirName, context)) {
                    holder.headSub2.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
                } else {
                    Picasso.with(context).load(url).into(holder.headSub2);
                    imageHandler.downloadImage("ability", imageDirName, url, context);
                }
                if (player.user.headSkills.subs.size() > 2 && player.user.headSkills.subs.get(2) != null) {
                    url = "https://app.splatoon2.nintendo.net" + player.user.headSkills.subs.get(2).url;
                    imageHandler = new ImageHandler();
                    imageDirName = player.user.headSkills.subs.get(2).name.toLowerCase().replace(" ", "_");
                    if (imageHandler.imageExists("ability", imageDirName, context)) {
                        holder.headSub3.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
                    } else {
                        Picasso.with(context).load(url).into(holder.headSub3);
                        imageHandler.downloadImage("ability", imageDirName, url, context);
                    }

                } else {
                    holder.headSub3Layout.setVisibility(View.GONE);
                }
            } else {
                holder.headSub2Layout.setVisibility(View.GONE);
                holder.headSub3Layout.setVisibility(View.GONE);
            }
        } else {
            holder.headSub1Layout.setVisibility(View.GONE);
            holder.headSub2Layout.setVisibility(View.GONE);
            holder.headSub3Layout.setVisibility(View.GONE);
        }

        //CLOTHES

        url = "https://app.splatoon2.nintendo.net" + player.user.clothes.url;
        imageHandler = new ImageHandler();
        imageDirName = player.user.clothes.name.toLowerCase().replace(" ", "_");
        if (imageHandler.imageExists("gear", imageDirName, context)) {
            holder.clothes.setImageBitmap(imageHandler.loadImage("gear", imageDirName));
        } else {
            Picasso.with(context).load(url).into(holder.clothes);
            imageHandler.downloadImage("gear", imageDirName, url, context);
        }

        url = "https://app.splatoon2.nintendo.net" + player.user.clothesSkills.main.url;
        imageHandler = new ImageHandler();
        imageDirName = player.user.clothesSkills.main.name.toLowerCase().replace(" ", "_");
        if (imageHandler.imageExists("ability", imageDirName, context)) {
            holder.clothesMain.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
        } else {
            Picasso.with(context).load(url).into(holder.clothesMain);
            imageHandler.downloadImage("ability", imageDirName, url, context);
        }

        if (player.user.clothesSkills.subs.size() > 0 && player.user.clothesSkills.subs.get(0) != null) {
            url = "https://app.splatoon2.nintendo.net" + player.user.clothesSkills.subs.get(0).url;
            imageHandler = new ImageHandler();
            imageDirName = player.user.clothesSkills.subs.get(0).name.toLowerCase().replace(" ", "_");
            if (imageHandler.imageExists("ability", imageDirName, context)) {
                holder.clothesSub1.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
            } else {
                Picasso.with(context).load(url).into(holder.clothesSub1);
                imageHandler.downloadImage("ability", imageDirName, url, context);
            }
            if (player.user.clothesSkills.subs.size() > 1 && player.user.clothesSkills.subs.get(1) != null) {
                url = "https://app.splatoon2.nintendo.net" + player.user.clothesSkills.subs.get(1).url;
                imageHandler = new ImageHandler();
                imageDirName = player.user.clothesSkills.subs.get(1).name.toLowerCase().replace(" ", "_");
                if (imageHandler.imageExists("ability", imageDirName, context)) {
                    holder.clothesSub2.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
                } else {
                    Picasso.with(context).load(url).into(holder.clothesSub2);
                    imageHandler.downloadImage("ability", imageDirName, url, context);
                }
                if (player.user.clothesSkills.subs.size() > 2 && player.user.clothesSkills.subs.get(2) != null) {
                    url = "https://app.splatoon2.nintendo.net" + player.user.clothesSkills.subs.get(2).url;
                    imageHandler = new ImageHandler();
                    imageDirName = player.user.clothesSkills.subs.get(2).name.toLowerCase().replace(" ", "_");
                    if (imageHandler.imageExists("ability", imageDirName, context)) {
                        holder.clothesSub3.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
                    } else {
                        Picasso.with(context).load(url).into(holder.clothesSub3);
                        imageHandler.downloadImage("ability", imageDirName, url, context);
                    }

                } else {
                    holder.clothesSub3Layout.setVisibility(View.GONE);
                }
            } else {
                holder.clothesSub2Layout.setVisibility(View.GONE);
                holder.clothesSub3Layout.setVisibility(View.GONE);
            }
        } else {
            holder.clothesSub1Layout.setVisibility(View.GONE);
            holder.clothesSub2Layout.setVisibility(View.GONE);
            holder.clothesSub3Layout.setVisibility(View.GONE);
        }

        //SHOES

        url = "https://app.splatoon2.nintendo.net" + player.user.shoes.url;
        imageHandler = new ImageHandler();
        imageDirName = player.user.shoes.name.toLowerCase().replace(" ", "_");
        if (imageHandler.imageExists("gear", imageDirName, context)) {
            holder.shoes.setImageBitmap(imageHandler.loadImage("gear", imageDirName));
        } else {
            Picasso.with(context).load(url).into(holder.shoes);
            imageHandler.downloadImage("gear", imageDirName, url, context);
        }

        url = "https://app.splatoon2.nintendo.net" + player.user.shoeSkills.main.url;
        imageHandler = new ImageHandler();
        imageDirName = player.user.shoeSkills.main.name.toLowerCase().replace(" ", "_");
        if (imageHandler.imageExists("ability", imageDirName, context)) {
            holder.shoesMain.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
        } else {
            Picasso.with(context).load(url).into(holder.shoesMain);
            imageHandler.downloadImage("ability", imageDirName, url, context);
        }

        if (player.user.shoeSkills.subs.size() > 0 && player.user.shoeSkills.subs.get(0) != null) {
            url = "https://app.splatoon2.nintendo.net" + player.user.shoeSkills.subs.get(0).url;
            imageHandler = new ImageHandler();
            imageDirName = player.user.shoeSkills.subs.get(0).name.toLowerCase().replace(" ", "_");
            if (imageHandler.imageExists("ability", imageDirName, context)) {
                holder.shoesSub1.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
            } else {
                Picasso.with(context).load(url).into(holder.shoesSub1);
                imageHandler.downloadImage("ability", imageDirName, url, context);
            }
            if (player.user.shoeSkills.subs.size() > 1 && player.user.shoeSkills.subs.get(1) != null) {
                url = "https://app.splatoon2.nintendo.net" + player.user.shoeSkills.subs.get(1).url;
                imageHandler = new ImageHandler();
                imageDirName = player.user.shoeSkills.subs.get(1).name.toLowerCase().replace(" ", "_");
                if (imageHandler.imageExists("ability", imageDirName, context)) {
                    holder.shoesSub2.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
                } else {
                    Picasso.with(context).load(url).into(holder.shoesSub2);
                    imageHandler.downloadImage("ability", imageDirName, url, context);
                }
                if (player.user.shoeSkills.subs.size() > 2 && player.user.shoeSkills.subs.get(2) != null) {
                    url = "https://app.splatoon2.nintendo.net" + player.user.shoeSkills.subs.get(2).url;
                    imageHandler = new ImageHandler();
                    imageDirName = player.user.shoeSkills.subs.get(2).name.toLowerCase().replace(" ", "_");
                    if (imageHandler.imageExists("ability", imageDirName, context)) {
                        holder.shoesSub3.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
                    } else {
                        Picasso.with(context).load(url).into(holder.shoesSub3);
                        imageHandler.downloadImage("ability", imageDirName, url, context);
                    }

                } else {
                    holder.shoesSub3Layout.setVisibility(View.GONE);
                }
            } else {
                holder.shoesSub2Layout.setVisibility(View.GONE);
                holder.shoesSub3Layout.setVisibility(View.GONE);
            }
        } else {
            holder.shoesSub1Layout.setVisibility(View.GONE);
            holder.shoesSub2Layout.setVisibility(View.GONE);
            holder.shoesSub3Layout.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return input.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout card,specialIconLayout,deathsIconLayout,killsIconLayout;
        RelativeLayout child;
        RelativeLayout gearLayout,headSub1Layout,headSub2Layout,headSub3Layout,clothesSub1Layout,clothesSub2Layout,clothesSub3Layout,shoesSub1Layout,shoesSub2Layout,shoesSub3Layout;

        ImageView weapon,specialIcon;
        ImageView head,headMain,headSub1,headSub2,headSub3,clothes,clothesMain,clothesSub1,clothesSub2,clothesSub3,shoes,shoesMain,shoesSub1,shoesSub2,shoesSub3;
        TextView rank,name,fesGrade,points,killsText,deathsText,specialText;


        public ViewHolder(View itemView) {
            super(itemView);

            card = (RelativeLayout) itemView.findViewById(R.id.playerCard);

            specialIconLayout = (RelativeLayout) itemView.findViewById(R.id.specialIcon);
            deathsIconLayout = (RelativeLayout) itemView.findViewById(R.id.deathsIcon);
            killsIconLayout = (RelativeLayout) itemView.findViewById(R.id.killsIcon);

            weapon = (ImageView) itemView.findViewById(R.id.Weapon);
            specialIcon = (ImageView) itemView.findViewById(R.id.SpecialIcon);

            rank = (TextView) itemView.findViewById(R.id.Rank);
            name = (TextView) itemView.findViewById(R.id.Name);
            fesGrade = (TextView) itemView.findViewById(R.id.FesGrade);
            points = (TextView) itemView.findViewById(R.id.Points);
            killsText = (TextView) itemView.findViewById(R.id.KillsText);
            deathsText = (TextView) itemView.findViewById(R.id.DeathsText);
            specialText = (TextView) itemView.findViewById(R.id.SpecialText);

            child = (RelativeLayout) itemView.findViewById(R.id.child);

            gearLayout = (RelativeLayout) itemView.findViewById(R.id.Gear);
            headSub1Layout = (RelativeLayout) itemView.findViewById(R.id.headSub1);
            headSub2Layout = (RelativeLayout) itemView.findViewById(R.id.headSub2);
            headSub3Layout = (RelativeLayout) itemView.findViewById(R.id.headSub3);
            clothesSub1Layout = (RelativeLayout) itemView.findViewById(R.id.clothesSub1);
            clothesSub2Layout = (RelativeLayout) itemView.findViewById(R.id.clothesSub2);
            clothesSub3Layout = (RelativeLayout) itemView.findViewById(R.id.clothesSub3);
            shoesSub1Layout = (RelativeLayout) itemView.findViewById(R.id.shoesSub1);
            shoesSub2Layout = (RelativeLayout) itemView.findViewById(R.id.shoesSub2);
            shoesSub3Layout = (RelativeLayout) itemView.findViewById(R.id.shoesSub3);

            head = (ImageView) itemView.findViewById(R.id.HeadGear);
            headMain = (ImageView) itemView.findViewById(R.id.HeadMain);
            headSub1 = (ImageView) itemView.findViewById(R.id.HeadSub1);
            headSub2 = (ImageView) itemView.findViewById(R.id.HeadSub2);
            headSub3 = (ImageView) itemView.findViewById(R.id.HeadSub3);
            clothes = (ImageView) itemView.findViewById(R.id.ClothesGear);
            clothesMain = (ImageView) itemView.findViewById(R.id.ClothesMain);
            clothesSub1 = (ImageView) itemView.findViewById(R.id.ClothesSub1);
            clothesSub2 = (ImageView) itemView.findViewById(R.id.ClothesSub2);
            clothesSub3 = (ImageView) itemView.findViewById(R.id.ClothesSub3);
            shoes = (ImageView) itemView.findViewById(R.id.ShoesGear);
            shoesMain = (ImageView) itemView.findViewById(R.id.ShoesMain);
            shoesSub1 = (ImageView) itemView.findViewById(R.id.ShoesSub1);
            shoesSub2 = (ImageView) itemView.findViewById(R.id.ShoesSub2);
            shoesSub3 = (ImageView) itemView.findViewById(R.id.ShoesSub3);

        }

    }

}