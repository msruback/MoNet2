package com.mattrubacky.monet2.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.ui.activities.BattleInfo;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.ui.adapter.ListView.SplatfestBattleAdapter;
import com.mattrubacky.monet2.data.deserialized.splatoon.Battle;
import com.mattrubacky.monet2.data.deserialized.splatoon.Splatfest;
import com.mattrubacky.monet2.data.deserialized.splatoon.SplatfestColor;

import java.util.ArrayList;

/**
 * Created by mattr on 11/16/2017.
 */

public class SplatfestBattleDialog extends Dialog {
    ArrayList<Battle> battles;
    SplatfestColor color,otherColor;
    Splatfest splatfest;

    public SplatfestBattleDialog(Activity activity, ArrayList<Battle> battles, Splatfest splatfest,SplatfestColor color,SplatfestColor otherColor) {
        super(activity);
        this.battles = battles;
        this.color = color;
        this.otherColor = otherColor;
        this.splatfest = splatfest;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(getContext().getResources().getColor(R.color.transparent)));
        setContentView(R.layout.dialog_battle_list);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(getContext().getAssets(), "Paintball.otf");

        RelativeLayout hook = findViewById(R.id.hook);
        RelativeLayout card = findViewById(R.id.dialogCard);
        RelativeLayout bar = findViewById(R.id.bar);

        card.setClipToOutline(true);

        TextView title = findViewById(R.id.title);

        ListView list = findViewById(R.id.ItemList);

        title.setTypeface(fontTitle);

        hook.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color.getColor())));
        bar.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color.getColor())));

        SplatfestBattleAdapter adapter = new SplatfestBattleAdapter(getContext(),battles,otherColor);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), BattleInfo.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("battle",battles.get(position));
                bundle.putParcelable("splatfest",splatfest);
                intent.putExtras(bundle);
                getContext().startActivity(intent);
            }
        });


    }
}
