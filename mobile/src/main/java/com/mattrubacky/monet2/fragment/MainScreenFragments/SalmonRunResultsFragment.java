package com.mattrubacky.monet2.fragment.MainScreenFragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.adapter.RecyclerView.BattleListPagerAdapter;
import com.mattrubacky.monet2.adapter.RecyclerView.SalmonRunShiftPagerAdapter;
import com.mattrubacky.monet2.api.splatnet.CoopResultsRequest;
import com.mattrubacky.monet2.api.splatnet.CoopSchedulesRequest;
import com.mattrubacky.monet2.api.splatnet.MonthlyGearRequest;
import com.mattrubacky.monet2.api.splatnet.SchedulesRequest;
import com.mattrubacky.monet2.api.splatnet.SplatnetConnected;
import com.mattrubacky.monet2.api.splatnet.SplatnetConnector;
import com.mattrubacky.monet2.deserialized.splatoon.Battle;
import com.mattrubacky.monet2.deserialized.splatoon.SalmonRunDetail;
import com.mattrubacky.monet2.deserialized.splatoon.Timeline;
import com.mattrubacky.monet2.helper.WearLink;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.util.ArrayList;

/**
 * Created by mattr on 11/6/2018.
 */

public class SalmonRunResultsFragment extends Fragment implements SplatnetConnected {

    ViewGroup rootView;
    SplatnetConnector connector;
    SplatnetSQLManager database;

    RecyclerView listView;
    LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_salmon_result, container, false);

        database = new SplatnetSQLManager(getContext());

        listView = (RecyclerView) rootView.findViewById(R.id.Shifts);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(listView);

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
        connector.addRequest(new CoopResultsRequest(getContext()));

        update(connector.getCurrentData());
        connector.execute();
    }

    @Override
    public void update(Bundle bundle){
        updateUI();
    }
    private void updateUI(){
        ArrayList<SalmonRunDetail> salmonRunDetails = database.getShifts();
        ArrayList<ArrayList<SalmonRunDetail>> detailLists = new ArrayList<>();
        for(int i=salmonRunDetails.size();i>0;i-=25){
            detailLists.add(sort(new ArrayList<>(salmonRunDetails.subList(Math.max(0, i - 25),i))));
        }
        SalmonRunShiftPagerAdapter shiftAdapter = new SalmonRunShiftPagerAdapter(getContext(),detailLists);
        listView.setAdapter(shiftAdapter);
        linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        listView.setLayoutManager(linearLayoutManager);
    }

    private ArrayList<SalmonRunDetail> sort(ArrayList<SalmonRunDetail> data){
        if(data.size()<=1){
            return data;
        }
        if(data.size()==2){
            if(data.get(0).start>=data.get(1).start){
                return data;
            }else{
                SalmonRunDetail hold = data.get(0);
                data.remove(0);
                data.add(hold);
                return data;
            }
        }else{
            SalmonRunDetail pivot = data.get(0);
            ArrayList<SalmonRunDetail> lower = new ArrayList<>();
            ArrayList<SalmonRunDetail> upper = new ArrayList<>();
            for(int i=1;i<data.size();i++){
                if(pivot.start<data.get(i).start){
                    lower.add(data.get(i));
                }else{
                    upper.add(data.get(i));
                }
            }
            ArrayList<SalmonRunDetail> result = sort(lower);
            result.add(pivot);
            result.addAll(sort(upper));
            return result;
        }
    }
}
