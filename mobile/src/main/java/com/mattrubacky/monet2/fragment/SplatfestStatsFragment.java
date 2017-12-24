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
import com.mattrubacky.monet2.SplatfestDetail;
import com.mattrubacky.monet2.adapter.RecyclerView.SplatfestAdapter;
import com.mattrubacky.monet2.deserialized.Record;
import com.mattrubacky.monet2.deserialized.Splatfest;
import com.mattrubacky.monet2.deserialized.SplatfestDatabase;
import com.mattrubacky.monet2.deserialized.SplatfestRecords;
import com.mattrubacky.monet2.deserialized.SplatfestResult;
import com.mattrubacky.monet2.splatnet.PastSplatfestRequest;
import com.mattrubacky.monet2.splatnet.RecordsRequest;
import com.mattrubacky.monet2.splatnet.SplatnetConnected;
import com.mattrubacky.monet2.splatnet.SplatnetConnector;

import java.util.ArrayList;

/**
 * Created by mattr on 11/15/2017.
 */

public class SplatfestStatsFragment extends Fragment implements SplatnetConnected{
    ViewGroup rootView;
    SharedPreferences settings;
    Record records;
    ArrayList<SplatfestDatabase> splatfests;
    RecyclerView splatfestList;

    SplatnetConnector splatnetConnector;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (ViewGroup)  inflater.inflate(R.layout.fragment_splatfest_stats, container, false);

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
        splatnetConnector.addRequest(new PastSplatfestRequest(getContext()));
        splatnetConnector.addRequest(new RecordsRequest(getContext()));
        update(splatnetConnector.getCurrentData());
        splatnetConnector.execute();
    }

    private void updateUI(){
        splatfestList = (RecyclerView) rootView.findViewById(R.id.SplatfestList);
        SplatfestAdapter splatfestAdapter = new SplatfestAdapter(getContext(), splatfests, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = splatfestList.getChildAdapterPosition(v);
                Intent intent = new Intent(getActivity(), SplatfestDetail.class);
                Bundle bundle = new Bundle();
                Splatfest splatfest = splatfests.get(itemPosition).splatfest;
                SplatfestResult result = splatfests.get(itemPosition).result;

                bundle.putParcelable("splatfest", splatfest);
                bundle.putParcelable("result", result);
                SplatfestRecords splatrec = records.records.splatfestRecords.get(splatfest.id);
                if (splatrec != null){
                    bundle.putString("grade", records.records.splatfestRecords.get(splatfest.id).grade.name);
                    bundle.putInt("power", records.records.splatfestRecords.get(splatfest.id).power);
                }
                //Need to get votes
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        splatfestList.setLayoutManager(new GridLayoutManager(getContext(), 1));
        splatfestList.setAdapter(splatfestAdapter);
    }

    @Override
    public void update(Bundle bundle) {
        splatfests =  bundle.getParcelableArrayList("splatfests");
        splatfests = sort(splatfests);
        records = bundle.getParcelable("records");
        updateUI();
    }

    private ArrayList<SplatfestDatabase> sort(ArrayList<SplatfestDatabase> data){
        if(data.size()<=1){
            return data;
        }
        if(data.size()==2){
            if(data.get(0).splatfest.times.end>=data.get(1).splatfest.times.end){
                return data;
            }else{
                SplatfestDatabase hold = data.get(0);
                data.remove(0);
                data.add(hold);
                return data;
            }
        }else {
            SplatfestDatabase pivot = data.get(0);
            ArrayList<SplatfestDatabase> lower = new ArrayList<>();
            ArrayList<SplatfestDatabase> upper = new ArrayList<>();
            for (int i = 1; i < data.size(); i++) {
                if (pivot.splatfest.times.end < data.get(i).splatfest.times.end) {
                    lower.add(data.get(i));
                } else {
                    upper.add(data.get(i));
                }
            }
            ArrayList<SplatfestDatabase> result = sort(lower);
            result.add(pivot);
            result.addAll(sort(upper));
            return result;
        }
    }
}
