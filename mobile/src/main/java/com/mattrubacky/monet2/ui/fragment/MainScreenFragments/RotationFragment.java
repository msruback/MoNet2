package com.mattrubacky.monet2.ui.fragment.MainScreenFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mattrubacky.monet2.*;
import com.mattrubacky.monet2.backend.WearLink;
import com.mattrubacky.monet2.backend.api.splatnet.SplatnetParsley;
import com.mattrubacky.monet2.backend.viewmodels.RotationViewModel;
import com.mattrubacky.monet2.data.parsley.Bunch;
import com.mattrubacky.monet2.data.parsley.splatnet.bunch.RotationBunch;
import com.mattrubacky.monet2.data.rooms.SplatnetDatabase;
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

        MediatorLiveData<Bunch<SplatnetParsley, SplatnetDatabase>> rotation = viewModel.feedMo(new RotationBunch());
//        rotation.observe(this, new Observer<RotationBunch>() {
//            @Override
//            public void onChanged(RotationBunch rotationBunch) {
//                RecyclerView scheduleList = rootView.findViewById(R.id.ScheduleList);
//                //ScheduleAdapter scheduleAdapter = new ScheduleAdapter(getContext(), rotationBunch.schedules(), rotationBunch.salmonSchedule(), rotationBunch.rewardGear(), rotationBunch.splatfest());
//                //scheduleList.setAdapter(scheduleAdapter);
//                //scheduleList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
//
//                //wearLink.sendRotation(rotationBunch.schedules());
//               //wearLink.sendSalmon(rotationBunch.salmonSchedule());
//            }
//        });
        return rootView;
    }
}