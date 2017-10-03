package com.mattrubacky.monet2;

import android.app.Service;

import android.content.Intent;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;

import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by mattr on 10/3/2017.
 */

public class UpdateData extends Service {


    @Override

    public void onCreate() {
        super.onCreate();

    }


    @Override

    public IBinder onBind(Intent intent) {

        return null;

    }


    @Override

    public void onDestroy() {

// TODO Auto-generated method stub

        super.onDestroy();

    }


    @Override

    public int onStartCommand(Intent intent,int flags, int startId) {

// TODO Auto-generated method stub

        super.onStartCommand(intent,flags,startId);

        Thread t = new Thread(updateSplatnetData);
        t.start();

        return START_NOT_STICKY;
    }


    @Override

    public boolean onUnbind(Intent intent) {

// TODO Auto-generated method stub

        return super.onUnbind(intent);

    }
    private Runnable updateSplatnetData = new Runnable() {
        public void run() {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String cookie = settings.getString("cookie","");
            Gson gson = new Gson();
            gson.fromJson(settings.getString("rotationState",""),Schedules.class);

            ArrayList<Battle> battles = new ArrayList<>();
            Annie shop = gson.fromJson(settings.getString("shopState",""),Annie.class);
            Schedules schedules = gson.fromJson(settings.getString("rotationState",""),Schedules.class);

            SplatnetSQL database = new SplatnetSQL(getApplicationContext());

            try {
                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://app.splatoon2.nintendo.net").addConverterFactory(GsonConverterFactory.create()).build();
                Splatnet splatnet = retrofit.create(Splatnet.class);
                Response response;

                Call<Schedules> rotationGet = splatnet.getSchedules(cookie);
                response = rotationGet.execute();
                if(response.isSuccessful()){
                    schedules = (Schedules) response.body();
                }else{

                }

                Call<Annie> shopUpdate = splatnet.getShop(cookie);
                response = shopUpdate.execute();
                if(response.isSuccessful()){
                    shop = (Annie) response.body();
                }else{

                }

                response = splatnet.get50Results(cookie).execute();
                ResultList results = (ResultList) response.body();
                for(int i = 0;i<results.resultIds.size();i++) {
                    response = splatnet.getBattle(String.valueOf(results.resultIds.get(i).id),cookie).execute();
                    Battle battle = (Battle) response.body();
                    battles.add(battle);
                    if (!database.existsIn(SplatnetContract.Battle.TABLE_NAME, SplatnetContract.Battle._ID, results.resultIds.get(i).id)) {
                        database.insertBattle(battle);
                    }
                }

                SharedPreferences.Editor edit = settings.edit();
                String json;

                json = gson.toJson(schedules);
                edit.putString("rotationState",json);

                json = gson.toJson(shop);
                edit.putString("shopState",json);

                json = gson.toJson(battles);
                edit.putString("recentBattles",json);

                edit.commit();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

}