package com.mattrubacky.monet2.adapter.Pager;

import android.os.Bundle;
import android.os.Parcelable;

import com.mattrubacky.monet2.deserialized.splatoon.Splatfest;
import com.mattrubacky.monet2.deserialized.splatoon.SplatfestResult;
import com.mattrubacky.monet2.fragment.SplatfestDetail.SplatfestPerformanceFragment;
import com.mattrubacky.monet2.fragment.SplatfestDetail.SplatfestResultFragment;
import com.mattrubacky.monet2.helper.SplatfestStats;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * Created by mattr on 11/17/2017.
 */

public class SplatfestPerformanceAdapter extends FragmentStatePagerAdapter {
    private Splatfest splatfest;
    private SplatfestResult result;
    private SplatfestStats stats;
    public SplatfestPerformanceAdapter(FragmentManager fm, Splatfest splatfest, SplatfestResult result, SplatfestStats stats) {
        super(fm);
        this.splatfest = splatfest;
        this.result = result;
        this.stats = stats;
    }

    @Override
    public Fragment getItem(int position) {

        Bundle bundle = new Bundle();
        Fragment performance;
        if(position==0&&stats.grade!=null){
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
        if(result.rates.vote.alpha!=0&&stats.grade!=null){
            return 2;
        }else if(result.rates.vote.alpha==0&&stats.grade==null){
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