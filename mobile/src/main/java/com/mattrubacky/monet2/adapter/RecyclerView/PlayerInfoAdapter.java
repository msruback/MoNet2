package com.mattrubacky.monet2.adapter.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders.PlayerInfoViewHolder;
import com.mattrubacky.monet2.deserialized.Battle;
import com.mattrubacky.monet2.deserialized.Player;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mattr on 11/1/2017.
 */

    public class PlayerInfoAdapter extends RecyclerView.Adapter<PlayerInfoViewHolder>{

    private ArrayList<Player> input = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private RecyclerView listView;
    private boolean isAlly;
    private Battle battle;

    public PlayerInfoAdapter(Context context, ArrayList<Player> input,RecyclerView listView,Battle battle,boolean isAlly) {
        this.inflater = LayoutInflater.from(context);
        this.input = input;
        this.context = context;
        this.listView = listView;
        this.isAlly = isAlly;
        this.battle = battle;
    }
    @Override
    public PlayerInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PlayerInfoViewHolder viewHolder = new PlayerInfoViewHolder(inflater,parent,context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PlayerInfoViewHolder holder, final int position) {
        Player player = input.get(position);
        holder.manageHolder(player,battle,isAlly);
    }

    @Override
    public int getItemCount() {
        return input.size();
    }

}