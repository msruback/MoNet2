package com.mattrubacky.monet2.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mattrubacky.monet2.data.deserialized.splatoon.Ordered;
import com.mattrubacky.monet2.data.deserialized.splatoon.Product;
import com.mattrubacky.monet2.backend.ImageHandler;
import com.mattrubacky.monet2.R;

import com.mattrubacky.monet2.backend.api.splatnet.SplatnetConnected;
import com.squareup.picasso.Picasso;

import java.util.Date;

/**
 * Created by mattr on 9/20/2017.
 */

public class BuyDialog extends Dialog{
    private Product toBuy;
    private Ordered alreadyOrdered;
    private SplatnetConnected caller;
    private Activity activity;

    public BuyDialog(Activity activity,SplatnetConnected caller,Product product,Ordered ordered) {
        super(activity);
        this.activity = activity;
        toBuy = product;
        alreadyOrdered = ordered;
        this.caller = caller;
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

            RelativeLayout dialogCard = findViewById(R.id.dialogCard);
            dialogCard.setClipToOutline(true);

            RelativeLayout item = findViewById(R.id.Item);
            RelativeLayout infoBar = findViewById(R.id.InfoBar);
            RelativeLayout infoPatch = findViewById(R.id.infoPatch);
            item.setClipToOutline(true);

            ImageView brand = findViewById(R.id.Brand);
            ImageView gear = findViewById(R.id.Image);
            ImageView mainAbility = findViewById(R.id.MainAbility);
            ImageView sub2 = findViewById(R.id.Sub2);
            ImageView sub3 = findViewById(R.id.Sub3);

            TextView name = findViewById(R.id.Name);
            TextView cost = findViewById(R.id.Cost);
            final TextView time = findViewById(R.id.Time);

            Button orderButton = findViewById(R.id.OrderButton);

            orderButton.setTypeface(font);
            orderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    SplatnetConnector splatnetConnector = new SplatnetConnector(caller,activity,getContext());
//                    splatnetConnector.addRequest(new OrderRequest(toBuy.id));
//                    splatnetConnector.execute();
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

            RelativeLayout dialogCard = findViewById(R.id.dialogCard);
            dialogCard.setClipToOutline(true);

            RelativeLayout orderingItem = findViewById(R.id.OrderingItem);
            RelativeLayout orderingInfoBar = findViewById(R.id.OrderingInfoBar);
            RelativeLayout orderingInfoPatch = findViewById(R.id.orderingInfoPatch);
            orderingItem.setClipToOutline(true);

            ImageView orderingBrand = findViewById(R.id.OrderingBrand);
            ImageView orderingGear = findViewById(R.id.OrderingImage);
            ImageView orderingMainAbility = findViewById(R.id.OrderingMainAbility);
            ImageView orderingSub2 = findViewById(R.id.OrderingSub2);
            ImageView orderingSub3 = findViewById(R.id.OrderingSub3);

            TextView orderingName = findViewById(R.id.OrderingName);
            TextView orderingCost = findViewById(R.id.OrderingCost);
            final TextView orderingTime = findViewById(R.id.OrderingTime);

            //Ordered Layout
            RelativeLayout orderedItem = findViewById(R.id.OrderedItem);
            RelativeLayout orderedInfoBar = findViewById(R.id.OrderedInfoBar);
            RelativeLayout orderedInfoPatch = findViewById(R.id.orderedInfoPatch);
            orderingItem.setClipToOutline(true);

            ImageView orderedBrand = findViewById(R.id.OrderedBrand);
            ImageView orderedGear = findViewById(R.id.OrderedImage);
            ImageView orderedMainAbility = findViewById(R.id.OrderedMainAbility);
            ImageView orderedSub2 = findViewById(R.id.OrderedSub2);
            ImageView orderedSub3 = findViewById(R.id.OrderedSub3);

            TextView orderedName = findViewById(R.id.OrderedName);
            TextView orderedCost = findViewById(R.id.OrderedCost);

            Button orderButton = findViewById(R.id.OrderButton);
            Button keepButton = findViewById(R.id.KeepButton);

            orderButton.setTypeface(font);
            keepButton.setTypeface(font);

            orderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    SplatnetConnector splatnetConnector = new SplatnetConnector(caller,activity,getContext());
//                    splatnetConnector.addRequest(new OrderRequest(toBuy.id));
//                    splatnetConnector.execute();
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
}