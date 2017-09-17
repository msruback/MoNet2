package com.mattrubacky.monet2;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.annotation.Dimension;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
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
        GridView currentMerch = (GridView) rootView.findViewById(R.id.CurrentMerch);
        MerchAdapter merchAdapter = new MerchAdapter(getContext(),shop.merch);
        currentMerch.setAdapter(merchAdapter);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Paintball.otf");
        customHandler = new android.os.Handler();
        customHandler.post(updateNeeded);
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
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        shop = gson.fromJson(settings.getString("shopState",""),Annie.class);
    }


    private Runnable updateUI = new Runnable(){
        @Override
        public void run() {
            GridView currentMerch = (GridView) getActivity().findViewById(R.id.CurrentMerch);
            MerchAdapter merchAdapter = new MerchAdapter(getContext(),shop.merch);
            currentMerch.setAdapter(merchAdapter);

        }
    };
    //Adapters
    private class MerchAdapter extends ArrayAdapter<Product> {
        public MerchAdapter(Context context, ArrayList<Product> input) {
            super(context, 0, input);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_merch, parent, false);
            }
            ImageHandler imageHandler = new ImageHandler();
            Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");
            RelativeLayout item = (RelativeLayout) convertView.findViewById(R.id.Item);
            RelativeLayout infoBar = (RelativeLayout) convertView.findViewById(R.id.InfoBar);
            item.setClipToOutline(true);

            ImageView brand = (ImageView) convertView.findViewById(R.id.Brand);
            ImageView gear = (ImageView) convertView.findViewById(R.id.Image);
            ImageView mainAbility = (ImageView) convertView.findViewById(R.id.MainAbility);
            ImageView sub2 = (ImageView) convertView.findViewById(R.id.Sub2);
            ImageView sub3 = (ImageView) convertView.findViewById(R.id.Sub3);

            TextView name = (TextView) convertView.findViewById(R.id.Name);
            TextView cost = (TextView) convertView.findViewById(R.id.Cost);

            final TextView time = (TextView) convertView.findViewById(R.id.Time);

            Product product = getItem(position);

            name.setTypeface(font);
            cost.setTypeface(font);
            time.setTypeface(font);

            name.setText(product.gear.name);
            cost.setText(product.price);

            String gearUrl = "https://app.splatoon2.nintendo.net"+product.gear.url;
            String gearLocation = product.gear.name.toLowerCase().replace(" ","_");
            if(imageHandler.imageExists("gear",gearLocation,getContext())){
                gear.setImageBitmap(imageHandler.loadImage("gear",gearLocation));
            }else{
                Picasso.with(getContext()).load(gearUrl).into(gear);
                imageHandler.downloadImage("gear",gearLocation,gearUrl,getContext());
            }

            String brandUrl = "https://app.splatoon2.nintendo.net"+product.gear.brand.url;
            String brandLocation = product.gear.brand.name.toLowerCase().replace(" ","_");
            if(imageHandler.imageExists("brand",brandLocation,getContext())){
                brand.setImageBitmap(imageHandler.loadImage("brand",brandLocation));
            }else{
                Picasso.with(getContext()).load(brandUrl).into(brand);
                imageHandler.downloadImage("brand",gearLocation,brandUrl,getContext());
            }

            String abilityUrl = "https://app.splatoon2.nintendo.net"+product.skill.url;
            String abilityLocation = product.skill.name.toLowerCase().replace(" ","_");
            if(imageHandler.imageExists("ability",abilityLocation,getContext())){
                mainAbility.setImageBitmap(imageHandler.loadImage("ability",abilityLocation));
            }else{
                Picasso.with(getContext()).load(abilityUrl).into(mainAbility);
                imageHandler.downloadImage("ability",abilityLocation,abilityUrl,getContext());
            }

            if(product.gear.rarity==1){
                sub3.setVisibility(View.INVISIBLE);
            }else if(product.gear.rarity==0) {
                sub2.setVisibility(View.INVISIBLE);
                sub3.setVisibility(View.INVISIBLE);
            }
            Long now = new Date().getTime();
            new CountDownTimer((product.endTime*1000)-now, 60000) {

                public void onTick(long millisUntilFinished) {
                    Long minutes = ((millisUntilFinished/1000)/60);
                    Long hours = minutes/60;
                    String timeString;
                    if(hours>1){
                        timeString=hours+" Hours";
                    }else if(hours==1){
                        timeString=hours+" Hour";
                    }else if(minutes>1){
                        timeString=minutes+" Minutes";
                    }else{
                        timeString=minutes+" Minute";
                    }
                    time.setText(timeString);
                }

                public void onFinish() {
                    time.setText("Expired");
                }

            }.start();


            return convertView;
        }
    }

    private Runnable updateRotationData = new Runnable() {
        public void run() {
            try{
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
                String cookie = settings.getString("cookie","");
                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://app.splatoon2.nintendo.net").addConverterFactory(GsonConverterFactory.create()).build();
                Splatnet splatnet = retrofit.create(Splatnet.class);
                Call<Annie> shopUpdate = splatnet.getShop(cookie);
                Response response = shopUpdate.execute();
                if(response.isSuccessful()){
                    shop = (Annie) response.body();
                }else{

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    private Runnable updateNeeded = new Runnable()
    {
        public void run() {
            Thread t = new Thread(updateRotationData);
            customHandler.postDelayed(updateUI,10000);
            t.start();
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

}
