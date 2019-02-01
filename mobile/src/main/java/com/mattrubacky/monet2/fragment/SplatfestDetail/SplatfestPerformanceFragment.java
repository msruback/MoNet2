package com.mattrubacky.monet2.fragment.SplatfestDetail;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.splatoon.Splatfest;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.mattrubacky.monet2.helper.SplatfestStats;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * Created by mattr on 11/17/2017.
 */

public class SplatfestPerformanceFragment extends Fragment {
    private ViewGroup rootView;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = (ViewGroup)  inflater.inflate(R.layout.item_splatfest_performance, container, false);
        Bundle bundle = this.getArguments();

        Splatfest splatfest = bundle.getParcelable("splatfest");
        SplatfestStats stats = bundle.getParcelable("stats");

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(getContext().getAssets(), "Paintball.otf");

        RelativeLayout winLossMeter = rootView.findViewById(R.id.WinLossMeter);
        RelativeLayout wins = rootView.findViewById(R.id.Wins);
        RelativeLayout losses = rootView.findViewById(R.id.Losses);
        RelativeLayout meterLayout = rootView.findViewById(R.id.winOutline);
        RelativeLayout sameTeam = rootView.findViewById(R.id.sameTeamLayout);
        RelativeLayout disconnects = rootView.findViewById(R.id.disconnectLayout);
        RelativeLayout timePlayedLayout = rootView.findViewById(R.id.timeLayout);
        RelativeLayout imageLayout = rootView.findViewById(R.id.imageLayout);

        wins.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.alpha.getColor())));
        losses.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));

        TextView winText = rootView.findViewById(R.id.WinText);
        TextView lossText = rootView.findViewById(R.id.LossText);
        TextView gradeTitle = rootView.findViewById(R.id.GradeTitleText);
        TextView gradeText = rootView.findViewById(R.id.GradeText);
        TextView sameTeamTitle = rootView.findViewById(R.id.SameTeamTitleText);
        TextView sameTeamText = rootView.findViewById(R.id.SameTeamText);
        TextView disconnectTitle = rootView.findViewById(R.id.DisconnectTitleText);
        TextView disconnectText = rootView.findViewById(R.id.DisconnectText);
        TextView powerTitle = rootView.findViewById(R.id.PowerTitleText);
        TextView powerText = rootView.findViewById(R.id.PowerText);
        TextView playedTitle = rootView.findViewById(R.id.TimeTitleText);
        TextView playedText = rootView.findViewById(R.id.TimeText);

        ImageView image = rootView.findViewById(R.id.Image);

        winText.setTypeface(font);
        lossText.setTypeface(font);
        gradeTitle.setTypeface(fontTitle);
        gradeText.setTypeface(font);
        sameTeamTitle.setTypeface(fontTitle);
        sameTeamText.setTypeface(font);
        disconnectTitle.setTypeface(fontTitle);
        disconnectText.setTypeface(font);
        powerTitle.setTypeface(fontTitle);
        powerText.setTypeface(font);
        playedTitle.setTypeface(fontTitle);
        playedText.setTypeface(font);

        winText.setText(String.valueOf(stats.wins));
        lossText.setText(String.valueOf(stats.losses));
        gradeText.setText(stats.grade);
        sameTeamText.setText(String.valueOf(stats.sameTeam));
        disconnectText.setText(String.valueOf(stats.disconnects));
        powerText.setText(String.valueOf(stats.power));

        gradeTitle.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));
        sameTeamTitle.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));
        disconnectTitle.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));
        powerTitle.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));
        playedTitle.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        stats.timePlayed+=calendar.getTimeInMillis();
        SimpleDateFormat format = new SimpleDateFormat("h:mm");
        playedText.setText(format.format(stats.timePlayed));


        winLossMeter.setClipToOutline(true);

        ViewGroup.LayoutParams layoutParams = wins.getLayoutParams();

        float total = stats.wins+stats.losses+stats.disconnects;
        float width = stats.wins/total;

        if(total==0){
            meterLayout.setVisibility(View.GONE);
            sameTeam.setVisibility(View.GONE);
            disconnects.setVisibility(View.GONE);
            timePlayedLayout.setVisibility(View.GONE);
            imageLayout.setVisibility(View.VISIBLE);

            ImageHandler imageHandler = new ImageHandler();
            String imageDirName,url;
            if(stats.grade!=null) {
                if (stats.grade.contains(splatfest.names.alpha)) {
                    imageDirName = splatfest.names.alpha.toLowerCase().replace(" ", "_");
                    url = "https://app.splatoon2.nintendo.net" + splatfest.images.alpha;
                } else {
                    imageDirName = splatfest.names.bravo.toLowerCase().replace(" ", "_");
                    url = "https://app.splatoon2.nintendo.net" + splatfest.images.bravo;
                }
                if (imageHandler.imageExists("splatfest", imageDirName, getContext())) {
                    image.setImageBitmap(imageHandler.loadImage("splatfest", imageDirName));
                } else {
                    Picasso.with(getContext()).load(url).into(image);
                    imageHandler.downloadImage("splatfest", imageDirName, url, getContext());
                }
            }

        }else{
            imageLayout.setVisibility(View.GONE);
        }

        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        wins.setLayoutParams(layoutParams);

        layoutParams = losses.getLayoutParams();
        width = stats.losses/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        losses.setLayoutParams(layoutParams);

        return rootView;
    }
}
