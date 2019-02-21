package com.mattrubacky.monet2.ui.fragment.MainScreenFragments.StatFragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.ui.adapter.RecyclerView.CampaignWeaponAdapter;
import com.mattrubacky.monet2.ui.adapter.RecyclerView.CampaignWorldAdapter;
import com.mattrubacky.monet2.data.deserialized.splatoon.CampaignRecords;
import com.mattrubacky.monet2.data.deserialized.splatoon.CampaignStageInfo;
import com.mattrubacky.monet2.data.deserialized.splatoon.CampaignWeapon;
import com.mattrubacky.monet2.backend.api.splatnet.CampaignRecordsRequest;
import com.mattrubacky.monet2.backend.api.splatnet.SplatnetConnected;
import com.mattrubacky.monet2.backend.api.splatnet.SplatnetConnector;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 12/6/2017.
 */

public class CampaignStatsFragment extends Fragment implements SplatnetConnected{
    private ViewGroup rootView;
    private CampaignRecords campaignRecords;
    private SplatnetConnector connector;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (ViewGroup)  inflater.inflate(R.layout.fragment_campaign_stats, container, false);

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
        Typeface fontTitle = Typeface.createFromAsset(getActivity().getAssets(), "Paintball.otf");

        RecyclerView weaponList = rootView.findViewById(R.id.WeaponList);
        RecyclerView worldList = rootView.findViewById(R.id.WorldList);

        TextView honor = rootView.findViewById(R.id.Honor);
        TextView percent = rootView.findViewById(R.id.PercentComplete);

        honor.setTypeface(fontTitle);
        percent.setTypeface(fontTitle);

        if(campaignRecords.summary!=null) {
            honor.setText(campaignRecords.summary.honor.name);
            percent.setText(Math.round(campaignRecords.summary.clear * 100) + "% Complete");
        }

        ArrayList<CampaignWeapon> weapons = new ArrayList<>(campaignRecords.weaponMap.values());
        CampaignWeaponAdapter campaignWeaponAdapter = new CampaignWeaponAdapter(getActivity(),weapons,campaignRecords.info);

        weaponList.setAdapter(campaignWeaponAdapter);
        weaponList.setLayoutManager(new LinearLayoutManager(getContext()));

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

        CampaignWorldAdapter campaignWorldAdapter = new CampaignWorldAdapter(getActivity(),worlds,campaignRecords.weaponMap);
        worldList.setAdapter(campaignWorldAdapter);
        worldList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void update(Bundle bundle) {
        campaignRecords = bundle.getParcelable("campaign_records");
        updateUI();
    }
}
