package com.mattrubacky.monet2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;

import android.content.Intent;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;

import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
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
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {

        super.onStartCommand(intent,flags,startId);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Thread t = new Thread(updateSplatnetData);
        if(settings.getBoolean("updateData",false)){
            t.start();
        }else{
            ConnectivityManager connManager = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
            NetworkInfo wifiStatus = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if(wifiStatus.isConnected()){
                t.start();
            }
        }

        return START_NOT_STICKY;
    }

    private Runnable updateSplatnetData = new Runnable() {
        public void run() {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String cookie = settings.getString("cookie","");
            Gson gson = new Gson();

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
                findShopNotifications(shop);


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
                WearLink wearLink = new WearLink(getApplicationContext());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private void findStageNotifications(Schedules schedules){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        StageNotifications stageNotifications = gson.fromJson(settings.getString("stageNotifications",""),StageNotifications.class);
    }

    private void findShopNotifications(Annie shop){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        GearNotifications gearNotifications = gson.fromJson(settings.getString("gearNotifications",""),GearNotifications.class);
        for(int i = 0 ;i<shop.merch.size();i++){
            Product product = shop.merch.get(i);
            for(int j=0;j<gearNotifications.notifications.size();j++){
                GearNotification notification = gearNotifications.notifications.get(j);
                if(notification.gear.id == product.gear.id){
                    if(notification.skill.id==-1||notification.skill.id == product.skill.id){
                        postShopNotification(product);
                    }
                }
            }
        }
    }

    private void postShopNotification(Product product){
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent shopIntent = new Intent(this, MainActivity.class);
        shopIntent.putExtra("fragment",1);
        PendingIntent shopIntentPending = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), shopIntent, 0);

        Intent orderIntent = new Intent(this,OrderGear.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("product",product);
        orderIntent.putExtras(bundle);
        PendingIntent orderIntentPending = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), orderIntent, 0);


        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");

        String time = sdf.format(product.endTime);
        String title = "New "+product.gear.name+" Available!";
        String content = product.gear.name + " with " + product.skill.name + " is now available until "+time+"!";

        Notification notification  = new Notification.Builder(this)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(shopIntentPending)
                .setAutoCancel(true)
                .addAction(R.drawable.char_annie,"Order",orderIntentPending)
                .build();
        notificationManager.notify(0, notification);
    }

}