package com.mattrubacky.monet2;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.os.Process;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.annotation.Dimension;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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


        RecyclerView currentMerch = (RecyclerView) rootView.findViewById(R.id.CurrentMerch);
        currentMerch.setLayoutManager(new GridLayoutManager(getContext(), 2));
        MerchAdapter merchAdapter = new MerchAdapter(getContext(),shop.merch);
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
        customHandler.removeCallbacks(updateUI);
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

            RecyclerView currentMerch = (RecyclerView) getActivity().findViewById(R.id.CurrentMerch);
            currentMerch.setLayoutManager(new GridLayoutManager(getContext(), 2));
            MerchAdapter merchAdapter = new MerchAdapter(getContext(),shop.merch);
            currentMerch.setAdapter(merchAdapter);
            orderAdapter();
        }
    };

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
    class MerchAdapter extends RecyclerView.Adapter<MerchAdapter.ViewHolder>{

        private ArrayList<Product> input = new ArrayList<Product>();
        private LayoutInflater inflater;
        private Context context;

        public MerchAdapter(Context context, ArrayList<Product> input) {
            this.inflater = LayoutInflater.from(context);
            this.input = input;
            this.context = context;

        }
        @Override
        public MerchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_merch, parent, false);
            MerchAdapter.ViewHolder viewHolder = new MerchAdapter.ViewHolder(view);
            //view.setOnClickListener(new ShopClickListener());
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final MerchAdapter.ViewHolder holder, int position) {
            final Product product  = input.get(position);
            Typeface font = Typeface.createFromAsset(context.getAssets(),"Splatfont2.ttf");
            ImageHandler imageHandler = new ImageHandler();

            //Change the info bar color to match gear kind
            switch (product.gear.kind){
                case "head":
                    holder.infoBar.setBackgroundTintList(context.getResources().getColorStateList(R.color.head));
                    holder.infoPatch.setBackgroundTintList(context.getResources().getColorStateList(R.color.head));
                    break;
                case "clothes":
                    holder.infoBar.setBackgroundTintList(context.getResources().getColorStateList(R.color.clothes));
                    holder.infoPatch.setBackgroundTintList(context.getResources().getColorStateList(R.color.clothes));
                    break;
                case "shoes":
                    holder.infoBar.setBackgroundTintList(context.getResources().getColorStateList(R.color.shoes));
                    holder.infoPatch.setBackgroundTintList(context.getResources().getColorStateList(R.color.shoes));
                    break;
            }

            //Set the fonts
            holder.name.setTypeface(font);
            holder.cost.setTypeface(font);
            holder.time.setTypeface(font);

            //Set the name and cost of the gear
            holder.name.setText(product.gear.name);
            holder.cost.setText(product.price);

            //Set the gear image
            String gearUrl = "https://app.splatoon2.nintendo.net"+product.gear.url;
            String gearLocation = product.gear.name.toLowerCase().replace(" ","_");
            if(imageHandler.imageExists("gear",gearLocation,context)){
                holder.gear.setImageBitmap(imageHandler.loadImage("gear",gearLocation));
            }else{
                Picasso.with(context).load(gearUrl).into(holder.gear);
                imageHandler.downloadImage("gear",gearLocation,gearUrl,context);
            }

            //Set the brand image
            String brandUrl = "https://app.splatoon2.nintendo.net"+product.gear.brand.url;
            String brandLocation = product.gear.brand.name.toLowerCase().replace(" ","_");
            if(imageHandler.imageExists("brand",brandLocation,context)){
                holder.brand.setImageBitmap(imageHandler.loadImage("brand",brandLocation));
            }else{
                Picasso.with(context).load(brandUrl).into(holder.brand);
                imageHandler.downloadImage("brand",gearLocation,brandUrl,context);
            }

            //Set the ability image
            String abilityUrl = "https://app.splatoon2.nintendo.net"+product.skill.url;
            String abilityLocation = product.skill.name.toLowerCase().replace(" ","_");
            if(imageHandler.imageExists("ability",abilityLocation,context)){
                holder.mainAbility.setImageBitmap(imageHandler.loadImage("ability",abilityLocation));
            }else{
                Picasso.with(context).load(abilityUrl).into(holder.mainAbility);
                imageHandler.downloadImage("ability",abilityLocation,abilityUrl,context);
            }

            //Set the number of slots the gear has
            if(product.gear.rarity==1){
                holder.sub3.setVisibility(View.INVISIBLE);
            }else if(product.gear.rarity==0) {
                holder.sub2.setVisibility(View.INVISIBLE);
                holder.sub3.setVisibility(View.INVISIBLE);
            }

            //Set the timer
            Long now = new Date().getTime();
            new CountDownTimer((product.endTime * 1000) - now, 60000) {

                public void onTick(long millisUntilFinished) {
                    Long minutes = ((millisUntilFinished / 1000) / 60);
                    Long hours = minutes / 60;
                    String timeString;
                    if (hours > 1) {
                        timeString = hours + " Hours";
                    } else if (hours == 1) {
                        timeString = hours + " Hour";
                    } else if (minutes > 1) {
                        timeString = minutes + " Minutes";
                    } else {
                        timeString = minutes + " Minute";
                    }
                    holder.time.setText(timeString);
                }

                public void onFinish() {
                    holder.time.setText("Expired");
                }

            }.start();
        }

        @Override
        public int getItemCount() {
            return input.size();
        }



        public class ViewHolder extends RecyclerView.ViewHolder{
            RelativeLayout item,infoBar,infoPatch;
            ImageView brand,gear,mainAbility,sub2,sub3;
            TextView name,cost,time;


            public ViewHolder(View itemView) {
                super(itemView);

                item = (RelativeLayout) itemView.findViewById(R.id.Item);
                infoBar = (RelativeLayout) itemView.findViewById(R.id.InfoBar);
                infoPatch = (RelativeLayout) itemView.findViewById(R.id.infoPatch);
                item.setClipToOutline(true);

                brand = (ImageView) itemView.findViewById(R.id.Brand);
                gear = (ImageView) itemView.findViewById(R.id.Image);
                mainAbility = (ImageView) itemView.findViewById(R.id.MainAbility);
                sub2 = (ImageView) itemView.findViewById(R.id.Sub2);
                sub3 = (ImageView) itemView.findViewById(R.id.Sub3);

                name = (TextView) itemView.findViewById(R.id.Name);
                cost = (TextView) itemView.findViewById(R.id.Cost);

                time = (TextView) itemView.findViewById(R.id.Time);
            }

        }

    }
    class ShopClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            RecyclerView currentMerch = (RecyclerView) rootView.findViewById(R.id.CurrentMerch);
            int itemPosition = currentMerch.indexOfChild(v);
            BuyDialog buyDialog = new BuyDialog(getActivity(),shop.merch.get(itemPosition),shop.ordered);
            buyDialog.show();
            customHandler.post(updateNeeded);
        }
    }

    private Runnable updateShopData = new Runnable() {
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
    public Runnable updateNeeded = new Runnable()
    {
        public void run() {
            Thread t = new Thread(updateShopData);
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
    public void update(){
        updateNeeded.run();
    }

}
