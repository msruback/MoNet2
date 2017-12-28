package com.mattrubacky.monet2.adapter.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders.NoStatViewHolder;
import com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders.PlayerGeneralStatViewHolder;
import com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders.SoloStatCardViewHolder;
import com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders.StageViewHolder;
import com.mattrubacky.monet2.deserialized.Record;
import com.mattrubacky.monet2.helper.PlayerStats;
import com.mattrubacky.monet2.helper.StageStats;
import com.mattrubacky.monet2.notifications.Notification;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by mattr on 12/28/2017.
 */

public class PlayerStatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<String> input;
    private LayoutInflater inflater;
    private Context context;
    PlayerStats stats;
    Record records;
    private Random random;

    public PlayerStatAdapter(Context context, PlayerStats stats, Record records) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.stats = stats;
        this.records = records;
        random =  new Random();
        random.setSeed(new Date().getTime());
        input = new ArrayList<>();
        input.add("general");
        boolean hasStats = false;
        if(stats.inkStats[0]!=stats.inkStats[4]){
            hasStats = true;
            input.add("ink");
        }
        if(stats.killStats[0]!=stats.killStats[4]){
            hasStats = true;
            input.add("kill");
        }
        if(stats.deathStats[0]!=stats.deathStats[4]){
            hasStats = true;
            input.add("death");
        }
        if(stats.specialStats[0]!=stats.specialStats[4]){
            hasStats = true;
            input.add("special");
        }
        if(!hasStats){
            input.add("no");
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==0){
            return new PlayerGeneralStatViewHolder(inflater,parent,context);
        }else if (viewType==5){
            return new NoStatViewHolder(inflater,parent,context);
        }else{
            return new SoloStatCardViewHolder(inflater,parent,context);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holderAb, final int position) {
        float rotation = (float) (random.nextInt(400)/100.0)-2;
        if(holderAb.getItemViewType()==0){
            PlayerGeneralStatViewHolder holder = (PlayerGeneralStatViewHolder) holderAb;
            holder.manageHolder(stats,records);
            holder.card.setRotation(rotation);
        }else if(holderAb.getItemViewType()==5){
            NoStatViewHolder holder = (NoStatViewHolder) holderAb;
            holder.manageHolder();
            holder.card.setRotation(rotation);
        }else{
            SoloStatCardViewHolder holder = (SoloStatCardViewHolder) holderAb;
            holder.card.setRotation(rotation);
            switch(holder.getItemViewType()){
                case 1:
                    holder.manageHolder(context.getString(R.string.inkspread),stats.inkStats);
                    break;
                case 2:
                    holder.manageHolder(context.getString(R.string.killspread),stats.killStats);
                    break;
                case 3:
                    holder.manageHolder(context.getString(R.string.deathspread),stats.deathStats);
                    break;
                case 4:
                    holder.manageHolder(context.getString(R.string.specialspread),stats.specialStats);
            }
        }
    }

    @Override
    public int getItemCount() {
        return input.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (input.get(position)){
            case "general":
                return 0;
            case "ink":
                return 1;
            case "kill":
                return 2;
            case "death":
                return 3;
            case "special":
                return 4;
            default:
                return 5;
        }
    }
}
