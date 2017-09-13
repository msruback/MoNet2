package com.mattrubacky.monet2;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;


public class RankedRotation extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_ranked_rotation, container, false);
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");

        TextView time = (TextView) rootView.findViewById(R.id.rankTime);
        TextView mode = (TextView) rootView.findViewById(R.id.rankMode);
        TextView title1 = (TextView) rootView.findViewById(R.id.rankStageName1);
        TextView title2 = (TextView) rootView.findViewById(R.id.rankStageName2);

        ImageView image1 = (ImageView) rootView.findViewById(R.id.rankStageImage1);
        ImageView image2 = (ImageView) rootView.findViewById(R.id.rankStageImage2);

        time.setTypeface(font);
        mode.setTypeface(font);
        title1.setTypeface(font);
        title2.setTypeface(font);

        Bundle bundle = this.getArguments();
        TimePeriod timePeriod = bundle.getParcelable("timePeriod");

        Stage a = timePeriod.a;
        Stage b = timePeriod.b;
        Date startTime = new Date((timePeriod.start*1000));
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        String startText = sdf.format(startTime);
        Date endTime = new Date((timePeriod.end*1000));
        String endText = sdf.format(endTime);

        title1.setText(a.name);
        title2.setText(b.name);
        mode.setText(timePeriod.rule.name);
        time.setText(startText+" - "+endText);

        String url1 = "https://app.splatoon2.nintendo.net"+a.image;
        String url2 = "https://app.splatoon2.nintendo.net"+b.image;

        ImageHandler imageHandler = new ImageHandler();
        String image1DirName = a.name.toLowerCase().replace(" ","_");
        String image2DirName = b.name.toLowerCase().replace(" ","_");

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

        return rootView;
    }
}