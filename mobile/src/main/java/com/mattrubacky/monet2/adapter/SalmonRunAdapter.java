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

import com.mattrubacky.monet2.AddRun;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.*;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by mattr on 10/21/2017.
 */

public class SalmonRunAdapter extends ArrayAdapter<SalmonRun> {
    public SalmonRunAdapter(Context context, ArrayList<SalmonRun> input) {
        super(context, 0, input);
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_salmon_run, parent, false);
        }

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Splatfont2.ttf");

        final SalmonRun salmonRun = getItem(position);

        RelativeLayout weapon1Layout = (RelativeLayout) convertView.findViewById(R.id.weapon1);
        RelativeLayout weapon2Layout = (RelativeLayout) convertView.findViewById(R.id.weapon2);
        RelativeLayout weapon3Layout = (RelativeLayout) convertView.findViewById(R.id.weapon3);
        RelativeLayout weapon4Layout = (RelativeLayout) convertView.findViewById(R.id.weapon4);


        TextView time = (TextView) convertView.findViewById(R.id.time);
        TextView stage = (TextView) convertView.findViewById(R.id.stage);

        time.setTypeface(font);
        stage.setTypeface(font);

        ImageView weapon1 = (ImageView) convertView.findViewById(R.id.Weapon1);
        ImageView weapon2 = (ImageView) convertView.findViewById(R.id.Weapon2);
        ImageView weapon3 = (ImageView) convertView.findViewById(R.id.Weapon3);
        ImageView weapon4 = (ImageView) convertView.findViewById(R.id.Weapon4);
        ImageView editButton = (ImageView) convertView.findViewById(R.id.EditButton);

        SimpleDateFormat sdf = new SimpleDateFormat("M/d h a");


        String startText = sdf.format(salmonRun.startTime);
        String endText = sdf.format(salmonRun.endTime);

        time.setText(startText + " to " + endText);
        stage.setText(salmonRun.stage);
        if(salmonRun.stage.equals("")){
            stage.setVisibility(View.GONE);
        }else{
            stage.setVisibility(View.VISIBLE);
        }


        ImageHandler imageHandler = new ImageHandler();
        if(salmonRun.weapons.get(0)!=null) {
            if(salmonRun.weapons.get(0).id==-1){
                weapon1.setImageDrawable(getContext().getResources().getDrawable(R.drawable.skill_blank));
            }else {
                String url = "https://app.splatoon2.nintendo.net" + salmonRun.weapons.get(0).url;
                String imageDirName = salmonRun.weapons.get(0).name.toLowerCase().replace(" ", "_");
                if (imageHandler.imageExists("weapon", imageDirName, getContext())) {
                    weapon1.setImageBitmap(imageHandler.loadImage("weapon", imageDirName));
                } else {
                    Picasso.with(getContext()).load(url).into(weapon1);
                    imageHandler.downloadImage("weapon", imageDirName, url, getContext());
                }
            }
        }
        if(salmonRun.weapons.get(1)!=null) {
            if(salmonRun.weapons.get(1).id==-1){
                weapon2.setImageDrawable(getContext().getResources().getDrawable(R.drawable.skill_blank));
            }else {
                String url = "https://app.splatoon2.nintendo.net" + salmonRun.weapons.get(1).url;
                String imageDirName = salmonRun.weapons.get(1).name.toLowerCase().replace(" ", "_");
                if (imageHandler.imageExists("weapon", imageDirName, getContext())) {
                    weapon2.setImageBitmap(imageHandler.loadImage("weapon", imageDirName));
                } else {
                    Picasso.with(getContext()).load(url).into(weapon2);
                    imageHandler.downloadImage("weapon", imageDirName, url, getContext());
                }
            }
        }

        if(salmonRun.weapons.get(2)!=null) {
            if(salmonRun.weapons.get(2).id==-1){
                weapon3.setImageDrawable(getContext().getResources().getDrawable(R.drawable.skill_blank));
            }else {
                String url = "https://app.splatoon2.nintendo.net" + salmonRun.weapons.get(2).url;
                String imageDirName = salmonRun.weapons.get(2).name.toLowerCase().replace(" ", "_");
                if (imageHandler.imageExists("weapon", imageDirName, getContext())) {
                    weapon3.setImageBitmap(imageHandler.loadImage("weapon", imageDirName));
                } else {
                    Picasso.with(getContext()).load(url).into(weapon3);
                    imageHandler.downloadImage("weapon", imageDirName, url, getContext());
                }
            }
        }

        if(salmonRun.weapons.get(3)!=null) {
            if(salmonRun.weapons.get(3).id==-1){
                weapon4.setImageDrawable(getContext().getResources().getDrawable(R.drawable.skill_blank));
            }else {
                String url = "https://app.splatoon2.nintendo.net" + salmonRun.weapons.get(3).url;
                String imageDirName = salmonRun.weapons.get(3).name.toLowerCase().replace(" ", "_");
                if (imageHandler.imageExists("weapon", imageDirName, getContext())) {
                    weapon4.setImageBitmap(imageHandler.loadImage("weapon", imageDirName));
                } else {
                    Picasso.with(getContext()).load(url).into(weapon4);
                    imageHandler.downloadImage("weapon", imageDirName, url, getContext());
                }
            }
        }
        if(salmonRun.weapons.get(0)==null&&salmonRun.weapons.get(1)==null&&salmonRun.weapons.get(2)==null&&salmonRun.weapons.get(3)==null){
            weapon1Layout.setVisibility(View.GONE);
            weapon2Layout.setVisibility(View.GONE);
            weapon3Layout.setVisibility(View.GONE);
            weapon4Layout.setVisibility(View.GONE);
        }else{
            weapon1Layout.setVisibility(View.VISIBLE);
            weapon2Layout.setVisibility(View.VISIBLE);
            weapon3Layout.setVisibility(View.VISIBLE);
            weapon4Layout.setVisibility(View.VISIBLE);
        }

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddRun.class);
                Bundle bundle = new Bundle();
                bundle.putString("type","edit");
                bundle.putInt("position",position);
                bundle.putParcelable("run",getItem(position));
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getContext().startActivity(intent);
            }
        });


        return convertView;
    }
}
