package com.mattrubacky.monet2.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.WeaponLockerDetail;
import com.mattrubacky.monet2.adapter.WeaponAdapter;
import com.mattrubacky.monet2.deserialized.Record;
import com.mattrubacky.monet2.deserialized.WeaponStats;
import com.mattrubacky.monet2.dialog.AlertDialog;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.mattrubacky.monet2.helper.StatCalc;
import com.mattrubacky.monet2.splatnet_interface.Splatnet;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mattr on 11/1/2017.
 */

public class WeaponLockerFragment extends Fragment {

    ViewGroup rootView;
    SharedPreferences settings;
    Record records;
    ArrayList<WeaponStats> weaponStatsList;
    UpdateRecords updateRecords;
    RecyclerView weaponList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (ViewGroup)  inflater.inflate(R.layout.fragment_weapon_locker, container, false);

        settings = PreferenceManager.getDefaultSharedPreferences(getContext());

        updateRecords = new UpdateRecords();
        updateRecords.execute();

        return rootView;

    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor edit = settings.edit();
        Gson gson = new Gson();
        String json = gson.toJson(records);
        edit.putString("records",json);
        edit.commit();

        ImageView loading =(ImageView) getActivity().findViewById(R.id.loading_indicator);
        loading.setVisibility(View.GONE);
        loading.setAnimation(null);
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
    }

    private void updateUi(){
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

    private class UpdateRecords extends AsyncTask<Void,Void,Void> {

        boolean isUnconn,isUnauth;
        ImageView loading;
        @Override
        protected void onPreExecute() {
            loading =(ImageView) getActivity().findViewById(R.id.loading_indicator);

            RotateAnimation animation = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
            animation.setInterpolator(new LinearInterpolator());
            animation.setRepeatCount(Animation.INFINITE);
            animation.setDuration(1000);
            loading.startAnimation(animation);
            loading.setVisibility(View.VISIBLE);

            isUnconn = false;
            isUnauth = false;
        }
        @Override
        protected Void doInBackground(Void... params) {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
            String cookie = settings.getString("cookie","");
            String uniqueId = settings.getString("unique_id","");

            try {
                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://app.splatoon2.nintendo.net").addConverterFactory(GsonConverterFactory.create()).build();
                Splatnet splatnet = retrofit.create(Splatnet.class);
                Response response;
                response = splatnet.getRecords(cookie,uniqueId).execute();
                if(response.isSuccessful()){
                    records = (Record) response.body();
                    Integer[] keys = new Integer[2];
                    keys = records.records.weaponStats.keySet().toArray(keys);
                    weaponStatsList = new ArrayList<>();
                    for(int i=0;i<keys.length;i++){
                        weaponStatsList.add(records.records.weaponStats.get(keys[i]));
                    }
                }else if(response.code()==403){
                    isUnauth = true;
                }

            } catch (IOException e) {
                e.printStackTrace();
                isUnconn = true;
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            updateUi();

            if(isUnconn){
                AlertDialog alertDialog = new AlertDialog(getActivity(),"Error: Could not reach Splatnet");
                alertDialog.show();
            }else if(isUnauth){
                AlertDialog alertDialog = new AlertDialog(getActivity(),"Error: Cookie is invalid, please obtain a new cookie");
                alertDialog.show();
            }

            loading.setAnimation(null);
            loading.setVisibility(View.GONE);
        }

    }
}