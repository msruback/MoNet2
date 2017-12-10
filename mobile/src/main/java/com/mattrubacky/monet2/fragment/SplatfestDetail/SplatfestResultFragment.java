package com.mattrubacky.monet2.fragment.SplatfestDetail;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.sax.RootElement;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.ResultList;
import com.mattrubacky.monet2.deserialized.Splatfest;
import com.mattrubacky.monet2.deserialized.SplatfestColors;
import com.mattrubacky.monet2.deserialized.SplatfestResult;
import com.mattrubacky.monet2.deserialized.SplatfestStats;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

/**
 * Created by mattr on 11/17/2017.
 */

public class SplatfestResultFragment extends Fragment {
    ViewGroup rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = (ViewGroup)  inflater.inflate(R.layout.fragment_splatfest_results, container, false);
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

        TextView alphaVotePercent = (TextView) rootView.findViewById(R.id.alphaVotePercent);
        TextView bravoVotePercent = (TextView) rootView.findViewById(R.id.bravoVotePercent);
        TextView alphaSoloPercent = (TextView) rootView.findViewById(R.id.alphaSoloPercent);
        TextView bravoSoloPercent = (TextView) rootView.findViewById(R.id.bravoSoloPercent);
        TextView alphaTeamPercent = (TextView) rootView.findViewById(R.id.alphaTeamPercent);
        TextView bravoTeamPercent = (TextView) rootView.findViewById(R.id.bravoTeamPercent);

        voteTitle.setTypeface(fontTitle);
        alphaVoteText.setTypeface(font);
        bravoVoteText.setTypeface(font);
        alphaVotePercent.setTypeface(fontTitle);
        bravoVotePercent.setTypeface(fontTitle);
        soloTitle.setTypeface(fontTitle);
        alphaSoloText.setTypeface(font);
        bravoSoloText.setTypeface(font);
        alphaSoloPercent.setTypeface(fontTitle);
        bravoSoloPercent.setTypeface(fontTitle);
        teamTitle.setTypeface(fontTitle);
        alphaTeamText.setTypeface(font);
        bravoTeamText.setTypeface(font);
        alphaTeamPercent.setTypeface(fontTitle);
        bravoTeamPercent.setTypeface(fontTitle);

        float total = result.participants.alpha +result.participants.bravo;
        double alphaPercent = (result.participants.alpha/total)*100;
        double bravoPercent = (result.participants.bravo/total)*100;

        alphaPercent = Math.round(alphaPercent);
        bravoPercent = Math.round(bravoPercent);
        alphaVotePercent.setText(((int)alphaPercent)+"%");
        bravoVotePercent.setText(((int)bravoPercent)+"%");

        total = result.teamScores.alphaSolo+result.teamScores.bravoSolo;
        alphaPercent = (result.teamScores.alphaSolo/total)*100;
        bravoPercent = (result.teamScores.bravoSolo/total)*100;

        alphaPercent = Math.round(alphaPercent);
        bravoPercent = Math.round(bravoPercent);
        alphaSoloPercent.setText(((int)alphaPercent)+"%");
        bravoSoloPercent.setText(((int)bravoPercent)+"%");

        total = result.teamScores.alphaTeam+result.teamScores.bravoTeam;
        alphaPercent = (result.teamScores.alphaTeam/total)*100;
        bravoPercent = (result.teamScores.bravoTeam/total)*100;
        alphaPercent = Math.round(alphaPercent);
        bravoPercent = Math.round(bravoPercent);
        alphaTeamPercent.setText(((int)alphaPercent)+"%");
        bravoTeamPercent.setText(((int)bravoPercent)+"%");

        alphaVoteText.setText(String.valueOf(result.participants.alpha));
        bravoVoteText.setText(String.valueOf(result.participants.bravo));
        alphaSoloText.setText(String.valueOf(result.teamScores.alphaSolo));
        bravoSoloText.setText(String.valueOf(result.teamScores.bravoSolo));
        alphaTeamText.setText(String.valueOf(result.teamScores.alphaTeam));
        bravoTeamText.setText(String.valueOf(result.teamScores.bravoTeam));

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
        total = result.participants.alpha+result.participants.bravo;
        float width = result.participants.alpha/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        alphaVote.setLayoutParams(layoutParams);

        layoutParams = bravoVote.getLayoutParams();
        width = result.participants.bravo/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        bravoVote.setLayoutParams(layoutParams);

        layoutParams = alphaSolo.getLayoutParams();
        total = result.teamScores.alphaSolo+result.teamScores.bravoSolo;
        width = result.teamScores.alphaSolo/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        alphaSolo.setLayoutParams(layoutParams);

        layoutParams = bravoSolo.getLayoutParams();
        width = result.teamScores.bravoSolo/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        bravoSolo.setLayoutParams(layoutParams);

        layoutParams = alphaTeam.getLayoutParams();
        total = result.teamScores.alphaTeam+result.teamScores.bravoTeam;
        width = result.teamScores.alphaTeam/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        alphaTeam.setLayoutParams(layoutParams);

        layoutParams = bravoTeam.getLayoutParams();
        width = result.teamScores.bravoTeam/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        bravoTeam.setLayoutParams(layoutParams);

        return rootView;
    }
}
