package com.mattrubacky.monet2.fragment.SplatfestDetail;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
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
 * Created by mattr on 11/17/2017.
 */

public class SplatfestResultFragment extends Fragment {
    ViewGroup rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = (ViewGroup)  inflater.inflate(R.layout.item_splatfest_results, container, false);
        Bundle bundle = this.getArguments();

        Splatfest splatfest = bundle.getParcelable("splatfest");
        SplatfestResult result = bundle.getParcelable("result");

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(getContext().getAssets(), "Paintball.otf");

        RelativeLayout voteMeter = (RelativeLayout) rootView.findViewById(R.id.VoteWinLossMeter);
        RelativeLayout alphaVote = (RelativeLayout) rootView.findViewById(R.id.VoteWins);
        RelativeLayout bravoVote = (RelativeLayout) rootView.findViewById(R.id.VoteLosses);
        RelativeLayout soloMeter = (RelativeLayout) rootView.findViewById(R.id.SoloWinLossMeter);
        RelativeLayout alphaSolo = (RelativeLayout) rootView.findViewById(R.id.SoloWins);
        RelativeLayout bravoSolo = (RelativeLayout) rootView.findViewById(R.id.SoloLosses);
        RelativeLayout teamMeter = (RelativeLayout) rootView.findViewById(R.id.TeamWinLossMeter);
        RelativeLayout alphaTeam = (RelativeLayout) rootView.findViewById(R.id.TeamWins);
        RelativeLayout bravoTeam = (RelativeLayout) rootView.findViewById(R.id.TeamLosses);

        alphaVote.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.alpha.getColor())));
        bravoVote.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));
        alphaSolo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.alpha.getColor())));
        bravoSolo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));
        alphaTeam.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.alpha.getColor())));
        bravoTeam.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));

        ImageView alphaImage = (ImageView) rootView.findViewById(R.id.AlphaImage);
        ImageView bravoImage = (ImageView) rootView.findViewById(R.id.BravoImage);

        TextView voteTitle = (TextView) rootView.findViewById(R.id.VoteTitle);
        TextView alphaVoteText = (TextView) rootView.findViewById(R.id.VoteWinText);
        TextView bravoVoteText = (TextView) rootView.findViewById(R.id.VoteLossText);
        TextView soloTitle = (TextView) rootView.findViewById(R.id.SoloTitle);
        TextView alphaSoloText = (TextView) rootView.findViewById(R.id.SoloWinText);
        TextView bravoSoloText = (TextView) rootView.findViewById(R.id.SoloLossText);
        TextView teamTitle = (TextView) rootView.findViewById(R.id.TeamTitle);
        TextView alphaTeamText = (TextView) rootView.findViewById(R.id.TeamWinText);
        TextView bravoTeamText = (TextView) rootView.findViewById(R.id.TeamLossText);



        voteTitle.setTypeface(fontTitle);
        alphaVoteText.setTypeface(font);
        bravoVoteText.setTypeface(font);
        soloTitle.setTypeface(fontTitle);
        alphaSoloText.setTypeface(font);
        bravoSoloText.setTypeface(font);
        teamTitle.setTypeface(fontTitle);
        alphaTeamText.setTypeface(font);
        bravoTeamText.setTypeface(font);

        double alphaVotePercent = ((double)result.rates.vote.alpha)/100;
        double bravoVotePercent = ((double)result.rates.vote.bravo)/100;


        double alphaSoloPercent = ((double)result.rates.solo.alpha)/100;
        double bravoSoloPercent = ((double)result.rates.solo.bravo)/100;

        double alphaTeamPercent = ((double)result.rates.team.alpha)/100;
        double bravoTeamPercent = ((double)result.rates.team.bravo)/100;

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
        if (imageHandler.imageExists("splatfest", imageDirName, getContext())) {
            alphaImage.setImageBitmap(imageHandler.loadImage("splatfest", imageDirName));
        } else {
            Picasso.with(getContext()).load(url).into(alphaImage);
            imageHandler.downloadImage("splatfest", imageDirName, url, getContext());
        }
        url = "https://app.splatoon2.nintendo.net"+splatfest.images.bravo;
        imageDirName = splatfest.names.bravo.toLowerCase().replace(" ", "_");
        if (imageHandler.imageExists("splatfest", imageDirName, getContext())) {
            bravoImage.setImageBitmap(imageHandler.loadImage("splatfest", imageDirName));
        } else {
            Picasso.with(getContext()).load(url).into(bravoImage);
            imageHandler.downloadImage("splatfest", imageDirName, url, getContext());
        }

        voteMeter.setClipToOutline(true);
        soloMeter.setClipToOutline(true);
        teamMeter.setClipToOutline(true);

        ViewGroup.LayoutParams layoutParams = alphaVote.getLayoutParams();
        double width = (250*((double)result.rates.vote.alpha))/10000;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float)width, getResources().getDisplayMetrics());
        alphaVote.setLayoutParams(layoutParams);

        layoutParams = bravoVote.getLayoutParams();
        width =(250*((double)result.rates.vote.bravo))/10000;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float)width, getResources().getDisplayMetrics());
        bravoVote.setLayoutParams(layoutParams);

        layoutParams = alphaSolo.getLayoutParams();
        width =(250*((double)result.rates.solo.alpha))/10000;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float)width, getResources().getDisplayMetrics());
        alphaSolo.setLayoutParams(layoutParams);

        layoutParams = bravoSolo.getLayoutParams();
        width =(250*((double)result.rates.solo.bravo))/10000;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float)width, getResources().getDisplayMetrics());
        bravoSolo.setLayoutParams(layoutParams);

        layoutParams = alphaTeam.getLayoutParams();
        width =(250*((double)result.rates.team.alpha))/10000;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float)width, getResources().getDisplayMetrics());
        alphaTeam.setLayoutParams(layoutParams);

        layoutParams = bravoTeam.getLayoutParams();
        width =(250*((double)result.rates.team.bravo))/10000;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,(float) width, getResources().getDisplayMetrics());
        bravoTeam.setLayoutParams(layoutParams);

        return rootView;
    }
}
