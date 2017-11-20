package com.mattrubacky.monet2.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.Chunk;
import com.mattrubacky.monet2.deserialized.WeaponStats;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mattr on 11/20/2017.
 */

public class ChunkableAdapter extends RecyclerView.Adapter<ChunkableAdapter.ViewHolder>{

    private ArrayList<Chunk> input = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private View.OnClickListener onClick;

    public ChunkableAdapter(Context context, ArrayList<Chunk> input) {
        this.inflater = LayoutInflater.from(context);
        this.input = input;
        this.context = context;
    }
    @Override
    public ChunkableAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_chunkable, parent, false);
        ChunkableAdapter.ViewHolder viewHolder = new ChunkableAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ChunkableAdapter.ViewHolder holder, final int position) {
        Typeface font = Typeface.createFromAsset(context.getAssets(),"Splatfont2.ttf");
        ImageHandler imageHandler = new ImageHandler();

        final Chunk chunk = input.get(position);

        String url = "https://app.splatoon2.nintendo.net"+chunk.skill.url;
        String location = chunk.skill.name.toLowerCase().replace(" ","_");
        if(imageHandler.imageExists("ability",location,context)){
            holder.skill.setImageBitmap(imageHandler.loadImage("ability",location));
        }else{
            Picasso.with(context).load(url).into(holder.skill);
            imageHandler.downloadImage("ability",location,url,context);
        }

        holder.name.setTypeface(font);
        holder.count.setTypeface(font);
        holder.addButton.setTypeface(font);
        holder.subButton.setTypeface(font);


        holder.name.setText(chunk.skill.name);
        holder.count.setText(String.valueOf(chunk.count));

        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chunk.add();
                holder.count.setText(String.valueOf(chunk.count));
            }
        });
        holder.subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chunk.sub();
                holder.count.setText(String.valueOf(chunk.count));
            }
        });

    }

    @Override
    public int getItemCount() {
        return input.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView skill;
        TextView name,addButton,count,subButton;


        public ViewHolder(View itemView) {
            super(itemView);

            skill = (ImageView) itemView.findViewById(R.id.SkillImage);
            name = (TextView) itemView.findViewById(R.id.Name);
            count = (TextView) itemView.findViewById(R.id.Count);
            addButton = (TextView) itemView.findViewById(R.id.Add);
            subButton = (TextView) itemView.findViewById(R.id.Sub);

        }

    }

}
