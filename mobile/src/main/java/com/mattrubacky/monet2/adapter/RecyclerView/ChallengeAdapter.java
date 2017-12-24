package com.mattrubacky.monet2.adapter.RecyclerView;

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

import java.util.ArrayList;

/**
 * Created by mattr on 12/24/2017.
 */

public class ChallengeAdapter extends RecyclerView.Adapter<ChallengeAdapter.ViewHolder>{

    private ArrayList<Challenge> input = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private Long paintpoints;

    public ChallengeAdapter(Context context, ArrayList<Challenge> input, Long paintpoints) {
        this.inflater = LayoutInflater.from(context);
        this.input = input;
        this.context = context;
        this.paintpoints = paintpoints;
    }
    @Override
    public ChallengeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_challenge, parent, false);
        ChallengeAdapter.ViewHolder viewHolder = new ChallengeAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ChallengeAdapter.ViewHolder holder, final int position) {
        Typeface font = Typeface.createFromAsset(context.getAssets(),"Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(context.getAssets(), "Paintball.otf");
        ImageHandler imageHandler = new ImageHandler();
        Challenge challenge = input.get(position);

        if(position==0) {

            ViewGroup.LayoutParams layoutParams = holder.progress.getLayoutParams();

            float width = paintpoints/challenge.paintpoints;

            width *= 250;
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
            holder.progress.setLayoutParams(layoutParams);

            holder.progressText.setText((paintpoints/challenge.paintpoints)*100+"%");
            holder.challengeName.setVisibility(View.GONE);
        }else{
            String url = "https://app.splatoon2.nintendo.net"+challenge.url;
            String location = challenge.name.toLowerCase().replace(" ","_");
            if(imageHandler.imageExists("challenge",location,context)){
                holder.challengeImage.setImageBitmap(imageHandler.loadImage("challenge",location));
            }else{
                Picasso.with(context).load(url).into(holder.challengeImage);
                imageHandler.downloadImage("challenge",location,url,context);
            }

            holder.progressText.setText("Complete");
            holder.challengeName.setText(challenge.name);
            holder.challengeName.setVisibility(View.VISIBLE);
        }

        holder.card.setClipToOutline(true);
        holder.progressMeter.setClipToOutline(true);

        holder.progressText.setTypeface(font);
        holder.challengeName.setTypeface(fontTitle);

    }

    @Override
    public int getItemCount() {
        return input.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout card,progressMeter,progress;
        ImageView challengeImage;
        TextView progressText,challengeName;


        public ViewHolder(View itemView) {
            super(itemView);

            card = (RelativeLayout) itemView.findViewById(R.id.card);
            progressMeter = (RelativeLayout) itemView.findViewById(R.id.meter);
            progress = (RelativeLayout) itemView.findViewById(R.id.ProgressMeter);

            challengeImage = (ImageView) itemView.findViewById(R.id.challengeImage);

            progressText = (TextView) itemView.findViewById(R.id.ProgressPercent);
            challengeName = (TextView) itemView.findViewById(R.id.challengeName);
        }

    }

}
