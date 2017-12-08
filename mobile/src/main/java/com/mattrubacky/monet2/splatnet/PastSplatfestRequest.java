package com.mattrubacky.monet2.splatnet;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.mattrubacky.monet2.deserialized.PastSplatfest;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by mattr on 12/6/2017.
 */

public class PastSplatfestRequest extends SplatnetRequest {

    private Context context;
    private PastSplatfest splatfests;

    public PastSplatfestRequest(Context context){
        this.context = context;
    }

    @Override
    protected void manageResponse(Response response) throws IOException, SplatnetUnauthorizedException {
        splatfests = (PastSplatfest) response.body();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = settings.edit();
        Gson gson = new Gson();
        String json = gson.toJson(splatfests);
        edit.putString("splatfests",json);
        edit.commit();
    }

    @Override
    public void setup(Splatnet splatnet, String cookie, String uniqueID) {
        call = splatnet.getPastSplatfests(cookie,uniqueID);
    }

    @Override
    public Bundle result(Bundle bundle){
        bundle.putParcelable("pastSplatfests",splatfests);
        return bundle;
    }
}
