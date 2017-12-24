package com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.Challenge;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

/**
 * Created by mattr on 12/24/2017.
 */

public class ChallengeViewHolder extends RecyclerView.ViewHolder{

    public RelativeLayout card,progressMeter,progress;
    public ImageView challengeImage;
    public TextView progressText,challengeName;
    private Context context;

    public ChallengeViewHolder(LayoutInflater inflater, ViewGroup parent,Context context) {
        super(inflater.inflate(R.layout.item_challenge, parent, false));
        this.context = context;

        card = (RelativeLayout) itemView.findViewById(R.id.card);
        progressMeter = (RelativeLayout) itemView.findViewById(R.id.meter);
        progress = (RelativeLayout) itemView.findViewById(R.id.ProgressMeter);

        challengeImage = (ImageView) itemView.findViewById(R.id.challengeImage);

        progressText = (TextView) itemView.findViewById(R.id.ProgressPercent);
        challengeName = (TextView) itemView.findViewById(R.id.challengeName);
    }
    public void manageHolder(Challenge challenge,boolean isTop,long paintpoints){
        Typeface font = Typeface.createFromAsset(context.getAssets(),"Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(context.getAssets(), "Paintball.otf");
        ImageHandler imageHandler = new ImageHandler();

        if(isTop) {

            ViewGroup.LayoutParams layoutParams = progress.getLayoutParams();

            float width = paintpoints/challenge.paintpoints;

            width *= 250;
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
            progress.setLayoutParams(layoutParams);

            progressText.setText((paintpoints/challenge.paintpoints)*100+"%");
            challengeName.setVisibility(View.GONE);
        }else{
            String url = "https://app.splatoon2.nintendo.net"+challenge.url;
            String location = challenge.name.toLowerCase().replace(" ","_");
            if(imageHandler.imageExists("challenge",location,context)){
                challengeImage.setImageBitmap(imageHandler.loadImage("challenge",location));
            }else{
                Picasso.with(context).load(url).into(challengeImage);
                imageHandler.downloadImage("challenge",location,url,context);
            }

            progressText.setText("Complete");
            challengeName.setText(challenge.name);challengeName.setVisibility(View.VISIBLE);
        }

        card.setClipToOutline(true);
        progressMeter.setClipToOutline(true);

        progressText.setTypeface(font);
        challengeName.setTypeface(fontTitle);
    }
}
