package com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.adapter.RecyclerView.SalmonRunJobNibAdapter;
import com.mattrubacky.monet2.deserialized.splatoon.CoopResult;
import com.mattrubacky.monet2.deserialized.splatoon.RewardGear;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 11/5/2018.
 */

public class SalmonJobViewHolder extends RecyclerView.ViewHolder{

    private Context context;
    private TextView wave1,wave2,wave3,result;
    private RecyclerView nibHolder;

    public SalmonJobViewHolder(LayoutInflater inflater, ViewGroup parent, Context context) {
        super(inflater.inflate(R.layout.item_coop_result, parent, false));

        this.context = context;
        wave1 = itemView.findViewById(R.id.wave1);
        wave2 = itemView.findViewById(R.id.wave2);
        wave3 = itemView.findViewById(R.id.wave3);
        result = itemView.findViewById(R.id.result);
        nibHolder = itemView.findViewById(R.id.nibs);

    }

    public void manageHolder(CoopResult job, RewardGear rewardGear,int preMoney) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "Splatfont2.ttf");
        ArrayList<Integer> capsules = new ArrayList<>();
        if(preMoney<1200){
            capsules.addAll(calcRewardsBelow(preMoney,job.money));
            if (preMoney+job.money>1200) {
                capsules.addAll(calcRewardsAbove(0,(job.money-(1200-preMoney))));
            }
        }
        if(preMoney>1200){
            capsules.addAll(calcRewardsAbove(preMoney-1200,job.money));
        }
        String grade ="";
        SalmonRunJobNibAdapter nibAdapter;
        if(job.gradePoint%100<(job.gradePoint+job.gradePointDelta)%100){
            grade = job.grade.name;
            nibAdapter = new SalmonRunJobNibAdapter(context,capsules,true,grade,rewardGear);
        }else{
            nibAdapter = new SalmonRunJobNibAdapter(context,capsules,false,grade,rewardGear);
        }

        nibHolder.setAdapter(nibAdapter);
        nibHolder.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));

        wave1.setTypeface(font);
        wave2.setTypeface(font);
        wave3.setTypeface(font);
        result.setTypeface(font);

        if(job.waves.size()>0){
            wave1.setText(job.waves.get(0).waterLevel.getName(context));
            if(job.waves.size()>1){
                wave2.setText(job.waves.get(1).waterLevel.getName(context));
                if(job.waves.size()>2){
                    wave3.setText(job.waves.get(2).waterLevel.getName(context));
                }
            }
        }

        if(job.jobResult.isClear){
            result.setTextColor(context.getResources().getColor(R.color.salmonAccent));
            result.setText("✔");
        }else{
            result.setTextColor(context.getResources().getColor(R.color.fail));
            result.setText(String.valueOf(job.jobResult.failureWave));
        }
    }
    private ArrayList<Integer> calcRewardsAbove(int offset,int jobScore){
        ArrayList<Integer> rewards = new ArrayList<>();
        int extraOldClothes = offset/400;
        int extraClothes;
        int numOldClothes = (offset+jobScore)/400;
        int numClothes;
        if((offset+jobScore)>200){
            numClothes = (offset+jobScore+200)/400;
        }else{
            numClothes = (offset+jobScore)/200;
        }
        if(offset>200){
            extraClothes = (offset+200)/400;
        }else{
            extraClothes = offset/200;
        }
        for(int i=0;i<(numClothes-extraClothes);i++){
            rewards.add(2);
        }
        for(int i=0;i<(numOldClothes-extraOldClothes);i++){
            rewards.add(0);
        }
        return rewards;
    }

    private ArrayList<Integer> calcRewardsBelow(int preP,int jobScore){
        ArrayList<Integer> rewards = new ArrayList<>();
        switch(preP/100){
            case 0:
                if((preP+jobScore)>=100){
                    rewards.add(0);
                }
            case 1:
                if((preP+jobScore)>=200){
                    rewards.add(2);
                }
            case 2:
                if((preP+jobScore)>=300){
                    rewards.add(0);
                }
            case 3:
                if((preP+jobScore)>=400){
                    rewards.add(0);
                }
            case 4:
                if((preP+jobScore)>=500){
                    rewards.add(0);
                }
            case 5:
                if((preP+jobScore)>=600){
                    rewards.add(1);
                }
            case 6:
                if((preP+jobScore)>=700){
                    rewards.add(0);
                }
            case 7:
                if((preP+jobScore)>=800){
                    rewards.add(2);
                }
            case 8:
                if((preP+jobScore)>=900){
                    rewards.add(0);
                }
            case 9:
                if((preP+jobScore)>=1000){
                    rewards.add(0);
                }
            case 10:
                if((preP+jobScore)>=1100){
                    rewards.add(0);
                }
            case 11:
                if((preP+jobScore)>=1200){
                    rewards.add(1);
                }
        }
        return rewards;
    }
}
