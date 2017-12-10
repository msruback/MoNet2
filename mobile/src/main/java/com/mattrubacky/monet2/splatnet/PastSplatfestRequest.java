package com.mattrubacky.monet2.splatnet;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.mattrubacky.monet2.deserialized.PastSplatfest;
import com.mattrubacky.monet2.deserialized.SplatfestDatabase;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Response;

/**
 * Created by mattr on 12/6/2017.
 */

public class PastSplatfestRequest extends SplatnetRequest {

    private Context context;
    private ArrayList<SplatfestDatabase> splatfests;
    private ActiveSplatfestRequest activeSplatfestRequest;

    public PastSplatfestRequest(Context context){
        this.context = context;
        activeSplatfestRequest = new ActiveSplatfestRequest(context);
    }

    @Override
    protected void manageResponse(Response response) throws IOException, SplatnetUnauthorizedException {
        PastSplatfest pastSplatfest = (PastSplatfest) response.body();

        activeSplatfestRequest.run();

        SplatnetSQLManager database = new SplatnetSQLManager(context);
        database.insertSplatfests(pastSplatfest.splatfests,pastSplatfest.results);
        splatfests = database.getSplatfests();
    }

    @Override
    public void setup(Splatnet splatnet, String cookie, String uniqueID) {
        call = splatnet.getPastSplatfests(cookie,uniqueID);
        activeSplatfestRequest.setup(splatnet,cookie,uniqueID);
    }



    @Override
    public Bundle result(Bundle bundle){
        bundle.putParcelableArrayList("splatfests",splatfests);
        return bundle;
    }
}
