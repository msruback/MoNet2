package com.mattrubacky.monet2.backend.api.splatnet;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.mattrubacky.monet2.data.deserialized.splatoon.SalmonRun;
import com.mattrubacky.monet2.data.deserialized.splatoon.SalmonRunDetail;
import com.mattrubacky.monet2.data.deserialized.splatoon.SalmonSchedule;
import com.mattrubacky.monet2.data.rooms.SplatnetDatabase;
import com.mattrubacky.monet2.data.rooms.dao.entity.SalmonShiftDao;
import com.mattrubacky.monet2.data.rooms.dao.entity.SalmonStageDao;
import com.mattrubacky.monet2.data.rooms.dao.entity.SalmonWeaponDao;
import com.mattrubacky.monet2.data.rooms.dao.entity.WeaponDao;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Response;

/**
 * Created by mattr on 12/6/2017.
 */

public class CoopSchedulesRequest extends SplatnetRequest {

    private Context context;
    private SalmonSchedule salmonSchedule;
    private boolean override;

    public CoopSchedulesRequest(Context context,boolean override){
        this.context = context;
        this.override = override;
    }

    @Override
    protected void manageResponse(Response response) {
        salmonSchedule = (SalmonSchedule) response.body();
        if(!override){
            salmonSchedule.times.remove(0);
            salmonSchedule.times.remove(0);
        }
        SplatnetDatabase db = SplatnetDatabase.getInstance(context);
        SalmonShiftDao shiftDao = db.getSalmonShiftDao();
        SalmonStageDao stageDao = db.getSalmonStageDao();
        SalmonWeaponDao salmonWeaponDao = db.getSalmonWeaponDao();
        WeaponDao weaponDao = db.getWeaponDao();
        for(SalmonRunDetail salmonRunDetail:salmonSchedule.details) {
            shiftDao.insertSalmonShift(salmonRunDetail,stageDao,salmonWeaponDao,weaponDao);
        }
        for(SalmonRun salmonRun:salmonSchedule.times){
            shiftDao.insertSalmonShift(salmonRun);
        }
        db.close();
    }

    @Override
    public void setup(Splatnet splatnet, String cookie, String uniqueID) {
        call = splatnet.getSalmonSchedule(cookie,uniqueID);
    }


    @Override
    public boolean shouldUpdate(){
        SplatnetDatabase db = SplatnetDatabase.getInstance(context);
        int count = db.getSalmonShiftDao().countUpcoming(Calendar.getInstance().getTimeInMillis()/1000);
        db.close();
        return !(count==6);
    }

    @Override
    public Bundle result(Bundle bundle) {
        bundle.putParcelable("salmonSchedule",salmonSchedule);
        return bundle;
    }
}
