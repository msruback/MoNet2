package com.mattrubacky.monet2.splatnet;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.mattrubacky.monet2.deserialized.Battle;
import com.mattrubacky.monet2.deserialized.ResultList;
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
    }

    @Override
    protected void manageResponse(Response response) throws IOException, SplatnetUnauthorizedException {
        ResultList results = (ResultList) response.body();

        SplatnetSQLManager database = new SplatnetSQLManager(context);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = settings.edit();
        Gson gson = new Gson();

        list = new ArrayList<>();

        for (int i = 0; i < results.resultIds.size(); i++) {
            resultRequest = new ResultRequest(results.resultIds.get(i).id);
            resultRequest.setup(splatnet,cookie,uniqueID);
            resultRequest.run();
            list.add((Battle) resultRequest.result(new Bundle()).getParcelable("battle"));
        }
        database.insertBattles(list);

        String json = gson.toJson(list);
        edit.putString("recentBattles",json);
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
