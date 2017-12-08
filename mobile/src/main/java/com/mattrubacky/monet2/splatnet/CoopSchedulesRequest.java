package com.mattrubacky.monet2.splatnet;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.mattrubacky.monet2.deserialized.SalmonSchedule;
import com.mattrubacky.monet2.deserialized.Schedules;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by mattr on 12/6/2017.
 */

public class CoopSchedulesRequest extends SplatnetRequest {

    private Context context;
    private SalmonSchedule salmonSchedule;

    public CoopSchedulesRequest(Context context){
        this.context = context;
    }

    @Override
    protected void manageResponse(Response response) throws IOException, SplatnetUnauthorizedException {
        salmonSchedule = (SalmonSchedule) response.body();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = settings.edit();
        Gson gson = new Gson();

        String json = gson.toJson(salmonSchedule);
        edit.putString("salmonRunSchedule",json);
        edit.commit();
    }

    @Override
    public void setup(Splatnet splatnet, String cookie, String uniqueID) {
        call = splatnet.getSalmonSchedule(cookie,uniqueID);
    }

    @Override
    public boolean shouldUpdate(){

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        long now = new Date().getTime();
        salmonSchedule = gson.fromJson(settings.getString("salmonRunSchedule",""),SalmonSchedule.class);
        if(salmonSchedule.details!=null&&salmonSchedule.details.size()>0){
            if((salmonSchedule.details.get(0).end*1000)<now){
                return true;
            }else{
                return false;
            }
        }
        return true;
    }

    @Override
    public Bundle result(Bundle bundle) {
        bundle.putParcelable("salmonSchedule",salmonSchedule);
        return bundle;
    }
}
