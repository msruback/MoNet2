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
import com.mattrubacky.monet2.MainActivity;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.SplatfestDetail;
import com.mattrubacky.monet2.WeaponLockerDetail;
import com.mattrubacky.monet2.adapter.SplatfestAdapter;
import com.mattrubacky.monet2.adapter.WeaponAdapter;
import com.mattrubacky.monet2.deserialized.CurrentSplatfest;
import com.mattrubacky.monet2.deserialized.PastSplatfest;
import com.mattrubacky.monet2.deserialized.Record;
import com.mattrubacky.monet2.deserialized.Records;
import com.mattrubacky.monet2.deserialized.Splatfest;
import com.mattrubacky.monet2.deserialized.SplatfestResult;
import com.mattrubacky.monet2.deserialized.SplatfestStats;
import com.mattrubacky.monet2.deserialized.SplatfestVote;
import com.mattrubacky.monet2.deserialized.SplatfestVotes;
import com.mattrubacky.monet2.deserialized.WeaponStats;
import com.mattrubacky.monet2.dialog.AlertDialog;
import com.mattrubacky.monet2.helper.StatCalc;
import com.mattrubacky.monet2.splatnet_interface.Splatnet;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mattr on 11/15/2017.
 */

public class SplatfestStatsFragment extends Fragment {
    ViewGroup rootView;
    SharedPreferences settings;
    Record records;
    PastSplatfest splatfests;
    UpdateRecords updateRecords;
    RecyclerView splatfestList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (ViewGroup)  inflater.inflate(R.layout.fragment_splatfest_stats, container, false);

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
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        records = gson.fromJson(settings.getString("records",""),Record.class);
    }

    private void updateUi(){
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

    private class UpdateRecords extends AsyncTask<Void,Void,Void> {

        ImageView loading;
        @Override
        protected void onPreExecute() {
            loading =(ImageView) getActivity().findViewById(R.id.loading_indicator);

            RotateAnimation animation = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
            animation.setInterpolator(new LinearInterpolator());
            animation.setRepeatCount(Animation.INFINITE);
            animation.setDuration(1000);
            loading.startAnimation(animation);
            loading.setVisibility(View.VISIBLE);}
        @Override
        protected Void doInBackground(Void... params) {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
            String cookie = settings.getString("cookie","");
            String uniqueId = settings.getString("unique_id","");

            try {
                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://app.splatoon2.nintendo.net").addConverterFactory(GsonConverterFactory.create()).build();
                Splatnet splatnet = retrofit.create(Splatnet.class);
                Call<PastSplatfest> getSplatfest = splatnet.getPastSplatfests(cookie,uniqueId);
                Response response = getSplatfest.execute();
                if(response.isSuccessful()){
                    splatfests = (PastSplatfest) response.body();
                    Call<Record> getRecords = splatnet.getRecords(cookie,uniqueId);
                    response = getRecords.execute();
                    if(response.isSuccessful()){
                        records = (Record) response.body();
                    }else if(response.code()==403){
                        AlertDialog alertDialog = new AlertDialog(getActivity(),"Error: Cookie is invalid, please obtain a new cookie");
                        alertDialog.show();
                    }
                }else if(response.code()==403){
                    AlertDialog alertDialog = new AlertDialog(getActivity(),"Error: Cookie is invalid, please obtain a new cookie");
                    alertDialog.show();
                }

            } catch (IOException e) {
                e.printStackTrace();
                AlertDialog alertDialog = new AlertDialog(getActivity(),"Error: Could not reach Splatnet");
                alertDialog.show();
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            updateUi();
            loading.setAnimation(null);
            loading.setVisibility(View.GONE);
        }

    }
}
