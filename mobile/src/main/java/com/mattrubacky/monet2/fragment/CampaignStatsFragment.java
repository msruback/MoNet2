package com.mattrubacky.monet2.fragment;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.CampaignRecords;
import com.mattrubacky.monet2.deserialized.PastSplatfest;
import com.mattrubacky.monet2.deserialized.Record;
import com.mattrubacky.monet2.splatnet.CampaignRecordsRequest;
import com.mattrubacky.monet2.splatnet.CoopSchedulesRequest;
import com.mattrubacky.monet2.splatnet.MonthlyGearRequest;
import com.mattrubacky.monet2.splatnet.SchedulesRequest;
import com.mattrubacky.monet2.splatnet.SplatnetConnected;
import com.mattrubacky.monet2.splatnet.SplatnetConnector;

/**
 * Created by mattr on 12/6/2017.
 */

public class CampaignStatsFragment extends Fragment implements SplatnetConnected{
    ViewGroup rootView;
    SharedPreferences settings;
    CampaignRecords campaignRecords;
    SplatnetConnector connector;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (ViewGroup)  inflater.inflate(R.layout.fragment_campaign_stats, container, false);

        settings = PreferenceManager.getDefaultSharedPreferences(getContext());

        return rootView;

    }

    @Override
    public void onPause() {
        super.onPause();

        connector.cancel(true);
    }

    @Override
    public void onResume() {
        super.onResume();

        connector = new SplatnetConnector(this, getActivity(),getContext());
        connector.addRequest(new CampaignRecordsRequest(getContext()));

        update(connector.getCurrentData());

        connector.execute();
    }

    public void updateUi(){
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(getActivity().getAssets(), "Paintball.otf");

        TextView honor = (TextView) rootView.findViewById(R.id.Honor);
        TextView percent = (TextView) rootView.findViewById(R.id.PercentComplete);
    }

    @Override
    public void update(Bundle bundle) {
        campaignRecords = bundle.getParcelable("campaign_records");
        updateUi();
    }
}
