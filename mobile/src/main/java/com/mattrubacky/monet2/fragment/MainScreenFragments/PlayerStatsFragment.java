package com.mattrubacky.monet2.fragment.MainScreenFragments;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.adapter.RecyclerView.PlayerAdapter;
import com.mattrubacky.monet2.deserialized.splatoon.NicknameIcon;
import com.mattrubacky.monet2.deserialized.splatoon.NicknameIcons;
import com.mattrubacky.monet2.deserialized.splatoon.Record;
import com.mattrubacky.monet2.api.splatnet.NicknameRequest;
import com.mattrubacky.monet2.api.splatnet.RecordsRequest;
import com.mattrubacky.monet2.api.splatnet.SplatnetConnected;
import com.mattrubacky.monet2.api.splatnet.SplatnetConnector;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 12/18/2017.
 */

public class PlayerStatsFragment extends Fragment implements SplatnetConnected {
    private ViewGroup rootView;

    private SplatnetConnector splatnetConnector;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView pager;

    private RelativeLayout playerTab,challengeTab,statsTab;
    private ImageView playerImage,challengeImage,statsImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (ViewGroup)  inflater.inflate(R.layout.fragment_player_stats, container, false);


        linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        pager = rootView.findViewById(R.id.PlayerPager);
        pager.setLayoutManager(linearLayoutManager);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(pager);

        playerTab = rootView.findViewById(R.id.UserTab);
        challengeTab = rootView.findViewById(R.id.ChallengeTab);
        statsTab = rootView.findViewById(R.id.StatsTab);

        playerImage = rootView.findViewById(R.id.UserImage);
        challengeImage = rootView.findViewById(R.id.ChallengeImage);
        statsImage = rootView.findViewById(R.id.StatsImage);

        playerTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutManager.scrollToPosition(0);
                playerTab.setBackgroundTintList(getResources().getColorStateList(R.color.favColorGreen));
                challengeTab.setBackgroundTintList(getResources().getColorStateList(R.color.favColorYellow));
                statsTab.setBackgroundTintList(getResources().getColorStateList(R.color.favColorYellow));

                playerImage.setImageTintList(getResources().getColorStateList(R.color.favColorYellow));
                challengeImage.setImageTintList(getResources().getColorStateList(R.color.favColorGreen));
                statsImage.setImageTintList(getResources().getColorStateList(R.color.favColorGreen));
            }
        });

        challengeTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutManager.scrollToPosition(1);
                playerTab.setBackgroundTintList(getResources().getColorStateList(R.color.favColorYellow));
                challengeTab.setBackgroundTintList(getResources().getColorStateList(R.color.favColorGreen));
                statsTab.setBackgroundTintList(getResources().getColorStateList(R.color.favColorYellow));

                playerImage.setImageTintList(getResources().getColorStateList(R.color.favColorGreen));
                challengeImage.setImageTintList(getResources().getColorStateList(R.color.favColorYellow));
                statsImage.setImageTintList(getResources().getColorStateList(R.color.favColorGreen));
            }
        });

        statsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutManager.scrollToPosition(2);
                playerTab.setBackgroundTintList(getResources().getColorStateList(R.color.favColorYellow));
                challengeTab.setBackgroundTintList(getResources().getColorStateList(R.color.favColorYellow));
                statsTab.setBackgroundTintList(getResources().getColorStateList(R.color.favColorGreen));

                playerImage.setImageTintList(getResources().getColorStateList(R.color.favColorGreen));
                challengeImage.setImageTintList(getResources().getColorStateList(R.color.favColorGreen));
                statsImage.setImageTintList(getResources().getColorStateList(R.color.favColorYellow));
            }
        });
        pager.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState==0){
                    playerImage.setImageTintList(getResources().getColorStateList(R.color.favColorGreen));
                    challengeImage.setImageTintList(getResources().getColorStateList(R.color.favColorGreen));
                    statsImage.setImageTintList(getResources().getColorStateList(R.color.favColorGreen));
                    switch(linearLayoutManager.findFirstVisibleItemPosition()){
                        case 0:
                            playerTab.setBackgroundTintList(getResources().getColorStateList(R.color.favColorGreen));
                            challengeTab.setBackgroundTintList(getResources().getColorStateList(R.color.favColorYellow));
                            statsTab.setBackgroundTintList(getResources().getColorStateList(R.color.favColorYellow));

                            playerImage.setImageTintList(getResources().getColorStateList(R.color.favColorYellow));
                            break;
                        case 1:
                            playerTab.setBackgroundTintList(getResources().getColorStateList(R.color.favColorYellow));
                            challengeTab.setBackgroundTintList(getResources().getColorStateList(R.color.favColorGreen));
                            statsTab.setBackgroundTintList(getResources().getColorStateList(R.color.favColorYellow));

                            challengeImage.setImageTintList(getResources().getColorStateList(R.color.favColorYellow));
                            break;
                        case 2:
                            playerTab.setBackgroundTintList(getResources().getColorStateList(R.color.favColorYellow));
                            challengeTab.setBackgroundTintList(getResources().getColorStateList(R.color.favColorYellow));
                            statsTab.setBackgroundTintList(getResources().getColorStateList(R.color.favColorGreen));

                            statsImage.setImageTintList(getResources().getColorStateList(R.color.favColorYellow));
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


        playerTab.setBackgroundTintList(getResources().getColorStateList(R.color.favColorGreen));
        challengeTab.setBackgroundTintList(getResources().getColorStateList(R.color.favColorYellow));
        statsTab.setBackgroundTintList(getResources().getColorStateList(R.color.favColorYellow));

        playerImage.setImageTintList(getResources().getColorStateList(R.color.favColorYellow));
        challengeImage.setImageTintList(getResources().getColorStateList(R.color.favColorGreen));
        statsImage.setImageTintList(getResources().getColorStateList(R.color.favColorGreen));

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
