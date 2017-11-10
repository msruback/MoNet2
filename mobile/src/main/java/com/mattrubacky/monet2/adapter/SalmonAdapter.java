package com.mattrubacky.monet2.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mattrubacky.monet2.deserialized.SalmonRun;
import com.mattrubacky.monet2.deserialized.SalmonSchedule;
import com.mattrubacky.monet2.deserialized.TimePeriod;
import com.mattrubacky.monet2.fragment.schedule.SalmonRotation;
import com.mattrubacky.monet2.fragment.schedule.SalmonRotationDetail;
import com.mattrubacky.monet2.fragment.schedule.TurfRotation;

import java.util.ArrayList;

/**
 * Created by mattr on 11/9/2017.
 */

public class SalmonAdapter extends FragmentStatePagerAdapter {
    SalmonSchedule schedule;
    public SalmonAdapter(FragmentManager fm, SalmonSchedule schedule) {
        super(fm);
        this.schedule = schedule;
    }

    @Override
    public Fragment getItem(int position) {

        Bundle bundle = new Bundle();
        Fragment salmon;
        if(position>schedule.details.size()-1){
            bundle.putParcelableArrayList("salmon_runs",schedule.times);
            salmon = new SalmonRotation();
        }else {
            bundle.putParcelable("detail", schedule.details.get(position));
            salmon = new SalmonRotationDetail();
        }
        salmon.setArguments(bundle);
        return salmon;
    }

    @Override
    public int getCount() {
        return schedule.details.size()+1;
    }
    @Override
    public Parcelable saveState() {
        // Do Nothing
        return null;
    }

}
