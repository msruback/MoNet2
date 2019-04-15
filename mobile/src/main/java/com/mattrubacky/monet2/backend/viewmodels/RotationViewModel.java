package com.mattrubacky.monet2.backend.viewmodels;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.mattrubacky.monet2.backend.api.splatnet.CoopSchedulesRequest;
import com.mattrubacky.monet2.backend.api.splatnet.MonthlyGearRequest;
import com.mattrubacky.monet2.backend.api.splatnet.SchedulesRequest;
import com.mattrubacky.monet2.backend.api.splatnet.SplatnetConnected;
import com.mattrubacky.monet2.backend.api.splatnet.SplatnetConnector;
import com.mattrubacky.monet2.data.deserialized_entities.Gear;
import com.mattrubacky.monet2.data.deserialized.splatoon.SalmonSchedule;
import com.mattrubacky.monet2.data.deserialized_entities.RewardGear;
import com.mattrubacky.monet2.data.deserialized_entities.SalmonStage;
import com.mattrubacky.monet2.data.deserialized.splatoon.Schedules;
import com.mattrubacky.monet2.data.deserialized.splatoon.Splatfest;
import com.mattrubacky.monet2.data.deserialized_entities.Stage;
import com.mattrubacky.monet2.data.deserialized_entities.TimePeriod;
import com.mattrubacky.monet2.data.rooms.SplatnetDatabase;
import com.mattrubacky.monet2.data.entity.SalmonShiftRoom;
import com.mattrubacky.monet2.data.entity.SplatfestRoom;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

public class RotationViewModel extends AndroidViewModel implements SplatnetConnected{
    private LiveData<List<TimePeriod>> regular, ranked, league, splatfest;
    private LiveData<List<Stage>> stages;
    private LiveData<List<SalmonShiftRoom>> salmonRun;
    private LiveData<List<SalmonStage>> salmonStages;
    private LiveData<RewardGear> gearId;
    private LiveData<SplatfestRoom> currentSplatfest;

    private MediatorLiveData<Schedules> schedules;
    private MediatorLiveData<SalmonSchedule> salmonSchedule;
    private MediatorLiveData<Gear> monthlyGear;
    private MediatorLiveData<Splatfest> splatfestInfo;


    private SplatnetDatabase database;
    private SplatnetConnector connector;

