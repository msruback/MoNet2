package com.mattrubacky.monet2.fragment.schedule;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattrubacky.monet2.helper.ImageHandler;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.*;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mattr on 10/13/2017.
 */

public class SplatfestRotation extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_festival_rotation, container, false);
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Splatfont2.ttf");

        TextView time = (TextView) rootView.findViewById(R.id.fesTime);
        TextView title1 = (TextView) rootView.findViewById(R.id.fesStageName1);
        TextView title2 = (TextView) rootView.findViewById(R.id.fesStageName2);
        TextView title3 = (TextView) rootView.findViewById(R.id.fesStageName3);
        ImageView image1 = (ImageView) rootView.findViewById(R.id.fesStageImage1);
        ImageView image2 = (ImageView) rootView.findViewById(R.id.fesStageImage2);
        ImageView image3 = (ImageView) rootView.findViewById(R.id.fesStageImage3);

        time.setTypeface(font);
        title1.setTypeface(font);
        title2.setTypeface(font);
        title3.setTypeface(font);

        Bundle bundle = this.getArguments();
        TimePeriod timePeriod = bundle.getParcelable("timePeriod");
        Splatfest splatfest = bundle.getParcelable("splatfest");

        Stage a = timePeriod.a;
        Stage b = timePeriod.b;
        Date startTime = new Date((timePeriod.start*1000));
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        String startText = sdf.format(startTime);
        Date endTime = new Date((timePeriod.end*1000));
        String endText = sdf.format(endTime);

        title1.setText(a.name);
        title2.setText(b.name);
        title3.setText(splatfest.stage.name);
        time.setText(startText+" - "+endText);

        String url1 = "https://app.splatoon2.nintendo.net"+a.url;
        String url2 = "https://app.splatoon2.nintendo.net"+b.url;
        String url3 = "https://app.splatoon2.nintendo.net"+splatfest.stage.url;

        ImageHandler imageHandler = new ImageHandler();
        String image1DirName = a.name.toLowerCase().replace(" ","_");
        String image2DirName = b.name.toLowerCase().replace(" ","_");
        String image3DirName = splatfest.stage.name;

        if(imageHandler.imageExists("stage",image1DirName,getContext())){
            image1.setImageBitmap(imageHandler.loadImage("stage",image1DirName));
        }else{
            Picasso.with(getContext()).load(url1).resize(1280,720).into(image1);
            imageHandler.downloadImage("stage",image1DirName,url1,getContext());
        }

        if(imageHandler.imageExists("stage",image2DirName,getContext())){
            image2.setImageBitmap(imageHandler.loadImage("stage",image2DirName));
        }else{
            Picasso.with(getContext()).load(url2).resize(1280,720).into(image2);
            imageHandler.downloadImage("stage",image2DirName,url2,getContext());
        }

        if(imageHandler.imageExists("stage",image3DirName,getContext())){
            image3.setImageBitmap(imageHandler.loadImage("stage",image3DirName));
        }else{
            Picasso.with(getContext()).load(url3).resize(1280,720).into(image3);
            imageHandler.downloadImage("stage",image3DirName,url3,getContext());
        }

        return rootView;
    }

}
