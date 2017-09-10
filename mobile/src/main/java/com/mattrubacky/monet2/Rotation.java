package com.mattrubacky.monet2;

import android.app.ActionBar;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

public class Rotation extends AppCompatActivity {
    int TurfPage = 5;
    int LeaguePage = 5;
    int RankPage = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotation);

        Typeface font = Typeface.createFromAsset(getAssets(),"Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(getAssets(),"Paintball.otf");

        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.title);

        TextView turfWarTitle = (TextView) findViewById(R.id.turfWarName);
        TextView rankedTitle = (TextView) findViewById(R.id.rankedName);
        TextView leagueTitle = (TextView) findViewById(R.id.leagueName);
        TextView salmonTitle = (TextView) findViewById(R.id.salmonName);

        ViewPager TurfPager = (ViewPager) findViewById(R.id.TurfPager);
        ViewPager RankPager = (ViewPager) findViewById(R.id.RankedPager);
        ViewPager LeaguePager = (ViewPager) findViewById(R.id.LeaguePager);

        PagerAdapter turfAdapter = new TurfAdapter(getSupportFragmentManager());
        PagerAdapter rankAdapter = new RankAdapter(getSupportFragmentManager());
        PagerAdapter leagueAdapter = new LeagueAdapter(getSupportFragmentManager());

        TurfPager.setAdapter(turfAdapter);
        RankPager.setAdapter(rankAdapter);
        LeaguePager.setAdapter(leagueAdapter);

        turfWarTitle.setTypeface(fontTitle);
        rankedTitle.setTypeface(fontTitle);
        leagueTitle.setTypeface(fontTitle);
        salmonTitle.setTypeface(fontTitle);
    }
    private class TurfAdapter extends FragmentStatePagerAdapter {
        public TurfAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new TurfRotation();
        }

        @Override
        public int getCount() {
            return TurfPage;
        }

    }
    private class RankAdapter extends FragmentStatePagerAdapter {
        public RankAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new RankedRotation();
        }

        @Override
        public int getCount() {
            return RankPage;
        }

    }
    private class LeagueAdapter extends FragmentStatePagerAdapter {
        public LeagueAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new LeagueRotation();
        }

        @Override
        public int getCount() {
            return LeaguePage;
        }

    }
}

