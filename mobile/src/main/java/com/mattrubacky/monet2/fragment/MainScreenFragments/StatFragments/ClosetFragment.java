package com.mattrubacky.monet2.fragment.MainScreenFragments.StatFragments;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.adapter.RecyclerView.GearPagerAdapter;
import com.mattrubacky.monet2.helper.ClosetHanger;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by mattr on 11/6/2017.
 */

public class ClosetFragment extends Fragment {

    private ViewGroup rootView;
    private ArrayList<ClosetHanger> gearList;
    private RecyclerView gearListView;
    private RelativeLayout headTab,clothesTab,shoeTab;
    private LinearLayoutManager linearLayoutManager;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (ViewGroup)  inflater.inflate(R.layout.fragment_closet, container, false);

        SplatnetSQLManager database = new SplatnetSQLManager(getContext());
        gearList = database.getCloset();

        gearListView = rootView.findViewById(R.id.GearList);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(gearListView);

        headTab = rootView.findViewById(R.id.HeadTab);
        clothesTab = rootView.findViewById(R.id.ClothesTab);
        shoeTab = rootView.findViewById(R.id.ShoesTab);

        headTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutManager.scrollToPosition(0);
                headTab.setBackgroundTintList(getResources().getColorStateList(R.color.head));
                clothesTab.setBackgroundTintList(getResources().getColorStateList(R.color.rankAccent));
                shoeTab.setBackgroundTintList(getResources().getColorStateList(R.color.rankAccent));
            }
        });

        clothesTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutManager.scrollToPosition(1);
                headTab.setBackgroundTintList(getResources().getColorStateList(R.color.rankAccent));
                clothesTab.setBackgroundTintList(getResources().getColorStateList(R.color.clothes));
                shoeTab.setBackgroundTintList(getResources().getColorStateList(R.color.rankAccent));
            }
        });

        shoeTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutManager.scrollToPosition(2);
                headTab.setBackgroundTintList(getResources().getColorStateList(R.color.rankAccent));
                clothesTab.setBackgroundTintList(getResources().getColorStateList(R.color.rankAccent));
                shoeTab.setBackgroundTintList(getResources().getColorStateList(R.color.shoes));
            }
        });
        gearListView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState==0){
                    switch(linearLayoutManager.findFirstVisibleItemPosition()){
                        case 0:
                            headTab.setBackgroundTintList(getResources().getColorStateList(R.color.head));
                            clothesTab.setBackgroundTintList(getResources().getColorStateList(R.color.rankAccent));
                            shoeTab.setBackgroundTintList(getResources().getColorStateList(R.color.rankAccent));
                            break;
                        case 1:
                            headTab.setBackgroundTintList(getResources().getColorStateList(R.color.rankAccent));
                            clothesTab.setBackgroundTintList(getResources().getColorStateList(R.color.clothes));
                            shoeTab.setBackgroundTintList(getResources().getColorStateList(R.color.rankAccent));
                            break;
                        case 2:
                            headTab.setBackgroundTintList(getResources().getColorStateList(R.color.rankAccent));
                            clothesTab.setBackgroundTintList(getResources().getColorStateList(R.color.rankAccent));
                            shoeTab.setBackgroundTintList(getResources().getColorStateList(R.color.shoes));
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
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUi();

        headTab.setBackgroundTintList(getResources().getColorStateList(R.color.head));
        clothesTab.setBackgroundTintList(getResources().getColorStateList(R.color.rankAccent));
        shoeTab.setBackgroundTintList(getResources().getColorStateList(R.color.rankAccent));
    }

    private void updateUi(){
        ArrayList<ArrayList<ClosetHanger>> gearLists = new ArrayList<>();
        ArrayList<ClosetHanger> head = new ArrayList<>(),clothes = new ArrayList<>(),shoes = new ArrayList<>();
        ClosetHanger closetHanger;

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

        for(int i=0;i<gearList.size();i++){
            closetHanger = gearList.get(i);
            switch(closetHanger.gear.kind){
                case "head":
                    head.add(closetHanger);
                    break;
                case "clothes":
                    clothes.add(closetHanger);
                    break;
                case "shoes":
                    shoes.add(closetHanger);
                    break;
            }
        }

        gearLists.add(sort(head));
        gearLists.add(sort(clothes));
        gearLists.add(sort(shoes));

        GearPagerAdapter gearAdapter = new GearPagerAdapter(getContext(), gearLists);

        linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        gearListView.setLayoutManager(linearLayoutManager);
        gearListView.setAdapter(gearAdapter);
    }
    private ArrayList<ClosetHanger> sort(ArrayList<ClosetHanger> data){
        if(data.size()<=1){
            return data;
        }
        if(data.size()==2){
            if(data.get(0).gear.id<=data.get(1).gear.id){
                return data;
            }else{
                ClosetHanger hold = data.get(0);
                data.remove(0);
                data.add(hold);
                return data;
            }
        }else{
            ClosetHanger pivot = data.get(0);
            ArrayList<ClosetHanger> lower = new ArrayList<>();
            ArrayList<ClosetHanger> upper = new ArrayList<>();
            for(int i=1;i<data.size();i++){
                if(pivot.gear.id>data.get(i).gear.id){
                    lower.add(data.get(i));
                }else{
                    upper.add(data.get(i));
                }
            }
            ArrayList<ClosetHanger> result = sort(lower);
            result.add(pivot);
            result.addAll(sort(upper));
            return result;
        }
    }
}
