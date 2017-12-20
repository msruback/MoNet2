package com.mattrubacky.monet2.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mattrubacky.monet2.deserialized.Battle;
import com.mattrubacky.monet2.deserialized.GearNotification;
import com.mattrubacky.monet2.deserialized.GearNotifications;

import java.util.ArrayList;

/**
 * Created by mattr on 12/19/2017.
 */

public class BattleGearNotificationFactory extends NotificationFactory {

    public BattleGearNotificationFactory(Context context) {
        super(context);
    }

    @Override
    public ArrayList<Notification> findNotifications() {
        ArrayList<Notification> notifications = new ArrayList<>();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        GearNotifications gearNotifications = gson.fromJson(settings.getString("gearNotifications",""),GearNotifications.class);
        ArrayList<Battle> battles = gson.fromJson(settings.getString("recentBattles","[]"),new TypeToken<ArrayList<Battle>>(){}.getType());

        Battle battle;
        GearNotification currentNotification;
        BattleGearNotification battleGearNotification;
        for(int i=0;i<2;i++) {
            battle = battles.get(i);
            for (int j = 0; j < gearNotifications.notifications.size(); j++) {
                currentNotification = gearNotifications.notifications.get(j);
                for (int k = 0; k < battle.myTeam.size(); k++) {
                    switch (currentNotification.gear.kind) {
                        case "head":
                            if (currentNotification.gear.id == battle.myTeam.get(k).user.head.id) {
                                if(currentNotification.skill.id == battle.myTeam.get(k).user.headSkills.main.id||currentNotification.skill.id==-1) {
                                    battleGearNotification = new BattleGearNotification(context,battle.myTeam.get(k),battle,currentNotification.gear,currentNotification.skill,true);
                                    notifications.add(battleGearNotification);
                                }
                            }
                            break;
                        case "clothes":
                            if (currentNotification.gear.id == battle.myTeam.get(k).user.clothes.id) {
                                if(currentNotification.skill.id == battle.myTeam.get(k).user.clothesSkills.main.id||currentNotification.skill.id==-1) {
                                    battleGearNotification = new BattleGearNotification(context,battle.myTeam.get(k),battle,currentNotification.gear,currentNotification.skill,true);
                                    notifications.add(battleGearNotification);
                                }
                            }
                            break;
                        case "shoes":
                            if (currentNotification.gear.id == battle.myTeam.get(k).user.shoes.id) {
                                if(currentNotification.skill.id == battle.myTeam.get(k).user.shoeSkills.main.id||currentNotification.skill.id==-1) {
                                    battleGearNotification = new BattleGearNotification(context,battle.myTeam.get(k),battle,currentNotification.gear,currentNotification.skill,true);
                                    notifications.add(battleGearNotification);
                                }
                            }
                            break;
                    }
                }
                for (int k = 0; k < battle.myTeam.size(); k++) {
                    switch (currentNotification.gear.kind) {
                        case "head":
                            if (currentNotification.gear.id == battle.myTeam.get(k).user.head.id) {
                                if(currentNotification.skill.id == battle.myTeam.get(k).user.headSkills.main.id||currentNotification.skill.id==-1) {
                                    battleGearNotification = new BattleGearNotification(context,battle.otherTeam.get(k),battle,currentNotification.gear,currentNotification.skill,false);
                                    notifications.add(battleGearNotification);
                                }
                            }
                            break;
                        case "clothes":
                            if (currentNotification.gear.id == battle.myTeam.get(k).user.clothes.id) {
                                if(currentNotification.skill.id == battle.myTeam.get(k).user.clothesSkills.main.id||currentNotification.skill.id==-1) {
                                    battleGearNotification = new BattleGearNotification(context,battle.otherTeam.get(k),battle,currentNotification.gear,currentNotification.skill,false);
                                    notifications.add(battleGearNotification);
                                }
                            }
                            break;
                        case "shoes":
                            if (currentNotification.gear.id == battle.myTeam.get(k).user.shoes.id) {
                                if(currentNotification.skill.id == battle.myTeam.get(k).user.shoeSkills.main.id||currentNotification.skill.id==-1) {
                                    battleGearNotification = new BattleGearNotification(context,battle.otherTeam.get(k),battle,currentNotification.gear,currentNotification.skill,false);
                                    notifications.add(battleGearNotification);
                                }
                            }
                            break;
                    }
                }
            }
        }

        return notifications;
    }
}
