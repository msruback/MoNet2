package com.mattrubacky.monet2.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mattrubacky.monet2.*;
import com.mattrubacky.monet2.adapter.RecyclerView.ScheduleAdapter;
import com.mattrubacky.monet2.deserialized.*;
import com.mattrubacky.monet2.helper.*;
import com.mattrubacky.monet2.splatnet.CoopSchedulesRequest;
import com.mattrubacky.monet2.splatnet.MonthlyGearRequest;
import com.mattrubacky.monet2.splatnet.SchedulesRequest;
import com.mattrubacky.monet2.splatnet.SplatnetConnected;
import com.mattrubacky.monet2.splatnet.SplatnetConnector;


import java.util.ArrayList;

/**
 * Created by mattr on 9/14/2017.
 */

public class RotationFragment extends Fragment implements SplatnetConnected{
    ViewGroup rootView;
    WearLink wearLink;
    SplatnetConnector connector;

    Schedules schedules;
    SalmonSchedule salmonSchedule;
    Gear monthlyGear;
    CurrentSplatfest currentSplatfest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_rotation, container, false);

        wearLink = new WearLink(getContext());

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
        wearLink.openConnection();

        connector = new SplatnetConnector(this, getActivity(),getContext());
        connector.addRequest(new SchedulesRequest(getContext()));
        connector.addRequest(new CoopSchedulesRequest(getContext(),false));
        connector.addRequest(new MonthlyGearRequest(getContext()));

        update(connector.getCurrentData());
        connector.execute();
    }

    @Override
    public void update(Bundle bundle){
        schedules = bundle.getParcelable("schedules");
        salmonSchedule = bundle.getParcelable("salmonSchedule");
        currentSplatfest = bundle.getParcelable("currentSplatfest");
        Timeline timeline = bundle.getParcelable("timeline");
        if(timeline!=null&&timeline.currentRun!=null&&timeline.currentRun.rewardGear!=null) {
            monthlyGear = timeline.currentRun.rewardGear.gear;
        }
        updateUI();
        wearLink.sendRotation(schedules);
        wearLink.sendSalmon(salmonSchedule);
    }

    public void updateUI(){
        RecyclerView scheduleList = (RecyclerView) rootView.findViewById(R.id.ScheduleList);

        wearLink.openConnection();

        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(getContext(),schedules,salmonSchedule,monthlyGear,currentSplatfest);
        scheduleList.setAdapter(scheduleAdapter);
        scheduleList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        wearLink.sendRotation(schedules);
        wearLink.sendSalmon(salmonSchedule);
    }
}