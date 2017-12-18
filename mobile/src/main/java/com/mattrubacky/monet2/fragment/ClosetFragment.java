package com.mattrubacky.monet2.fragment;

import android.content.Intent;
import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.mattrubacky.monet2.ClosetDetail;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.adapter.GearAdapter;
import com.mattrubacky.monet2.deserialized.ClosetHanger;
import com.mattrubacky.monet2.helper.StatCalc;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.util.ArrayList;


/**
 * Created by mattr on 11/6/2017.
 */

public class ClosetFragment extends Fragment {

    ViewGroup rootView;
    SharedPreferences settings;
    ArrayList<ClosetHanger> gearList;
    RecyclerView gearListView;
    RelativeLayout headTab,clothesTab,shoeTab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (ViewGroup)  inflater.inflate(R.layout.fragment_closet, container, false);

        settings = PreferenceManager.getDefaultSharedPreferences(getContext());

        SplatnetSQLManager database = new SplatnetSQLManager(getContext());
        gearList = database.getCloset();

        headTab = (RelativeLayout) rootView.findViewById(R.id.HeadTab);
        clothesTab = (RelativeLayout) rootView.findViewById(R.id.ClothesTab);
        shoeTab = (RelativeLayout) rootView.findViewById(R.id.ShoesTab);

        headTab.setBackgroundTintList(getResources().getColorStateList(R.color.head));
        clothesTab.setBackgroundTintList(getResources().getColorStateList(R.color.rankAccent));
        shoeTab.setBackgroundTintList(getResources().getColorStateList(R.color.rankAccent));

        headTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headTab.setBackgroundTintList(getResources().getColorStateList(R.color.head));
                clothesTab.setBackgroundTintList(getResources().getColorStateList(R.color.rankAccent));
                shoeTab.setBackgroundTintList(getResources().getColorStateList(R.color.rankAccent));
            }
        });

        clothesTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headTab.setBackgroundTintList(getResources().getColorStateList(R.color.rankAccent));
                clothesTab.setBackgroundTintList(getResources().getColorStateList(R.color.clothes));
                shoeTab.setBackgroundTintList(getResources().getColorStateList(R.color.rankAccent));
            }
        });

        shoeTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headTab.setBackgroundTintList(getResources().getColorStateList(R.color.rankAccent));
                clothesTab.setBackgroundTintList(getResources().getColorStateList(R.color.rankAccent));
                shoeTab.setBackgroundTintList(getResources().getColorStateList(R.color.shoes));
            }
        });

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

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        width = width/3;
        ViewGroup.LayoutParams layoutParams = headTab.getLayoutParams();
        layoutParams.width = width;
        headTab.setLayoutParams(layoutParams);
        layoutParams = clothesTab.getLayoutParams();
        layoutParams.width = width;
        clothesTab.setLayoutParams(layoutParams);
        layoutParams = shoeTab.getLayoutParams();
        layoutParams.width = width;
        shoeTab.setLayoutParams(layoutParams);
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
