package com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.Splatfest;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.mattrubacky.monet2.helper.SplatfestStats;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by mattr on 12/24/2017.
 */

public class SplatfestPerformanceViewHolder extends RecyclerView.ViewHolder{

    public RelativeLayout winLossMeter,wins,losses,meterLayout,sameTeam,disconnects,timePlayedLayout,imageLayout;
    public TextView gradeTitle,sameTeamTitle,disconnectTitle,powerTitle,playedTitle;
    public TextView winText,lossText,gradeText,sameTeamText,disconnectText,powerText,playedText;
    public ImageView image;
    private Context context;

    public SplatfestPerformanceViewHolder(LayoutInflater inflater, ViewGroup parent,Context context) {
        super(inflater.inflate(R.layout.item_pager_list, parent, false));

        this.context = context;

        winLossMeter = (RelativeLayout) itemView.findViewById(R.id.WinLossMeter);
        wins = (RelativeLayout) itemView.findViewById(R.id.Wins);
        losses = (RelativeLayout) itemView.findViewById(R.id.Losses);
        meterLayout = (RelativeLayout) itemView.findViewById(R.id.winOutline);
        sameTeam = (RelativeLayout) itemView.findViewById(R.id.sameTeamLayout);
        disconnects = (RelativeLayout) itemView.findViewById(R.id.disconnectLayout);
        timePlayedLayout = (RelativeLayout) itemView.findViewById(R.id.timeLayout);
        imageLayout = (RelativeLayout) itemView.findViewById(R.id.imageLayout);

        winText = (TextView) itemView.findViewById(R.id.WinText);
        lossText = (TextView) itemView.findViewById(R.id.LossText);
        gradeTitle = (TextView) itemView.findViewById(R.id.GradeTitleText);
        gradeText = (TextView) itemView.findViewById(R.id.GradeText);
        sameTeamTitle = (TextView) itemView.findViewById(R.id.SameTeamTitleText);
        sameTeamText = (TextView) itemView.findViewById(R.id.SameTeamText);
        disconnectTitle = (TextView) itemView.findViewById(R.id.DisconnectTitleText);
        disconnectText = (TextView) itemView.findViewById(R.id.DisconnectText);
        powerTitle = (TextView) itemView.findViewById(R.id.PowerTitleText);
        powerText = (TextView) itemView.findViewById(R.id.PowerText);
        playedTitle = (TextView) itemView.findViewById(R.id.TimeTitleText);
        playedText = (TextView) itemView.findViewById(R.id.TimeText);

        image = (ImageView) itemView.findViewById(R.id.Image);
    }

    public void manageHolder(SplatfestStats stats,Splatfest splatfest){

        Typeface font = Typeface.createFromAsset(context.getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(context.getAssets(), "Paintball.otf");


        wins.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.alpha.getColor())));
        losses.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));

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
            if(stats.grade.contains(splatfest.names.alpha)) {
                imageDirName = splatfest.names.alpha.toLowerCase().replace(" ", "_");
                url = "https://app.splatoon2.nintendo.net" + splatfest.images.alpha;
            }else {
                imageDirName = splatfest.names.bravo.toLowerCase().replace(" ", "_");
                url = "https://app.splatoon2.nintendo.net" + splatfest.images.bravo;
            }
            if (imageHandler.imageExists("splatfest", imageDirName, context)) {
                image.setImageBitmap(imageHandler.loadImage("splatfest", imageDirName));
            } else {
                Picasso.with(context).load(url).into(image);
                imageHandler.downloadImage("splatfest", imageDirName, url, context);
            }

        }else{
            imageLayout.setVisibility(View.GONE);
        }

        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        wins.setLayoutParams(layoutParams);

        layoutParams = losses.getLayoutParams();
        width = stats.losses/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        losses.setLayoutParams(layoutParams);
    }
}
