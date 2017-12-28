package com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.FloatProperty;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;

import java.util.Date;
import java.util.Random;

/**
 * Created by mattr on 12/24/2017.
 */

public class SoloStatCardViewHolder extends RecyclerView.ViewHolder{

    public RelativeLayout card,lowerWhisker,box,lowerBox,upperBox,upperWhisker,zigzag;
    public TextView title,minimum,lowerQuartile,median,upperQuartile,maximum;
    public ImageView product;

    private Context context;

    public SoloStatCardViewHolder(LayoutInflater inflater, ViewGroup parent, Context context) {
        super(inflater.inflate(R.layout.item_solo_stat, parent, false));

        this.context = context;

        card = (RelativeLayout) itemView.findViewById(R.id.card);
        title = (TextView) itemView.findViewById(R.id.Title);
        product = (ImageView) itemView.findViewById(R.id.product);
        zigzag = (RelativeLayout) itemView.findViewById(R.id.zigzag);

        lowerWhisker = (RelativeLayout) itemView.findViewById(R.id.LowerWhisker);
        box = (RelativeLayout) itemView.findViewById(R.id.Box);
        lowerBox = (RelativeLayout) itemView.findViewById(R.id.LowerBox);
        upperBox = (RelativeLayout) itemView.findViewById(R.id.UpperBox);
        upperWhisker = (RelativeLayout) itemView.findViewById(R.id.UpperWhisker);

        minimum = (TextView) itemView.findViewById(R.id.Minimum);
        lowerQuartile = (TextView) itemView.findViewById(R.id.LowerQuartile);
        median = (TextView) itemView.findViewById(R.id.Median);
        upperQuartile = (TextView) itemView.findViewById(R.id.UpperQuartile);
        maximum = (TextView) itemView.findViewById(R.id.Maximum);
    }
    public void manageHolder(String type, int[] stats){
        Typeface font = Typeface.createFromAsset(context.getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(context.getAssets(), "Paintball.otf");

        card.setClipToOutline(true);
        title.setTypeface(fontTitle);
        title.setText(type);

        box.setClipToOutline(true);

        minimum.setTypeface(font);
        lowerQuartile.setTypeface(font);
        median.setTypeface(font);
        upperQuartile.setTypeface(font);
        maximum.setTypeface(font);

        minimum.setText(String.valueOf(stats[0]));
        lowerQuartile.setText(String.valueOf(stats[1]));
        median.setText(String.valueOf(stats[2]));
        upperQuartile.setText(String.valueOf(stats[3]));
        maximum.setText(String.valueOf(stats[4]));

        if(stats[1]==stats[0]){
            lowerQuartile.setVisibility(View.GONE);
        }
        if(stats[2]==stats[1]){
            lowerQuartile.setVisibility(View.GONE);
        }
        if(stats[2]==stats[0]){
            median.setVisibility(View.GONE);
        }
        if(stats[3]==stats[2]){
            median.setVisibility(View.GONE);
        }
        if(stats[4]==stats[3]){
            upperQuartile.setVisibility(View.GONE);
        }
        if(stats[4]==stats[2]){
            median.setVisibility(View.GONE);
        }

        float range = stats[4] - stats[0];

        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) median.getLayoutParams();

        //Need to position the inkMedian TextView so as to line it up with the center line
        float width = (((stats[2] - stats[1])/range) * (270));
        marginLayoutParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        median.setLayoutParams(marginLayoutParams);

        ViewGroup.LayoutParams layoutParams = lowerWhisker.getLayoutParams();
        width = ((stats[1] - stats[0])/range) * (270);
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        lowerWhisker.setLayoutParams(layoutParams);

        layoutParams = box.getLayoutParams();
        width = ((stats[3] - stats[1])/range) * (270);
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        box.setLayoutParams(layoutParams);

        layoutParams = lowerBox.getLayoutParams();
        width = ((stats[2] - stats[1])/range) * (270);
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        lowerBox.setLayoutParams(layoutParams);

        layoutParams = upperBox.getLayoutParams();
        width = ((stats[3] - stats[2])/range) * (270);
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        upperBox.setLayoutParams(layoutParams);

        layoutParams = upperWhisker.getLayoutParams();
        width = ((stats[4] - stats[3])/range) * (270);
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        upperWhisker.setLayoutParams(layoutParams);
    }
}
