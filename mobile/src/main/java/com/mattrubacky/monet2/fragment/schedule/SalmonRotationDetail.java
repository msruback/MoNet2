package com.mattrubacky.monet2.fragment.schedule;

import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.SalmonRunDetail;
import com.mattrubacky.monet2.deserialized.Stage;
import com.mattrubacky.monet2.deserialized.TimePeriod;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mattr on 11/9/2017.
 */

public class SalmonRotationDetail extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_salmon_rotation_detail, container, false);
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Splatfont2.ttf");

        TextView time = (TextView) rootView.findViewById(R.id.Time);
        TextView stageName = (TextView) rootView.findViewById(R.id.StageName);

        ImageView stageImage = (ImageView) rootView.findViewById(R.id.StageImage);
        ImageView weapon1Image = (ImageView) rootView.findViewById(R.id.Weapon1Image);
        ImageView weapon2Image = (ImageView) rootView.findViewById(R.id.Weapon2Image);
        ImageView weapon3Image = (ImageView) rootView.findViewById(R.id.Weapon3Image);
        ImageView weapon4Image = (ImageView) rootView.findViewById(R.id.Weapon4Image);

        time.setTypeface(font);
        stageName.setTypeface(font);

        Bundle bundle = this.getArguments();
        SalmonRunDetail detail = bundle.getParcelable("detail");

        SimpleDateFormat sdf = new SimpleDateFormat("EEE M/d h a");
        String startText = sdf.format(detail.start*1000);
        String endText = sdf.format(detail.end*1000);
        time.setText(startText + " to " + endText);

        stageName.setText(detail.stage.name);

        ImageHandler imageHandler = new ImageHandler();
        String imageDirName = detail.stage.name.toLowerCase().replace(" ","_");
        String url = "https://app.splatoon2.nintendo.net"+detail.stage.url;

        if(imageHandler.imageExists("salmon_stage",imageDirName,getContext())){
            stageImage.setImageBitmap(imageHandler.loadImage("salmon_stage",imageDirName));
        }else{
            Picasso.with(getContext()).load(url).resize(1280,720).into(stageImage);
            imageHandler.downloadImage("salmon_stage",imageDirName,url,getContext());
        }

        if(detail.weapons.get(0)!=null){
            imageDirName = detail.weapons.get(0).name.toLowerCase().replace(" ","_");
            url = "https://app.splatoon2.nintendo.net"+detail.weapons.get(0).url;

            if(imageHandler.imageExists("weapon",imageDirName,getContext())){
                weapon1Image.setImageBitmap(imageHandler.loadImage("weapon",imageDirName));
            }else{
                Picasso.with(getContext()).load(url).into(weapon1Image);
                imageHandler.downloadImage("weapon",imageDirName,url,getContext());
            }
        }else{
            weapon1Image.setImageDrawable(getResources().getDrawable(R.drawable.weapon_mystery));
        }

        if(detail.weapons.get(1)!=null){
            imageDirName = detail.weapons.get(1).name.toLowerCase().replace(" ","_");
            url = "https://app.splatoon2.nintendo.net"+detail.weapons.get(1).url;

            if(imageHandler.imageExists("weapon",imageDirName,getContext())){
                weapon2Image.setImageBitmap(imageHandler.loadImage("weapon",imageDirName));
            }else{
                Picasso.with(getContext()).load(url).into(weapon2Image);
                imageHandler.downloadImage("weapon",imageDirName,url,getContext());
            }
        }else{
            weapon2Image.setImageDrawable(getResources().getDrawable(R.drawable.weapon_mystery));
        }

        if(detail.weapons.get(2)!=null){
            imageDirName = detail.weapons.get(2).name.toLowerCase().replace(" ","_");
            url = "https://app.splatoon2.nintendo.net"+detail.weapons.get(2).url;

            if(imageHandler.imageExists("weapon",imageDirName,getContext())){
                weapon3Image.setImageBitmap(imageHandler.loadImage("weapon",imageDirName));
            }else{
                Picasso.with(getContext()).load(url).into(weapon3Image);
                imageHandler.downloadImage("weapon",imageDirName,url,getContext());
            }
        }else{
            weapon3Image.setImageDrawable(getResources().getDrawable(R.drawable.weapon_mystery));
        }

        if(detail.weapons.get(3)!=null){
            imageDirName = detail.weapons.get(3).name.toLowerCase().replace(" ","_");
            url = "https://app.splatoon2.nintendo.net"+detail.weapons.get(3).url;

            if(imageHandler.imageExists("weapon",imageDirName,getContext())){
                weapon4Image.setImageBitmap(imageHandler.loadImage("weapon",imageDirName));
            }else{
                Picasso.with(getContext()).load(url).into(weapon4Image);
                imageHandler.downloadImage("weapon",imageDirName,url,getContext());
            }
        }else{
            weapon4Image.setImageDrawable(getResources().getDrawable(R.drawable.weapon_mystery));
        }


        return rootView;
    }
}
