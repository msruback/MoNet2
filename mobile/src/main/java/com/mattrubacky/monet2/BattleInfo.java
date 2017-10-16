package com.mattrubacky.monet2;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class BattleInfo extends AppCompatActivity {
    Battle battle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_info);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int id = bundle.getInt("id");
        SplatnetSQL database = new SplatnetSQL(getApplicationContext());
        battle = database.selectBattle(id);


        RelativeLayout meter = (RelativeLayout) findViewById(R.id.meter);
        meter.setClipToOutline(true);

        RelativeLayout fesMode = (RelativeLayout) findViewById(R.id.FesMode);
        RelativeLayout alpha = (RelativeLayout) findViewById(R.id.Alpha);
        RelativeLayout bravo = (RelativeLayout) findViewById(R.id.Bravo);

        Typeface font = Typeface.createFromAsset(getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(getAssets(), "Paintball.otf");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) findViewById(R.id.title);
        title.setTypeface(fontTitle);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        RelativeLayout allyMeter = (RelativeLayout) findViewById(R.id.AllyMeter);
        RelativeLayout foeMeter = (RelativeLayout) findViewById(R.id.FoeMeter);

        ImageView stageImage = (ImageView) findViewById(R.id.StageImage);
        ImageView modeImage = (ImageView) findViewById(R.id.Mode);

        TextView stageName = (TextView) findViewById(R.id.StageName);
        TextView rule = (TextView) findViewById(R.id.Rule);
        TextView allyTitle = (TextView) findViewById(R.id.allyTitle);
        TextView foeTitle = (TextView) findViewById(R.id.foeTitle);
        TextView allyCount = (TextView) findViewById(R.id.AllyPercent);
        TextView foeCount = (TextView) findViewById(R.id.FoePercent);
        TextView result = (TextView) findViewById(R.id.Result);
        TextView power = (TextView) findViewById(R.id.Power);
        TextView startTime = (TextView) findViewById(R.id.Time);
        TextView elapsedTime = (TextView) findViewById(R.id.Length);

        title.setTypeface(fontTitle);
        stageName.setTypeface(fontTitle);
        rule.setTypeface(fontTitle);
        allyTitle.setTypeface(font);
        foeTitle.setTypeface(font);
        allyCount.setTypeface(font);
        foeCount.setTypeface(font);
        result.setTypeface(fontTitle);
        power.setTypeface(font);
        startTime.setTypeface(font);
        elapsedTime.setTypeface(font);


        SimpleDateFormat startFormat = new SimpleDateFormat("MM/dd/yyyy h:mm a");

        String start = startFormat.format(battle.start*1000);

        title.setText("Battle #"+battle.id);
        result.setText(battle.result.name);
        startTime.setText(start);
        rule.setText(battle.rule.name);
        stageName.setText(battle.stage.name);

        String url = "https://app.splatoon2.nintendo.net"+battle.stage.image;

        ImageHandler imageHandler = new ImageHandler();
        String imageDirName = battle.stage.name.toLowerCase().replace(" ", "_");
        if (imageHandler.imageExists("stage", imageDirName, getApplicationContext())) {
            stageImage.setImageBitmap(imageHandler.loadImage("stage", imageDirName));
        } else {
            Picasso.with(getApplicationContext()).load(url).into(stageImage);
            imageHandler.downloadImage("stage", imageDirName, url, getApplicationContext());
        }

        int total;
        float allyWidth,foeWidth;
        ViewGroup.LayoutParams allyParams,foeParams;
        String percentCount,elapsed;
        SimpleDateFormat elapsedFormat = new SimpleDateFormat("mm:ss");
        switch(battle.type){
            case "regular":
                modeImage.setImageDrawable(getResources().getDrawable(R.drawable.battle_regular));

                fesMode.setVisibility(View.GONE);
                power.setVisibility(View.GONE);
                elapsedTime.setVisibility(View.GONE);

                allyWidth = (float) 2.5*battle.myTeamPercent;
                foeWidth = (float) 2.5*battle.otherTeamPercent;

                allyParams = allyMeter.getLayoutParams();
                allyParams.width =(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, allyWidth, getResources().getDisplayMetrics());
                allyMeter.setLayoutParams(allyParams);

                foeParams = foeMeter.getLayoutParams();
                foeParams.width =(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, foeWidth, getResources().getDisplayMetrics());
                foeMeter.setLayoutParams(foeParams);

                percentCount = battle.myTeamPercent + "%";
                allyCount.setText(percentCount);
                percentCount = battle.otherTeamPercent + "%";
                foeCount.setText(percentCount);
                break;
            case "gachi":
                modeImage.setImageDrawable(getResources().getDrawable(R.drawable.battle_ranked));

                fesMode.setVisibility(View.GONE);

                elapsed = elapsedFormat.format(battle.time*1000);
                elapsedTime.setText(elapsed);
                String powerString = String.valueOf(battle.gachiPower);
                power.setText(powerString);

                total = battle.myTeamCount+battle.otherTeamCount;
                allyWidth = (float) battle.myTeamCount/total;
                allyWidth *= 250;
                foeWidth = (float) battle.otherTeamCount/total;
                foeWidth *= 250;

                allyParams = allyMeter.getLayoutParams();
                allyParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, allyWidth, getResources().getDisplayMetrics());
                allyMeter.setLayoutParams(allyParams);

                foeParams = foeMeter.getLayoutParams();
                foeParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, foeWidth, getResources().getDisplayMetrics());
                foeMeter.setLayoutParams(foeParams);

                percentCount = String.valueOf(battle.myTeamCount);
                allyCount.setText(percentCount);
                percentCount = String.valueOf(battle.otherTeamCount);
                foeCount.setText(percentCount);
                break;
            case "league":
                modeImage.setImageDrawable(getResources().getDrawable(R.drawable.battle_league));

                fesMode.setVisibility(View.GONE);
                power.setVisibility(View.GONE);

                elapsed = elapsedFormat.format(battle.time*1000);
                elapsedTime.setText(elapsed);

                total = battle.myTeamCount+battle.otherTeamCount;
                allyWidth = (float) 250*(battle.myTeamCount/total);
                foeWidth = (float) 250*(battle.otherTeamCount/total);

                allyParams = allyMeter.getLayoutParams();
                allyParams.width =(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, allyWidth, getResources().getDisplayMetrics());
                allyMeter.setLayoutParams(allyParams);

                foeParams = foeMeter.getLayoutParams();
                foeParams.width =(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, foeWidth, getResources().getDisplayMetrics());
                foeMeter.setLayoutParams(foeParams);

                percentCount = String.valueOf(battle.myTeamCount);
                allyCount.setText(percentCount);
                percentCount = String.valueOf(battle.otherTeamCount);
                foeCount.setText(percentCount);
                break;
            case "fes":
                Splatfest splatfest = database.selectSplatfest(battle.splatfestID);

                alpha.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.alpha.getColor())));
                bravo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));

                modeImage.setVisibility(View.GONE);
                elapsedTime.setVisibility(View.GONE);

                powerString = String.valueOf(battle.fesPower);
                power.setText(powerString);

                allyWidth = (float) 2.5*battle.myTeamPercent;
                foeWidth = (float) 2.5*battle.otherTeamPercent;
                allyParams = allyMeter.getLayoutParams();
                allyParams.width =(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, allyWidth, getResources().getDisplayMetrics());
                allyMeter.setLayoutParams(allyParams);

                foeParams = foeMeter.getLayoutParams();
                foeParams.width =(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, foeWidth, getResources().getDisplayMetrics());
                foeMeter.setLayoutParams(foeParams);

                percentCount = battle.myTeamPercent + "%";
                allyCount.setText(percentCount);
                percentCount = battle.otherTeamPercent + "%";
                foeCount.setText(percentCount);

                rule.setText("Splatfest Battle");

        }
        ArrayList<Player> allies = new ArrayList<>();
        allies.add(battle.user);
        allies.addAll(battle.myTeam);
        PlayerAdapter allyAdapter = new PlayerAdapter(getApplicationContext(),allies,true);
        PlayerAdapter foeAdapter = new PlayerAdapter(getApplicationContext(),battle.otherTeam,false);

        final ExpandableListView allyList = (ExpandableListView) findViewById(R.id.AllyList);
        final ExpandableListView foeList = (ExpandableListView) findViewById(R.id.FoeList);

        allyList.setAdapter(allyAdapter);
        foeList.setAdapter(foeAdapter);

        allyList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int height = 0;
                for (int i = 0; i < allyList.getChildCount(); i++) {
                    height += allyList.getChildAt(i).getMeasuredHeight();
                    height += allyList.getDividerHeight();
                }
                height += (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, getResources().getDisplayMetrics());
                ViewGroup.LayoutParams params = allyList.getLayoutParams();
                params.height = (height);
                allyList.setLayoutParams(params);
            }
        });

        // Listview Group collapsed listener
        allyList.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                int height = 0;
                for (int i = 0; i < allyList.getChildCount(); i++) {
                    height += allyList.getChildAt(i).getMeasuredHeight();
                    height += allyList.getDividerHeight();
                }
                ViewGroup.LayoutParams params = allyList.getLayoutParams();
                params.height -= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, getResources().getDisplayMetrics());
                allyList.setLayoutParams(params);
            }
        });

        foeList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int height = 0;
                for (int i = 0; i < foeList.getChildCount(); i++) {
                    height += foeList.getChildAt(i).getMeasuredHeight();
                    height += foeList.getDividerHeight();
                }
                height += (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, getResources().getDisplayMetrics());
                ViewGroup.LayoutParams params = foeList.getLayoutParams();
                params.height = (height);
                foeList.setLayoutParams(params);
            }
        });

        // Listview Group collapsed listener
        foeList.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                int height = 0;
                for (int i = 0; i < foeList.getChildCount(); i++) {
                    height += foeList.getChildAt(i).getMeasuredHeight();
                    height += foeList.getDividerHeight();
                }
                ViewGroup.LayoutParams params = foeList.getLayoutParams();
                params.height -= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, getResources().getDisplayMetrics());
                foeList.setLayoutParams(params);
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("fragment",3);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public class PlayerAdapter extends BaseExpandableListAdapter{

        private Context context;
        private ArrayList<Player> players;
        private boolean isAlly;

        public PlayerAdapter(Context context,ArrayList<Player> players,boolean isAlly){
            this.players = players;
            this.context = context;
            this.isAlly = isAlly;
        }

        @Override
        public int getGroupCount() {
            return players.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return 1;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return players.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return null;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_player_expandable, null);
            }

            Player player = (Player) getGroup(groupPosition);

            Typeface font = Typeface.createFromAsset(getAssets(), "Splatfont2.ttf");
            Typeface fontTitle = Typeface.createFromAsset(getAssets(), "Paintball.otf");

            RelativeLayout card = (RelativeLayout) convertView.findViewById(R.id.playerCard);

            //Special Pieces
            RelativeLayout specialBase = (RelativeLayout) convertView.findViewById(R.id.SpecialBase);
            RelativeLayout specialColor = (RelativeLayout) convertView.findViewById(R.id.SpecialColor);
            RelativeLayout specialOverlay = (RelativeLayout) convertView.findViewById(R.id.SpecialOverlay);

            RelativeLayout killsIcon = (RelativeLayout) convertView.findViewById(R.id.KillsIcon);
            RelativeLayout deathsIcon = (RelativeLayout) convertView.findViewById(R.id.DeathsIcon);

            TextView rank = (TextView) convertView.findViewById(R.id.Rank);
            TextView name = (TextView) convertView.findViewById(R.id.Name);
            TextView fesGrade = (TextView) convertView.findViewById(R.id.FesGrade);
            TextView points = (TextView) convertView.findViewById(R.id.Points);
            TextView killsText = (TextView) convertView.findViewById(R.id.KillsText);
            TextView deathsText = (TextView) convertView.findViewById(R.id.DeathsText);
            TextView specialText = (TextView) convertView.findViewById(R.id.SpecialText);

            ImageView weapon = (ImageView) convertView.findViewById(R.id.Weapon);
            ImageView specialOveride = (ImageView) convertView.findViewById(R.id.SpecialOveride);

            rank.setTypeface(font);
            name.setTypeface(font);
            fesGrade.setTypeface(font);
            points.setTypeface(font);
            killsText.setTypeface(font);
            deathsText.setTypeface(font);
            specialText.setTypeface(font);

            name.setText(player.user.name);
            String point = player.points+"p";
            points.setText(point);
            String kills;
            if(player.assists==0){
                kills = String.valueOf(player.kills);
            }else{
                kills = player.kills +"("+player.assists+")";
            }
            killsText.setText(kills);
            String deaths = String.valueOf(player.deaths);
            deathsText.setText(deaths);
            String specials = String.valueOf(player.special);
            specialText.setText(specials);

            String url = "https://app.splatoon2.nintendo.net"+player.user.weapon.url;

            ImageHandler imageHandler = new ImageHandler();
            String imageDirName = player.user.weapon.name.toLowerCase().replace(" ", "_");
            if (imageHandler.imageExists("weapon", imageDirName, context)) {
                weapon.setImageBitmap(imageHandler.loadImage("weapon", imageDirName));
            } else {
                Picasso.with(context).load(url).into(weapon);
                imageHandler.downloadImage("weapon", imageDirName, url, context);
            }

            switch(player.user.weapon.special.id){
                case 0://tentamissiles

                    specialBase.setVisibility(View.VISIBLE);
                    specialColor.setVisibility(View.VISIBLE);
                    specialOverlay.setVisibility(View.VISIBLE);
                    specialOveride.setVisibility(View.GONE);

                    specialBase.setBackground(getResources().getDrawable(R.drawable.special_tentamissles_base));
                    specialColor.setBackground(getResources().getDrawable(R.drawable.special_tentamissles_color));
                    specialOverlay.setBackground(getResources().getDrawable(R.drawable.special_tentamissles_overlay));
                    break;
                case 1://ink armor

                    specialBase.setVisibility(View.GONE);
                    specialColor.setVisibility(View.VISIBLE);
                    specialOverlay.setVisibility(View.VISIBLE);
                    specialOveride.setVisibility(View.GONE);

                    specialColor.setBackground(getResources().getDrawable(R.drawable.special_inkarmor_color));
                    specialOverlay.setBackground(getResources().getDrawable(R.drawable.special_inkarmor_overlay));
                    break;
                case 2://splat bombs

                    specialBase.setVisibility(View.VISIBLE);
                    specialColor.setVisibility(View.VISIBLE);
                    specialOverlay.setVisibility(View.VISIBLE);
                    specialOveride.setVisibility(View.GONE);

                    specialBase.setBackground(getResources().getDrawable(R.drawable.special_bombrush_splatbombs_base));
                    specialColor.setBackground(getResources().getDrawable(R.drawable.special_bombrush_splatbombs_color));
                    specialOverlay.setBackground(getResources().getDrawable(R.drawable.special_bombrush_splatbombs_overlay));
                    break;
                case 3://suction bombs
                    specialBase.setVisibility(View.VISIBLE);
                    specialColor.setVisibility(View.VISIBLE);
                    specialOverlay.setVisibility(View.VISIBLE);
                    specialOveride.setVisibility(View.GONE);

                    specialBase.setBackground(getResources().getDrawable(R.drawable.special_bombrush_suctionbombs_base));
                    specialColor.setBackground(getResources().getDrawable(R.drawable.special_bombrush_suctionbombs_color));
                    specialOverlay.setBackground(getResources().getDrawable(R.drawable.special_bombrush_suctionbombs_overlay));
                    break;
                case 4:
                    specialBase.setVisibility(View.GONE);
                    specialColor.setVisibility(View.GONE);
                    specialOverlay.setVisibility(View.GONE);
                    specialOveride.setVisibility(View.VISIBLE);
                    url = "https://app.splatoon2.nintendo.net"+player.user.weapon.special.url;

                    imageHandler = new ImageHandler();
                    imageDirName = player.user.weapon.special.name.toLowerCase().replace(" ", "_");
                    if (imageHandler.imageExists("special", imageDirName, context)) {
                        specialOveride.setImageBitmap(imageHandler.loadImage("special", imageDirName));
                    } else {
                        Picasso.with(context).load(url).into(specialOveride);
                        imageHandler.downloadImage("special", imageDirName, url, context);
                    }
                    break;
                case 5://curling bombs
                    specialBase.setVisibility(View.VISIBLE);
                    specialColor.setVisibility(View.VISIBLE);
                    specialOverlay.setVisibility(View.VISIBLE);
                    specialOveride.setVisibility(View.GONE);

                    specialBase.setBackground(getResources().getDrawable(R.drawable.special_bombrush_curlingbombs_base));
                    specialColor.setBackground(getResources().getDrawable(R.drawable.special_bombrush_curlingbombs_color));
                    specialColor.setBackground(getResources().getDrawable(R.drawable.special_bombrush_curlingbombs_overlay));
                    break;
                case 6:
                    url = "https://app.splatoon2.nintendo.net"+player.user.weapon.special.url;

                    imageHandler = new ImageHandler();
                    imageDirName = player.user.weapon.special.name.toLowerCase().replace(" ", "_");
                    if (imageHandler.imageExists("special", imageDirName, context)) {
                        specialOveride.setImageBitmap(imageHandler.loadImage("special", imageDirName));
                    } else {
                        Picasso.with(context).load(url).into(specialOveride);
                        imageHandler.downloadImage("special", imageDirName, url, context);
                    }
                    break;
                case 7://stingray
                    specialBase.setVisibility(View.VISIBLE);
                    specialColor.setVisibility(View.VISIBLE);
                    specialOverlay.setVisibility(View.VISIBLE);
                    specialOveride.setVisibility(View.GONE);

                    specialBase.setBackground(getResources().getDrawable(R.drawable.special_stingray_base));
                    specialColor.setBackground(getResources().getDrawable(R.drawable.special_stingray_color));
                    specialOverlay.setBackground(getResources().getDrawable(R.drawable.special_stingray_overlay));
                    break;
                case 8://inkjet
                    specialBase.setVisibility(View.VISIBLE);
                    specialColor.setVisibility(View.VISIBLE);
                    specialOverlay.setVisibility(View.VISIBLE);
                    specialOveride.setVisibility(View.GONE);

                    specialBase.setBackground(getResources().getDrawable(R.drawable.special_inkjet_base));
                    specialColor.setBackground(getResources().getDrawable(R.drawable.special_inkjet_color));
                    specialOverlay.setBackground(getResources().getDrawable(R.drawable.special_inkjet_overlay));
                    break;
                case 9://splashdown
                    specialBase.setVisibility(View.VISIBLE);
                    specialColor.setVisibility(View.VISIBLE);
                    specialOverlay.setVisibility(View.VISIBLE);
                    specialOveride.setVisibility(View.GONE);

                    specialBase.setBackground(getResources().getDrawable(R.drawable.special_splashdown_base));
                    specialColor.setBackground(getResources().getDrawable(R.drawable.special_splashdown_color));
                    specialOverlay.setBackground(getResources().getDrawable(R.drawable.special_splashdown_overlay));
                    break;
                case 10://ink storm
                    specialBase.setVisibility(View.GONE);
                    specialColor.setVisibility(View.VISIBLE);
                    specialOverlay.setVisibility(View.VISIBLE);
                    specialOveride.setVisibility(View.GONE);

                    specialColor.setBackground(getResources().getDrawable(R.drawable.special_inkstorm_color));
                    specialOverlay.setBackground(getResources().getDrawable(R.drawable.special_inkstorm_overlay));
                    break;
                case 11://baller
                    specialBase.setVisibility(View.GONE);
                    specialColor.setVisibility(View.VISIBLE);
                    specialOverlay.setVisibility(View.VISIBLE);
                    specialOveride.setVisibility(View.GONE);

                    specialColor.setBackground(getResources().getDrawable(R.drawable.special_baller_color));
                    specialOverlay.setBackground(getResources().getDrawable(R.drawable.special_baller_overlay));
                    break;
                case 12://bubble blower
                    specialBase.setVisibility(View.VISIBLE);
                    specialColor.setVisibility(View.VISIBLE);
                    specialOverlay.setVisibility(View.GONE);
                    specialOveride.setVisibility(View.GONE);

                    specialBase.setBackground(getResources().getDrawable(R.drawable.special_bubbleblower_base));
                    specialColor.setBackground(getResources().getDrawable(R.drawable.special_bubbleblower_color));
                    break;
            }
            if(isAlly){
                specialColor.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.primary));
            }else{
                specialColor.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.accent));
            }

            String rankString;
            switch (battle.type){
                case "regular":
                    rankString = String.valueOf(player.user.rank);
                    rank.setText(rankString);
                    fesGrade.setVisibility(View.GONE);
                    break;
                case "gachi":
                    if(player.user.udamae.sPlus==null) {
                        rank.setText(player.user.udamae.rank);
                    }else{
                        rankString = player.user.udamae.rank + player.user.udamae.sPlus;
                        rank.setText(rankString);
                    }
                    fesGrade.setVisibility(View.GONE);
                    break;
                case "league":
                    if(player.user.udamae.sPlus==null) {
                        rank.setText(player.user.udamae.rank);
                    }else{
                        rankString = player.user.udamae.rank + player.user.udamae.sPlus;
                        rank.setText(rankString);
                    }
                    fesGrade.setVisibility(View.GONE);
                    break;
                case "fes":
                    if(isAlly){
                        //specialColor.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor));
                    }else{

                    }
                    rankString = String.valueOf(player.user.rank);
                    rank.setText(rankString);
                    fesGrade.setText(player.user.grade.name);
                    break;
            }
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_player_child, null);
            }
            Player player =(Player) getGroup(groupPosition);

            RelativeLayout child = (RelativeLayout) convertView.findViewById(R.id.child);
            RelativeLayout headSub1Layout = (RelativeLayout) convertView.findViewById(R.id.headSub1);
            RelativeLayout headSub2Layout = (RelativeLayout) convertView.findViewById(R.id.headSub2);
            RelativeLayout headSub3Layout = (RelativeLayout) convertView.findViewById(R.id.headSub3);
            RelativeLayout clothesSub1Layout = (RelativeLayout) convertView.findViewById(R.id.clothesSub1);
            RelativeLayout clothesSub2Layout = (RelativeLayout) convertView.findViewById(R.id.clothesSub2);
            RelativeLayout clothesSub3Layout = (RelativeLayout) convertView.findViewById(R.id.clothesSub3);
            RelativeLayout shoesSub1Layout = (RelativeLayout) convertView.findViewById(R.id.shoesSub1);
            RelativeLayout shoesSub2Layout = (RelativeLayout) convertView.findViewById(R.id.shoesSub2);
            RelativeLayout shoesSub3Layout = (RelativeLayout) convertView.findViewById(R.id.shoesSub3);

            ImageView head = (ImageView) convertView.findViewById(R.id.HeadGear);
            ImageView headMain = (ImageView) convertView.findViewById(R.id.HeadMain);
            ImageView headSub1 = (ImageView) convertView.findViewById(R.id.HeadSub1);
            ImageView headSub2 = (ImageView) convertView.findViewById(R.id.HeadSub2);
            ImageView headSub3 = (ImageView) convertView.findViewById(R.id.HeadSub3);
            ImageView clothes = (ImageView) convertView.findViewById(R.id.ClothesGear);
            ImageView clothesMain = (ImageView) convertView.findViewById(R.id.ClothesMain);
            ImageView clothesSub1 = (ImageView) convertView.findViewById(R.id.ClothesSub1);
            ImageView clothesSub2 = (ImageView) convertView.findViewById(R.id.ClothesSub2);
            ImageView clothesSub3 = (ImageView) convertView.findViewById(R.id.ClothesSub3);
            ImageView shoes = (ImageView) convertView.findViewById(R.id.ShoesGear);
            ImageView shoesMain = (ImageView) convertView.findViewById(R.id.ShoesMain);
            ImageView shoesSub1 = (ImageView) convertView.findViewById(R.id.ShoesSub1);
            ImageView shoesSub2 = (ImageView) convertView.findViewById(R.id.ShoesSub2);
            ImageView shoesSub3 = (ImageView) convertView.findViewById(R.id.ShoesSub3);

            child.setClipToOutline(true);

            String url = "https://app.splatoon2.nintendo.net"+player.user.head.url;
            ImageHandler imageHandler = new ImageHandler();
            String imageDirName = player.user.head.name.toLowerCase().replace(" ", "_");
            if (imageHandler.imageExists("gear", imageDirName, context)) {
                head.setImageBitmap(imageHandler.loadImage("gear", imageDirName));
            } else {
                Picasso.with(context).load(url).into(head);
                imageHandler.downloadImage("gear", imageDirName, url, context);
            }

            url = "https://app.splatoon2.nintendo.net"+player.user.headSkills.main.url;
            imageHandler = new ImageHandler();
            imageDirName = player.user.headSkills.main.name.toLowerCase().replace(" ", "_");
            if (imageHandler.imageExists("ability", imageDirName, context)) {
                headMain.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
            } else {
                Picasso.with(context).load(url).into(headMain);
                imageHandler.downloadImage("ability", imageDirName, url, context);
            }

            if(player.user.headSkills.subs.size()>0&&player.user.headSkills.subs.get(0)!=null) {
                url = "https://app.splatoon2.nintendo.net" + player.user.headSkills.subs.get(0).url;
                imageHandler = new ImageHandler();
                imageDirName = player.user.headSkills.subs.get(0).name.toLowerCase().replace(" ", "_");
                if (imageHandler.imageExists("ability", imageDirName, context)) {
                    headSub1.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
                } else {
                    Picasso.with(context).load(url).into(headSub1);
                    imageHandler.downloadImage("ability", imageDirName, url, context);
                }
                if(player.user.headSkills.subs.size()>1&&player.user.headSkills.subs.get(1)!=null){
                    url = "https://app.splatoon2.nintendo.net" + player.user.headSkills.subs.get(1).url;
                    imageHandler = new ImageHandler();
                    imageDirName = player.user.headSkills.subs.get(1).name.toLowerCase().replace(" ", "_");
                    if (imageHandler.imageExists("ability", imageDirName, context)) {
                        headSub2.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
                    } else {
                        Picasso.with(context).load(url).into(headSub2);
                        imageHandler.downloadImage("ability", imageDirName, url, context);
                    }
                    if(player.user.headSkills.subs.size()>2&&player.user.headSkills.subs.get(2)!=null){
                        url = "https://app.splatoon2.nintendo.net" + player.user.headSkills.subs.get(2).url;
                        imageHandler = new ImageHandler();
                        imageDirName = player.user.headSkills.subs.get(2).name.toLowerCase().replace(" ", "_");
                        if (imageHandler.imageExists("ability", imageDirName, context)) {
                            headSub3.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
                        } else {
                            Picasso.with(context).load(url).into(headSub3);
                            imageHandler.downloadImage("ability", imageDirName, url, context);
                        }

                    }else{
                        headSub3Layout.setVisibility(View.GONE);
                    }
                }else{
                    headSub2Layout.setVisibility(View.GONE);
                    headSub3Layout.setVisibility(View.GONE);
                }
            }else{
                headSub1Layout.setVisibility(View.GONE);
                headSub2Layout.setVisibility(View.GONE);
                headSub3Layout.setVisibility(View.GONE);
            }

            //CLOTHES

            url = "https://app.splatoon2.nintendo.net"+player.user.clothes.url;
            imageHandler = new ImageHandler();
            imageDirName = player.user.clothes.name.toLowerCase().replace(" ", "_");
            if (imageHandler.imageExists("gear", imageDirName, context)) {
                clothes.setImageBitmap(imageHandler.loadImage("gear", imageDirName));
            } else {
                Picasso.with(context).load(url).into(clothes);
                imageHandler.downloadImage("gear", imageDirName, url, context);
            }

            url = "https://app.splatoon2.nintendo.net"+player.user.clothesSkills.main.url;
            imageHandler = new ImageHandler();
            imageDirName = player.user.clothesSkills.main.name.toLowerCase().replace(" ", "_");
            if (imageHandler.imageExists("ability", imageDirName, context)) {
                clothesMain.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
            } else {
                Picasso.with(context).load(url).into(clothesMain);
                imageHandler.downloadImage("ability", imageDirName, url, context);
            }

            if(player.user.clothesSkills.subs.size()>0&&player.user.clothesSkills.subs.get(0)!=null) {
                url = "https://app.splatoon2.nintendo.net" + player.user.clothesSkills.subs.get(0).url;
                imageHandler = new ImageHandler();
                imageDirName = player.user.clothesSkills.subs.get(0).name.toLowerCase().replace(" ", "_");
                if (imageHandler.imageExists("ability", imageDirName, context)) {
                    clothesSub1.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
                } else {
                    Picasso.with(context).load(url).into(clothesSub1);
                    imageHandler.downloadImage("ability", imageDirName, url, context);
                }
                if(player.user.clothesSkills.subs.size()>1&&player.user.clothesSkills.subs.get(1)!=null){
                    url = "https://app.splatoon2.nintendo.net" + player.user.clothesSkills.subs.get(1).url;
                    imageHandler = new ImageHandler();
                    imageDirName = player.user.clothesSkills.subs.get(1).name.toLowerCase().replace(" ", "_");
                    if (imageHandler.imageExists("ability", imageDirName, context)) {
                        clothesSub2.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
                    } else {
                        Picasso.with(context).load(url).into(clothesSub2);
                        imageHandler.downloadImage("ability", imageDirName, url, context);
                    }
                    if(player.user.clothesSkills.subs.size()>2&&player.user.clothesSkills.subs.get(2)!=null){
                        url = "https://app.splatoon2.nintendo.net" + player.user.clothesSkills.subs.get(2).url;
                        imageHandler = new ImageHandler();
                        imageDirName = player.user.clothesSkills.subs.get(2).name.toLowerCase().replace(" ", "_");
                        if (imageHandler.imageExists("ability", imageDirName, context)) {
                            clothesSub3.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
                        } else {
                            Picasso.with(context).load(url).into(clothesSub3);
                            imageHandler.downloadImage("ability", imageDirName, url, context);
                        }

                    }else{
                        clothesSub3Layout.setVisibility(View.GONE);
                    }
                }else{
                    clothesSub2Layout.setVisibility(View.GONE);
                    clothesSub3Layout.setVisibility(View.GONE);
                }
            }else{
                clothesSub1Layout.setVisibility(View.GONE);
                clothesSub2Layout.setVisibility(View.GONE);
                clothesSub3Layout.setVisibility(View.GONE);
            }

            //SHOES

            url = "https://app.splatoon2.nintendo.net"+player.user.shoes.url;
            imageHandler = new ImageHandler();
            imageDirName = player.user.shoes.name.toLowerCase().replace(" ", "_");
            if (imageHandler.imageExists("gear", imageDirName, context)) {
                shoes.setImageBitmap(imageHandler.loadImage("gear", imageDirName));
            } else {
                Picasso.with(context).load(url).into(shoes);
                imageHandler.downloadImage("gear", imageDirName, url, context);
            }

            url = "https://app.splatoon2.nintendo.net"+player.user.shoeSkills.main.url;
            imageHandler = new ImageHandler();
            imageDirName = player.user.shoeSkills.main.name.toLowerCase().replace(" ", "_");
            if (imageHandler.imageExists("ability", imageDirName, context)) {
                shoesMain.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
            } else {
                Picasso.with(context).load(url).into(shoesMain);
                imageHandler.downloadImage("ability", imageDirName, url, context);
            }

            if(player.user.shoeSkills.subs.size()>0&&player.user.shoeSkills.subs.get(0)!=null) {
                url = "https://app.splatoon2.nintendo.net" + player.user.shoeSkills.subs.get(0).url;
                imageHandler = new ImageHandler();
                imageDirName = player.user.shoeSkills.subs.get(0).name.toLowerCase().replace(" ", "_");
                if (imageHandler.imageExists("ability", imageDirName, context)) {
                    shoesSub1.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
                } else {
                    Picasso.with(context).load(url).into(shoesSub1);
                    imageHandler.downloadImage("ability", imageDirName, url, context);
                }
                if(player.user.shoeSkills.subs.size()>1&&player.user.shoeSkills.subs.get(1)!=null){
                    url = "https://app.splatoon2.nintendo.net" + player.user.shoeSkills.subs.get(1).url;
                    imageHandler = new ImageHandler();
                    imageDirName = player.user.shoeSkills.subs.get(1).name.toLowerCase().replace(" ", "_");
                    if (imageHandler.imageExists("ability", imageDirName, context)) {
                        shoesSub2.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
                    } else {
                        Picasso.with(context).load(url).into(shoesSub2);
                        imageHandler.downloadImage("ability", imageDirName, url, context);
                    }
                    if(player.user.shoeSkills.subs.size()>2&&player.user.shoeSkills.subs.get(2)!=null){
                        url = "https://app.splatoon2.nintendo.net" + player.user.shoeSkills.subs.get(2).url;
                        imageHandler = new ImageHandler();
                        imageDirName = player.user.shoeSkills.subs.get(2).name.toLowerCase().replace(" ", "_");
                        if (imageHandler.imageExists("ability", imageDirName, context)) {
                            shoesSub3.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
                        } else {
                            Picasso.with(context).load(url).into(shoesSub3);
                            imageHandler.downloadImage("ability", imageDirName, url, context);
                        }

                    }else{
                        shoesSub3Layout.setVisibility(View.GONE);
                    }
                }else{
                    shoesSub2Layout.setVisibility(View.GONE);
                    shoesSub3Layout.setVisibility(View.GONE);
                }
            }else{
                shoesSub1Layout.setVisibility(View.GONE);
                shoesSub2Layout.setVisibility(View.GONE);
                shoesSub3Layout.setVisibility(View.GONE);
            }
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }
}