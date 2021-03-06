package com.mattrubacky.monet2.api.splatnet;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mattrubacky.monet2.deserialized.splatoon.Battle;
import com.mattrubacky.monet2.deserialized.splatoon.ResultList;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Response;

/**
 * Created by mattr on 12/6/2017.
 */

public class ResultsRequest extends SplatnetRequest {
    ResultRequest resultRequest;
    ArrayList<Battle> list;
    Splatnet splatnet;
    String cookie,uniqueID;
    Context context;

    public ResultsRequest(Context context){
        this.context = context;
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        list = gson.fromJson(settings.getString("recentBattles","[]"),new TypeToken<ArrayList<Battle>>(){}.getType());
    }

    @Override
    protected void manageResponse(Response response) throws IOException, SplatnetUnauthorizedException,SplatnetMaintenanceException {
        ResultList results = (ResultList) response.body();

        SplatnetSQLManager database = new SplatnetSQLManager(context);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = settings.edit();
        Gson gson = new Gson();

        ArrayList<Battle> newList =  new ArrayList<>();
        int maxId = settings.getInt("lastBattle",-1);
        for (int i = 0; i < results.resultIds.size()&&maxId<results.resultIds.get(i).id; i++) {
            resultRequest = new ResultRequest(results.resultIds.get(i).id);
            resultRequest.setup(splatnet,cookie,uniqueID);
            resultRequest.run();
            newList.add((Battle) resultRequest.result(new Bundle()).getParcelable("battle"));
        }
        database.insertBattles(newList);

        if(list.size()>0) {
            edit.putInt("lastBattle", list.get(0).id);
        }
    }

    @Override
    public void setup(Splatnet splatnet, String cookie, String uniqueID) {
        call = splatnet.get50Results(cookie,uniqueID);
        this.splatnet = splatnet;
        this.cookie = cookie;
        this.uniqueID = uniqueID;
    }

    @Override
    public Bundle result(Bundle bundle){
        bundle.putParcelableArrayList("battles",list);
        return bundle;
    }
}
