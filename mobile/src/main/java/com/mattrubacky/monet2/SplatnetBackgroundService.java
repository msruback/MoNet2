package com.mattrubacky.monet2;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mattr on 9/26/2017.
 */

public class SplatnetBackgroundService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        String cookie = settings.getString("cookie","");

        SplatnetSQL database = new SplatnetSQL(context);

        try {
            Retrofit retrofit = new Retrofit.Builder().baseUrl("http://app.splatoon2.nintendo.net").addConverterFactory(GsonConverterFactory.create()).build();
            Splatnet splatnet = retrofit.create(Splatnet.class);
            Response response;

            response = splatnet.get50Results(cookie).execute();
            ResultList results = (ResultList) response.body();
            for(int i = 0;i<results.resultIds.size();i++) {
                if (!database.existsIn(SplatnetContract.Battle.TABLE_NAME, SplatnetContract.Battle._ID, results.resultIds.get(i).id)) {
                    response = splatnet.getBattle(String.valueOf(results.resultIds.get(i).id),cookie).execute();
                    database.insertBattle((Battle) response.body());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
