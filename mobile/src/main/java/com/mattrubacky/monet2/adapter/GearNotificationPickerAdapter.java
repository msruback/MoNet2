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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.AddNotification;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.GearNotification;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mattr on 10/21/2017.
 *
 * This adapter populates the list of GearNotifications in GearNotificationPickerDialog
 */

public class GearNotificationPickerAdapter extends ArrayAdapter<GearNotification> {
    public GearNotificationPickerAdapter(Context context, ArrayList<GearNotification> input) {
        super(context, 0, input);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_gear_notification, parent, false);
        }
        final GearNotification notification = getItem(position);
        final Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");

        RelativeLayout skillLayout = (RelativeLayout) convertView.findViewById(R.id.skillImage);

        TextView gearName = (TextView) convertView.findViewById(R.id.GearName);

        ImageView gearImage = (ImageView) convertView.findViewById(R.id.GearImage);
        ImageView skillImage = (ImageView) convertView.findViewById(R.id.SkillImage);
        ImageView editButton = (ImageView) convertView.findViewById(R.id.EditButton);

        gearName.setTypeface(font);
        gearName.setText(notification.gear.name);

        ImageHandler imageHandler = new ImageHandler();

        String url = "https://app.splatoon2.nintendo.net"+notification.gear.url;
        String location = notification.gear.name.toLowerCase().replace(" ","_");
        if(imageHandler.imageExists("gear",location,getContext())){
            gearImage.setImageBitmap(imageHandler.loadImage("gear",location));
        }else{
            Picasso.with(getContext()).load(url).into(gearImage);
            imageHandler.downloadImage("gear",location,url,getContext());
        }
        if(notification.skill!=null&&notification.skill.id!=-1) {
            url = "https://app.splatoon2.nintendo.net" + notification.skill.url;
            location = notification.skill.name.toLowerCase().replace(" ", "_");
            if (imageHandler.imageExists("ability", location, getContext())) {
                skillImage.setImageBitmap(imageHandler.loadImage("ability", location));
            } else {
                Picasso.with(getContext()).load(url).into(skillImage);
                imageHandler.downloadImage("ability", location, url, getContext());
            }
        }else{
            skillImage.setImageDrawable(getContext().getResources().getDrawable(R.drawable.skill_blank));
        }


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddNotification.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("isGear",true);
                bundle.putBoolean("isEdit",true);
                bundle.putParcelable("notification",notification);
                intent.putExtras(bundle);
                getContext().startActivity(intent);
            }
        });


        return convertView;
    }
}
