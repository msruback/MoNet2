package com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.data.deserialized.splatoon.Record;
import com.mattrubacky.monet2.data.stats.PlayerStats;

import java.text.SimpleDateFormat;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 12/24/2017.
 */

public class PlayerGeneralStatViewHolder extends RecyclerView.ViewHolder{

    public RelativeLayout card,winLossMeter,wins,losses;
    public TextView inkTitle,firstPlayedTitle,lastPlayedTitle;
    private TextView winText,lossText,inkedText,firstPlayedText,lastPlayedText;
    private Context context;

    public PlayerGeneralStatViewHolder(LayoutInflater inflater, ViewGroup parent, Context context) {
        super(inflater.inflate(R.layout.item_player_general_stats, parent, false));

        this.context = context;

        card = itemView.findViewById(R.id.generalStats);
        winLossMeter = itemView.findViewById(R.id.WinLossMeter);
        wins = itemView.findViewById(R.id.Wins);
        losses = itemView.findViewById(R.id.Losses);

        inkTitle = itemView.findViewById(R.id.InkedTitleText);
        firstPlayedTitle = itemView.findViewById(R.id.FirstTitleText);
        lastPlayedTitle = itemView.findViewById(R.id.LastTitleText);

        winText = itemView.findViewById(R.id.WinText);
        lossText = itemView.findViewById(R.id.LossText);
        inkedText = itemView.findViewById(R.id.InkedText);
        firstPlayedText = itemView.findViewById(R.id.FirstText);
        lastPlayedText = itemView.findViewById(R.id.LastText);
    }

    public void manageHolder(PlayerStats stats,Record records){
        Typeface font = Typeface.createFromAsset(context.getAssets(),"Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(context.getAssets(), "Paintball.otf");

        card.setClipToOutline(true);

        inkTitle.setTypeface(fontTitle);
        firstPlayedTitle.setTypeface(fontTitle);
        lastPlayedTitle.setTypeface(fontTitle);

        winText.setTypeface(font);
        lossText.setTypeface(font);
        inkedText.setTypeface(font);
        firstPlayedText.setTypeface(font);
        lastPlayedText.setTypeface(font);

        winText.setText(String.valueOf(records.records.wins));
        lossText.setText(String.valueOf(records.records.losses));
        inkedText.setText(String.valueOf(records.challenges.totalPaint));

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyy h:mm:ss a");

        String time = sdf.format(records.records.startTime);

        firstPlayedText.setText(time);

        time = sdf.format(stats.lastPlayed);

        lastPlayedText.setText(time);

        winLossMeter.setClipToOutline(true);

        ViewGroup.LayoutParams layoutParams = wins.getLayoutParams();
        float total = records.records.wins + records.records.losses;
        float width = records.records.wins / total;

        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        wins.setLayoutParams(layoutParams);

        layoutParams = losses.getLayoutParams();
        width = records.records.losses / total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        losses.setLayoutParams(layoutParams);
    }
}
