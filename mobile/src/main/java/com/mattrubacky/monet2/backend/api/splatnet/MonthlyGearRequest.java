package com.mattrubacky.monet2.backend.api.splatnet;

import android.content.Context;

import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.io.IOException;
import java.util.Date;

import retrofit2.Response;

/**
 * Created by mattr on 12/10/2017.
 */

public class MonthlyGearRequest extends TimelineRequest {

    SplatnetSQLManager database;

    public MonthlyGearRequest(Context context){
        super(context);
        database = new SplatnetSQLManager(context);
    }

    @Override
    protected void manageResponse(Response response) throws IOException, SplatnetUnauthorizedException, SplatnetMaintenanceException {
        super.manageResponse(response);
    }

    @Override
    public boolean shouldUpdate(){
        long now = new Date().getTime();
//        if(timeline.currentRun!=null&&timeline.currentRun.rewardGear!=null) {
//            if ((timeline.currentRun.rewardGear.available * 1000) < now) {
//                return true;
//
//            }
//            return false;
//        }

        return true;
    }
}
