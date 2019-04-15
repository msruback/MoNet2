package com.mattrubacky.monet2.data.stats;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by mattr on 12/17/2017.
 */

public abstract class Stats {


    protected ArrayList<Integer> sort(ArrayList<Integer> data){
        if(data.size()<=1){
            return data;
        }
        if(data.size()==2){
            if(data.get(0)<=data.get(1)){
                return data;
            }else{
                int hold = data.get(0);
                data.remove(0);
                data.add(hold);
                return data;
            }
        }else{
            int pivot = data.get(0);
            ArrayList<Integer> lower = new ArrayList<>();
            ArrayList<Integer> upper = new ArrayList<>();
            for(int i=1;i<data.size();i++){
                if(pivot>data.get(i)){
                    lower.add(data.get(i));
                }else{
                    upper.add(data.get(i));
                }
            }
            ArrayList<Integer> result = sort(lower);
            result.add(pivot);
            result.addAll(sort(upper));
            return result;
        }
    }

    protected int[] calcSpread(ArrayList<Integer> data){
        int[] spread = new int[5];

        spread[0] = data.get(0);
        spread[1] = calcLowerQuartile(data);
        spread[2] = calcMedian(data);
        spread[3] = calcUpperQuartile(data);
        spread[4] = data.get(data.size()-1);

        return spread;
    }

    protected int calcMedian(ArrayList<Integer> data){
        int median,lower,upper;
        if(data.size()%2==0){
            median = data.get(data.size()/2);
        }else{
            lower = data.get(data.size()/2);
            upper = data.get((data.size()/2)+1);
            lower+= upper;
            median = lower/2;
        }

        return median;
    }
    protected int calcLowerQuartile(ArrayList<Integer> data){
        int lowerQuartile,lower,upper;
        if (data.size() % 4 == 0) {
            lowerQuartile = data.get(data.size()/4);
        }else{
            lower = data.get(data.size()/4);
            upper = data.get((data.size()/4)+1);
            lower += upper;
            lowerQuartile = lower/2;
        }
        return lowerQuartile;
    }
    protected int calcUpperQuartile(ArrayList<Integer> data){
        int upperQuartile,lower,upper;
        if (data.size() % 4 == 0) {
            upperQuartile = data.get((data.size()/2)+(data.size()/4));
        }else{

            lower = data.get((data.size()/2)+(data.size()/4));
            upper = data.get((data.size()/2)+(data.size()/4)+1);
            lower += upper;
            upperQuartile = lower/2;
        }
        return upperQuartile;
    }

}
