package com.mattrubacky.monet2.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mattrubacky.monet2.*;
import com.mattrubacky.monet2.adapter.ListView.ScheduleAdapter;
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
        if(timeline.currentRun!=null&&timeline.currentRun.rewardGear!=null) {
            monthlyGear = timeline.currentRun.rewardGear.gear;
        }
        updateUI();
        wearLink.sendRotation(schedules);
        wearLink.sendSalmon(salmonSchedule);
    }

    public void updateUI(){
        ListView scheduleList = (ListView) rootView.findViewById(R.id.ScheduleList);

        wearLink.openConnection();

        ArrayList<String> rotation = new ArrayList<>();
        if(schedules.regular.size()>0){
            rotation.add("regular");
        }
        if(schedules.ranked.size()>0){
            rotation.add("ranked");
        }
        if(schedules.league.size()>0){
            rotation.add("league");
        }
        if(currentSplatfest.splatfests.size()>0&&schedules.splatfest.size()>0){
            if(schedules.regular.size()==0||currentSplatfest.splatfests.get(0).times.start<schedules.regular.get(0).start){
                rotation.add(0,"fes");
            }else{
                rotation.add("fes");
            }
        }

        if(salmonSchedule.details.size()>0){
            rotation.add("salmon");
        }

        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(getContext(),rotation,getChildFragmentManager(),schedules,currentSplatfest,salmonSchedule,monthlyGear);
        scheduleList.setAdapter(scheduleAdapter);

        wearLink.sendRotation(schedules);
        wearLink.sendSalmon(salmonSchedule);
    }
}