package com.mattrubacky.monet2;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mattr on 9/26/2017.
 */

public class DataUpdateAlarm extends WakefulBroadcastReceiver {

    Context context;
    PowerManager.WakeLock wl;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context =context;
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

        new UpdateData().execute();


    }

    public void setAlarm(Context context)
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Long alarmTime, alarmSpacing;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE,0);
        int hour;
        switch(settings.getInt("updateInterval",0)){
            case 0:
                calendar.add(Calendar.HOUR_OF_DAY,1);
                alarmTime = calendar.getTimeInMillis()-(new Date().getTime());
                alarmSpacing = Long.valueOf(1000*60*60);
                break;
            case 1:
                hour = 2 - calendar.get(Calendar.HOUR_OF_DAY)%2;
                calendar.add(Calendar.HOUR_OF_DAY,hour);
                alarmTime = calendar.getTimeInMillis()-(new Date().getTime());//2 Hour
                alarmSpacing = Long.valueOf(1000*60*60*2);
                break;
            case 2:
                hour = 4 - calendar.get(Calendar.HOUR_OF_DAY)%4;
                calendar.add(Calendar.HOUR_OF_DAY,hour);
                alarmTime = calendar.getTimeInMillis()-(new Date().getTime());//4 Hour
                alarmSpacing = Long.valueOf(1000*60*60*4);
                break;
            case 3:
                hour = 6 - calendar.get(Calendar.HOUR_OF_DAY)%6;
                calendar.add(Calendar.HOUR_OF_DAY,hour);
                alarmTime = calendar.getTimeInMillis()-(new Date().getTime());//6 Hour
                alarmSpacing = Long.valueOf(1000*60*60*6);
                break;
            case 4:
                hour = 8 - calendar.get(Calendar.HOUR_OF_DAY)%8;
                calendar.add(Calendar.HOUR_OF_DAY,hour);
                alarmTime = calendar.getTimeInMillis()-(new Date().getTime());//8 Hour
                alarmSpacing = Long.valueOf(1000*60*60*8);
                break;
            case 5:
                hour = 10 - calendar.get(Calendar.HOUR_OF_DAY)%10;
                calendar.add(Calendar.HOUR_OF_DAY,hour);
                alarmTime = calendar.getTimeInMillis()-(new Date().getTime());//10 Hour
                alarmSpacing = Long.valueOf(1000*60*60*10);
                break;
            case 6:
                hour = 12 - calendar.get(Calendar.HOUR_OF_DAY)%12;
                calendar.add(Calendar.HOUR_OF_DAY,hour);
                alarmTime = calendar.getTimeInMillis()-(new Date().getTime());//12 Hour
                alarmSpacing = Long.valueOf(1000*60*60*12);
                break;
            case 7:
                hour = 24 - calendar.get(Calendar.HOUR_OF_DAY);
                calendar.add(Calendar.HOUR_OF_DAY,hour);
                alarmTime = calendar.getTimeInMillis()-(new Date().getTime());//24 Hour
                alarmSpacing = Long.valueOf(1000*60*60*24);
                break;
            default:
                calendar.set(Calendar.MINUTE,0);
                calendar.add(Calendar.HOUR_OF_DAY,1);
                alarmTime = calendar.getTimeInMillis()-(new Date().getTime());
                alarmSpacing = Long.valueOf(1000*60*60);
                break;
        }
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, DataUpdateAlarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+alarmTime,alarmSpacing, pi); // Millisec * Second * Minute
    }
    public void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, DataUpdateAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
    public boolean isAlarmSet(Context context){
        Intent intent = new Intent(context, DataUpdateAlarm.class);
        return (PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE) != null);
    }

    private class UpdateData extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {}
        @Override
        protected Void doInBackground(Void... params) {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
            String cookie = settings.getString("cookie","");
            Gson gson = new Gson();

            ArrayList<Battle> battles = new ArrayList<>();
            Annie shop = gson.fromJson(settings.getString("shopState",""),Annie.class);
            Schedules schedules = gson.fromJson(settings.getString("rotationState",""),Schedules.class);
            StageNotifications stageNotifications = gson.fromJson(settings.getString("stageNotifications",""),StageNotifications.class);


            SplatnetSQL database = new SplatnetSQL(context);

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
                WearLink wearLink = new WearLink(context);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            wl.release();
        }

    }

    private void findStageNotifications(ArrayList<TimePeriod> timePeriods,StageNotification notification){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
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
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        GearNotifications gearNotifications = gson.fromJson(settings.getString("gearNotifications",""),GearNotifications.class);
        for(int i = 0 ;i<shop.merch.size();i++){
            Product product = shop.merch.get(i);
            for(int j=0;j<gearNotifications.notifications.size();j++){
                GearNotification notification = gearNotifications.notifications.get(j);
                if(notification.notified==null){
                    notification.notified = new ArrayList<>();
                }
                if(notification.gear.id == product.gear.id){
                    boolean notified = false;
                    for(int k=0;k<notification.notified.size();k++){
                        if(notification.notified.get(k).id.equals(product.gear.id)){
                            notified= true;
                        }
                    }
                    if(!notified) {
                        if (notification.skill.id == -1 || notification.skill.id == product.skill.id) {
                            postShopNotification(product);
                            gearNotifications.notifications.get(j).notified.add(product);
                        }
                    }
                }
            }
        }
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("gearNotifications",gson.toJson(gearNotifications));
        edit.commit();
    }

    private void postStageNotification(TimePeriod timePeriod,StageNotification stageNotification){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Intent stageIntent = new Intent(context, MainActivity.class);
        stageIntent.putExtra("fragment",0);
        PendingIntent stageIntentPending = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), stageIntent, 0);

        String time;
        String title;
        String content;
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        Notification.Builder builder = new Notification.Builder(context);
        if(stageNotification.stage.id==-1){
            if((timePeriod.start *1000)<new Date().getTime()){
                title = timePeriod.rule.name + " available now in " + timePeriod.gamemode.name + "!";
                time = sdf.format((timePeriod.end*1000));
                content = "Play "+timePeriod.rule.name+ " on "+timePeriod.a.name + " and "+ timePeriod.b.name +" until "+time+"!";
            }else{
                title = timePeriod.rule.name + " available soon in " + timePeriod.gamemode.name + "!";
                time = sdf.format((timePeriod.start*1000));
                content = "Play "+timePeriod.rule.name + " on "+timePeriod.a.name + " and " + timePeriod.b.name+ " at "+time+"!";
            }
        }else{
            if((timePeriod.start *1000)<new Date().getTime()){
                title = stageNotification.stage.name + " available now in " + timePeriod.gamemode.name + "!";
                time = sdf.format((timePeriod.end*1000));
                content = "Play " + timePeriod.rule.name + " on "+ stageNotification.stage.name +" now until "+time+"!";
            }else{
                title = stageNotification.stage.name + " available soon in " + timePeriod.gamemode.name + "!";
                time = sdf.format((timePeriod.start*1000));
                content = "Play " + timePeriod.rule.name + " on "+ stageNotification.stage.name + " at "+time+"!";
            }
        }

        Random random = new Random();
        if(random.nextInt(2)==1){
            builder.setSmallIcon(R.drawable.char_marina);
        }else{
            builder.setSmallIcon(R.drawable.char_pearl);
        }
        builder.setContentTitle(title)
                .setStyle(new Notification.BigTextStyle().bigText(content))
                .setContentIntent(stageIntentPending)
                .setAutoCancel(true);
        Notification notification = builder.build();
        notification.defaults = Notification.DEFAULT_ALL;
        notificationManager.notify((int) (new Date().getTime()%10000), notification);
    }

    private void postShopNotification(Product product){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Intent shopIntent = new Intent(context, MainActivity.class);
        shopIntent.putExtra("fragment",1);
        PendingIntent shopIntentPending = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), shopIntent, 0);

        Intent orderIntent = new Intent(context,OrderGear.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("product",product);
        orderIntent.putExtras(bundle);
        PendingIntent orderIntentPending = PendingIntent.getService(context, (int) System.currentTimeMillis(), orderIntent, 0);


        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");

        String time = sdf.format((product.endTime*1000));
        String title = "New "+product.gear.name+" Available!";
        String content = product.gear.name + " with " + product.skill.name + " is now available until "+time+"!";

        Notification notification  = new Notification.Builder(context)
                .setContentTitle(title)
                .setStyle(new Notification.BigTextStyle().bigText(content))
                .setSmallIcon(R.drawable.char_annie)
                .setContentIntent(shopIntentPending)
                .setAutoCancel(true)
                .addAction(R.drawable.char_annie,"Order",orderIntentPending)
                .build();
        notification.defaults = Notification.DEFAULT_ALL;
        notificationManager.notify((int) (new Date().getTime()%10000), notification);
    }
}
