package com.mattrubacky.monet2.fragment;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.adapter.CampaignWeaponAdapter;
import com.mattrubacky.monet2.adapter.CampaignWorldAdapter;
import com.mattrubacky.monet2.deserialized.CampaignRecords;
import com.mattrubacky.monet2.deserialized.CampaignStage;
import com.mattrubacky.monet2.deserialized.CampaignStageInfo;
import com.mattrubacky.monet2.deserialized.CampaignWeapon;
import com.mattrubacky.monet2.deserialized.PastSplatfest;
import com.mattrubacky.monet2.deserialized.Record;
import com.mattrubacky.monet2.splatnet.CampaignRecordsRequest;
import com.mattrubacky.monet2.splatnet.CoopSchedulesRequest;
import com.mattrubacky.monet2.splatnet.MonthlyGearRequest;
import com.mattrubacky.monet2.splatnet.SchedulesRequest;
import com.mattrubacky.monet2.splatnet.SplatnetConnected;
import com.mattrubacky.monet2.splatnet.SplatnetConnector;

import java.util.ArrayList;
import java.util.List;

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

    public void updateUI(){
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(getActivity().getAssets(), "Paintball.otf");

        ListView weaponList = (ListView) rootView.findViewById(R.id.WeaponList);
        ListView worldList = (ListView) rootView.findViewById(R.id.WorldList);

        TextView honor = (TextView) rootView.findViewById(R.id.Honor);
        TextView percent = (TextView) rootView.findViewById(R.id.PercentComplete);

        honor.setTypeface(fontTitle);
        percent.setTypeface(fontTitle);

        if(campaignRecords.summary!=null) {
            honor.setText(campaignRecords.summary.honor.name);
            percent.setText(Math.round(campaignRecords.summary.clear * 100) + "% Complete");
        }

        ArrayList<CampaignWeapon> weapons = new ArrayList<>();
        Integer[] keys = new Integer[2];
        keys = campaignRecords.weaponMap.keySet().toArray(keys);
        for(int i=0;i<keys.length;i++){
            weapons.add(campaignRecords.weaponMap.get(keys[i]));
        }
        CampaignWeaponAdapter campaignWeaponAdapter = new CampaignWeaponAdapter(getContext(),weapons,campaignRecords.info);

        weaponList.setAdapter(campaignWeaponAdapter);
        ViewGroup.LayoutParams layoutParams = weaponList.getLayoutParams();
        float height = (weapons.size()*50)+5;
        layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, getResources().getDisplayMetrics());
        weaponList.setLayoutParams(layoutParams);

        ArrayList<ArrayList<CampaignStageInfo>> worlds = new ArrayList<>();
        ArrayList<CampaignStageInfo> world1 = new ArrayList<>(),world2 = new ArrayList<>(),world3 = new ArrayList<>(),world4 = new ArrayList<>(),world5 = new ArrayList<>();
        CampaignStageInfo info;
        for(int i=0;i<campaignRecords.info.size();i++){
            info = campaignRecords.info.get(i);
            switch(info.stage.area){
                case 1:
                    world1.add(info);
                    break;
                case 2:
                    world2.add(info);
                    break;
                case 3:
                    world3.add(info);
                    break;
                case 4:
                    world4.add(info);
                    break;
                case 5:
                    world5.add(info);
                    break;
            }
        }
        worlds.add(world1);
        worlds.add(world2);
        worlds.add(world3);
        worlds.add(world4);
        worlds.add(world5);

        CampaignWorldAdapter campaignWorldAdapter = new CampaignWorldAdapter(getContext(),worlds,campaignRecords.weaponMap);
        worldList.setAdapter(campaignWorldAdapter);

    }

    @Override
    public void update(Bundle bundle) {
        campaignRecords = bundle.getParcelable("campaign_records");
        updateUI();
    }
}