    public RotationViewModel(Application application) {
        super(application);
        database = SplatnetDatabase.getInstance(application);

        stages = database.getStageDao().selectAll();

        salmonStages = database.getSalmonStageDao().selectAll();

        regular = database.getTimePeriodDao().selectRegularLive();
        ranked = database.getTimePeriodDao().selectGachiLive();
        league = database.getTimePeriodDao().selectLeagueLive();
        splatfest = database.getTimePeriodDao().selectFestivalLive();

        gearId = database.getSalmonGearDao().selectCurrentGear(RewardGear.generateId(Calendar.getInstance().getTimeInMillis()));

        currentSplatfest = database.getSplatfestDao().selectUpcoming(Calendar.getInstance().getTimeInMillis()/1000);

        salmonRun = database.getSalmonShiftDao().selectUpcoming(Calendar.getInstance().getTimeInMillis()/1000);

        schedules = new MediatorLiveData<>();

        schedules.addSource(regular, new Observer<List<TimePeriod>>() {
            @Override
            public void onChanged(List<TimePeriod> timePeriods) {
                Schedules rotation = schedules.getValue();
                if(rotation==null){
                    rotation = new Schedules();
                }

                rotation.regular = new ArrayList<>();
                for (TimePeriod timePeriod : timePeriods) {
                    for(Stage stage:stages.getValue()){
                        if(timePeriod.a.id == stage.id){
                            timePeriod.a = stage;
                        }else if(timePeriod.b.id == stage.id){
                            timePeriod.b = stage;
                        }

                    }
                    rotation.regular.add(timePeriod);
                }
                schedules.setValue(rotation);
            }
        });
        schedules.addSource(ranked, new Observer<List<TimePeriod>>() {
            @Override
            public void onChanged(List<TimePeriod> timePeriods) {
                Schedules rotation = schedules.getValue();
                if(rotation==null){
                    rotation = new Schedules();
                }
                rotation.ranked = new ArrayList<>();
                for (TimePeriod timePeriod : timePeriods) {
                    for(Stage stage:stages.getValue()){
                        if(timePeriod.a.id == stage.id){
                            timePeriod.a = stage;
                        }else if(timePeriod.b.id == stage.id){
                            timePeriod.b = stage;
                        }

                    }
                    rotation.ranked.add(timePeriod);
                }
                schedules.setValue(rotation);
            }
        });
        schedules.addSource(league, new Observer<List<TimePeriod>>() {
            @Override
            public void onChanged(List<TimePeriod> timePeriods) {
                Schedules rotation = schedules.getValue();
                if(rotation==null){
                    rotation = new Schedules();
                }
                rotation.league = new ArrayList<>();
                for (TimePeriod timePeriod : timePeriods) {
                    for(Stage stage:stages.getValue()){
                        if(timePeriod.a.id == stage.id){
                            timePeriod.a = stage;
                        }else if(timePeriod.b.id == stage.id){
                            timePeriod.b = stage;
                        }

                    }
                    rotation.league.add(timePeriod);
                }
                schedules.setValue(rotation);
            }
        });
        schedules.addSource(splatfest, new Observer<List<TimePeriod>>() {
            @Override
            public void onChanged(List<TimePeriod> timePeriods) {
                Schedules rotation = schedules.getValue();
                if(rotation==null){
                    rotation = new Schedules();
                }
                rotation.splatfest = new ArrayList<>();
                for (TimePeriod timePeriod : timePeriods) {
                    for(Stage stage:stages.getValue()){
                        if(timePeriod.a.id == stage.id){
                            timePeriod.a = stage;
                        }else if(timePeriod.b.id == stage.id){
                            timePeriod.b = stage;
                        }

                    }
                    rotation.splatfest.add(timePeriod);
                }
                schedules.setValue(rotation);
            }
        });

        salmonSchedule = new MediatorLiveData<>();

        salmonSchedule.addSource(salmonRun, new Observer<List<SalmonShiftRoom>>() {
            @Override
            public void onChanged(List<SalmonShiftRoom> salmonShiftRooms) {
                SalmonSchedule salmonSchedule = new SalmonSchedule();
                salmonSchedule.details = new ArrayList<>();
                salmonSchedule.times = new ArrayList<>();
                for(SalmonShiftRoom salmonShiftRoom:salmonShiftRooms){
                    if(salmonShiftRoom.stage.id!=-1){
                        //List<Weapon> weaponRooms = database.getWeaponDao().selectFromShift(salmonShiftRoom.id);
                        //salmonSchedule.details.add(salmonShiftRoom.toDeserialised(salmonStages.getValue(),weaponRooms));
                    }else{
                        salmonSchedule.times.add(salmonShiftRoom.toDeserialised());
                    }
                }
            }
        });

        monthlyGear = new MediatorLiveData<>();

        monthlyGear.addSource(gearId, new Observer<RewardGear>() {
            @Override
            public void onChanged(RewardGear salmonGear) {
                monthlyGear.setValue(database.getGearDao().select(salmonGear.gear.generatedId));
            }
        });

        splatfestInfo = new MediatorLiveData<>();

        splatfestInfo.addSource(currentSplatfest, new Observer<SplatfestRoom>() {
            @Override
            public void onChanged(SplatfestRoom splatfestRoom) {
                splatfestInfo.setValue(currentSplatfest.getValue().toDeserialized(stages.getValue()));
            }
        });
    }

    public void refresh(Activity activity){
        connector = new SplatnetConnector(this, activity, getApplication());
        connector.addRequest(new SchedulesRequest(getApplication()));
        connector.addRequest(new CoopSchedulesRequest(getApplication(),false));
        connector.addRequest(new MonthlyGearRequest(getApplication()));
        connector.execute();
    }

    public MediatorLiveData<Schedules> getSchedules() {
        return schedules;
    }

    public MediatorLiveData<SalmonSchedule> getSalmonSchedule(){
        return salmonSchedule;
    }

    public MediatorLiveData<Gear> getMonthlyGear(){
        return monthlyGear;
    }

    public MediatorLiveData<Splatfest> getSplatfest(){
        return splatfestInfo;
    }


    //Really just an appendix sort of organ until SplatnetConnected is not needed.
    @Override
    public void update(Bundle bundle) {
    }
}
