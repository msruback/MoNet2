package com.mattrubacky.monet2.splatnet;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mattrubacky.monet2.deserialized.CampaignRecords;
import com.mattrubacky.monet2.deserialized.SplatfestDatabase;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Response;

/**
 * Created by mattr on 12/6/2017.
 */

public class CampaignRecordsRequest extends SplatnetRequest {
    private CampaignRecords records;
    private Context context;

    public CampaignRecordsRequest(Context context){
        this.context = context;

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        records = gson.fromJson(settings.getString("campaig_records","{\"stage_infos\":[],\"weapon_map\":{}}"),CampaignRecords.class);
    }

    @Override
    protected void manageResponse(Response response) throws IOException, SplatnetUnauthorizedException {
        records = (CampaignRecords) response.body();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = settings.edit();
        Gson gson = new Gson();

        String json = gson.toJson(records);
        edit.putString("campaign_records",json);
        edit.commit();
    }

    @Override
    public void setup(Splatnet splatnet, String cookie, String uniqueID) {
        call = splatnet.getHeroData(cookie,uniqueID);
    }

    @Override
    public Bundle result(Bundle bundle){
        bundle.putParcelable("campaign_records",records);
        return bundle;
    }
}
