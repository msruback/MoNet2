package com.mattrubacky.monet2.ui.fragment.MainScreenFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mattrubacky.monet2.*;
import com.mattrubacky.monet2.backend.WearLink;
import com.mattrubacky.monet2.backend.viewmodels.RotationViewModel;
import com.mattrubacky.monet2.data.mediator.RotationMediator;
import com.mattrubacky.monet2.ui.adapter.RecyclerView.ScheduleAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MediatorLiveData;
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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_rotation, container, false);

        wearLink = new WearLink(getContext());

        wearLink.openConnection();

        RotationViewModel viewModel = ViewModelProviders.of(this).get(RotationViewModel.class);

        MediatorLiveData<RotationMediator> rotation = viewModel.getRotation();
        rotation.observe(this, new Observer<RotationMediator>() {
            @Override
            public void onChanged(RotationMediator rotationMediator) {
                RecyclerView scheduleList = rootView.findViewById(R.id.ScheduleList);
                ScheduleAdapter scheduleAdapter = new ScheduleAdapter(getContext(), rotationMediator.getSchedules(), rotationMediator.getSalmonSchedule(), rotationMediator.getRewardGear(), rotationMediator.getSplatfest());
                scheduleList.setAdapter(scheduleAdapter);
                scheduleList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

                wearLink.sendRotation(rotationMediator.getSchedules());
                wearLink.sendSalmon(rotationMediator.getSalmonSchedule());
            }
        });
        return rootView;
    }
}