package com.mattrubacky.monet2.ui.adapter.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders.NoStatViewHolder;
import com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders.PlayerGeneralStatViewHolder;
import com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders.SoloStatCardViewHolder;
import com.mattrubacky.monet2.data.deserialized.splatoon.Record;
import com.mattrubacky.monet2.data.stats.PlayerStats;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==0){
            return new PlayerGeneralStatViewHolder(inflater,parent,context);
        }else if (viewType==5){
            return new NoStatViewHolder(inflater,parent,context);
        }else{
            return new SoloStatCardViewHolder(inflater,parent,context);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holderAb, final int position) {
        float rotation = (float) (random.nextInt(200)/100.0);
        if(position%2==0){
            rotation*=-1;
        }
        if(holderAb.getItemViewType()==0){
            PlayerGeneralStatViewHolder holder = (PlayerGeneralStatViewHolder)holderAb;
            holder.manageHolder(stats,records);
            holder.card.setRotation(rotation);
            holder.card.setBackgroundTintList(context.getResources().getColorStateList(R.color.favColorGreen));
            holder.inkTitle.setBackgroundTintList(context.getResources().getColorStateList(R.color.favColorYellow));
            holder.firstPlayedTitle.setBackgroundTintList(context.getResources().getColorStateList(R.color.favColorYellow));
            holder.lastPlayedTitle.setBackgroundTintList(context.getResources().getColorStateList(R.color.favColorYellow));
        }else if(holderAb.getItemViewType()==5){
            NoStatViewHolder holder = (NoStatViewHolder) holderAb;
            holder.manageHolder();
            holder.card.setRotation(rotation);
            holder.card.setBackgroundTintList(context.getResources().getColorStateList(R.color.favColorGreen));
            holder.product.setImageTintList(context.getResources().getColorStateList(R.color.darkgrey));
        }else{
            SoloStatCardViewHolder holder = (SoloStatCardViewHolder) holderAb;
            holder.card.setRotation(rotation);
            holder.card.setBackgroundTintList(context.getResources().getColorStateList(R.color.favColorGreen));
            holder.title.setBackgroundTintList(context.getResources().getColorStateList(R.color.favColorYellow));
            holder.product.setImageTintList(context.getResources().getColorStateList(R.color.darkgrey));
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
