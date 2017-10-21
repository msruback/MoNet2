package com.mattrubacky.monet2.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import com.mattrubacky.monet2.helper.ImageHandler;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.splatnet_interface.Splatnet;
import com.mattrubacky.monet2.deserialized.*;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mattr on 9/20/2017.
 */

public class BuyDialog extends Dialog{
    Product toBuy;
    Ordered alreadyOrdered;
    LoadingDialog loadingDialog;
    public BuyDialog(Activity activity,Product product,Ordered ordered) {
        super(activity);
        loadingDialog = new LoadingDialog(activity,"Ordering");
        toBuy = product;
        alreadyOrdered = ordered;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if(alreadyOrdered==null){
            setContentView(R.layout.dialog_buy_item);

            Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");
            ImageHandler imageHandler = new ImageHandler();

            RelativeLayout dialogCard = (RelativeLayout) findViewById(R.id.dialogCard);
            dialogCard.setClipToOutline(true);

            RelativeLayout item = (RelativeLayout) findViewById(R.id.Item);
            RelativeLayout infoBar = (RelativeLayout) findViewById(R.id.InfoBar);
            RelativeLayout infoPatch = (RelativeLayout) findViewById(R.id.infoPatch);
            item.setClipToOutline(true);

            ImageView brand = (ImageView) findViewById(R.id.Brand);
            ImageView gear = (ImageView) findViewById(R.id.Image);
            ImageView mainAbility = (ImageView) findViewById(R.id.MainAbility);
            ImageView sub2 = (ImageView) findViewById(R.id.Sub2);
            ImageView sub3 = (ImageView) findViewById(R.id.Sub3);

            TextView name = (TextView) findViewById(R.id.Name);
            TextView cost = (TextView) findViewById(R.id.Cost);
            final TextView time = (TextView) findViewById(R.id.Time);

            Button orderButton = (Button) findViewById(R.id.OrderButton);

            orderButton.setTypeface(font);
            orderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadingDialog.show();
                    Thread t = new Thread(orderGear);
                    t.start();
                    dismiss();
                }
            });

            //Change the info bar color to match gear kind
            switch (toBuy.gear.kind) {
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
            time.setTypeface(font);

            //Set the name and cost of the gear
            name.setText(toBuy.gear.name);
            cost.setText(toBuy.price);

            //Set the gear image
            String gearUrl = "https://app.splatoon2.nintendo.net" + toBuy.gear.url;
            String gearLocation = toBuy.gear.name.toLowerCase().replace(" ", "_");
            if (imageHandler.imageExists("gear", gearLocation, getContext())) {
                gear.setImageBitmap(imageHandler.loadImage("gear", gearLocation));
            } else {
                Picasso.with(getContext()).load(gearUrl).into(gear);
                imageHandler.downloadImage("gear", gearLocation, gearUrl, getContext());
            }

            //Set the brand image
            String brandUrl = "https://app.splatoon2.nintendo.net" + toBuy.gear.brand.url;
            String brandLocation = toBuy.gear.brand.name.toLowerCase().replace(" ", "_");
            if (imageHandler.imageExists("brand", brandLocation, getContext())) {
                brand.setImageBitmap(imageHandler.loadImage("brand", brandLocation));
            } else {
                Picasso.with(getContext()).load(brandUrl).into(brand);
                imageHandler.downloadImage("brand", gearLocation, brandUrl, getContext());
            }

            //Set the ability image
            String abilityUrl = "https://app.splatoon2.nintendo.net" + toBuy.skill.url;
            String abilityLocation = toBuy.skill.name.toLowerCase().replace(" ", "_");
            if (imageHandler.imageExists("ability", abilityLocation, getContext())) {
                mainAbility.setImageBitmap(imageHandler.loadImage("ability", abilityLocation));
            } else {
                Picasso.with(getContext()).load(abilityUrl).into(mainAbility);
                imageHandler.downloadImage("ability", abilityLocation, abilityUrl, getContext());
            }

            mainAbility.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),toBuy.skill.name,Toast.LENGTH_SHORT).show();
                }
            });

            //Set the number of slots the gear has
            if (toBuy.gear.rarity == 1) {
                sub3.setVisibility(View.INVISIBLE);
            } else if (toBuy.gear.rarity == 0) {
                sub2.setVisibility(View.INVISIBLE);
                sub3.setVisibility(View.INVISIBLE);
            }

            //Set the timer
            Long now = new Date().getTime();
            new CountDownTimer((toBuy.endTime * 1000) - now, 60000) {

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
                    time.setText(timeString);
                }

                public void onFinish() {
                    time.setText("Expired");
                }

            }.start();

        }else{
            setContentView(R.layout.dialog_buy_item_choose);

            Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");
            ImageHandler imageHandler = new ImageHandler();

            RelativeLayout dialogCard = (RelativeLayout) findViewById(R.id.dialogCard);
            dialogCard.setClipToOutline(true);

            RelativeLayout orderingItem = (RelativeLayout) findViewById(R.id.OrderingItem);
            RelativeLayout orderingInfoBar = (RelativeLayout) findViewById(R.id.OrderingInfoBar);
            RelativeLayout orderingInfoPatch = (RelativeLayout) findViewById(R.id.orderingInfoPatch);
            orderingItem.setClipToOutline(true);

            ImageView orderingBrand = (ImageView) findViewById(R.id.OrderingBrand);
            ImageView orderingGear = (ImageView) findViewById(R.id.OrderingImage);
            ImageView orderingMainAbility = (ImageView) findViewById(R.id.OrderingMainAbility);
            ImageView orderingSub2 = (ImageView) findViewById(R.id.OrderingSub2);
            ImageView orderingSub3 = (ImageView) findViewById(R.id.OrderingSub3);

            TextView orderingName = (TextView) findViewById(R.id.OrderingName);
            TextView orderingCost = (TextView) findViewById(R.id.OrderingCost);
            final TextView orderingTime = (TextView) findViewById(R.id.OrderingTime);

            //Ordered Layout
            RelativeLayout orderedItem = (RelativeLayout) findViewById(R.id.OrderedItem);
            RelativeLayout orderedInfoBar = (RelativeLayout) findViewById(R.id.OrderedInfoBar);
            RelativeLayout orderedInfoPatch = (RelativeLayout) findViewById(R.id.orderedInfoPatch);
            orderingItem.setClipToOutline(true);

            ImageView orderedBrand = (ImageView) findViewById(R.id.OrderedBrand);
            ImageView orderedGear = (ImageView) findViewById(R.id.OrderedImage);
            ImageView orderedMainAbility = (ImageView) findViewById(R.id.OrderedMainAbility);
            ImageView orderedSub2 = (ImageView) findViewById(R.id.OrderedSub2);
            ImageView orderedSub3 = (ImageView) findViewById(R.id.OrderedSub3);

            TextView orderedName = (TextView) findViewById(R.id.OrderedName);
            TextView orderedCost = (TextView) findViewById(R.id.OrderedCost);

            Button orderButton = (Button) findViewById(R.id.OrderButton);
            Button keepButton = (Button) findViewById(R.id.KeepButton);

            orderButton.setTypeface(font);
            keepButton.setTypeface(font);

            orderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadingDialog.show();
                    Thread t = new Thread(orderGear);
                    t.start();
                    dismiss();
                }
            });

            keepButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            //Change the info bar color to match gear kind

            switch (toBuy.gear.kind) {
                case "head":
                    orderingInfoBar.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.head));
                    orderingInfoPatch.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.head));
                    break;
                case "clothes":
                    orderingInfoBar.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.clothes));
                    orderingInfoPatch.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.clothes));
                    break;
                case "shoes":
                    orderingInfoBar.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.shoes));
                    orderingInfoPatch.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.shoes));
                    break;
            }
            switch (alreadyOrdered.gear.kind) {
                case "head":
                    orderedInfoBar.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.head));
                    orderedInfoPatch.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.head));
                    break;
                case "clothes":
                    orderedInfoBar.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.clothes));
                    orderedInfoPatch.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.clothes));
                    break;
                case "shoes":
                    orderedInfoBar.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.shoes));
                    orderedInfoPatch.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.shoes));
                    break;
            }

            //Set the fonts
            orderingName.setTypeface(font);
            orderingCost.setTypeface(font);
            orderingTime.setTypeface(font);

            orderedName.setTypeface(font);
            orderedCost.setTypeface(font);

            //Set the name and cost of the gear
            orderingName.setText(toBuy.gear.name);
            orderingCost.setText(toBuy.price);

            orderedName.setText(alreadyOrdered.gear.name);
            orderedCost.setText(alreadyOrdered.price);

            //Set the gear image
            String gearUrl = "https://app.splatoon2.nintendo.net" + toBuy.gear.url;
            String gearLocation = toBuy.gear.name.toLowerCase().replace(" ", "_");
            if (imageHandler.imageExists("gear", gearLocation, getContext())) {
                orderingGear.setImageBitmap(imageHandler.loadImage("gear", gearLocation));
            } else {
                Picasso.with(getContext()).load(gearUrl).into(orderingGear);
                imageHandler.downloadImage("gear", gearLocation, gearUrl, getContext());
            }
            gearUrl = "https://app.splatoon2.nintendo.net" + alreadyOrdered.gear.url;
            gearLocation = toBuy.gear.name.toLowerCase().replace(" ", "_");
            if (imageHandler.imageExists("gear", gearLocation, getContext())) {
                orderedGear.setImageBitmap(imageHandler.loadImage("gear", gearLocation));
            } else {
                Picasso.with(getContext()).load(gearUrl).into(orderedGear);
                imageHandler.downloadImage("gear", gearLocation, gearUrl, getContext());
            }

            //Set the brand image
            String brandUrl = "https://app.splatoon2.nintendo.net" + toBuy.gear.brand.url;
            String brandLocation = toBuy.gear.brand.name.toLowerCase().replace(" ", "_");
            if (imageHandler.imageExists("brand", brandLocation, getContext())) {
                orderingBrand.setImageBitmap(imageHandler.loadImage("brand", brandLocation));
            } else {
                Picasso.with(getContext()).load(brandUrl).into(orderingBrand);
                imageHandler.downloadImage("brand", gearLocation, brandUrl, getContext());
            }
            brandUrl = "https://app.splatoon2.nintendo.net" + alreadyOrdered.gear.brand.url;
            brandLocation = toBuy.gear.brand.name.toLowerCase().replace(" ", "_");
            if (imageHandler.imageExists("brand", brandLocation, getContext())) {
                orderedBrand.setImageBitmap(imageHandler.loadImage("brand", brandLocation));
            } else {
                Picasso.with(getContext()).load(brandUrl).into(orderedBrand);
                imageHandler.downloadImage("brand", gearLocation, brandUrl, getContext());
            }

            //Set the ability image
            String abilityUrl = "https://app.splatoon2.nintendo.net" + toBuy.skill.url;
            String abilityLocation = toBuy.skill.name.toLowerCase().replace(" ", "_");
            if (imageHandler.imageExists("ability", abilityLocation, getContext())) {
                orderingMainAbility.setImageBitmap(imageHandler.loadImage("ability", abilityLocation));
            } else {
                Picasso.with(getContext()).load(abilityUrl).into(orderingMainAbility);
                imageHandler.downloadImage("ability", abilityLocation, abilityUrl, getContext());
            }
            abilityUrl = "https://app.splatoon2.nintendo.net" + alreadyOrdered.skill.url;
            abilityLocation = toBuy.skill.name.toLowerCase().replace(" ", "_");
            if (imageHandler.imageExists("ability", abilityLocation, getContext())) {
                orderedMainAbility.setImageBitmap(imageHandler.loadImage("ability", abilityLocation));
            } else {
                Picasso.with(getContext()).load(abilityUrl).into(orderedMainAbility);
                imageHandler.downloadImage("ability", abilityLocation, abilityUrl, getContext());
            }

            orderingMainAbility.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),toBuy.skill.name,Toast.LENGTH_SHORT).show();
                }
            });

            orderedMainAbility.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),alreadyOrdered.skill.name,Toast.LENGTH_SHORT).show();
                }
            });

            //Set the number of slots the gear has
            if (toBuy.gear.rarity == 1) {
                orderingSub3.setVisibility(View.INVISIBLE);
            } else if (toBuy.gear.rarity == 0) {
                orderingSub2.setVisibility(View.INVISIBLE);
                orderingSub3.setVisibility(View.INVISIBLE);
            }

            if (alreadyOrdered.gear.rarity == 1) {
                orderedSub3.setVisibility(View.INVISIBLE);
            } else if (alreadyOrdered.gear.rarity == 0) {
                orderedSub2.setVisibility(View.INVISIBLE);
                orderedSub3.setVisibility(View.INVISIBLE);
            }

            //Set the Time
            Long now = new Date().getTime();
            new CountDownTimer((toBuy.endTime * 1000) - now, 60000) {

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
                    orderingTime.setText(timeString);
                }

                public void onFinish() {
                    orderingTime.setText("Expired");
                }

            }.start();

        }

    }

    private Runnable orderGear = new Runnable() {
        public void run() {
            try{
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
                String cookie = settings.getString("cookie","");
                String id = settings.getString("unique_id","");
                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://app.splatoon2.nintendo.net").addConverterFactory(GsonConverterFactory.create()).build();
                Splatnet splatnet = retrofit.create(Splatnet.class);
                RequestBody override = RequestBody.create(MediaType.parse("text/plain"), "1");
                Call<ResponseBody> buy = splatnet.orderMerch(toBuy.id,id,override,cookie);
                okhttp3.Request request = buy.request();
                Response response = buy.execute();
                System.out.println("Code: "+response.code());
                loadingDialog.dismiss();
                Gson gson = new Gson();
                Annie shop = gson.fromJson(settings.getString("shopState",""),Annie.class);
                shop.ordered = new Ordered();
                shop.ordered.gear = toBuy.gear;
                shop.ordered.price = toBuy.price;
                shop.ordered.skill = toBuy.skill;

                SharedPreferences.Editor edit = settings.edit();

                edit.putString("shopState",gson.toJson(shop));
                edit.commit();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
}