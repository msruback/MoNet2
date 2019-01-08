package com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.splatoon.Splatfest;
import com.mattrubacky.monet2.deserialized.splatoon.SplatfestResult;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

/**
 * Created by mattr on 12/24/2017.
 */

public class SplatfestResultViewHolder extends RecyclerView.ViewHolder{

    public RelativeLayout voteMeter,alphaVote,bravoVote,soloMeter,alphaSolo,bravoSolo,teamMeter,alphaTeam,bravoTeam;
    public ImageView alphaImage,bravoImage;
    public TextView voteTitle,alphaVoteText,bravoVoteText,soloTitle,alphaSoloText,bravoSoloText,teamTitle,alphaTeamText,bravoTeamText;
    private Context context;

    public SplatfestResultViewHolder(LayoutInflater inflater, ViewGroup parent,Context context) {
        super(inflater.inflate(R.layout.item_splatfest_results, parent, false));

        this.context = context;

        voteMeter = (RelativeLayout) itemView.findViewById(R.id.VoteWinLossMeter);
        alphaVote = (RelativeLayout) itemView.findViewById(R.id.VoteWins);
        bravoVote = (RelativeLayout) itemView.findViewById(R.id.VoteLosses);
        soloMeter = (RelativeLayout) itemView.findViewById(R.id.SoloWinLossMeter);
        alphaSolo = (RelativeLayout) itemView.findViewById(R.id.SoloWins);
        bravoSolo = (RelativeLayout) itemView.findViewById(R.id.SoloLosses);
        teamMeter = (RelativeLayout) itemView.findViewById(R.id.TeamWinLossMeter);
        alphaTeam = (RelativeLayout) itemView.findViewById(R.id.TeamWins);
        bravoTeam = (RelativeLayout) itemView.findViewById(R.id.TeamLosses);

        alphaImage = (ImageView) itemView.findViewById(R.id.AlphaImage);
        bravoImage = (ImageView) itemView.findViewById(R.id.BravoImage);

        voteTitle = (TextView) itemView.findViewById(R.id.VoteTitle);
        alphaVoteText = (TextView) itemView.findViewById(R.id.VoteWinText);
        bravoVoteText = (TextView) itemView.findViewById(R.id.VoteLossText);
        soloTitle = (TextView) itemView.findViewById(R.id.SoloTitle);
        alphaSoloText = (TextView) itemView.findViewById(R.id.SoloWinText);
        bravoSoloText = (TextView) itemView.findViewById(R.id.SoloLossText);
        teamTitle = (TextView) itemView.findViewById(R.id.TeamTitle);
        alphaTeamText = (TextView) itemView.findViewById(R.id.TeamWinText);
        bravoTeamText = (TextView) itemView.findViewById(R.id.TeamLossText);
    }

    public void manageHolder(Splatfest splatfest, SplatfestResult result){

        Typeface font = Typeface.createFromAsset(context.getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(context.getAssets(), "Paintball.otf");

        alphaVote.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.alpha.getColor())));
        bravoVote.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));
        alphaSolo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.alpha.getColor())));
        bravoSolo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));
        alphaTeam.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.alpha.getColor())));
        bravoTeam.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));

        voteTitle.setTypeface(fontTitle);
        alphaVoteText.setTypeface(font);
        bravoVoteText.setTypeface(font);
        soloTitle.setTypeface(fontTitle);
        alphaSoloText.setTypeface(font);
        bravoSoloText.setTypeface(font);
        teamTitle.setTypeface(fontTitle);
        alphaTeamText.setTypeface(font);
        bravoTeamText.setTypeface(font);

        double alphaVotePercent = result.rates.vote.alpha;
        double bravoVotePercent = result.rates.vote.bravo;


        double alphaSoloPercent;
        double bravoSoloPercent;

        double alphaTeamPercent;
        double bravoTeamPercent;
        if(!result.rates.solo.equals(0)) {
                alphaSoloPercent = result.rates.solo.alpha / 100;
                bravoSoloPercent = result.rates.solo.bravo / 100;

                alphaTeamPercent = result.rates.team.alpha / 100;
                bravoTeamPercent = result.rates.team.bravo / 100;

                soloTitle.setText("Solo");
                teamTitle.setText("Team");
        }else{
                alphaSoloPercent = result.rates.challenge.alpha/100;
                bravoSoloPercent = result.rates.challenge.bravo/100;

                alphaTeamPercent = result.rates.regular.alpha/100;
                bravoTeamPercent = result.rates.regular.bravo/100;

                soloTitle.setText("Pro");
                teamTitle.setText("Regular");
        }

        alphaVoteText.setText(String.valueOf(alphaVotePercent)+"%");
        bravoVoteText.setText(String.valueOf(bravoVotePercent)+"%");
        alphaSoloText.setText(String.valueOf(alphaSoloPercent)+"%");
        bravoSoloText.setText(String.valueOf(bravoSoloPercent)+"%");
        alphaTeamText.setText(String.valueOf(alphaTeamPercent)+"%");
        bravoTeamText.setText(String.valueOf(bravoTeamPercent)+"%");

        voteTitle.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));
        soloTitle.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));
        teamTitle.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));

        ImageHandler imageHandler = new ImageHandler();
        String imageDirName = splatfest.names.alpha.toLowerCase().replace(" ", "_");
        String url = "https://app.splatoon2.nintendo.net"+splatfest.images.alpha;
        if (imageHandler.imageExists("splatfest", imageDirName, context)) {
            alphaImage.setImageBitmap(imageHandler.loadImage("splatfest", imageDirName));
        } else {
            Picasso.with(context).load(url).into(alphaImage);
            imageHandler.downloadImage("splatfest", imageDirName, url, context);
        }
        url = "https://app.splatoon2.nintendo.net"+splatfest.images.bravo;
        imageDirName = splatfest.names.bravo.toLowerCase().replace(" ", "_");
        if (imageHandler.imageExists("splatfest", imageDirName, context)) {
            bravoImage.setImageBitmap(imageHandler.loadImage("splatfest", imageDirName));
        } else {
            Picasso.with(context).load(url).into(bravoImage);
            imageHandler.downloadImage("splatfest", imageDirName, url, context);
        }

        voteMeter.setClipToOutline(true);
        soloMeter.setClipToOutline(true);
        teamMeter.setClipToOutline(true);

        ViewGroup.LayoutParams layoutParams = alphaVote.getLayoutParams();
        float width = result.rates.vote.alpha/1000;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        alphaVote.setLayoutParams(layoutParams);

        layoutParams = bravoVote.getLayoutParams();
        width = result.rates.vote.bravo/1000;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        bravoVote.setLayoutParams(layoutParams);

        layoutParams = alphaSolo.getLayoutParams();
        width = result.rates.solo.alpha/1000;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        alphaSolo.setLayoutParams(layoutParams);

        layoutParams = bravoSolo.getLayoutParams();
        width = result.rates.solo.bravo/1000;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        bravoSolo.setLayoutParams(layoutParams);

        layoutParams = alphaTeam.getLayoutParams();
        width = result.rates.team.alpha/1000;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        alphaTeam.setLayoutParams(layoutParams);

        layoutParams = bravoTeam.getLayoutParams();
        width = result.rates.team.bravo/1000;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        bravoTeam.setLayoutParams(layoutParams);
    }
}
