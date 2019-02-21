package com.mattrubacky.monet2.ui.fragment.MainScreenFragments.StatFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.ui.activities.StagePostcardsDetail;
import com.mattrubacky.monet2.ui.adapter.RecyclerView.StageAdapter;
import com.mattrubacky.monet2.data.deserialized.splatoon.Record;
import com.mattrubacky.monet2.data.deserialized.splatoon.Stage;
import com.mattrubacky.monet2.data.stats.StageStats;
import com.mattrubacky.monet2.backend.api.splatnet.RecordsRequest;
import com.mattrubacky.monet2.backend.api.splatnet.SplatnetConnected;
import com.mattrubacky.monet2.backend.api.splatnet.SplatnetConnector;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 11/15/2017.
 */

public class StagePostcardsFragment extends Fragment implements SplatnetConnected{

    private ViewGroup rootView;
    private Record records;
    private ArrayList<StageStats> stageStatsList;
    private RecyclerView stageList;
    private SplatnetConnector splatnetConnector;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (ViewGroup)  inflater.inflate(R.layout.fragment_stage_postcards, container, false);

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
        stageList = rootView.findViewById(R.id.StageList);
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

        SplatnetSQLManager database = new SplatnetSQLManager(getContext());
        ArrayList<Stage> stages = database.getStages();

        stageStatsList = new ArrayList<>();
        StageStats stats;
        for(int i=0;i<stages.size();i++){
            if(records.records.stageStats.containsKey(stages.get(i).id)){
                stats = records.records.stageStats.get(stages.get(i).id);
                stats.isSplatnet = true;
            }else{
                stats = new StageStats();
                stats.stage = stages.get(i);
                stats.isSplatnet = false;
            }
            stageStatsList.add(stats);
        }
        updateUI();
    }
}


