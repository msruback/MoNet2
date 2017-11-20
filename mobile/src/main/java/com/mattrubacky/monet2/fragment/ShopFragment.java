package com.mattrubacky.monet2.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import com.mattrubacky.monet2.adapter.MerchAdapter;
import com.mattrubacky.monet2.dialog.AlertDialog;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.splatnet_interface.Splatnet;
import com.mattrubacky.monet2.sqlite.SplatnetContract;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;
import com.mattrubacky.monet2.deserialized.*;

import com.mattrubacky.monet2.dialog.BuyDialog;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mattr on 9/15/2017.
 */

public class ShopFragment extends Fragment {
    ViewGroup rootView;
    android.os.Handler customHandler;
    UpdateShopData updateShopData;
    Annie shop;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_shop, container, false);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        if(settings.contains("shopState")) {
            Gson gson = new Gson();
            shop = gson.fromJson(settings.getString("shopState",""),Annie.class);
            if(shop == null){
                shop = new Annie();
                shop.merch = new ArrayList<Product>();
            }
        }else{
            shop = new Annie();
            shop.merch = new ArrayList<Product>();
        }
        updateShopData = new UpdateShopData();
        RecyclerView currentMerch = (RecyclerView) rootView.findViewById(R.id.CurrentMerch);
        currentMerch.setLayoutManager(new GridLayoutManager(getContext(), 2));
        MerchAdapter merchAdapter = new MerchAdapter(getContext(), shop.merch, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerView currentMerch = (RecyclerView) rootView.findViewById(R.id.CurrentMerch);
                int itemPosition = currentMerch.indexOfChild(v);
                BuyDialog buyDialog = new BuyDialog(getActivity(),shop.merch.get(itemPosition),shop.ordered);
                buyDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        updateUi();
                        updateShopData = new UpdateShopData();
                        updateShopData.execute();
                    }
                });
                buyDialog.show();
            }
        });
        currentMerch.setAdapter(merchAdapter);
        orderAdapter();

        Typeface titleFont = Typeface.createFromAsset(getContext().getAssets(),"Paintball.otf");
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");

        TextView orderTitle = (TextView) rootView.findViewById(R.id.OrderTitle);
        TextView noOrderText = (TextView) rootView.findViewById(R.id.NothingOrdered);
        TextView merchTitle = (TextView) rootView.findViewById(R.id.MerchTitle);

        orderTitle.setTypeface(titleFont);
        noOrderText.setTypeface(font);
        merchTitle.setTypeface(titleFont);

        customHandler = new android.os.Handler();
        customHandler.post(updateNeeded);

        while(shop.merch.size()>0&&(shop.merch.get(0).endTime*1000)<new Date().getTime()){
            shop.merch.remove(0);
        }
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor edit = settings.edit();
        Gson gson = new Gson();
        String json = gson.toJson(shop);
        edit.putString("shopState",json);
        edit.commit();
        updateShopData.cancel(true);
        customHandler.removeCallbacks(updateNeeded);
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        shop = gson.fromJson(settings.getString("shopState",""),Annie.class);
    }

    private void updateUi(){
        RecyclerView currentMerch = (RecyclerView) getActivity().findViewById(R.id.CurrentMerch);
        currentMerch.setLayoutManager(new GridLayoutManager(getContext(), 2));
        if(shop==null){
            shop = new Annie();
        }
        if(shop.merch==null){
            shop.merch = new ArrayList<>();
        }
        MerchAdapter merchAdapter = new MerchAdapter(getContext(), shop.merch, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerView currentMerch = (RecyclerView) rootView.findViewById(R.id.CurrentMerch);
                int itemPosition = currentMerch.indexOfChild(v);
                BuyDialog buyDialog = new BuyDialog(getActivity(),shop.merch.get(itemPosition),shop.ordered);
                buyDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        updateUi();
                        updateShopData = new UpdateShopData();
                        updateShopData.execute();
                    }
                });
                buyDialog.show();
            }
        });
        currentMerch.setAdapter(merchAdapter);
        orderAdapter();
    }

    //Adapters
    public void orderAdapter(){
        Ordered ordered = shop.ordered;
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");
        ImageHandler imageHandler = new ImageHandler();

        RelativeLayout order = (RelativeLayout) rootView.findViewById(R.id.Ordered);
        RelativeLayout noOrder = (RelativeLayout) rootView.findViewById(R.id.nothingToShow);

        if(ordered==null){
            order.setVisibility(View.GONE);
        }else {
            noOrder.setVisibility(View.GONE);
            order.setVisibility(View.VISIBLE);
            RelativeLayout item = (RelativeLayout) rootView.findViewById(R.id.OrderedItem);
            RelativeLayout infoBar = (RelativeLayout) rootView.findViewById(R.id.OrderedInfoBar);
            RelativeLayout infoPatch = (RelativeLayout) rootView.findViewById(R.id.OrderedInfoPatch);
            item.setClipToOutline(true);

            ImageView brand = (ImageView) rootView.findViewById(R.id.OrderedBrand);
            ImageView gear = (ImageView) rootView.findViewById(R.id.OrderedImage);
            ImageView mainAbility = (ImageView) rootView.findViewById(R.id.OrderedMainAbility);
            ImageView sub2 = (ImageView) rootView.findViewById(R.id.OrderedSub2);
            ImageView sub3 = (ImageView) rootView.findViewById(R.id.OrderedSub3);

            TextView name = (TextView) rootView.findViewById(R.id.OrderedName);
            TextView cost = (TextView) rootView.findViewById(R.id.OrderedCost);

            //Change the info bar color to match gear kind
            switch (ordered.gear.kind) {
                case "head":
                    infoBar.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.head));
                    infoPatch.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.head));
                    break;
                case "clothes":
                    infoBar.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.clothes));
                    infoPatch.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.clothes));
                    break;
                case "shoes":
                    infoBar.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.shoes));
                    infoPatch.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.shoes));
                    break;
            }

            //Set the fonts
            name.setTypeface(font);
            cost.setTypeface(font);

            //Set the name and cost of the gear
            name.setText(ordered.gear.name);
            cost.setText(ordered.price);

            //Set the gear image
            String gearUrl = "https://app.splatoon2.nintendo.net" + ordered.gear.url;
            String gearLocation = ordered.gear.name.toLowerCase().replace(" ", "_");
            if (imageHandler.imageExists("gear", gearLocation, getContext())) {
                gear.setImageBitmap(imageHandler.loadImage("gear", gearLocation));
            } else {
                Picasso.with(getContext()).load(gearUrl).into(gear);
                imageHandler.downloadImage("gear", gearLocation, gearUrl, getContext());
            }

            //Set the brand image
            String brandUrl = "https://app.splatoon2.nintendo.net" + ordered.gear.brand.url;
            String brandLocation = ordered.gear.brand.name.toLowerCase().replace(" ", "_");
            if (imageHandler.imageExists("brand", brandLocation, getContext())) {
                brand.setImageBitmap(imageHandler.loadImage("brand", brandLocation));
            } else {
                Picasso.with(getContext()).load(brandUrl).into(brand);
                imageHandler.downloadImage("brand", gearLocation, brandUrl, getContext());
            }

            //Set the ability image
            String abilityUrl = "https://app.splatoon2.nintendo.net" + ordered.skill.url;
            String abilityLocation = ordered.skill.name.toLowerCase().replace(" ", "_");
            if (imageHandler.imageExists("ability", abilityLocation, getContext())) {
                mainAbility.setImageBitmap(imageHandler.loadImage("ability", abilityLocation));
            } else {
                Picasso.with(getContext()).load(abilityUrl).into(mainAbility);
                imageHandler.downloadImage("ability", abilityLocation, abilityUrl, getContext());
            }

            //Set the number of slots the gear has
            if (ordered.gear.rarity == 1) {
                sub3.setVisibility(View.INVISIBLE);
            } else if (ordered.gear.rarity == 0) {
                sub2.setVisibility(View.INVISIBLE);
                sub3.setVisibility(View.INVISIBLE);
            }
        }

    }

    private class UpdateShopData extends AsyncTask<Void,Void,Void> {

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
        }
        @Override
        protected Void doInBackground(Void... params) {
            try{
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
                String cookie = settings.getString("cookie","");
                String uniqueId = settings.getString("unique_id","");

                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://app.splatoon2.nintendo.net").addConverterFactory(GsonConverterFactory.create()).build();
                Splatnet splatnet = retrofit.create(Splatnet.class);
                Call<Annie> shopUpdate = splatnet.getShop(cookie,uniqueId);
                Response response = shopUpdate.execute();
                if(response.isSuccessful()){
                    shop = (Annie) response.body();
                    SplatnetSQLManager database = new SplatnetSQLManager(getContext());
                    ArrayList<Gear> gear = new ArrayList<>();
                    for(int i=0;i<shop.merch.size();i++){
                        gear.add(shop.merch.get(i).gear);
                    }
                    database.insertGear(gear);
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

    public Runnable updateNeeded = new Runnable()
    {
        public void run() {
            updateShopData = new UpdateShopData();
            updateShopData.execute();
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());
            Calendar nextUpdate = Calendar.getInstance();
            nextUpdate.setTimeInMillis(now.getTimeInMillis());
            int hour = now.get(Calendar.HOUR);
            hour++;
            nextUpdate.set(Calendar.HOUR,hour);
            nextUpdate.set(Calendar.MINUTE,0);
            nextUpdate.set(Calendar.SECOND,0);
            nextUpdate.set(Calendar.MILLISECOND,0);
            Long nextUpdateTime = nextUpdate.getTimeInMillis()-now.getTimeInMillis();
            customHandler.postDelayed(this, nextUpdateTime);
        }
    };
    public void update(){
        updateNeeded.run();
    }

}
