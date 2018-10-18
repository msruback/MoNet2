package com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.adapter.RecyclerView.SalmonRotationAdapter;
import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.tables.Gear;
import com.mattrubacky.monet2.deserialized.splatoon.SalmonSchedule;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

/**
 * Created by mattr on 1/14/2018.
 */

public class SalmonRunCardViewHolder extends RecyclerView.ViewHolder{

    public RelativeLayout salmonRun;
    public RecyclerView salmonPager;
    public TextView salmonTitle;
    public ImageView currentGear;
    private Context context;

    public SalmonRunCardViewHolder(LayoutInflater inflater, ViewGroup parent,Context context) {
        super(inflater.inflate(R.layout.card_salmon, parent, false));
        this.context = context;

        salmonRun = (RelativeLayout) itemView.findViewById(R.id.SalmonRun);
        salmonTitle = (TextView) itemView.findViewById(R.id.salmonName);
        currentGear = (ImageView) itemView.findViewById(R.id.monthlyGear);
        salmonPager = (RecyclerView) itemView.findViewById(R.id.SalmonPager);

    }

    public void manageHolder(SalmonSchedule salmonSchedule, Gear monthlyGear){

        Typeface font = Typeface.createFromAsset(context.getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(context.getAssets(), "Paintball.otf");

        salmonTitle.setTypeface(fontTitle);
        salmonRun.setClipToOutline(true);
        if(monthlyGear!=null) {
            String url = "https://app.splatoon2.nintendo.net" + monthlyGear.url;
            ImageHandler imageHandler = new ImageHandler();
            String imageDirName = monthlyGear.name.toLowerCase().replace(" ", "_");
            if (imageHandler.imageExists("gear", imageDirName, context)) {
                currentGear.setImageBitmap(imageHandler.loadImage("gear", imageDirName));
            } else {
                Picasso.with(context).load(url).into(currentGear);
                imageHandler.downloadImage("gear", imageDirName, url, context);
            }
        }
        SalmonRotationAdapter salmonRotationAdapter = new SalmonRotationAdapter(context,salmonSchedule.details,salmonSchedule.times);
        salmonPager.setAdapter(salmonRotationAdapter);
        salmonPager.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(salmonPager);

    }
}