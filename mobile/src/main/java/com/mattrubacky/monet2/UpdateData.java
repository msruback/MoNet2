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
import java.util.Random;

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
            StageNotifications stageNotifications = gson.fromJson(settings.getString("stageNotifications",""),StageNotifications.class);


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
                for(int i=0;i<stageNotifications.notifications.size();i++){
                    switch(stageNotifications.notifications.get(i).type){
                        case "any":
                            findStageNotifications(schedules.regular,stageNotifications.notifications.get(i));
                            findStageNotifications(schedules.ranked,stageNotifications.notifications.get(i));
                            findStageNotifications(schedules.league,stageNotifications.notifications.get(i));
                            break;
                        case "regular":
                            findStageNotifications(schedules.regular,stageNotifications.notifications.get(i));
                            break;
                        case "gachi":
                            findStageNotifications(schedules.ranked,stageNotifications.notifications.get(i));
                            break;
                        case "league":
                            findStageNotifications(schedules.league,stageNotifications.notifications.get(i));
                            break;
                    }
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

    private void findStageNotifications(ArrayList<TimePeriod> timePeriods,StageNotification notification){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int numTimePeriods = 1;
        switch(settings.getInt("updateInterval",0)){
            case 0://1 Hour
                numTimePeriods = 1;
                break;
            case 1://2 Hour
                numTimePeriods = 1;
                break;
            case 2://4 Hour
                numTimePeriods = 2;
                break;
            case 3://6 Hour
                numTimePeriods = 3;
                break;
            case 4://8 Hour
                numTimePeriods = 4;
                break;
            case 5://10 Hour
                numTimePeriods = 5;
                break;
            case 6://12 Hour
                numTimePeriods = 6;
                break;
            case 7://24 Hour
                numTimePeriods = 12;
                break;
        }
        for(int i=0;i<numTimePeriods;i++){
            TimePeriod timePeriod = timePeriods.get(i);
            if(timePeriod.rule.key.equals(notification.rule)||notification.rule.equals("any")){
                if(notification.stage.id == timePeriod.a.id||notification.stage.id == timePeriod.b.id||notification.stage.id==-1){
                    postStageNotification(timePeriod,notification);
                }
            }
        }

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

    private void postStageNotification(TimePeriod timePeriod,StageNotification stageNotification){
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent stageIntent = new Intent(this, MainActivity.class);
        stageIntent.putExtra("fragment",0);
        PendingIntent stageIntentPending = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), stageIntent, 0);

        String title;
        String content;
        Notification.Builder builder = new Notification.Builder(this);

        Random random = new Random();
        if(random.nextInt(2)==1){
            builder.setSmallIcon(R.drawable.char_marina);
        }else{
            builder.setSmallIcon(R.drawable.char_pearl);
        }
        builder.setContentTitle(title)
                .setContentText(content)
                .setContentIntent(stageIntentPending)
                .setAutoCancel(true);
        Notification notification = builder.build();
        notificationManager.notify(0, notification);
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
                .setSmallIcon(R.drawable.char_annie)
                .setContentIntent(shopIntentPending)
                .setAutoCancel(true)
                .addAction(R.drawable.char_annie,"Order",orderIntentPending)
                .build();
        notificationManager.notify(0, notification);
    }

}