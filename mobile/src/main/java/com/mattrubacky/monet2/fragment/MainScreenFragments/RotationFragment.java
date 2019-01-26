package com.mattrubacky.monet2.fragment.MainScreenFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mattrubacky.monet2.*;
import com.mattrubacky.monet2.adapter.RecyclerView.ScheduleAdapter;
import com.mattrubacky.monet2.deserialized.splatoon.CurrentSplatfest;
import com.mattrubacky.monet2.deserialized.splatoon.Gear;
import com.mattrubacky.monet2.deserialized.splatoon.SalmonSchedule;
import com.mattrubacky.monet2.deserialized.splatoon.Schedules;
import com.mattrubacky.monet2.helper.*;
import com.mattrubacky.monet2.viewmodels.RotationViewModel;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 9/14/2017.
 */

public class RotationFragment extends Fragment {
    private ViewGroup rootView;
    private WearLink wearLink;
    private RotationViewModel viewModel;

    private Schedules schedules;
    private SalmonSchedule salmonSchedule;
    private Gear monthlyGear;
    private CurrentSplatfest currentSplatfest;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_rotation, container, false);

        wearLink = new WearLink(getContext());

        viewModel = ViewModelProviders.of(this).get(RotationViewModel.class);

        viewModel.getSchedules().observe(this, new Observer<Schedules>() {
            @Override
            public void onChanged(Schedules schedules) {
                RecyclerView scheduleList = rootView.findViewById(R.id.ScheduleList);
                ScheduleAdapter scheduleAdapter = new ScheduleAdapter(getContext(),schedules,salmonSchedule,monthlyGear,currentSplatfest);
                scheduleList.setAdapter(scheduleAdapter);
                scheduleList.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
            }
        });

        wearLink.openConnection();

        wearLink.sendRotation(schedules);
        wearLink.sendSalmon(salmonSchedule);

        return rootView;
    }
}