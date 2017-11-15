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
import com.mattrubacky.monet2.deserialized.StageStats;
import com.mattrubacky.monet2.deserialized.WeaponStats;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mattr on 11/13/2017.
 */

public class StageAdapter extends RecyclerView.Adapter<StageAdapter.ViewHolder>{

    private ArrayList<StageStats> input;
    private LayoutInflater inflater;
    private Context context;
    private View.OnClickListener onClickListener;

    public StageAdapter(Context context, ArrayList<StageStats> input, View.OnClickListener onClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.input = input;
        this.context = context;
        this.onClickListener = onClickListener;
    }
    @Override
    public StageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_stage, parent, false);
        StageAdapter.ViewHolder viewHolder = new StageAdapter.ViewHolder(view);
        view.setOnClickListener(onClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final StageAdapter.ViewHolder holder, final int position) {
        Typeface font = Typeface.createFromAsset(context.getAssets(),"Splatfont2.ttf");
        ImageHandler imageHandler = new ImageHandler();

        StageStats stageStats = input.get(position);

        String url = "https://app.splatoon2.nintendo.net"+stageStats.stage.url;
        String location = stageStats.stage.name.toLowerCase().replace(" ","_");
        if(imageHandler.imageExists("stage",location,context)){
            holder.stage.setImageBitmap(imageHandler.loadImage("stage",location));
        }else{
            Picasso.with(context).load(url).resize(1280,720).into(holder.stage);
            imageHandler.downloadImage("stage",location,url,context);
        }

        holder.name.setText(stageStats.stage.name);
        holder.name.setTypeface(font);

    }

    @Override
    public int getItemCount() {
        return input.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView stage;
        TextView name;


        public ViewHolder(View itemView) {
            super(itemView);

            stage = (ImageView) itemView.findViewById(R.id.StageImage);
            name = (TextView) itemView.findViewById(R.id.Name);
        }

    }

}