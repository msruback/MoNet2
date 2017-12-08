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
import com.mattrubacky.monet2.SplatfestDetail;
import com.mattrubacky.monet2.adapter.SplatfestAdapter;
import com.mattrubacky.monet2.deserialized.PastSplatfest;
import com.mattrubacky.monet2.deserialized.Record;
import com.mattrubacky.monet2.deserialized.Records;
import com.mattrubacky.monet2.deserialized.Splatfest;
import com.mattrubacky.monet2.deserialized.SplatfestResult;
import com.mattrubacky.monet2.dialog.AlertDialog;
import com.mattrubacky.monet2.helper.StatCalc;
import com.mattrubacky.monet2.splatnet.PastSplatfestRequest;
import com.mattrubacky.monet2.splatnet.RecordsRequest;
import com.mattrubacky.monet2.splatnet.Splatnet;
import com.mattrubacky.monet2.splatnet.SplatnetConnected;
import com.mattrubacky.monet2.splatnet.SplatnetConnector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mattr on 11/15/2017.
 */

public class SplatfestStatsFragment extends Fragment implements SplatnetConnected{
    ViewGroup rootView;
    SharedPreferences settings;
    Record records;
    PastSplatfest splatfests;
    RecyclerView splatfestList;

    SplatnetConnector splatnetConnector;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (ViewGroup)  inflater.inflate(R.layout.fragment_splatfest_stats, container, false);

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
        splatfests = gson.fromJson(settings.getString("splatfests",""),PastSplatfest.class);
        records = gson.fromJson(settings.getString("records",""),Record.class);
        if(records==null){
            records = new Record();
            records.records = new Records();
            records.records.splatfestRecords = Collections.emptyMap();;
        }
        if(splatfests==null){
            splatfests = new PastSplatfest();
            splatfests.splatfests = new ArrayList<>();
            splatfests.results = new ArrayList<>();
        }
        updateUI();

        splatnetConnector = new SplatnetConnector(this,getActivity(),getContext());
        splatnetConnector.addRequest(new PastSplatfestRequest(getContext()));
        splatnetConnector.addRequest(new RecordsRequest(getContext()));
        splatnetConnector.execute();
    }

    private void updateUI(){
        splatfestList = (RecyclerView) rootView.findViewById(R.id.SplatfestList);
        SplatfestAdapter splatfestAdapter = new SplatfestAdapter(getContext(), splatfests, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = splatfestList.getChildAdapterPosition(v);
                Intent intent = new Intent(getActivity(), SplatfestDetail.class);
                Bundle bundle = new Bundle();
                Splatfest splatfest = splatfests.splatfests.get(itemPosition);
                SplatfestResult result = null;
                for(int i=0;i<splatfests.results.size();i++){
                    if(splatfest.id == splatfests.results.get(i).id){
                        result = splatfests.results.get(i);
                    }
                }

                bundle.putParcelable("splatfest",splatfest);
                bundle.putParcelable("result",result);
                StatCalc statCalc = new StatCalc(getContext(),splatfest);
                bundle.putString("grade",records.records.splatfestRecords.get(splatfest.id).grade.name);
                bundle.putInt("power",records.records.splatfestRecords.get(splatfest.id).power);
                //Need to get votes
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        splatfestList.setLayoutManager(new GridLayoutManager(getContext(), 1));
        splatfestList.setAdapter(splatfestAdapter);
    }

    @Override
    public void update(Bundle bundle) {
        splatfests = bundle.getParcelable("pastSplatfests");
        records = bundle.getParcelable("records");
    }
}
