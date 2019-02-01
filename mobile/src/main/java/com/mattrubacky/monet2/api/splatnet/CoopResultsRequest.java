package com.mattrubacky.monet2.api.splatnet;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.mattrubacky.monet2.deserialized.splatoon.CoopResult;
import com.mattrubacky.monet2.deserialized.splatoon.CoopResults;
import com.mattrubacky.monet2.deserialized.splatoon.SalmonRunDetail;
import com.mattrubacky.monet2.helper.ShiftStats;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Response;

/**
 * Created by mattr on 10/23/2018.
 */

public class CoopResultsRequest extends SplatnetRequest {
    Splatnet splatnet;
    String cookie,uniqueID;
    Context context;
    CoopResultRequest resultRequest;
    CoopResults results;

    public CoopResultsRequest(Context context){
        this.context = context;

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        results = gson.fromJson(settings.getString("coop_results",""),CoopResults.class);
    }

    @Override
    protected void manageResponse(Response response) throws IOException, SplatnetUnauthorizedException, SplatnetMaintenanceException {
        results = (CoopResults) response.body();
        SplatnetSQLManager database = new SplatnetSQLManager(context);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = settings.edit();
        Gson gson = new Gson();

        ArrayList<CoopResult> newList =  new ArrayList<>();
        for (int i = 0; i < results.results.size(); i++) {
            resultRequest = new CoopResultRequest(results.results.get(i).jobId);
            resultRequest.setup(splatnet,cookie,uniqueID);
            resultRequest.run();
            newList.add((CoopResult) resultRequest.result(new Bundle()).getParcelable("job"));
        }
        ArrayList<SalmonRunDetail> salmonRunDetails = new ArrayList<>();
        for(ShiftStats shiftStats : results.coopSummary.shifts){
            salmonRunDetails.add(shiftStats.schedule);
        }
        database.insertShifts(salmonRunDetails);
        database.insertJobs(newList);
        String json = gson.toJson(results);
        edit.putString("coop_results",json);
        edit.commit();
    }

    @Override
    public void setup(Splatnet splatnet, String cookie, String uniqueID) {
        call = splatnet.getSalmonResults(cookie,uniqueID);
        this.splatnet = splatnet;
        this.cookie = cookie;
        this.uniqueID = uniqueID;
    }

    @Override
    public Bundle result(Bundle bundle){
        bundle.putParcelable("coop_results",results);
        return bundle;
    }
}