package com.mattrubacky.monet2.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.WeaponLockerDetail;
import com.mattrubacky.monet2.adapter.WeaponAdapter;
import com.mattrubacky.monet2.deserialized.Record;
import com.mattrubacky.monet2.deserialized.WeaponStats;
import com.mattrubacky.monet2.dialog.AlertDialog;
import com.mattrubacky.monet2.helper.StatCalc;
import com.mattrubacky.monet2.splatnet.RecordsRequest;
import com.mattrubacky.monet2.splatnet.Splatnet;
import com.mattrubacky.monet2.splatnet.SplatnetConnected;
import com.mattrubacky.monet2.splatnet.SplatnetConnector;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mattr on 11/1/2017.
 */

public class WeaponLockerFragment extends Fragment implements SplatnetConnected{

    ViewGroup rootView;
    SharedPreferences settings;
    Record records;
    ArrayList<WeaponStats> weaponStatsList;
    RecyclerView weaponList;
    SplatnetConnector splatnetConnector;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (ViewGroup)  inflater.inflate(R.layout.fragment_weapon_locker, container, false);

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
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        records = gson.fromJson(settings.getString("records",""),Record.class);
        Integer[] keys = new Integer[2];
        if(records!=null) {
            keys = records.records.weaponStats.keySet().toArray(keys);
            weaponStatsList = new ArrayList<>();
            for (int i = 0; i < keys.length; i++) {
                weaponStatsList.add(records.records.weaponStats.get(keys[i]));
            }
        }
        updateUI();
        splatnetConnector = new SplatnetConnector(this,getActivity(),getContext());
        splatnetConnector.addRequest(new RecordsRequest(getContext()));
        splatnetConnector.execute();
    }

    private void updateUI(){
        weaponList = (RecyclerView) rootView.findViewById(R.id.WeaponList);
        WeaponAdapter weaponAdapter = new WeaponAdapter(getContext(), weaponStatsList, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = weaponList.getChildAdapterPosition(v);
                Intent intent = new Intent(getActivity(), WeaponLockerDetail.class);
                Bundle bundle = new Bundle();
                WeaponStats weaponStats = weaponStatsList.get(itemPosition);

                StatCalc statCalc = new StatCalc(getContext(),weaponStats.weapon);
                weaponStats.inkStats = statCalc.getInkStats();
                weaponStats.killStats = statCalc.getKillStats();
                weaponStats.deathStats = statCalc.getDeathStats();
                weaponStats.specialStats = statCalc.getSpecialStats();
                weaponStats.numGames = statCalc.getNum();

                bundle.putParcelable("stats",weaponStats);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        weaponList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        weaponList.setAdapter(weaponAdapter);
    }

    @Override
    public void update(Bundle bundle) {
        records = bundle.getParcelable("records");

        Integer[] keys = new Integer[2];
        keys = records.records.weaponStats.keySet().toArray(keys);
        weaponStatsList = new ArrayList<>();
        for (int i = 0; i < keys.length; i++) {
            weaponStatsList.add(records.records.weaponStats.get(keys[i]));
        }

        updateUI();
    }
}