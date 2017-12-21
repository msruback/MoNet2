package com.mattrubacky.monet2.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.mattrubacky.monet2.BattleInfo;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.Battle;
import com.mattrubacky.monet2.deserialized.Gear;
import com.mattrubacky.monet2.deserialized.GearNotification;
import com.mattrubacky.monet2.deserialized.GearNotifications;
import com.mattrubacky.monet2.deserialized.Player;
import com.mattrubacky.monet2.deserialized.Skill;
import com.mattrubacky.monet2.deserialized.Splatfest;
import com.mattrubacky.monet2.sqlite.SplatnetContract;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mattr on 12/19/2017.
 */

public class BattleGearNotification extends Notification {

    @SerializedName("player")
    Player player;
    @SerializedName("battle")
    Battle battle;
    @SerializedName("gear")
    Gear gear;
    @SerializedName("skill")
    Skill skill;
    @SerializedName("is_ally")
    boolean isAlly;

    public BattleGearNotification(){
        name = "BattleGearNotification";
    }
    public BattleGearNotification(Context context,Player player,Battle battle,Gear gear,Skill skill,boolean isAlly){
        super(context,new Date().getTime(),(long) battle.id);
        this.player = player;
        this.battle = battle;
        this.gear = gear;
        this.skill = skill;
        this.isAlly = isAlly;
        name = "BattleGearNotification";
    }

    @Override
    public void show() {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Intent battleGearIntent = new Intent(context, BattleInfo.class);
        Bundle bundle = new Bundle();
        if(battle.type.equals("fes")){
            SplatnetSQLManager database = new SplatnetSQLManager(context);
            Splatfest splatfest = database.selectSplatfest(battle.splatfestID).splatfest;
            bundle.putParcelable("splatfest",splatfest);
        }
        bundle.putParcelable("battle",battle);
        battleGearIntent.putExtras(bundle);

        PendingIntent battleGearIntentPending = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), battleGearIntent, 0);

        String title = "Grab some "+gear.name+" from Merch!";
        StringBuilder contentBuilder = new StringBuilder();

        if(isAlly){
            contentBuilder.append("Your Ally, ");
        }else{
            contentBuilder.append("Your Opponent, ");
        }
        contentBuilder.append(player.user.name);
        contentBuilder.append(" had the ");
        contentBuilder.append(gear.name);
        if(skill.id!=-1){
            contentBuilder.append(" with ");
            contentBuilder.append(skill.name);
        }
        contentBuilder.append(" you were looking for!");

        String content = contentBuilder.toString();
        android.app.Notification notification  = new android.app.Notification.Builder(context)
                .setContentTitle(title)
                .setStyle(new android.app.Notification.BigTextStyle().bigText(content))
                .setSmallIcon(R.drawable.char_judd)
                .setContentIntent(battleGearIntentPending)
                .setColor(context.getResources().getColor(R.color.colorPrimary))
                .build();
        notification.defaults = android.app.Notification.DEFAULT_ALL;
        notificationManager.notify((int) (new Date().getTime()%10000), notification);
    }

    @Override
    public String writeJSON() {
        StringBuilder builder = new StringBuilder();
        builder.append(super.writeJSON());
        Gson gson = new Gson();
        builder.append(",\"player\":");
        builder.append(gson.toJson(player));
        builder.append(",\"battle\":");
        builder.append(gson.toJson(battle));
        builder.append(",\"gear\":");
        builder.append(gson.toJson(gear));
        builder.append(",\"skill\":");
        builder.append(gson.toJson(skill));
        builder.append(",\"is_ally\":");
        builder.append(gson.toJson(isAlly));
        return builder.toString();
    }

    @Override
    public boolean isUnique(Notification notification){
        BattleGearNotification compare = (BattleGearNotification) notification;
        if(compare.player.user.id.equals(player.user.id)&&compare.gear.id==gear.id&&compare.skill.id==skill.id){
            return false;
        }
        return true;
    }

    @Override
    public boolean isValid(){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        int id = settings.getInt("lastBattle",-1);
        if((id-2)>battle.id){
            return false;
        }
        return true;
    }
}
