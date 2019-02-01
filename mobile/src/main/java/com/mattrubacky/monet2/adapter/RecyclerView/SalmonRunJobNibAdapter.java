package com.mattrubacky.monet2.adapter.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders.CapsuleLeftNibViewHolder;
import com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders.CapsuleRightNibViewHolder;
import com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders.PromotionNibViewHolder;
import com.mattrubacky.monet2.deserialized.splatoon.RewardGear;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 11/10/2018.
 */

public class SalmonRunJobNibAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<Integer> rewards;
    private int promotion;
    private String grade;
    private RewardGear rewardGear;

    public SalmonRunJobNibAdapter(Context context, ArrayList<Integer> rewards, boolean promotion,String grade, RewardGear rewardGear){
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.rewardGear = rewardGear;
        this.rewards = rewards;
        this.grade = grade;

        if(promotion){
            this.promotion = 1;
        }else{
            this.promotion = 0;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
                return new CapsuleLeftNibViewHolder(inflater,parent,context);
            case 1:
                return new CapsuleRightNibViewHolder(inflater,parent,context);
            case 2:
                return new PromotionNibViewHolder(inflater,parent,context);
            default:
                return new CapsuleLeftNibViewHolder(inflater,parent,context);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderab, int position) {
        if(position<rewards.size()) {
            if (position%2 == 0) {
                CapsuleLeftNibViewHolder holder = (CapsuleLeftNibViewHolder) holderab;
                switch (rewards.get(position)){
                    case 0:
                        holder.manageHolder(false);
                        break;
                    case 1:
                        holder.manageHolder(true);
                        break;
                    case 2:
                        holder.manageHolder(rewardGear);
                        break;
                }
            }else{
                CapsuleRightNibViewHolder holder = (CapsuleRightNibViewHolder) holderab;
                switch (rewards.get(position)){
                    case 0:
                        holder.manageHolder(false);
                        break;
                    case 1:
                        holder.manageHolder(true);
                        break;
                    case 2:
                        holder.manageHolder(rewardGear);
                        break;
                }
            }
        }else{
            PromotionNibViewHolder holder = (PromotionNibViewHolder) holderab;
            holder.manageHolder(grade);
        }
    }

    @Override
    public int getItemCount() {
        return rewards.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position<rewards.size()){
            return position%2;
        }else{
            return 2;
        }
    }


}
