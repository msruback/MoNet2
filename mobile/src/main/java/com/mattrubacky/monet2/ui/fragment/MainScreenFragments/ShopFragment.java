package com.mattrubacky.monet2.ui.fragment.MainScreenFragments;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import com.mattrubacky.monet2.data.deserialized.splatoon.parsley.Annie;
import com.mattrubacky.monet2.ui.adapter.RecyclerView.MerchAdapter;
import com.mattrubacky.monet2.data.deserialized.splatoon.Ordered;
import com.mattrubacky.monet2.backend.ImageHandler;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.backend.api.splatnet.SplatnetConnected;
import com.mattrubacky.monet2.backend.api.splatnet.SplatnetConnector;

import com.mattrubacky.monet2.ui.dialog.BuyDialog;
import com.squareup.picasso.Picasso;

import java.util.Date;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 9/15/2017.
 */

public class ShopFragment extends Fragment implements SplatnetConnected {
    ViewGroup rootView;
    Annie shop;
    SplatnetConnector splatnetConnector;
    SplatnetConnected connected;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_shop, container, false);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        if(settings.contains("shopState")) {
            Gson gson = new Gson();
            shop = gson.fromJson(settings.getString("shopState",""),Annie.class);
            if(shop == null){
                shop = new Annie();
            }
        }else{
            shop = new Annie();
        }
        connected = this;

        Typeface titleFont = Typeface.createFromAsset(getContext().getAssets(),"Paintball.otf");
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");

        TextView noOrderText = rootView.findViewById(R.id.NothingOrdered);

        RelativeLayout orderLayout = rootView.findViewById(R.id.orderLayout);
        orderLayout.setClipToOutline(true);

        noOrderText.setTypeface(font);

        while(shop.merch.size()>0&&(shop.merch.get(0).endTime*1000)<new Date().getTime()){
            shop.merch.remove(0);
        }
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
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        shop = gson.fromJson(settings.getString("shopState",""),Annie.class);
        updateUI();
        splatnetConnector = new SplatnetConnector(this,getActivity(),getContext());
        splatnetConnector.execute();

    }

    private void updateUI(){
        RecyclerView currentMerch = getActivity().findViewById(R.id.CurrentMerch);
        currentMerch.setLayoutManager(new GridLayoutManager(getContext(), 2));
        if(shop==null){
            shop = new Annie();
        }
        MerchAdapter merchAdapter = new MerchAdapter(getActivity(), shop.merch, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerView currentMerch = rootView.findViewById(R.id.CurrentMerch);
                int itemPosition = currentMerch.indexOfChild(v);
                BuyDialog buyDialog = new BuyDialog(getActivity(),connected,shop.merch.get(itemPosition),shop.ordered);
                buyDialog.show();
            }
        });
        currentMerch.setAdapter(merchAdapter);
        orderAdapter();
    }

    public void orderAdapter(){
        Ordered ordered = shop.ordered;
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");
        ImageHandler imageHandler = new ImageHandler();

        RelativeLayout order = rootView.findViewById(R.id.Ordered);
        RelativeLayout noOrder = rootView.findViewById(R.id.nothingToShow);
        RelativeLayout orderLayout = rootView.findViewById(R.id.orderLayout);
        ViewGroup.LayoutParams layoutParams = orderLayout.getLayoutParams();
        if(ordered==null){
            order.setVisibility(View.GONE);
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        }else {
            noOrder.setVisibility(View.GONE);
            order.setVisibility(View.VISIBLE);
            RelativeLayout item = rootView.findViewById(R.id.OrderedItem);
            RelativeLayout infoBar = rootView.findViewById(R.id.OrderedInfoBar);
            RelativeLayout infoPatch = rootView.findViewById(R.id.OrderedInfoPatch);
            item.setClipToOutline(true);

            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());

            ImageView brand = rootView.findViewById(R.id.OrderedBrand);
            ImageView gear = rootView.findViewById(R.id.OrderedImage);
            ImageView mainAbility = rootView.findViewById(R.id.OrderedMainAbility);
            ImageView sub2 = rootView.findViewById(R.id.OrderedSub2);
            ImageView sub3 = rootView.findViewById(R.id.OrderedSub3);

            TextView name = rootView.findViewById(R.id.OrderedName);
            TextView cost = rootView.findViewById(R.id.OrderedCost);

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

    @Override
    public void update(Bundle bundle) {
        shop = bundle.getParcelable("shop");
        updateUI();
    }

}
