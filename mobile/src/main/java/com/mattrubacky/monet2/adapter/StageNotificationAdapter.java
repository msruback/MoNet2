package com.mattrubacky.monet2.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattrubacky.monet2.AddNotification;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.StageNotification;
import com.mattrubacky.monet2.dialog.StageNotificationPickerDialog;

import java.util.ArrayList;

/**
 * Created by mattr on 10/21/2017.
 */

public class StageNotificationAdapter extends ArrayAdapter<StageNotification> {
    StageNotificationPickerDialog dialog;
    public StageNotificationAdapter(Context context, ArrayList<StageNotification> input,StageNotificationPickerDialog dialog) {
        super(context, 0, input);
        this.dialog = dialog;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_stage_notification, parent, false);
        }
        final StageNotification notification = getItem(position);

        final Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");

        ImageView type = (ImageView) convertView.findViewById(R.id.TypeImage);

        TextView rule = (TextView) convertView.findViewById(R.id.Rule);
        TextView stage = (TextView) convertView.findViewById(R.id.Stage);

        rule.setTypeface(font);
        stage.setTypeface(font);

        rule.setText(notification.rule);
        stage.setText(notification.stage.name);

        switch(notification.type){
            case "regular":
                type.setImageDrawable(getContext().getResources().getDrawable(R.drawable.battle_regular));
                break;
            case "gachi":
                type.setImageDrawable(getContext().getResources().getDrawable(R.drawable.battle_ranked));
                break;
            case "league":
                type.setImageDrawable(getContext().getResources().getDrawable(R.drawable.battle_league));
                break;
        }

        ImageView editButton = (ImageView) convertView.findViewById(R.id.EditButton);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddNotification.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("isGear",false);
                bundle.putBoolean("isEdit",true);
                bundle.putParcelable("notification",notification);
                bundle.putInt("position",position);
                intent.putExtras(bundle);
                dialog.dismiss();
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
