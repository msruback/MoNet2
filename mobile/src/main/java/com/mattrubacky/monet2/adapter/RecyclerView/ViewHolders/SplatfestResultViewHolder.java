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
import com.mattrubacky.monet2.deserialized.Splatfest;
import com.mattrubacky.monet2.deserialized.SplatfestResult;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

/**
 * Created by mattr on 12/24/2017.
 */

public class SplatfestResultViewHolder extends RecyclerView.ViewHolder{

    public RelativeLayout voteMeter,alphaVote,bravoVote,soloMeter,alphaSolo,bravoSolo,teamMeter,alphaTeam,bravoTeam;
    public ImageView alphaImage,bravoImage;
    public TextView voteTitle,alphaVoteText,bravoVoteText,soloTitle,alphaSoloText,bravoSoloText,teamTitle,alphaTeamText,bravoTeamText;
    public TextView alphaVotePercent,bravoVotePercent,alphaSoloPercent,bravoSoloPercent,alphaTeamPercent,bravoTeamPercent;
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

        alphaVotePercent = (TextView) itemView.findViewById(R.id.alphaVotePercent);
        bravoVotePercent = (TextView) itemView.findViewById(R.id.bravoVotePercent);
        alphaSoloPercent = (TextView) itemView.findViewById(R.id.alphaSoloPercent);
        bravoSoloPercent = (TextView) itemView.findViewById(R.id.bravoSoloPercent);
        alphaTeamPercent = (TextView) itemView.findViewById(R.id.alphaTeamPercent);
        bravoTeamPercent = (TextView) itemView.findViewById(R.id.bravoTeamPercent);
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
        total = result.participants.alpha+result.participants.bravo;
        float width = result.participants.alpha/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        alphaVote.setLayoutParams(layoutParams);

        layoutParams = bravoVote.getLayoutParams();
        width = result.participants.bravo/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        bravoVote.setLayoutParams(layoutParams);

        layoutParams = alphaSolo.getLayoutParams();
        total = result.teamScores.alphaSolo+result.teamScores.bravoSolo;
        width = result.teamScores.alphaSolo/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        alphaSolo.setLayoutParams(layoutParams);

        layoutParams = bravoSolo.getLayoutParams();
        width = result.teamScores.bravoSolo/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        bravoSolo.setLayoutParams(layoutParams);

        layoutParams = alphaTeam.getLayoutParams();
        total = result.teamScores.alphaTeam+result.teamScores.bravoTeam;
        width = result.teamScores.alphaTeam/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        alphaTeam.setLayoutParams(layoutParams);

        layoutParams = bravoTeam.getLayoutParams();
        width = result.teamScores.bravoTeam/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        bravoTeam.setLayoutParams(layoutParams);
    }
}
