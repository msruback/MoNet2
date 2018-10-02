package com.mattrubacky.monet2.fragment.schedule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.adapter.ListView.SalmonRunAdapter;
import com.mattrubacky.monet2.deserialized.splatoon.SalmonRun;

import java.util.ArrayList;

/**
 * Created by mattr on 11/9/2017.
 */

public class SalmonRotation extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_salmon_rotation, container, false);

        Bundle bundle = this.getArguments();
        ArrayList<SalmonRun> salmonRuns = bundle.getParcelableArrayList("salmon_runs");

        ListView salmonList = (ListView) rootView.findViewById(R.id.SalmonList);
        SalmonRunAdapter adapter = new SalmonRunAdapter(getContext(),salmonRuns);
        salmonList.setAdapter(adapter);

        return rootView;
    }
}
