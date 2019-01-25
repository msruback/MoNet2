package com.mattrubacky.monet2.api.splatnet;

import android.content.Context;
import android.os.Bundle;

import com.mattrubacky.monet2.deserialized.splatoon.Schedules;
import com.mattrubacky.monet2.deserialized.splatoon.Stage;
import com.mattrubacky.monet2.deserialized.splatoon.TimePeriod;
import com.mattrubacky.monet2.rooms.SplatnetDatabase;
import com.mattrubacky.monet2.rooms.entity.TimePeriodRoom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Response;

/**
 * Created by mattr on 12/6/2017.
 */

public class SchedulesRequest extends SplatnetRequest {

    private Context context;
    private ActiveSplatfestRequest splatfestRequest;

    public SchedulesRequest(Context context){
        this.context = context;
        splatfestRequest = new ActiveSplatfestRequest(context);
    }

    @Override
    protected void manageResponse(Response response) throws IOException, SplatnetUnauthorizedException,SplatnetMaintenanceException {
        Schedules schedules = (Schedules) response.body();
        if(schedules.regular!=null) {
            for(TimePeriod timePeriod :schedules.regular){
                SplatnetDatabase.getInstance(context).getTimePeriodDao().insert(timePeriod.toRoom());
                SplatnetDatabase.getInstance(context).getStageDao().insert(timePeriod.a.toRoom());
                SplatnetDatabase.getInstance(context).getStageDao().insert(timePeriod.b.toRoom());
            }
        }
        if(schedules.ranked!=null) {
            for(TimePeriod timePeriod :schedules.ranked){
                SplatnetDatabase.getInstance(context).getTimePeriodDao().insert(timePeriod.toRoom());
                SplatnetDatabase.getInstance(context).getStageDao().insert(timePeriod.a.toRoom());
                SplatnetDatabase.getInstance(context).getStageDao().insert(timePeriod.b.toRoom());
            }
        }
        if(schedules.league!=null) {
            for(TimePeriod timePeriod :schedules.league){
                SplatnetDatabase.getInstance(context).getTimePeriodDao().insert(timePeriod.toRoom());
                SplatnetDatabase.getInstance(context).getStageDao().insert(timePeriod.a.toRoom());
                SplatnetDatabase.getInstance(context).getStageDao().insert(timePeriod.b.toRoom());
            }
        }
        if(schedules.splatfest!=null) {
            for(TimePeriod timePeriod :schedules.splatfest){
                SplatnetDatabase.getInstance(context).getTimePeriodDao().insert(timePeriod.toRoom());
                SplatnetDatabase.getInstance(context).getStageDao().insert(timePeriod.a.toRoom());
                SplatnetDatabase.getInstance(context).getStageDao().insert(timePeriod.b.toRoom());
            }
        }
    }

    @Override
    public boolean shouldUpdate(){
        List<TimePeriodRoom> roomList = SplatnetDatabase.getInstance(context).getTimePeriodDao().selectOld(Calendar.getInstance().getTimeInMillis());
        if(roomList.size()>0){
            for(TimePeriodRoom room : roomList){
                SplatnetDatabase.getInstance(context).getTimePeriodDao().delete(room);
            }
            return true;
        }
        return false;
    }

    @Override
    public void setup(Splatnet splatnet, String cookie, String uniqueID) {
        call = splatnet.getSchedules(cookie,uniqueID);
        splatfestRequest.setup(splatnet,cookie,uniqueID);
    }



    @Override
    public Bundle result(Bundle bundle) {
        return splatfestRequest.result(bundle);
    }
}
