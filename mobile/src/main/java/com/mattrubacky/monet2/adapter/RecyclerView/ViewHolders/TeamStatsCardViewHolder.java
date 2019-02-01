package com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 12/24/2017.
 */

public class TeamStatsCardViewHolder extends RecyclerView.ViewHolder{

    public RelativeLayout card,lowerWhisker,box,lowerBox,upperBox,upperWhisker,player,zigzag;
    public TextView title,minimum,lowerQuartile,median,upperQuartile,maximum,button;
    public ImageView product;

    private Context context;

    private String type;
    private int[]user,team;
    private float average;

    public TeamStatsCardViewHolder(LayoutInflater inflater, ViewGroup parent, Context context) {
        super(inflater.inflate(R.layout.item_team_stats, parent, false));
        this.context = context;

        card = itemView.findViewById(R.id.card);
        title = itemView.findViewById(R.id.Title);
        product = itemView.findViewById(R.id.product);
        zigzag = itemView.findViewById(R.id.zigzag);

        lowerWhisker = itemView.findViewById(R.id.LowerWhisker);
        box = itemView.findViewById(R.id.Box);
        lowerBox = itemView.findViewById(R.id.LowerBox);
        upperBox = itemView.findViewById(R.id.UpperBox);
        upperWhisker = itemView.findViewById(R.id.UpperWhisker);

        minimum = itemView.findViewById(R.id.Minimum);
        lowerQuartile = itemView.findViewById(R.id.LowerQuartile);
        median = itemView.findViewById(R.id.Median);
        upperQuartile = itemView.findViewById(R.id.UpperQuartile);
        maximum = itemView.findViewById(R.id.Maximum);

        player = itemView.findViewById(R.id.Player);
        button = itemView.findViewById(R.id.Button);
    }
    public void manageHolder(String type,int[]user,int[]team,float average){
        this.type = type;
        this.user = user;
        this.team = team;
        this.average = average;
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
        setSolo();
    }
    public void setSolo(){

        player.setVisibility(View.GONE);

        minimum.setText(String.valueOf(user[0]));
        lowerQuartile.setText(String.valueOf(user[1]));
        median.setText(String.valueOf(user[2]));
        upperQuartile.setText(String.valueOf(user[3]));
        maximum.setText(String.valueOf(user[4]));

        if(user[1]==user[0]){
            lowerQuartile.setVisibility(View.GONE);
        }
        if(user[2]==user[1]){
            lowerQuartile.setVisibility(View.GONE);
        }
        if(user[2]==user[0]){
            median.setVisibility(View.GONE);
        }
        if(user[3]==user[2]){
            median.setVisibility(View.GONE);
        }
        if(user[4]==user[3]){
            upperQuartile.setVisibility(View.GONE);
        }
        if(user[4]==user[2]){
            median.setVisibility(View.GONE);
        }

        float range = user[4] - user[0];

        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) median.getLayoutParams();

        //Need to position the inkMedian TextView so as to line it up with the center line
        float width = (((user[2] - user[1])/range) * (270));
        marginLayoutParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        median.setLayoutParams(marginLayoutParams);

        ViewGroup.LayoutParams layoutParams = lowerWhisker.getLayoutParams();
        width = ((user[1] - user[0])/range) * (270);
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        lowerWhisker.setLayoutParams(layoutParams);

        layoutParams = box.getLayoutParams();
        width = ((user[3] - user[1])/range) * (270);
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        box.setLayoutParams(layoutParams);

        layoutParams = lowerBox.getLayoutParams();
        width = ((user[2] - user[1])/range) * (270);
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        lowerBox.setLayoutParams(layoutParams);

        layoutParams = upperBox.getLayoutParams();
        width = ((user[3] - user[2])/range) * (270);
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        upperBox.setLayoutParams(layoutParams);

        layoutParams = upperWhisker.getLayoutParams();
        width = ((user[4] - user[3])/range) * (270);
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        upperWhisker.setLayoutParams(layoutParams);

        button.setText(context.getResources().getString(R.string.solo));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTeam();
            }
        });
    }
    public void setTeam(){

        player.setVisibility(View.VISIBLE);

        minimum.setText(String.valueOf(team[0]));
        lowerQuartile.setText(String.valueOf(team[1]));
        median.setText(String.valueOf(team[2]));
        upperQuartile.setText(String.valueOf(team[3]));
        maximum.setText(String.valueOf(team[4]));

        if(team[1]==team[0]){
            lowerQuartile.setVisibility(View.GONE);
        }
        if(team[2]==team[1]){
            lowerQuartile.setVisibility(View.GONE);
        }
        if(team[2]==team[0]){
            median.setVisibility(View.GONE);
        }
        if(team[3]==team[2]){
            median.setVisibility(View.GONE);
        }
        if(team[4]==team[3]){
            upperQuartile.setVisibility(View.GONE);
        }
        if(team[4]==team[2]){
            median.setVisibility(View.GONE);
        }

        float range = team[4] - team[0];

        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) median.getLayoutParams();

        //Need to position the inkMedian TextView so as to line it up with the center line
        float width = (((team[2] - team[1])/range) * (270));
        marginLayoutParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        median.setLayoutParams(marginLayoutParams);

        ViewGroup.LayoutParams layoutParams = lowerWhisker.getLayoutParams();
        width = ((team[1] - team[0])/range) * (270);
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        lowerWhisker.setLayoutParams(layoutParams);

        layoutParams = box.getLayoutParams();
        width = ((team[3] - team[1])/range) * (270);
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        box.setLayoutParams(layoutParams);

        layoutParams = lowerBox.getLayoutParams();
        width = ((team[2] - team[1])/range) * (270);
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        lowerBox.setLayoutParams(layoutParams);

        layoutParams = upperBox.getLayoutParams();
        width = ((team[3] - team[2])/range) * (270);
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        upperBox.setLayoutParams(layoutParams);

        layoutParams = upperWhisker.getLayoutParams();
        width = ((team[4] - team[3])/range) * (270);
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        upperWhisker.setLayoutParams(layoutParams);

        marginLayoutParams = (ViewGroup.MarginLayoutParams) player.getLayoutParams();
        width = ((average - team[0])/range) * (270);
        marginLayoutParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        player.setLayoutParams(marginLayoutParams);

        button.setText(context.getResources().getString(R.string.team));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSolo();
            }
        });
    }
}
