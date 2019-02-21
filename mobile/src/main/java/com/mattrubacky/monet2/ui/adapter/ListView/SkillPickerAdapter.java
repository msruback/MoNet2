package com.mattrubacky.monet2.ui.adapter.ListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.data.deserialized.splatoon.Skill;
import com.mattrubacky.monet2.backend.ImageHandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mattr on 11/13/2017.
 */

public class SkillPickerAdapter extends ArrayAdapter<Skill> {
    public SkillPickerAdapter(Context context, ArrayList<Skill> input) {
        super(context, 0, input);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_skill_picker, parent, false);
        }
        Skill skill = getItem(position);

        ImageView image = convertView.findViewById(R.id.Image);
        TextView weaponName = convertView.findViewById(R.id.SkillName);
        if(skill.id ==-1) {
            weaponName.setText("Any");
            image.setImageDrawable(getContext().getResources().getDrawable(R.drawable.skill_blank));
        }else{
            weaponName.setText(skill.name);

            String url = "https://app.splatoon2.nintendo.net" + skill.url;

            ImageHandler imageHandler = new ImageHandler();
            String imageDirName = skill.name.toLowerCase().replace(" ", "_");
            if (imageHandler.imageExists("ability", imageDirName, getContext())) {
                image.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
            } else {
                Picasso.with(getContext()).load(url).into(image);
                imageHandler.downloadImage("ability", imageDirName, url, getContext());
            }
        }

        return convertView;
    }
}
