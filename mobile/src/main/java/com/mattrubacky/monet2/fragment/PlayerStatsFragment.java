package com.mattrubacky.monet2.fragment;

import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.adapter.PlayerAdapter;
import com.mattrubacky.monet2.deserialized.NicknameIcon;
import com.mattrubacky.monet2.deserialized.NicknameIcons;
import com.mattrubacky.monet2.deserialized.Record;
import com.mattrubacky.monet2.deserialized.SplatfestDatabase;
import com.mattrubacky.monet2.splatnet.NicknameRequest;
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
    LinearLayoutManager linearLayoutManager;
    RecyclerView pager;

    RelativeLayout playerTab,challengeTab,statsTab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (ViewGroup)  inflater.inflate(R.layout.fragment_player_stats, container, false);

        settings = PreferenceManager.getDefaultSharedPreferences(getContext());

        linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        pager = (RecyclerView) rootView.findViewById(R.id.PlayerPager);
        pager.setLayoutManager(linearLayoutManager);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(pager);

        playerTab = (RelativeLayout) rootView.findViewById(R.id.UserTab);
        challengeTab = (RelativeLayout) rootView.findViewById(R.id.ChallengeTab);
        statsTab = (RelativeLayout) rootView.findViewById(R.id.StatsTab);

        playerTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutManager.scrollToPosition(0);
                playerTab.setBackgroundTintList(getResources().getColorStateList(R.color.primary));
                challengeTab.setBackgroundTintList(getResources().getColorStateList(R.color.accent));
                statsTab.setBackgroundTintList(getResources().getColorStateList(R.color.accent));
            }
        });

        challengeTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutManager.scrollToPosition(1);
                playerTab.setBackgroundTintList(getResources().getColorStateList(R.color.accent));
                challengeTab.setBackgroundTintList(getResources().getColorStateList(R.color.primary));
                statsTab.setBackgroundTintList(getResources().getColorStateList(R.color.accent));
            }
        });

        statsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutManager.scrollToPosition(2);
                playerTab.setBackgroundTintList(getResources().getColorStateList(R.color.accent));
                challengeTab.setBackgroundTintList(getResources().getColorStateList(R.color.accent));
                statsTab.setBackgroundTintList(getResources().getColorStateList(R.color.primary));
            }
        });
        pager.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState==0){
                    switch(linearLayoutManager.findFirstVisibleItemPosition()){
                        case 0:
                            playerTab.setBackgroundTintList(getResources().getColorStateList(R.color.primary));
                            challengeTab.setBackgroundTintList(getResources().getColorStateList(R.color.accent));
                            statsTab.setBackgroundTintList(getResources().getColorStateList(R.color.accent));
                            break;
                        case 1:
                            playerTab.setBackgroundTintList(getResources().getColorStateList(R.color.accent));
                            challengeTab.setBackgroundTintList(getResources().getColorStateList(R.color.primary));
                            statsTab.setBackgroundTintList(getResources().getColorStateList(R.color.accent));
                            break;
                        case 2:
                            playerTab.setBackgroundTintList(getResources().getColorStateList(R.color.accent));
                            challengeTab.setBackgroundTintList(getResources().getColorStateList(R.color.accent));
                            statsTab.setBackgroundTintList(getResources().getColorStateList(R.color.primary));
                            break;
                    }
                }
            }
        });

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
        Record record = splatnetConnector.getCurrentData().getParcelable("records");
        splatnetConnector.addRequest(new NicknameRequest(record.records.user.id));
        update(splatnetConnector.getCurrentData());
        splatnetConnector.execute();


        playerTab.setBackgroundTintList(getResources().getColorStateList(R.color.accent));
        challengeTab.setBackgroundTintList(getResources().getColorStateList(R.color.primary));
        statsTab.setBackgroundTintList(getResources().getColorStateList(R.color.primary));

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        width = width/3;
        ViewGroup.LayoutParams layoutParams = playerTab.getLayoutParams();
        layoutParams.width = width;
        playerTab.setLayoutParams(layoutParams);
        layoutParams = challengeTab.getLayoutParams();
        layoutParams.width = width;
        challengeTab.setLayoutParams(layoutParams);
        layoutParams = statsTab.getLayoutParams();
        layoutParams.width = width;
        statsTab.setLayoutParams(layoutParams);
    }

    @Override
    public void update(Bundle bundle) {
        Record records = bundle.getParcelable("records");
        NicknameIcons icon = bundle.getParcelable("nickname");
        NicknameIcon nicknameIcon = null;
        if(icon.nicknameIcons.size()>0){
            nicknameIcon = icon.nicknameIcons.get(0);
        }
        PlayerAdapter playerAdapter = new PlayerAdapter(getContext(),records,nicknameIcon,getChildFragmentManager());

        pager.setAdapter(playerAdapter);
    }
}
