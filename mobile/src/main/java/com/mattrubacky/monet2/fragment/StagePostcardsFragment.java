package com.mattrubacky.monet2.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.StagePostcardsDetail;
import com.mattrubacky.monet2.adapter.StageAdapter;
import com.mattrubacky.monet2.deserialized.Record;
import com.mattrubacky.monet2.helper.StageStats;
import com.mattrubacky.monet2.splatnet.RecordsRequest;
import com.mattrubacky.monet2.splatnet.SplatnetConnected;
import com.mattrubacky.monet2.splatnet.SplatnetConnector;

import java.util.ArrayList;

/**
 * Created by mattr on 11/15/2017.
 */

public class StagePostcardsFragment extends Fragment implements SplatnetConnected{

    ViewGroup rootView;
    SharedPreferences settings;
    Record records;
    ArrayList<StageStats> stageStatsList;
    RecyclerView stageList;
    SplatnetConnector splatnetConnector;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (ViewGroup)  inflater.inflate(R.layout.fragment_stage_postcards, container, false);

        settings = PreferenceManager.getDefaultSharedPreferences(getContext());

        return rootView;

    }

    @Override
    public void onPause() {
        super.onPause();

        splatnetConnector.cancel(true);
    }

    @Override
    public void onResume() {
        super.onResume();

        splatnetConnector = new SplatnetConnector(this,getActivity(),getContext());
        splatnetConnector.addRequest(new RecordsRequest(getContext()));
        update(splatnetConnector.getCurrentData());
        splatnetConnector.execute();
    }

    private void updateUI(){
        stageList = (RecyclerView) rootView.findViewById(R.id.StageList);
        StageAdapter stageAdapter = new StageAdapter(getContext(), stageStatsList, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = stageList.getChildAdapterPosition(v);
                Intent intent = new Intent(getActivity(), StagePostcardsDetail.class);
                Bundle bundle = new Bundle();
                StageStats stageStats = stageStatsList.get(itemPosition);

                stageStats.calcStats(getContext());

                bundle.putParcelable("stats",stageStats);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        stageList.setLayoutManager(new GridLayoutManager(getContext(), 1));
        stageList.setAdapter(stageAdapter);
    }

    @Override
    public void update(Bundle bundle) {
        records = bundle.getParcelable("records");

        Integer[] keys = new Integer[2];
        keys = records.records.stageStats.keySet().toArray(keys);
        stageStatsList = new ArrayList<>();
        for(int i=0;i<keys.length;i++){
            stageStatsList.add(records.records.stageStats.get(keys[i]));
        }
        updateUI();
    }
}


