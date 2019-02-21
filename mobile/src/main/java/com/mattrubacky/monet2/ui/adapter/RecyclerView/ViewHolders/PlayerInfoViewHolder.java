package com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.data.deserialized.splatoon.Battle;
import com.mattrubacky.monet2.data.deserialized.splatoon.Player;
import com.mattrubacky.monet2.backend.ImageHandler;
import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 12/24/2017.
 */

public class PlayerInfoViewHolder extends RecyclerView.ViewHolder{

    private RelativeLayout card,specialIconLayout,deathsIconLayout,killsIconLayout;
    private RelativeLayout gearLayout,headSub1Layout,headSub2Layout,headSub3Layout,clothesSub1Layout,clothesSub2Layout,clothesSub3Layout,shoesSub1Layout,shoesSub2Layout,shoesSub3Layout;

    private ImageView weapon,killsIcon,deathsIcon,specialIcon;
    private ImageView head,headMain,headSub1,headSub2,headSub3,clothes,clothesMain,clothesSub1,clothesSub2,clothesSub3,shoes,shoesMain,shoesSub1,shoesSub2,shoesSub3;
    private TextView rank,name,fesGrade,points,killsText,deathsText,specialText;

    private Context context;

    public PlayerInfoViewHolder(LayoutInflater inflater, ViewGroup parent,Context context) {
        super(inflater.inflate(R.layout.item_player, parent, false));
        this.context = context;

        card = itemView.findViewById(R.id.playerCard);

        specialIconLayout = itemView.findViewById(R.id.specialIcon);
        deathsIconLayout = itemView.findViewById(R.id.deathsIcon);
        killsIconLayout = itemView.findViewById(R.id.killsIcon);



        weapon = itemView.findViewById(R.id.Weapon);
        killsIcon = itemView.findViewById(R.id.KillsIcon);
        deathsIcon = itemView.findViewById(R.id.DeathsIcon);
        specialIcon = itemView.findViewById(R.id.SpecialIcon);

        rank = itemView.findViewById(R.id.Rank);
        name = itemView.findViewById(R.id.Name);
        fesGrade = itemView.findViewById(R.id.FesGrade);
        points = itemView.findViewById(R.id.Points);
        killsText = itemView.findViewById(R.id.KillsText);
        deathsText = itemView.findViewById(R.id.DeathsText);
        specialText = itemView.findViewById(R.id.SpecialText);

        gearLayout = itemView.findViewById(R.id.Gear);
        headSub1Layout = itemView.findViewById(R.id.headSub1);
        headSub2Layout = itemView.findViewById(R.id.headSub2);
        headSub3Layout = itemView.findViewById(R.id.headSub3);
        clothesSub1Layout = itemView.findViewById(R.id.clothesSub1);
        clothesSub2Layout = itemView.findViewById(R.id.clothesSub2);
        clothesSub3Layout = itemView.findViewById(R.id.clothesSub3);
        shoesSub1Layout = itemView.findViewById(R.id.shoesSub1);
        shoesSub2Layout = itemView.findViewById(R.id.shoesSub2);
        shoesSub3Layout = itemView.findViewById(R.id.shoesSub3);

        head = itemView.findViewById(R.id.HeadGear);
        headMain = itemView.findViewById(R.id.HeadMain);
        headSub1 = itemView.findViewById(R.id.HeadSub1);
        headSub2 = itemView.findViewById(R.id.HeadSub2);
        headSub3 = itemView.findViewById(R.id.HeadSub3);
        clothes = itemView.findViewById(R.id.ClothesGear);
        clothesMain = itemView.findViewById(R.id.ClothesMain);
        clothesSub1 = itemView.findViewById(R.id.ClothesSub1);
        clothesSub2 = itemView.findViewById(R.id.ClothesSub2);
        clothesSub3 = itemView.findViewById(R.id.ClothesSub3);
        shoes = itemView.findViewById(R.id.ShoesGear);
        shoesMain = itemView.findViewById(R.id.ShoesMain);
        shoesSub1 = itemView.findViewById(R.id.ShoesSub1);
        shoesSub2 = itemView.findViewById(R.id.ShoesSub2);
        shoesSub3 = itemView.findViewById(R.id.ShoesSub3);
    }

