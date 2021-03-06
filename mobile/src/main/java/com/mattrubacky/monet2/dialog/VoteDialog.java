package com.mattrubacky.monet2.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.adapter.ListView.VoteAdapter;
import com.mattrubacky.monet2.deserialized.splatoon.NicknameIcon;
import com.mattrubacky.monet2.deserialized.splatoon.Splatfest;
import com.mattrubacky.monet2.deserialized.splatoon.SplatfestVotes;

import java.util.ArrayList;

/**
 * Created by mattr on 11/16/2017.
 */

public class VoteDialog extends Dialog {
    Splatfest splatfest;
    RelativeLayout hook,bar,alpha,bravo;
    ArrayList<NicknameIcon> alphaList,bravoList;
    VoteAdapter alphaVotes,bravoVotes;
    ListView list;

    public VoteDialog(Activity activity, SplatfestVotes votes, Splatfest splatfest) {
        super(activity);
        this.splatfest = splatfest;
        alphaList = new ArrayList<>();
        bravoList = new ArrayList<>();
        for(int i=0;i<votes.players.size();i++){
            if(votes.votes.alpha.contains(votes.players.get(i).id)){
                alphaList.add(votes.players.get(i));
            }else{
                bravoList.add(votes.players.get(i));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(getContext().getResources().getColor(R.color.transparent)));
        setContentView(R.layout.dialog_votes);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(getContext().getAssets(), "Paintball.otf");

        hook = findViewById(R.id.hook);
        RelativeLayout card = findViewById(R.id.dialogCard);
        bar = findViewById(R.id.bar);
        alpha = findViewById(R.id.AlphaButton);
        bravo = findViewById(R.id.BravoButton);

        TextView title = findViewById(R.id.title);
        TextView alphaText = findViewById(R.id.AlphaText);
        TextView bravoText = findViewById(R.id.BravoText);

        list = findViewById(R.id.ItemList);

        card.setClipToOutline(true);

        alpha.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.alpha.getColor())));
        bravo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));
        hook.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.alpha.getColor())));
        bar.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.alpha.getColor())));

        title.setTypeface(fontTitle);
        alphaText.setTypeface(fontTitle);
        bravoText.setTypeface(fontTitle);

        alphaText.setText(splatfest.names.alpha);
        bravoText.setText(splatfest.names.bravo);

        alphaVotes = new VoteAdapter(getContext(),alphaList);
        bravoVotes = new VoteAdapter(getContext(),bravoList);

        list.setAdapter(alphaVotes);

        alpha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.setAdapter(alphaVotes);
                hook.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.alpha.getColor())));
                bar.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.alpha.getColor())));
            }
        });

        bravo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.setAdapter(bravoVotes);
                hook.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));
                bar.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));
            }
        });

    }
}
