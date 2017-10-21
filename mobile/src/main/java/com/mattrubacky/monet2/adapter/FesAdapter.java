package com.mattrubacky.monet2.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mattrubacky.monet2.deserialized.Splatfest;
import com.mattrubacky.monet2.deserialized.TimePeriod;
import com.mattrubacky.monet2.fragment.schedule.SplatfestRotation;

import java.util.ArrayList;

/**
 * Created by mattr on 10/21/2017.
 */

public class FesAdapter extends FragmentStatePagerAdapter {
    ArrayList<TimePeriod> input;
    Splatfest splatfest;
    public FesAdapter(FragmentManager fm, ArrayList<TimePeriod> input, Splatfest splatfest) {
        super(fm);
        this.input = input;
        this.splatfest = splatfest;
    }

    @Override
    public Fragment getItem(int position) {

        Bundle bundle = new Bundle();
        bundle.putParcelable("timePeriod",input.get(position));
        bundle.putParcelable("splatfest",splatfest);
        Fragment fes = new SplatfestRotation();
        fes.setArguments(bundle);
        return fes;
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
