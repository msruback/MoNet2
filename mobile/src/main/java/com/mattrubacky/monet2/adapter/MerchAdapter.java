package com.mattrubacky.monet2.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mattrubacky.monet2.ClosetDetail;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.ClosetHanger;
import com.mattrubacky.monet2.deserialized.Gear;
import com.mattrubacky.monet2.deserialized.Product;
import com.mattrubacky.monet2.fragment.ShopFragment;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.mattrubacky.monet2.helper.StatCalc;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mattr on 11/13/2017.
 */

public class MerchAdapter extends RecyclerView.Adapter<MerchAdapter.ViewHolder>{

    private ArrayList<Product> input = new ArrayList<Product>();
    private LayoutInflater inflater;
    private Context context;
    private Activity activity;
    private View.OnClickListener onClickListener;

    public MerchAdapter(Activity activity, ArrayList<Product> input, View.OnClickListener onClickListener) {
        this.inflater = LayoutInflater.from(activity);
        this.input = input;
        this.context = activity;
        this.activity = activity;
        this.onClickListener = onClickListener;

    }
    @Override
    public MerchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_merch, parent, false);
        MerchAdapter.ViewHolder viewHolder = new MerchAdapter.ViewHolder(view);
        view.setOnClickListener(onClickListener);
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                RecyclerView currentMerch = (RecyclerView) activity.findViewById(R.id.CurrentMerch);
                int itemPosition = currentMerch.indexOfChild(v);
                Intent intent = new Intent(activity, ClosetDetail.class);
                SplatnetSQLManager database = new SplatnetSQLManager(activity);
                Product gear = input.get(itemPosition);
                ClosetHanger hanger = database.selectCloset(gear.gear.id,gear.gear.kind);

                StatCalc statCalc = new StatCalc(context,gear.gear);
                hanger.inkStats = statCalc.getInkStats();
                hanger.killStats = statCalc.getKillStats();
                hanger.deathStats = statCalc.getDeathStats();
                hanger.specialStats = statCalc.getSpecialStats();
                hanger.numGames = statCalc.getNum();

                Bundle bundle = new Bundle();
                bundle.putParcelable("stats",hanger);
                intent.putExtras(bundle);

                activity.startActivity(intent);
                return false;
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MerchAdapter.ViewHolder holder, final int position) {
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
            imageHandler.downloadImage("brand",brandLocation,brandUrl,context);
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

        holder.mainAbility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,product.skill.name,Toast.LENGTH_SHORT).show();
            }
        });

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
