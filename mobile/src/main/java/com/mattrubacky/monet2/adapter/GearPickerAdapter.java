package com.mattrubacky.monet2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.Gear;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mattr on 10/21/2017.
 */

public class GearPickerAdapter extends ArrayAdapter<Gear> {
    public GearPickerAdapter(Context context, ArrayList<Gear> input) {
        super(context, 0, input);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_weapon_picker, parent, false);
        }
        Gear gear = getItem(position);

        RelativeLayout imageBackground = (RelativeLayout) convertView.findViewById(R.id.image);

        switch (gear.kind) {
            case "head":
                imageBackground.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.head));
                break;
            case "clothes":
                imageBackground.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.clothes));
                break;
            case "shoes":
                imageBackground.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.shoes));
                break;
        }

        ImageView image = (ImageView) convertView.findViewById(R.id.Image);
        TextView weaponName = (TextView) convertView.findViewById(R.id.WeaponName);

        weaponName.setText(gear.name);

        String url = "https://app.splatoon2.nintendo.net"+gear.url;

        ImageHandler imageHandler = new ImageHandler();
        String imageDirName = gear.name.toLowerCase().replace(" ","_");
        if(imageHandler.imageExists("gear",imageDirName,getContext())){
            image.setImageBitmap(imageHandler.loadImage("gear",imageDirName));
        }else{
            Picasso.with(getContext()).load(url).into(image);
            imageHandler.downloadImage("gear",imageDirName,url,getContext());
        }

        return convertView;
    }
}