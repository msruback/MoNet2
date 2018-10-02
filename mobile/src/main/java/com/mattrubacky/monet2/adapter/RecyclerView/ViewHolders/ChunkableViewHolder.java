package com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.splatoon.Chunk;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

/**
 * Created by mattr on 12/24/2017.
 */

public class ChunkableViewHolder extends RecyclerView.ViewHolder{

    public ImageView skill;
    public TextView name,addButton,count,subButton;

    private Context context;

    public ChunkableViewHolder(LayoutInflater inflater, ViewGroup parent, Context context) {
        super(inflater.inflate(R.layout.item_chunkable, parent, false));

        this.context = context;

        skill = (ImageView) itemView.findViewById(R.id.SkillImage);
        name = (TextView) itemView.findViewById(R.id.Name);
        count = (TextView) itemView.findViewById(R.id.Count);
        addButton = (TextView) itemView.findViewById(R.id.Add);
        subButton = (TextView) itemView.findViewById(R.id.Sub);
    }
    public void manageHolder(final Chunk chunk){
        Typeface font = Typeface.createFromAsset(context.getAssets(),"Splatfont2.ttf");
        ImageHandler imageHandler = new ImageHandler();


        String url = "https://app.splatoon2.nintendo.net"+chunk.skill.url;
        String location = chunk.skill.name.toLowerCase().replace(" ","_");
        if(imageHandler.imageExists("ability",location,context)){
            skill.setImageBitmap(imageHandler.loadImage("ability",location));
        }else{
            Picasso.with(context).load(url).into(skill);
            imageHandler.downloadImage("ability",location,url,context);
        }

        name.setTypeface(font);
        count.setTypeface(font);
        addButton.setTypeface(font);
        subButton.setTypeface(font);


        name.setText(chunk.skill.name);
        count.setText(String.valueOf(chunk.count));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chunk.add();
                count.setText(String.valueOf(chunk.count));
            }
        });
        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chunk.sub();
                count.setText(String.valueOf(chunk.count));
            }
        });
    }
}