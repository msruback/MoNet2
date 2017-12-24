package com.mattrubacky.monet2.adapter.Pager;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mattrubacky.monet2.fragment.SplatfestDetail.SoloMeterFragment;
import com.mattrubacky.monet2.fragment.SplatfestDetail.TeamMeterFragment;

/**
 * Created by mattr on 11/16/2017.
 */

public class MeterAdapter extends FragmentStatePagerAdapter {
    int[] playerStats,teamStats;
    float average;
    public MeterAdapter(FragmentManager fm, int[] playerStats,int[] teamStats,float average) {
        super(fm);
        this.playerStats = playerStats;
        this.teamStats = teamStats;
        this.average = average;
    }

    @Override
    public Fragment getItem(int position) {

        Bundle bundle = new Bundle();
        Fragment stats;
        if(position==0) {
            bundle.putIntArray("stats", playerStats);
            stats = new SoloMeterFragment();
        }else{
            bundle.putIntArray("stats", teamStats);
            bundle.putFloat("average",average);
            stats = new TeamMeterFragment();
        }
        stats.setArguments(bundle);
        return stats;
    }

    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public Parcelable saveState() {
        // Do Nothing
        return null;
    }

}
