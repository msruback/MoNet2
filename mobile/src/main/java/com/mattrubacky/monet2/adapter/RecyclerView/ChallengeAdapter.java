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
import com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders.ChallengeViewHolder;
import com.mattrubacky.monet2.deserialized.Challenge;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mattr on 12/24/2017.
 */

public class ChallengeAdapter extends RecyclerView.Adapter<ChallengeViewHolder>{

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
    public ChallengeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ChallengeViewHolder viewHolder = new ChallengeViewHolder(inflater,parent,context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ChallengeViewHolder holder, final int position) {

        Challenge challenge = input.get(position);

        boolean isTop = (position==0);
        holder.manageHolder(challenge,isTop,paintpoints);
    }

    @Override
    public int getItemCount() {
        return input.size();
    }

}
