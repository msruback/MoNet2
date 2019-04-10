package com.mattrubacky.monet2.backend.api.splatnet;

import android.content.Context;
import android.os.Bundle;

import com.mattrubacky.monet2.data.deserialized.splatoon.Schedules;
import com.mattrubacky.monet2.data.deserialized.splatoon.TimePeriod;
import com.mattrubacky.monet2.data.rooms.SplatnetDatabase;
import com.mattrubacky.monet2.data.rooms.dao.entity.StageDao;
import com.mattrubacky.monet2.data.rooms.dao.entity.TimePeriodDao;

import java.util.Calendar;
import java.util.List;

import retrofit2.Response;

/**
 * Created by mattr on 12/6/2017.
 */

public class SchedulesRequest extends SplatnetRequest {

    private Context context;
    private ActiveSplatfestRequest splatfestRequest;
    private SplatnetDatabase db;
    private StageDao stageDao;
    private TimePeriodDao timePeriodDao;

    public SchedulesRequest(Context context){
        this.context = context;
        splatfestRequest = new ActiveSplatfestRequest(context);
        db = SplatnetDatabase.getInstance(context);
        stageDao = db.getStageDao();
        timePeriodDao = db.getTimePeriodDao();
    }

    @Override
    protected void manageResponse(Response response) {
        Schedules schedules = (Schedules) response.body();
        if(schedules.regular!=null) {
            for(TimePeriod timePeriod :schedules.regular) {
                timePeriodDao.insertTimePeriod(timePeriod,stageDao);
            }
        }
        if(schedules.ranked!=null) {
            for(TimePeriod timePeriod :schedules.ranked){
                timePeriodDao.insertTimePeriod(timePeriod,stageDao);
            }
        }
        if(schedules.league!=null) {
            for(TimePeriod timePeriod :schedules.league){
                timePeriodDao.insertTimePeriod(timePeriod,stageDao);
            }
        }
        if(schedules.splatfest!=null) {
            for(TimePeriod timePeriod :schedules.splatfest){
                timePeriodDao.insertTimePeriod(timePeriod,stageDao);
            }
        }
        db.close();
    }

    @Override
    public boolean shouldUpdate(){
        List<TimePeriod> roomList = SplatnetDatabase.getInstance(context).getTimePeriodDao().selectOld(Calendar.getInstance().getTimeInMillis());
        if(roomList.size()>0){
            for(TimePeriod room : roomList){
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
