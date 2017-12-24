package com.mattrubacky.monet2.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.adapter.RecyclerView.GearPickerPagerAdapter;
import com.mattrubacky.monet2.deserialized.Gear;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.util.ArrayList;

/**
 * Created by mattr on 11/13/2017.
 */

public class GearPickerDialog extends Dialog {
    int selected;
    RelativeLayout headTab,clothesTab,shoeTab;
    ArrayList<Gear> gearList;
    Gear result;
    LinearLayoutManager linearLayoutManager;

    public GearPickerDialog(Activity activity) {
        super(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_gear_picker);
        Typeface titleFont = Typeface.createFromAsset(getContext().getAssets(),"Paintball.otf");

        selected =-1;

        RelativeLayout card = (RelativeLayout) findViewById(R.id.dialogCard);

        TextView title = (TextView) findViewById(R.id.title);
        final RecyclerView gearListView = (RecyclerView) findViewById(R.id.ItemList);
        Button submit = (Button) findViewById(R.id.Submit);
        Button cancel = (Button) findViewById(R.id.Cancel);

        submit.setTypeface(titleFont);
        cancel.setTypeface(titleFont);

        title.setText("Pick Gear");

        title.setTypeface(titleFont);

        SplatnetSQLManager splatnetSQLManager = new SplatnetSQLManager(getContext());
        gearList = splatnetSQLManager.getGear();
        ArrayList<ArrayList<Gear>> gears= new ArrayList<>();
        ArrayList<Gear> head = new ArrayList<>(),clothes = new ArrayList<>(), shoes = new ArrayList<>();

        Gear gear;
        for(int i=0;i<gearList.size();i++){
            gear = gearList.get(i);
            switch (gear.kind){
                case "head":
                    head.add(gear);
                    break;
                case "clothes":
                    clothes.add(gear);
                    break;
                case "shoes":
                    shoes.add(gear);
                    break;
            }
        }
        gears.add(sort(head));
        gears.add(sort(clothes));
        gears.add(sort(shoes));

        final GearPickerPagerAdapter gearAdapter = new GearPickerPagerAdapter(getContext(), gears,gearListView,new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = position;
            }
        });

        gearListView.setAdapter(gearAdapter);
        linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        gearListView.setLayoutManager(linearLayoutManager);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(gearListView);

        card.setClipToOutline(true);

        headTab = (RelativeLayout) findViewById(R.id.HeadTab);
        clothesTab = (RelativeLayout) findViewById(R.id.ClothesTab);
        shoeTab = (RelativeLayout) findViewById(R.id.ShoesTab);

        headTab.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorPrimary));
        clothesTab.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorAccent));
        shoeTab.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorAccent));

        headTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutManager.scrollToPosition(0);
                selected = -1;
                headTab.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorPrimary));
                clothesTab.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorAccent));
                shoeTab.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorAccent));
            }
        });

        clothesTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutManager.scrollToPosition(1);
                selected = -1;
                headTab.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorAccent));
                clothesTab.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorPrimary));
                shoeTab.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorAccent));
            }
        });

        shoeTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutManager.scrollToPosition(2);
                selected = -1;
                headTab.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorAccent));
                clothesTab.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorAccent));
                shoeTab.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorPrimary));
            }
        });
        gearListView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState==0){
                    selected = -1;
                    switch(linearLayoutManager.findFirstVisibleItemPosition()){
                        case 0:
                            headTab.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorPrimary));
                            clothesTab.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorAccent));
                            shoeTab.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorAccent));
                            break;
                        case 1:
                            headTab.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorAccent));
                            clothesTab.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorPrimary));
                            shoeTab.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorAccent));
                            break;
                        case 2:
                            headTab.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorAccent));
                            clothesTab.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorAccent));
                            shoeTab.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorPrimary));
                            break;
                    }
                }
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selected==-1){
                    dismiss();
                }else {
                    result = gearAdapter.getResult(selected);
                    dismiss();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public Gear getResult(){
        return result;
    }

    private ArrayList<Gear> sort(ArrayList<Gear> data){
        if(data.size()<=1){
            return data;
        }
        if(data.size()==2){
            if(data.get(0).id<=data.get(1).id){
                return data;
            }else{
                Gear hold = data.get(0);
                data.remove(0);
                data.add(hold);
                return data;
            }
        }else{
            Gear pivot = data.get(0);
            ArrayList<Gear> lower = new ArrayList<>();
            ArrayList<Gear> upper = new ArrayList<>();
            for(int i=1;i<data.size();i++){
                if(pivot.id>data.get(i).id){
                    lower.add(data.get(i));
                }else{
                    upper.add(data.get(i));
                }
            }
            ArrayList<Gear> result = sort(lower);
            result.add(pivot);
            result.addAll(sort(upper));
            return result;
        }
    }
}