    public void manageHolder(Player player, Battle battle, boolean isAlly){
        Typeface font = Typeface.createFromAsset(context.getAssets(),"Splatfont2.ttf");
        ImageHandler imageHandler = new ImageHandler();

        card.setClipToOutline(true);

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gearLayout.getVisibility()==View.GONE){
                    gearLayout.setVisibility(View.VISIBLE);
                }else{
                    gearLayout.setVisibility(View.GONE);
                }
            }
        });

        rank.setTypeface(font);
        name.setTypeface(font);
        fesGrade.setTypeface(font);
        points.setTypeface(font);
        killsText.setTypeface(font);
        deathsText.setTypeface(font);
        specialText.setTypeface(font);

        name.setText(player.user.name);
        String point = player.points + "p";
        points.setText(point);
        String kills;
        if (player.assists == 0) {
            kills = String.valueOf(player.kills);
        } else {
            kills = (player.kills+player.assists) + "(" + player.assists + ")";
        }
        killsText.setText(kills);
        String deaths = String.valueOf(player.deaths);
        deathsText.setText(deaths);
        String specials = String.valueOf(player.special);
        specialText.setText(specials);

        String url = "https://app.splatoon2.nintendo.net" + player.user.weapon.url;

        String imageDirName = player.user.weapon.name.toLowerCase().replace(" ", "_");
        if (imageHandler.imageExists("weapon", imageDirName, context)) {
            weapon.setImageBitmap(imageHandler.loadImage("weapon", imageDirName));
        } else {
            Picasso.with(context).load(url).into(weapon);
            imageHandler.downloadImage("weapon", imageDirName, url, context);
        }
        //Set default colors
        if (isAlly) {
            specialIconLayout.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            killsIconLayout.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            deathsIconLayout.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            specialIconLayout.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            killsIconLayout.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            deathsIconLayout.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        }

        //Set Octoling icons
        if(player.user.playerType!=null&&player.user.playerType.species!=null&&player.user.playerType.species.equals("octolings")){
            killsIcon.setImageDrawable(context.getDrawable(R.drawable.icon_octo_kills));
            deathsIcon.setImageDrawable(context.getDrawable(R.drawable.icon_octo_deaths));
        }else{
            killsIcon.setImageDrawable(context.getDrawable(R.drawable.icon_squid_kills));
            deathsIcon.setImageDrawable(context.getDrawable(R.drawable.icon_squid_deaths));
        }

        String rankString;
        switch (battle.type) {
            case "regular":
                rankString = String.valueOf(player.user.rank);
                rank.setText(rankString);
                fesGrade.setVisibility(View.GONE);
                break;
            case "gachi":
                if (player.user.udamae.sPlus == null) {
                    rank.setText(player.user.udamae.rank);
                } else {
                    rankString = player.user.udamae.rank + player.user.udamae.sPlus;
                    rank.setText(rankString);
                }
                fesGrade.setVisibility(View.GONE);
                break;
            case "league":
                if (player.user.udamae.sPlus == null) {
                    rank.setText(player.user.udamae.rank);
                } else {
                    rankString = player.user.udamae.rank + player.user.udamae.sPlus;
                    rank.setText(rankString);
                }
                fesGrade.setVisibility(View.GONE);
                break;
            case "fes":
                if (isAlly) {
                    killsIconLayout.setBackgroundColor(Color.parseColor(battle.myTheme.color.getColor()));
                    deathsIconLayout.setBackgroundColor(Color.parseColor(battle.myTheme.color.getColor()));
                    specialIcon.setBackgroundColor(Color.parseColor(battle.myTheme.color.getColor()));
                } else {
                    killsIconLayout.setBackgroundColor(Color.parseColor(battle.otherTheme.color.getColor()));
                    deathsIconLayout.setBackgroundColor(Color.parseColor(battle.otherTheme.color.getColor()));
                    specialIcon.setBackgroundColor(Color.parseColor(battle.otherTheme.color.getColor()));
                }
                rankString = String.valueOf(player.user.rank);
                rank.setText(rankString);
                fesGrade.setText(player.user.grade.name);
                break;
        }

        switch (player.user.weapon.special.id) {
            case 0://tentamissiles
                specialIcon.setImageDrawable(context.getDrawable(R.drawable.special_tentamissles));
                break;
            case 1://ink armor
                specialIcon.setImageDrawable(context.getDrawable(R.drawable.special_inkarmor));
                break;
            case 2://splat bombs
                specialIcon.setImageDrawable(context.getDrawable(R.drawable.special_bombrush_splatbombs));
                break;
            case 3://suction bombs
                specialIcon.setImageDrawable(context.getDrawable(R.drawable.special_bombrush_suctionbombs));
                break;
            case 4://burst bombs
                specialIcon.setImageDrawable(context.getDrawable(R.drawable.special_bombrush_burstbombs));
                break;
            case 5://curling bombs
                specialIcon.setImageDrawable(context.getDrawable(R.drawable.special_bombrush_curlingbombs));
                break;
            case 6:// autobombs
                specialIcon.setImageDrawable(context.getDrawable(R.drawable.special_bombrush_autobombs));
                break;
            case 7://stingray
                specialIcon.setImageDrawable(context.getDrawable(R.drawable.special_stingray));
                break;
            case 8://inkjet
                specialIcon.setImageDrawable(context.getDrawable(R.drawable.special_inkjet));
                break;
            case 9://splashdown
                specialIcon.setImageDrawable(context.getDrawable(R.drawable.special_splashdown));
                break;
            case 10://ink storm
                specialIcon.setImageDrawable(context.getDrawable(R.drawable.special_inkstorm));
                break;
            case 11://baller
                specialIcon.setImageDrawable(context.getDrawable(R.drawable.special_baller));
                break;
            case 12://bubble blower
                specialIcon.setImageDrawable(context.getDrawable(R.drawable.special_bubbleblower));
                break;
        }

        gearLayout.setClipToOutline(true);

        url = "https://app.splatoon2.nintendo.net" + player.user.head.url;
        imageDirName = player.user.head.name.toLowerCase().replace(" ", "_");
        if (imageHandler.imageExists("gear", imageDirName, context)) {
            head.setImageBitmap(imageHandler.loadImage("gear", imageDirName));
        } else {
            Picasso.with(context).load(url).into(head);
            imageHandler.downloadImage("gear", imageDirName, url, context);
        }

        url = "https://app.splatoon2.nintendo.net" + player.user.headSkills.main.url;
        imageHandler = new ImageHandler();
        imageDirName = player.user.headSkills.main.name.toLowerCase().replace(" ", "_");
        if (imageHandler.imageExists("ability", imageDirName, context)) {
            headMain.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
        } else {
            Picasso.with(context).load(url).into(headMain);
            imageHandler.downloadImage("ability", imageDirName, url, context);
        }

        if (player.user.headSkills.subs.size() > 0 && player.user.headSkills.subs.get(0) != null) {
            url = "https://app.splatoon2.nintendo.net" + player.user.headSkills.subs.get(0).url;
            imageHandler = new ImageHandler();
            imageDirName = player.user.headSkills.subs.get(0).name.toLowerCase().replace(" ", "_");
            if (imageHandler.imageExists("ability", imageDirName, context)) {
                headSub1.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
            } else {
                Picasso.with(context).load(url).into(headSub1);
                imageHandler.downloadImage("ability", imageDirName, url, context);
            }
            if (player.user.headSkills.subs.size() > 1 && player.user.headSkills.subs.get(1) != null) {
                url = "https://app.splatoon2.nintendo.net" + player.user.headSkills.subs.get(1).url;
                imageHandler = new ImageHandler();
                imageDirName = player.user.headSkills.subs.get(1).name.toLowerCase().replace(" ", "_");
                if (imageHandler.imageExists("ability", imageDirName, context)) {
                    headSub2.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
                } else {
                    Picasso.with(context).load(url).into(headSub2);
                    imageHandler.downloadImage("ability", imageDirName, url, context);
                }
                if (player.user.headSkills.subs.size() > 2 && player.user.headSkills.subs.get(2) != null) {
                    url = "https://app.splatoon2.nintendo.net" + player.user.headSkills.subs.get(2).url;
                    imageHandler = new ImageHandler();
                    imageDirName = player.user.headSkills.subs.get(2).name.toLowerCase().replace(" ", "_");
                    if (imageHandler.imageExists("ability", imageDirName, context)) {
                        headSub3.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
                    } else {
                        Picasso.with(context).load(url).into(headSub3);
                        imageHandler.downloadImage("ability", imageDirName, url, context);
                    }

                } else {
                    headSub3Layout.setVisibility(View.GONE);
                }
            } else {
                headSub2Layout.setVisibility(View.GONE);
                headSub3Layout.setVisibility(View.GONE);
            }
        } else {
            headSub1Layout.setVisibility(View.GONE);
            headSub2Layout.setVisibility(View.GONE);
            headSub3Layout.setVisibility(View.GONE);
        }

        //CLOTHES

        url = "https://app.splatoon2.nintendo.net" + player.user.clothes.url;
        imageHandler = new ImageHandler();
        imageDirName = player.user.clothes.name.toLowerCase().replace(" ", "_");
        if (imageHandler.imageExists("gear", imageDirName, context)) {
            clothes.setImageBitmap(imageHandler.loadImage("gear", imageDirName));
        } else {
            Picasso.with(context).load(url).into(clothes);
            imageHandler.downloadImage("gear", imageDirName, url, context);
        }

        url = "https://app.splatoon2.nintendo.net" + player.user.clothesSkills.main.url;
        imageHandler = new ImageHandler();
        imageDirName = player.user.clothesSkills.main.name.toLowerCase().replace(" ", "_");
        if (imageHandler.imageExists("ability", imageDirName, context)) {
            clothesMain.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
        } else {
            Picasso.with(context).load(url).into(clothesMain);
            imageHandler.downloadImage("ability", imageDirName, url, context);
        }

        if (player.user.clothesSkills.subs.size() > 0 && player.user.clothesSkills.subs.get(0) != null) {
            url = "https://app.splatoon2.nintendo.net" + player.user.clothesSkills.subs.get(0).url;
            imageHandler = new ImageHandler();
            imageDirName = player.user.clothesSkills.subs.get(0).name.toLowerCase().replace(" ", "_");
            if (imageHandler.imageExists("ability", imageDirName, context)) {
                clothesSub1.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
            } else {
                Picasso.with(context).load(url).into(clothesSub1);
                imageHandler.downloadImage("ability", imageDirName, url, context);
            }
            if (player.user.clothesSkills.subs.size() > 1 && player.user.clothesSkills.subs.get(1) != null) {
                url = "https://app.splatoon2.nintendo.net" + player.user.clothesSkills.subs.get(1).url;
                imageHandler = new ImageHandler();
                imageDirName = player.user.clothesSkills.subs.get(1).name.toLowerCase().replace(" ", "_");
                if (imageHandler.imageExists("ability", imageDirName, context)) {
                    clothesSub2.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
                } else {
                    Picasso.with(context).load(url).into(clothesSub2);
                    imageHandler.downloadImage("ability", imageDirName, url, context);
                }
                if (player.user.clothesSkills.subs.size() > 2 && player.user.clothesSkills.subs.get(2) != null) {
                    url = "https://app.splatoon2.nintendo.net" + player.user.clothesSkills.subs.get(2).url;
                    imageHandler = new ImageHandler();
                    imageDirName = player.user.clothesSkills.subs.get(2).name.toLowerCase().replace(" ", "_");
                    if (imageHandler.imageExists("ability", imageDirName, context)) {
                        clothesSub3.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
                    } else {
                        Picasso.with(context).load(url).into(clothesSub3);
                        imageHandler.downloadImage("ability", imageDirName, url, context);
                    }

                } else {
                    clothesSub3Layout.setVisibility(View.GONE);
                }
            } else {
                clothesSub2Layout.setVisibility(View.GONE);
                clothesSub3Layout.setVisibility(View.GONE);
            }
        } else {
            clothesSub1Layout.setVisibility(View.GONE);
            clothesSub2Layout.setVisibility(View.GONE);
            clothesSub3Layout.setVisibility(View.GONE);
        }

        //SHOES

        url = "https://app.splatoon2.nintendo.net" + player.user.shoes.url;
        imageHandler = new ImageHandler();
        imageDirName = player.user.shoes.name.toLowerCase().replace(" ", "_");
        if (imageHandler.imageExists("gear", imageDirName, context)) {
            shoes.setImageBitmap(imageHandler.loadImage("gear", imageDirName));
        } else {
            Picasso.with(context).load(url).into(shoes);
            imageHandler.downloadImage("gear", imageDirName, url, context);
        }

        url = "https://app.splatoon2.nintendo.net" + player.user.shoeSkills.main.url;
        imageHandler = new ImageHandler();
        imageDirName = player.user.shoeSkills.main.name.toLowerCase().replace(" ", "_");
        if (imageHandler.imageExists("ability", imageDirName, context)) {
            shoesMain.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
        } else {
            Picasso.with(context).load(url).into(shoesMain);
            imageHandler.downloadImage("ability", imageDirName, url, context);
        }

        if (player.user.shoeSkills.subs.size() > 0 && player.user.shoeSkills.subs.get(0) != null) {
            url = "https://app.splatoon2.nintendo.net" + player.user.shoeSkills.subs.get(0).url;
            imageHandler = new ImageHandler();
            imageDirName = player.user.shoeSkills.subs.get(0).name.toLowerCase().replace(" ", "_");
            if (imageHandler.imageExists("ability", imageDirName, context)) {
                shoesSub1.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
            } else {
                Picasso.with(context).load(url).into(shoesSub1);
                imageHandler.downloadImage("ability", imageDirName, url, context);
            }
            if (player.user.shoeSkills.subs.size() > 1 && player.user.shoeSkills.subs.get(1) != null) {
                url = "https://app.splatoon2.nintendo.net" + player.user.shoeSkills.subs.get(1).url;
                imageHandler = new ImageHandler();
                imageDirName = player.user.shoeSkills.subs.get(1).name.toLowerCase().replace(" ", "_");
                if (imageHandler.imageExists("ability", imageDirName, context)) {
                    shoesSub2.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
                } else {
                    Picasso.with(context).load(url).into(shoesSub2);
                    imageHandler.downloadImage("ability", imageDirName, url, context);
                }
                if (player.user.shoeSkills.subs.size() > 2 && player.user.shoeSkills.subs.get(2) != null) {
                    url = "https://app.splatoon2.nintendo.net" + player.user.shoeSkills.subs.get(2).url;
                    imageHandler = new ImageHandler();
                    imageDirName = player.user.shoeSkills.subs.get(2).name.toLowerCase().replace(" ", "_");
                    if (imageHandler.imageExists("ability", imageDirName, context)) {
                        shoesSub3.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
                    } else {
                        Picasso.with(context).load(url).into(shoesSub3);
                        imageHandler.downloadImage("ability", imageDirName, url, context);
                    }

                } else {
                    shoesSub3Layout.setVisibility(View.GONE);
                }
            } else {
                shoesSub2Layout.setVisibility(View.GONE);
                shoesSub3Layout.setVisibility(View.GONE);
            }
        } else {
            shoesSub1Layout.setVisibility(View.GONE);
            shoesSub2Layout.setVisibility(View.GONE);
            shoesSub3Layout.setVisibility(View.GONE);
        }
    }
}