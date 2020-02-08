package com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.data.deserialized.splatoon.parsley.SalmonSchedule;
import com.mattrubacky.monet2.ui.adapter.RecyclerView.SalmonRotationAdapter;
import com.mattrubacky.monet2.data.deserialized_entities.Gear;
import com.mattrubacky.monet2.backend.ImageHandler;
import com.squareup.picasso.Picasso;

/**
 * Created by mattr on 1/14/2018.
 */

public class SalmonRunCardViewHolder extends RecyclerView.ViewHolder{

    private RelativeLayout salmonRun;
    private RecyclerView salmonPager;
    private TextView salmonTitle;
    private ImageView currentGear;
    private Context context;

    public SalmonRunCardViewHolder(LayoutInflater inflater, ViewGroup parent,Context context) {
        super(inflater.inflate(R.layout.card_salmon, parent, false));
        this.context = context;

        salmonRun = itemView.findViewById(R.id.SalmonRun);
        salmonTitle = itemView.findViewById(R.id.salmonName);
        currentGear = itemView.findViewById(R.id.monthlyGear);
        salmonPager = itemView.findViewById(R.id.SalmonPager);

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
        SalmonRotationAdapter salmonRotationAdapter = new SalmonRotationAdapter(context,salmonSchedule.getDetails(),salmonSchedule.getTimes());
        salmonPager.setAdapter(salmonRotationAdapter);
        salmonPager.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(salmonPager);

    }
}