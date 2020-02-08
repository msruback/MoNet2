package com.mattrubacky.monet2.ui.fragment.MainScreenFragments.StatFragments;

import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.ui.adapter.RecyclerView.WeaponPagerAdapter;
import com.mattrubacky.monet2.data.deserialized.splatoon.Record;
import com.mattrubacky.monet2.data.stats.WeaponStats;
import com.mattrubacky.monet2.backend.api.splatnet.SplatnetConnected;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 11/1/2017.
 */

public class WeaponLockerFragment extends Fragment implements SplatnetConnected{

    private ViewGroup rootView;
    private SharedPreferences settings;
    private Record records;
    private ArrayList<WeaponStats> weaponStatsList;
    private RecyclerView weaponList;
//    private SplatnetConnector splatnetConnector;
    private LinearLayoutManager linearLayoutManager;
    private RelativeLayout shooterTab,brushTab,chargerTab,slosherTab,splatlingTab,dualieTab,brellaTab;
    private ImageView shooterImage,brushImage,chargerImage,slosherImage,splatlingImage,dualieImage,brellaImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (ViewGroup)  inflater.inflate(R.layout.fragment_weapon_locker, container, false);

        settings = PreferenceManager.getDefaultSharedPreferences(getContext());


        weaponList = rootView.findViewById(R.id.WeaponList);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(weaponList);

        shooterTab = rootView.findViewById(R.id.ShooterTab);
        brushTab = rootView.findViewById(R.id.BrushTab);
        chargerTab = rootView.findViewById(R.id.ChargerTab);
        slosherTab = rootView.findViewById(R.id.SlosherTab);
        splatlingTab = rootView.findViewById(R.id.SplatlingTab);
        dualieTab = rootView.findViewById(R.id.DualieTab);
        brellaTab = rootView.findViewById(R.id.BrellaTab);

        shooterImage = rootView.findViewById(R.id.ShooterImage);
        brushImage = rootView.findViewById(R.id.BrushImage);
        chargerImage = rootView.findViewById(R.id.ChargerImage);
        slosherImage = rootView.findViewById(R.id.SlosherImage);
        splatlingImage = rootView.findViewById(R.id.SplatlingImage);
        dualieImage = rootView.findViewById(R.id.DualieImage);
        brellaImage = rootView.findViewById(R.id.BrellaImage);

        shooterTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutManager.scrollToPosition(0);
                shooterTab.setBackgroundTintList(getResources().getColorStateList(R.color.accent));
                brushTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                chargerTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                slosherTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                splatlingTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                dualieTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                brellaTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));

                shooterImage.setImageDrawable(getResources().getDrawable(R.drawable.shooter2));
                brushImage.setImageDrawable(getResources().getDrawable(R.drawable.brushes));
                chargerImage.setImageDrawable(getResources().getDrawable(R.drawable.chargers));
                slosherImage.setImageDrawable(getResources().getDrawable(R.drawable.sloshers));
                splatlingImage.setImageDrawable(getResources().getDrawable(R.drawable.splatlings));
                dualieImage.setImageDrawable(getResources().getDrawable(R.drawable.dualies));
                brellaImage.setImageDrawable(getResources().getDrawable(R.drawable.brellas));
            }
        });
        brushTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutManager.scrollToPosition(1);
                shooterTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                brushTab.setBackgroundTintList(getResources().getColorStateList(R.color.accent));
                chargerTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                slosherTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                splatlingTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                dualieTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                brellaTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));

                shooterImage.setImageDrawable(getResources().getDrawable(R.drawable.shooter));
                brushImage.setImageDrawable(getResources().getDrawable(R.drawable.brushes2));
                chargerImage.setImageDrawable(getResources().getDrawable(R.drawable.chargers));
                slosherImage.setImageDrawable(getResources().getDrawable(R.drawable.sloshers));
                splatlingImage.setImageDrawable(getResources().getDrawable(R.drawable.splatlings));
                dualieImage.setImageDrawable(getResources().getDrawable(R.drawable.dualies));
                brellaImage.setImageDrawable(getResources().getDrawable(R.drawable.brellas));
            }
        });
        chargerTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutManager.scrollToPosition(2);
                shooterTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                brushTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                chargerTab.setBackgroundTintList(getResources().getColorStateList(R.color.accent));
                slosherTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                splatlingTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                dualieTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                brellaTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));

                shooterImage.setImageDrawable(getResources().getDrawable(R.drawable.shooter));
                brushImage.setImageDrawable(getResources().getDrawable(R.drawable.brushes));
                chargerImage.setImageDrawable(getResources().getDrawable(R.drawable.chargers2));
                slosherImage.setImageDrawable(getResources().getDrawable(R.drawable.sloshers));
                splatlingImage.setImageDrawable(getResources().getDrawable(R.drawable.splatlings));
                dualieImage.setImageDrawable(getResources().getDrawable(R.drawable.dualies));
                brellaImage.setImageDrawable(getResources().getDrawable(R.drawable.brellas));
            }
        });
        slosherTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutManager.scrollToPosition(3);
                shooterTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                brushTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                chargerTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                slosherTab.setBackgroundTintList(getResources().getColorStateList(R.color.accent));
                splatlingTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                dualieTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                brellaTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));

                shooterImage.setImageDrawable(getResources().getDrawable(R.drawable.shooter));
                brushImage.setImageDrawable(getResources().getDrawable(R.drawable.brushes));
                chargerImage.setImageDrawable(getResources().getDrawable(R.drawable.chargers));
                slosherImage.setImageDrawable(getResources().getDrawable(R.drawable.sloshers2));
                splatlingImage.setImageDrawable(getResources().getDrawable(R.drawable.splatlings));
                dualieImage.setImageDrawable(getResources().getDrawable(R.drawable.dualies));
                brellaImage.setImageDrawable(getResources().getDrawable(R.drawable.brellas));
            }
        });
        splatlingTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutManager.scrollToPosition(4);
                shooterTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                brushTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                chargerTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                slosherTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                splatlingTab.setBackgroundTintList(getResources().getColorStateList(R.color.accent));
                dualieTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                brellaTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));

                shooterImage.setImageDrawable(getResources().getDrawable(R.drawable.shooter));
                brushImage.setImageDrawable(getResources().getDrawable(R.drawable.brushes));
                chargerImage.setImageDrawable(getResources().getDrawable(R.drawable.chargers));
                slosherImage.setImageDrawable(getResources().getDrawable(R.drawable.sloshers));
                splatlingImage.setImageDrawable(getResources().getDrawable(R.drawable.splatlings2));
                dualieImage.setImageDrawable(getResources().getDrawable(R.drawable.dualies));
                brellaImage.setImageDrawable(getResources().getDrawable(R.drawable.brellas));
            }
        });
        dualieTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutManager.scrollToPosition(5);
                shooterTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                brushTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                chargerTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                slosherTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                splatlingTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                dualieTab.setBackgroundTintList(getResources().getColorStateList(R.color.accent));
                brellaTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));

                shooterImage.setImageDrawable(getResources().getDrawable(R.drawable.shooter));
                brushImage.setImageDrawable(getResources().getDrawable(R.drawable.brushes));
                chargerImage.setImageDrawable(getResources().getDrawable(R.drawable.chargers));
                slosherImage.setImageDrawable(getResources().getDrawable(R.drawable.sloshers));
                splatlingImage.setImageDrawable(getResources().getDrawable(R.drawable.splatlings));
                dualieImage.setImageDrawable(getResources().getDrawable(R.drawable.dualies2));
                brellaImage.setImageDrawable(getResources().getDrawable(R.drawable.brellas));
            }
        });
        brellaTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutManager.scrollToPosition(6);
                shooterTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                brushTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                chargerTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                slosherTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                splatlingTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                dualieTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                brellaTab.setBackgroundTintList(getResources().getColorStateList(R.color.accent));

                shooterImage.setImageDrawable(getResources().getDrawable(R.drawable.shooter));
                brushImage.setImageDrawable(getResources().getDrawable(R.drawable.brushes));
                chargerImage.setImageDrawable(getResources().getDrawable(R.drawable.chargers));
                slosherImage.setImageDrawable(getResources().getDrawable(R.drawable.sloshers));
                splatlingImage.setImageDrawable(getResources().getDrawable(R.drawable.splatlings));
                dualieImage.setImageDrawable(getResources().getDrawable(R.drawable.dualies));
                brellaImage.setImageDrawable(getResources().getDrawable(R.drawable.brellas2));
            }
        });
        weaponList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState==0){
                    shooterTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                    brushTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                    chargerTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                    slosherTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                    splatlingTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                    dualieTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
                    brellaTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));

                    shooterImage.setImageDrawable(getResources().getDrawable(R.drawable.shooter));
                    brushImage.setImageDrawable(getResources().getDrawable(R.drawable.brushes));
                    chargerImage.setImageDrawable(getResources().getDrawable(R.drawable.chargers));
                    slosherImage.setImageDrawable(getResources().getDrawable(R.drawable.sloshers));
                    splatlingImage.setImageDrawable(getResources().getDrawable(R.drawable.splatlings));
                    dualieImage.setImageDrawable(getResources().getDrawable(R.drawable.dualies));
                    brellaImage.setImageDrawable(getResources().getDrawable(R.drawable.brellas));
                    switch (linearLayoutManager.findFirstVisibleItemPosition()){
                        case 0:
                            shooterTab.setBackgroundTintList(getResources().getColorStateList(R.color.accent));
                            shooterImage.setImageDrawable(getResources().getDrawable(R.drawable.shooter2));
                            break;
                        case 1:
                            brushTab.setBackgroundTintList(getResources().getColorStateList(R.color.accent));
                            brushImage.setImageDrawable(getResources().getDrawable(R.drawable.brushes2));
                            break;
                        case 2:
                            chargerTab.setBackgroundTintList(getResources().getColorStateList(R.color.accent));
                            chargerImage.setImageDrawable(getResources().getDrawable(R.drawable.chargers2));
                            break;
                        case 3:
                            slosherTab.setBackgroundTintList(getResources().getColorStateList(R.color.accent));
                            slosherImage.setImageDrawable(getResources().getDrawable(R.drawable.sloshers2));
                            break;
                        case 4:
                            splatlingTab.setBackgroundTintList(getResources().getColorStateList(R.color.accent));
                            splatlingImage.setImageDrawable(getResources().getDrawable(R.drawable.splatlings2));
                            break;
                        case 5:
                            dualieTab.setBackgroundTintList(getResources().getColorStateList(R.color.accent));
                            dualieImage.setImageDrawable(getResources().getDrawable(R.drawable.dualies2));
                            break;
                        case 6:
                            brellaTab.setBackgroundTintList(getResources().getColorStateList(R.color.accent));
                            brellaImage.setImageDrawable(getResources().getDrawable(R.drawable.brellas2));
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
//        splatnetConnector.cancel(true);
    }

    @Override
    public void onResume() {
        super.onResume();
//        splatnetConnector = new SplatnetConnector(this,getActivity(),getContext());
//        splatnetConnector.addRequest(new RecordsRequest(getContext()));
//        update(splatnetConnector.getCurrentData());
//        splatnetConnector.execute();

        shooterTab.setBackgroundTintList(getResources().getColorStateList(R.color.accent));
        brushTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
        chargerTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
        slosherTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
        splatlingTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
        dualieTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));
        brellaTab.setBackgroundTintList(getResources().getColorStateList(R.color.weaponDark));

        shooterImage.setImageDrawable(getResources().getDrawable(R.drawable.shooter2));
        brushImage.setImageDrawable(getResources().getDrawable(R.drawable.brushes));
        chargerImage.setImageDrawable(getResources().getDrawable(R.drawable.chargers));
        slosherImage.setImageDrawable(getResources().getDrawable(R.drawable.sloshers));
        splatlingImage.setImageDrawable(getResources().getDrawable(R.drawable.splatlings));
        dualieImage.setImageDrawable(getResources().getDrawable(R.drawable.dualies));
        brellaImage.setImageDrawable(getResources().getDrawable(R.drawable.brellas));

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        width = width/7;
        ViewGroup.LayoutParams layoutParams = shooterTab.getLayoutParams();
        layoutParams.width = width;
        shooterTab.setLayoutParams(layoutParams);
        layoutParams = brushTab.getLayoutParams();
        layoutParams.width = width;
        brushTab.setLayoutParams(layoutParams);
        layoutParams = chargerTab.getLayoutParams();
        layoutParams.width = width;
        chargerTab.setLayoutParams(layoutParams);
        layoutParams = slosherTab.getLayoutParams();
        layoutParams.width = width;
        slosherTab.setLayoutParams(layoutParams);
        layoutParams = splatlingTab.getLayoutParams();
        layoutParams.width = width;
        splatlingTab.setLayoutParams(layoutParams);
        layoutParams = dualieTab.getLayoutParams();
        layoutParams.width = width;
        dualieTab.setLayoutParams(layoutParams);
        layoutParams = brellaTab.getLayoutParams();
        layoutParams.width = width;
        brellaTab.setLayoutParams(layoutParams);
    }

    private void updateUI(){
        ArrayList<ArrayList<WeaponStats>>weaponListList = new ArrayList<>();
        ArrayList<WeaponStats> shooter= new ArrayList<>(),brush = new ArrayList<>(),charger = new ArrayList<>(),slosher = new ArrayList<>();
        ArrayList<WeaponStats> splatling = new ArrayList<>(),dualie = new ArrayList<>(),brella = new ArrayList<>();
        WeaponStats stats;
        for(int i=0;i<weaponStatsList.size();i++){
            stats = weaponStatsList.get(i);
            if(stats.weapon.id<1000){
                shooter.add(stats);
            }else if(stats.weapon.id<2000){
                brush.add(stats);
            }else if(stats.weapon.id<3000){
                charger.add(stats);
            }else if(stats.weapon.id<4000) {
                slosher.add(stats);
            }else if(stats.weapon.id<5000){
                splatling.add(stats);
            }else if(stats.weapon.id<6000){
                dualie.add(stats);
            }else{
                brella.add(stats);
            }
        }
        weaponListList.add(sort(shooter));
        weaponListList.add(sort(brush));
        weaponListList.add(sort(charger));
        weaponListList.add(sort(slosher));
        weaponListList.add(sort(splatling));
        weaponListList.add(sort(dualie));
        weaponListList.add(sort(brella));

        WeaponPagerAdapter weaponAdapter = new WeaponPagerAdapter(getContext(), weaponListList);
        linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        weaponList.setLayoutManager(linearLayoutManager);
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

    private ArrayList<WeaponStats> sort(ArrayList<WeaponStats> data){
        if(data.size()<=1){
            return data;
        }
        if(data.size()==2){
            if(data.get(0).weapon.id<=data.get(1).weapon.id){
                return data;
            }else{
                WeaponStats hold = data.get(0);
                data.remove(0);
                data.add(hold);
                return data;
            }
        }else{
            WeaponStats pivot = data.get(0);
            ArrayList<WeaponStats> lower = new ArrayList<>();
            ArrayList<WeaponStats> upper = new ArrayList<>();
            for(int i=1;i<data.size();i++){
                if(pivot.weapon.id>data.get(i).weapon.id){
                    lower.add(data.get(i));
                }else{
                    upper.add(data.get(i));
                }
            }
            ArrayList<WeaponStats> result = sort(lower);
            result.add(pivot);
            result.addAll(sort(upper));
            return result;
        }
    }
}