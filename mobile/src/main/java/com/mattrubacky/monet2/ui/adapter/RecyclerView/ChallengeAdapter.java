package com.mattrubacky.monet2.ui.adapter.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders.ChallengeViewHolder;
import com.mattrubacky.monet2.data.deserialized.splatoon.Challenge;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 12/24/2017.
 */

public class ChallengeAdapter extends RecyclerView.Adapter<ChallengeViewHolder>{

    private ArrayList<Challenge> input;
    private LayoutInflater inflater;
    private Context context;
    private Long paintpoints;

    public ChallengeAdapter(Context context, ArrayList<Challenge> input, Long paintpoints) {
        this.inflater = LayoutInflater.from(context);
        this.input = input;
        this.context = context;
        this.paintpoints = paintpoints;
    }

    @NonNull
    @Override
    public ChallengeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChallengeViewHolder(inflater,parent,context);
    }

    @Override
    public void onBindViewHolder(@NonNull final ChallengeViewHolder holder, final int position) {

        Challenge challenge = input.get(position);

        boolean isTop = (position==0);
        holder.manageHolder(challenge,isTop,paintpoints);
    }

    @Override
    public int getItemCount() {
        return input.size();
    }

}
