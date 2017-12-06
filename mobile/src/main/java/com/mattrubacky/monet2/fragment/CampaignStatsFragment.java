package com.mattrubacky.monet2.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.PastSplatfest;
import com.mattrubacky.monet2.deserialized.Record;

/**
 * Created by mattr on 12/6/2017.
 */

public class CampaignStatsFragment extends Fragment {
    ViewGroup rootView;
    SharedPreferences settings;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (ViewGroup)  inflater.inflate(R.layout.fragment_campaign_stats, container, false);

        settings = PreferenceManager.getDefaultSharedPreferences(getContext());



        return rootView;

    }

}
