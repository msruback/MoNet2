package com.mattrubacky.monet2.adapter.Pager;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mattrubacky.monet2.deserialized.TimePeriod;
import com.mattrubacky.monet2.fragment.schedule.RankedRotation;

import java.util.ArrayList;

/**
 * Created by mattr on 10/21/2017.
 *
 * This Adapter populates the Rank Card in RotationFragment
 */

public class RankAdapter extends FragmentStatePagerAdapter {
    ArrayList<TimePeriod> input;
    public RankAdapter(FragmentManager fm, ArrayList<TimePeriod> input) {
        super(fm);
        this.input = input;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("timePeriod",input.get(position));
        Fragment rank = new RankedRotation();
        rank.setArguments(bundle);
        return rank;
    }

    @Override
    public int getCount() {
        return input.size();
    }
    @Override
    public Parcelable saveState() {
        // Do Nothing
        return null;
    }

}