package com.mattrubacky.monet2.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.Record;
import com.mattrubacky.monet2.deserialized.SplatfestDatabase;
import com.mattrubacky.monet2.splatnet.PastSplatfestRequest;
import com.mattrubacky.monet2.splatnet.RecordsRequest;
import com.mattrubacky.monet2.splatnet.SplatnetConnected;
import com.mattrubacky.monet2.splatnet.SplatnetConnector;

import java.util.ArrayList;

/**
 * Created by mattr on 12/18/2017.
 */

public class PlayerStatsFragment extends Fragment implements SplatnetConnected {
    ViewGroup rootView;
    SharedPreferences settings;

    SplatnetConnector splatnetConnector;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (ViewGroup)  inflater.inflate(R.layout.fragment_player_stats, container, false);

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
        splatnetConnector.addRequest(new RecordsRequest(getContext()));
        update(splatnetConnector.getCurrentData());
        splatnetConnector.execute();
    }

    @Override
    public void update(Bundle bundle) {

    }
}
