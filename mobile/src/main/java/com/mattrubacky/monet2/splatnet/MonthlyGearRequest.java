package com.mattrubacky.monet2.splatnet;

import android.content.Context;
import android.os.Bundle;

import com.mattrubacky.monet2.deserialized.Timeline;

import java.io.IOException;
import java.util.Date;

import retrofit2.Response;

/**
 * Created by mattr on 12/10/2017.
 */

public class MonthlyGearRequest extends TimelineRequest {

    public MonthlyGearRequest(Context context){
        super(context);
    }

    @Override
    public boolean shouldUpdate(){

        long now = new Date().getTime();
        if(timeline.currentRun!=null&&timeline.currentRun.rewardGear!=null) {
            if ((timeline.currentRun.rewardGear.available * 1000) < now) {
                return true;
            }
            return false;
        }

        return true;
    }
}
