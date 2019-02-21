package com.mattrubacky.monet2.ui.fragment.MainScreenFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mattrubacky.monet2.*;
import com.mattrubacky.monet2.backend.WearLink;
import com.mattrubacky.monet2.ui.adapter.RecyclerView.ScheduleAdapter;
import com.mattrubacky.monet2.data.deserialized.splatoon.Gear;
import com.mattrubacky.monet2.data.deserialized.splatoon.SalmonSchedule;
import com.mattrubacky.monet2.data.deserialized.splatoon.Schedules;
import com.mattrubacky.monet2.backend.viewmodels.RotationViewModel;

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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_rotation, container, false);

        wearLink = new WearLink(getContext());

        wearLink.openConnection();

        viewModel = ViewModelProviders.of(this).get(RotationViewModel.class);

        viewModel.getSchedules().observe(this, new Observer<Schedules>() {
            @Override
            public void onChanged(Schedules schedules) {
                RecyclerView scheduleList = rootView.findViewById(R.id.ScheduleList);
                ScheduleAdapter scheduleAdapter = new ScheduleAdapter(getContext(),schedules,viewModel.getSalmonSchedule().getValue(),viewModel.getMonthlyGear().getValue(),viewModel.getCurrentSplatfest());
                scheduleList.setAdapter(scheduleAdapter);
                scheduleList.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));

                wearLink.sendRotation(schedules);
            }
        });

        viewModel.getSalmonSchedule().observe(this, new Observer<SalmonSchedule>() {
            @Override
            public void onChanged(SalmonSchedule salmonSchedule) {
                RecyclerView scheduleList = rootView.findViewById(R.id.ScheduleList);
                ScheduleAdapter scheduleAdapter = new ScheduleAdapter(getContext(),viewModel.getSchedules().getValue(),salmonSchedule,viewModel.getMonthlyGear().getValue(),viewModel.getCurrentSplatfest());
                scheduleList.setAdapter(scheduleAdapter);
                scheduleList.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));

                wearLink.sendSalmon(salmonSchedule);
            }
        });

        viewModel.getMonthlyGear().observe(this, new Observer<Gear>() {
            @Override
            public void onChanged(Gear gear) {
                RecyclerView scheduleList = rootView.findViewById(R.id.ScheduleList);
                ScheduleAdapter scheduleAdapter = new ScheduleAdapter(getContext(),viewModel.getSchedules().getValue(),viewModel.getSalmonSchedule().getValue(),gear,viewModel.getCurrentSplatfest());
                scheduleList.setAdapter(scheduleAdapter);
                scheduleList.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
            }
        });

        return rootView;
    }
}