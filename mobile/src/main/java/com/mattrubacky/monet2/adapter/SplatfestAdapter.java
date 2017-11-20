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
import com.mattrubacky.monet2.deserialized.PastSplatfest;
import com.mattrubacky.monet2.deserialized.Splatfest;
import com.mattrubacky.monet2.deserialized.StageStats;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mattr on 11/20/2017.
 */

public class SplatfestAdapter extends RecyclerView.Adapter<SplatfestAdapter.ViewHolder>{

    private ArrayList<Splatfest> input;
    private LayoutInflater inflater;
    private Context context;
    private View.OnClickListener onClickListener;

    public SplatfestAdapter(Context context, PastSplatfest splatfests, View.OnClickListener onClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.input = splatfests.splatfests;
        this.context = context;
        this.onClickListener = onClickListener;
    }
    @Override
    public SplatfestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_stage, parent, false);
        SplatfestAdapter.ViewHolder viewHolder = new SplatfestAdapter.ViewHolder(view);
        view.setOnClickListener(onClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SplatfestAdapter.ViewHolder holder, final int position) {
        Typeface font = Typeface.createFromAsset(context.getAssets(),"Splatfont2.ttf");
        ImageHandler imageHandler = new ImageHandler();

        Splatfest splatfest = input.get(position);

        String location = String.valueOf(splatfest.id);
        String url = "https://app.splatoon2.nintendo.net"+splatfest.images.panel;
        if(imageHandler.imageExists("splatfest",location,context)){
            holder.panel.setImageBitmap(imageHandler.loadImage("splatfest",location));
        }else{
            Picasso.with(context).load(url).into(holder.panel);
            imageHandler.downloadImage("splatfest",location,url,context);
        }

        holder.name.setText(splatfest.names.alpha + " VS. "+splatfest.names.bravo);
        holder.name.setTypeface(font);

    }

    @Override
    public int getItemCount() {
        return input.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView panel;
        TextView name;


        public ViewHolder(View itemView) {
            super(itemView);

            panel = (ImageView) itemView.findViewById(R.id.StageImage);
            name = (TextView) itemView.findViewById(R.id.Name);
        }

    }

}
