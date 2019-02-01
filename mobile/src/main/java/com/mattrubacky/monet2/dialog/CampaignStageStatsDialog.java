package com.mattrubacky.monet2.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.adapter.ListView.CampaignStageStatsAdapter;
import com.mattrubacky.monet2.deserialized.splatoon.CampaignStageInfo;
import com.mattrubacky.monet2.deserialized.splatoon.CampaignWeapon;

import java.util.ArrayList;

/**
 * Created by mattr on 12/11/2017.
 */

public class CampaignStageStatsDialog extends Dialog {
    private CampaignStageInfo info;
    private ArrayList<CampaignWeapon> weapons;
    public CampaignStageStatsDialog(Activity activity, CampaignStageInfo info, ArrayList<CampaignWeapon> weapons) {
        super(activity);
        this.weapons = weapons;
        this.info = info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_campaign);

        RelativeLayout card = findViewById(R.id.dialogCard);
        card.setClipToOutline(true);

        ListView list = findViewById(R.id.ItemList);

        CampaignStageStatsAdapter campaignStageStatsAdapter = new CampaignStageStatsAdapter(getContext(),weapons,info);

        list.setAdapter(campaignStageStatsAdapter);
    }
}
