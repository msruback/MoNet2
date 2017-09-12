package com.mattrubacky.monet2;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class TurfRotation extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_turf_rotation, container, false);
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");

        TextView time = (TextView) rootView.findViewById(R.id.turfTime);
        TextView title1 = (TextView) rootView.findViewById(R.id.turfStageName1);
        TextView title2 = (TextView) rootView.findViewById(R.id.turfStageName2);
        ImageView image1 = (ImageView) rootView.findViewById(R.id.turfStageImage1);
        ImageView image2 = (ImageView) rootView.findViewById(R.id.turfStageImage2);

        time.setTypeface(font);
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
        time.setText(startText+" - "+endText);

        String url1 = "https://app.splatoon2.nintendo.net"+a.image;
        String url2 = "https://app.splatoon2.nintendo.net"+b.image;

        Picasso.with(getContext()).load(url1).resize(1280,720).into(image1);
        Picasso.with(getContext()).load(url2).resize(1280,720).into(image2);

        return rootView;
    }

}

