package com.mattrubacky.monet2.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mattrubacky.monet2.deserialized.SalmonSchedule;
import com.mattrubacky.monet2.deserialized.Splatfest;
import com.mattrubacky.monet2.deserialized.SplatfestResult;
import com.mattrubacky.monet2.deserialized.SplatfestStats;
import com.mattrubacky.monet2.fragment.SplatfestDetail.SplatfestPerformanceFragment;
import com.mattrubacky.monet2.fragment.SplatfestDetail.SplatfestResultFragment;
import com.mattrubacky.monet2.fragment.schedule.SalmonRotation;
import com.mattrubacky.monet2.fragment.schedule.SalmonRotationDetail;

/**
 * Created by mattr on 11/17/2017.
 */

public class SplatfestPerformanceAdapter extends FragmentStatePagerAdapter {
    Splatfest splatfest;
    SplatfestResult result;
    SplatfestStats stats;
    public SplatfestPerformanceAdapter(FragmentManager fm, Splatfest splatfest, SplatfestResult result,SplatfestStats stats) {
        super(fm);
        this.splatfest = splatfest;
        this.result = result;
        this.stats = stats;
    }

    @Override
    public Fragment getItem(int position) {

        Bundle bundle = new Bundle();
        Fragment performance;
        if(position==0&&stats!=null){
            bundle.putParcelable("stats",stats);
            bundle.putParcelable("splatfest",splatfest);
            performance = new SplatfestPerformanceFragment();
        }else{
            bundle.putParcelable("splatfest",splatfest);
            bundle.putParcelable("result", result);
            performance = new SplatfestResultFragment();
        }
        performance.setArguments(bundle);
        return performance;
    }

    @Override
    public int getCount() {
        if(result!=null&&stats!=null){
            return 2;
        }else if(result==null&&stats==null){
            return 0;
        }else{
            return 1;
        }
    }
    @Override
    public Parcelable saveState() {
        // Do Nothing
        return null;
    }

}