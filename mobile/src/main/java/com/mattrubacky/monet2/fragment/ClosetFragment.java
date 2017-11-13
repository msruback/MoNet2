package com.mattrubacky.monet2.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mattrubacky.monet2.ClosetDetail;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.WeaponLockerDetail;
import com.mattrubacky.monet2.adapter.GearAdapter;
import com.mattrubacky.monet2.deserialized.ClosetHanger;
import com.mattrubacky.monet2.deserialized.Record;
import com.mattrubacky.monet2.deserialized.WeaponStats;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.mattrubacky.monet2.helper.StatCalc;
import com.mattrubacky.monet2.splatnet_interface.Splatnet;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by mattr on 11/6/2017.
 */

public class ClosetFragment extends Fragment {

    ViewGroup rootView;
    SharedPreferences settings;
    ArrayList<ClosetHanger> gearList;
    RecyclerView gearListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (ViewGroup)  inflater.inflate(R.layout.fragment_closet, container, false);

        settings = PreferenceManager.getDefaultSharedPreferences(getContext());

        SplatnetSQLManager database = new SplatnetSQLManager(getContext());
        gearList = database.getCloset();
        return rootView;

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUi();
    }

    private void updateUi(){
        gearListView = (RecyclerView) rootView.findViewById(R.id.GearList);
        GearAdapter gearAdapter = new GearAdapter(getContext(), gearList, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = gearListView.getChildAdapterPosition(v);
                Intent intent = new Intent(getActivity(), ClosetDetail.class);
                Bundle bundle = new Bundle();
                ClosetHanger hanger = gearList.get(itemPosition);

                StatCalc statCalc = new StatCalc(getContext(),hanger.gear);
                hanger.inkStats = statCalc.getInkStats();
                hanger.killStats = statCalc.getKillStats();
                hanger.deathStats = statCalc.getDeathStats();
                hanger.specialStats = statCalc.getSpecialStats();
                hanger.numGames = statCalc.getNum();

                bundle.putParcelable("stats",hanger);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        gearListView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        gearListView.setAdapter(gearAdapter);
    }
}
