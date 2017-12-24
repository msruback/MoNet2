package com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.Product;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.util.Date;

/**
 * Created by mattr on 12/24/2017.
 */

public class MerchViewHolder extends RecyclerView.ViewHolder{

    public RelativeLayout item,infoBar,infoPatch;
    public ImageView brand,gear,mainAbility,sub2,sub3;
    public TextView name,cost,time;
    private Context context;

    public MerchViewHolder(LayoutInflater inflater, ViewGroup parent, Context context) {
        super(inflater.inflate(R.layout.item_merch, parent, false));
        this.context = context;

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
    public void manageHolder(final Product product){
        Typeface font = Typeface.createFromAsset(context.getAssets(),"Splatfont2.ttf");
        ImageHandler imageHandler = new ImageHandler();

        //Change the info bar color to match gear kind
        switch (product.gear.kind){
            case "head":
                infoBar.setBackgroundTintList(context.getResources().getColorStateList(R.color.head));
                infoPatch.setBackgroundTintList(context.getResources().getColorStateList(R.color.head));
                break;
            case "clothes":
                infoBar.setBackgroundTintList(context.getResources().getColorStateList(R.color.clothes));
                infoPatch.setBackgroundTintList(context.getResources().getColorStateList(R.color.clothes));
                break;
            case "shoes":
                infoBar.setBackgroundTintList(context.getResources().getColorStateList(R.color.shoes));
                infoPatch.setBackgroundTintList(context.getResources().getColorStateList(R.color.shoes));
                break;
        }

        //Set the fonts
        name.setTypeface(font);
        cost.setTypeface(font);
        time.setTypeface(font);

        //Set the name and cost of the gear
        name.setText(product.gear.name);
        cost.setText(product.price);

        //Set the gear image
        String gearUrl = "https://app.splatoon2.nintendo.net"+product.gear.url;
        String gearLocation = product.gear.name.toLowerCase().replace(" ","_");
        if(imageHandler.imageExists("gear",gearLocation,context)){
            gear.setImageBitmap(imageHandler.loadImage("gear",gearLocation));
        }else{
            Picasso.with(context).load(gearUrl).into(gear);
            imageHandler.downloadImage("gear",gearLocation,gearUrl,context);
        }

        //Set the brand image
        String brandUrl = "https://app.splatoon2.nintendo.net"+product.gear.brand.url;
        String brandLocation = product.gear.brand.name.toLowerCase().replace(" ","_");
        if(imageHandler.imageExists("brand",brandLocation,context)){
            brand.setImageBitmap(imageHandler.loadImage("brand",brandLocation));
        }else{
            Picasso.with(context).load(brandUrl).into(brand);
            imageHandler.downloadImage("brand",brandLocation,brandUrl,context);
        }

        //Set the ability image
        String abilityUrl = "https://app.splatoon2.nintendo.net"+product.skill.url;
        String abilityLocation = product.skill.name.toLowerCase().replace(" ","_");
        if(imageHandler.imageExists("ability",abilityLocation,context)){
            mainAbility.setImageBitmap(imageHandler.loadImage("ability",abilityLocation));
        }else{
            Picasso.with(context).load(abilityUrl).into(mainAbility);
            imageHandler.downloadImage("ability",abilityLocation,abilityUrl,context);
        }

        mainAbility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,product.skill.name,Toast.LENGTH_SHORT).show();
            }
        });

        //Set the number of slots the gear has
        if(product.gear.rarity==1){
            sub3.setVisibility(View.INVISIBLE);
        }else if(product.gear.rarity==0) {
            sub2.setVisibility(View.INVISIBLE);
            sub3.setVisibility(View.INVISIBLE);
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
                time.setText(timeString);
            }

            public void onFinish() {
                time.setText("Expired");
            }

        }.start();
    }}
