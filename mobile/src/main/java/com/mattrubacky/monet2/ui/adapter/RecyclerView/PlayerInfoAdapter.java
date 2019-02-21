package com.mattrubacky.monet2.ui.adapter.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders.PlayerInfoViewHolder;
import com.mattrubacky.monet2.data.deserialized.splatoon.Battle;
import com.mattrubacky.monet2.data.deserialized.splatoon.Player;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

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
        return new PlayerInfoViewHolder(inflater,parent,context);
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