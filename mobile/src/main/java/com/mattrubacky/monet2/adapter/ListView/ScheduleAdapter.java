package com.mattrubacky.monet2.adapter.ListView;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.adapter.Pager.FesAdapter;
import com.mattrubacky.monet2.adapter.Pager.LeagueAdapter;
import com.mattrubacky.monet2.adapter.Pager.RankAdapter;
import com.mattrubacky.monet2.adapter.Pager.SalmonAdapter;
import com.mattrubacky.monet2.adapter.Pager.TurfAdapter;
import com.mattrubacky.monet2.deserialized.*;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mattr on 10/21/2017.
 *
 * This adapter poplutates the list of cards in RotationFragment
 */

public class ScheduleAdapter extends ArrayAdapter<String> {
    FragmentManager childFragmentManager;
    Schedules schedules;
    CurrentSplatfest currentSplatfest;
    SalmonSchedule salmonSchedule;
    Gear monthlyGear;

    public ScheduleAdapter(Context context, ArrayList<String> input, FragmentManager childFragmentManager, Schedules schedules, CurrentSplatfest currentSplatfest, SalmonSchedule salmonSchedule, Gear monthlyGear) {
        super(context, 0, input);
        this.childFragmentManager = childFragmentManager;
        this.schedules = schedules;
        this.currentSplatfest = currentSplatfest;
        this.salmonSchedule = salmonSchedule;
        this.monthlyGear = monthlyGear;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(getContext().getAssets(), "Paintball.otf");

        switch(getItem(position)){
            case "regular":
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_regular, parent, false);

                TextView turfWarTitle = (TextView) convertView.findViewById(R.id.turfWarName);
                turfWarTitle.setTypeface(fontTitle);

                ViewPager TurfPager = (ViewPager) convertView.findViewById(R.id.TurfPager);
                TabLayout turfDots = (TabLayout) convertView.findViewById(R.id.TurfDots);

                turfDots.setupWithViewPager(TurfPager, true);
                PagerAdapter turfAdapter = new TurfAdapter(childFragmentManager, schedules.regular);
                TurfPager.setAdapter(turfAdapter);

                break;
            case "ranked":
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_ranked, parent, false);

                TextView rankedTitle = (TextView) convertView.findViewById(R.id.rankedName);
                rankedTitle.setTypeface(fontTitle);

                ViewPager RankPager = (ViewPager) convertView.findViewById(R.id.RankedPager);
                TabLayout rankDots = (TabLayout) convertView.findViewById(R.id.RankDots);
                rankDots.setupWithViewPager(RankPager, true);
                PagerAdapter rankAdapter = new RankAdapter(childFragmentManager, schedules.ranked);
                RankPager.setAdapter(rankAdapter);


                break;
            case "league":
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_league, parent, false);

                TextView leagueTitle = (TextView) convertView.findViewById(R.id.leagueName);
                leagueTitle.setTypeface(fontTitle);

                ViewPager LeaguePager = (ViewPager) convertView.findViewById(R.id.LeaguePager);
                TabLayout leagueDots = (TabLayout) convertView.findViewById(R.id.LeagueDots);
                leagueDots.setupWithViewPager(LeaguePager, true);
                PagerAdapter leagueAdapter = new LeagueAdapter(childFragmentManager, schedules.league);
                LeaguePager.setAdapter(leagueAdapter);


                break;
            case "fes":
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_festival, parent, false);

                RelativeLayout fesCard = (RelativeLayout) convertView.findViewById(R.id.Festival);
                RelativeLayout fesBanner = (RelativeLayout) convertView.findViewById(R.id.fesModeBanner);
                RelativeLayout Alpha = (RelativeLayout) convertView.findViewById(R.id.Alpha);
                RelativeLayout Bravo = (RelativeLayout) convertView.findViewById(R.id.Bravo);

                fesCard.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(currentSplatfest.splatfests.get(0).colors.alpha.getColor())));
                fesBanner.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(currentSplatfest.splatfests.get(0).colors.bravo.getColor())));

                Alpha.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(currentSplatfest.splatfests.get(0).colors.alpha.getColor())));
                Bravo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(currentSplatfest.splatfests.get(0).colors.bravo.getColor())));

                TextView fesTitle = (TextView) convertView.findViewById(R.id.fesName);
                fesTitle.setTypeface(fontTitle);

                ViewPager FesPager = (ViewPager) convertView.findViewById(R.id.FesPager);
                TabLayout fesDots = (TabLayout) convertView.findViewById(R.id.FesDots);

                fesDots.setupWithViewPager(FesPager, true);
                PagerAdapter fesAdapter = new FesAdapter(childFragmentManager, schedules.splatfest,currentSplatfest.splatfests.get(0));
                FesPager.setAdapter(fesAdapter);

                break;
            case "salmon":
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_salmon, parent, false);

                RelativeLayout salmonRun = (RelativeLayout) convertView.findViewById(R.id.SalmonRun);
                salmonRun.setClipToOutline(true);

                TextView salmonTitle = (TextView) convertView.findViewById(R.id.salmonName);
                salmonTitle.setTypeface(fontTitle);

                ImageView currentGear = (ImageView) convertView.findViewById(R.id.monthlyGear);

                if(monthlyGear!=null) {
                    String url = "https://app.splatoon2.nintendo.net" + monthlyGear.url;
                    ImageHandler imageHandler = new ImageHandler();
                    String imageDirName = monthlyGear.name.toLowerCase().replace(" ", "_");
                    if (imageHandler.imageExists("weapon", imageDirName, getContext())) {
                        currentGear.setImageBitmap(imageHandler.loadImage("weapon", imageDirName));
                    } else {
                        Picasso.with(getContext()).load(url).into(currentGear);
                        imageHandler.downloadImage("weapon", imageDirName, url, getContext());
                    }
                }

                ViewPager SalmonPager = (ViewPager) convertView.findViewById(R.id.SalmonPager);
                TabLayout salmonDots = (TabLayout) convertView.findViewById(R.id.SalmonDots);
                salmonDots.setupWithViewPager(SalmonPager, true);
                PagerAdapter salmonAdapter = new SalmonAdapter(childFragmentManager, salmonSchedule);
                SalmonPager.setAdapter(salmonAdapter);
                break;
        }
        for(int i=0;i<childFragmentManager.getBackStackEntryCount();i++){
            childFragmentManager.popBackStack();
        }
        return convertView;
    }
}