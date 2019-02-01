package com.mattrubacky.monet2.viewmodels;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.mattrubacky.monet2.api.splatnet.CoopSchedulesRequest;
import com.mattrubacky.monet2.api.splatnet.MonthlyGearRequest;
import com.mattrubacky.monet2.api.splatnet.SchedulesRequest;
import com.mattrubacky.monet2.api.splatnet.SplatnetConnected;
import com.mattrubacky.monet2.api.splatnet.SplatnetConnector;
import com.mattrubacky.monet2.deserialized.splatoon.Schedules;
import com.mattrubacky.monet2.deserialized.splatoon.TimePeriod;
import com.mattrubacky.monet2.rooms.SplatnetDatabase;
import com.mattrubacky.monet2.rooms.entity.StageRoom;
import com.mattrubacky.monet2.rooms.entity.TimePeriodRoom;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

public class RotationViewModel extends AndroidViewModel implements SplatnetConnected{
    private LiveData<List<TimePeriodRoom>> regular, ranked, league, splatfest;
    private LiveData<List<StageRoom>> stages;

    private MediatorLiveData<Schedules> schedules;

    private SplatnetDatabase database;
    private SplatnetConnector connector;

    public RotationViewModel(Application application) {
        super(application);
        database = SplatnetDatabase.getInstance(application);

        stages = database.getStageDao().selectAll();

        regular = database.getTimePeriodDao().selectRegular();
        ranked = database.getTimePeriodDao().selectGachi();
        league = database.getTimePeriodDao().selectLeague();
        splatfest = database.getTimePeriodDao().selectFestival();

        schedules.addSource(regular, new Observer<List<TimePeriodRoom>>() {
            @Override
            public void onChanged(List<TimePeriodRoom> timePeriods) {
                Schedules rotation = schedules.getValue();
                ArrayList<TimePeriod> regular = new ArrayList<>();
                for(TimePeriodRoom timePeriod : timePeriods){
                    regular.add(timePeriod.toDeserialized(stages.getValue()));
                }
                if(rotation==null){
                    rotation = new Schedules();
                }
                rotation.regular = regular;
                schedules.setValue(rotation);
            }
        });
        schedules.addSource(ranked, new Observer<List<TimePeriodRoom>>() {
            @Override
            public void onChanged(List<TimePeriodRoom> timePeriods) {
                Schedules rotation = schedules.getValue();
                ArrayList<TimePeriod> ranked = new ArrayList<>();
                for(TimePeriodRoom timePeriod: timePeriods){
                    ranked.add(timePeriod.toDeserialized(stages.getValue()));
                }
                if(rotation==null){
                    rotation = new Schedules();
                }
                rotation.ranked = ranked;
                schedules.setValue(rotation);
            }
        });
        schedules.addSource(league, new Observer<List<TimePeriodRoom>>() {
            @Override
            public void onChanged(List<TimePeriodRoom> timePeriods) {
                Schedules rotation = schedules.getValue();
                ArrayList<TimePeriod> league = new ArrayList<>();
                for(TimePeriodRoom timePeriod : timePeriods){
                    league.add(timePeriod.toDeserialized(stages.getValue()));
                }
                if(rotation==null){
                    rotation = new Schedules();
                }
                rotation.league = league;
                schedules.setValue(rotation);
            }
        });
        schedules.addSource(splatfest, new Observer<List<TimePeriodRoom>>() {
            @Override
            public void onChanged(List<TimePeriodRoom> timePeriods) {
                Schedules rotation = schedules.getValue();
                ArrayList<TimePeriod> splatfest = new ArrayList<>();
                for(TimePeriodRoom timePeriodPojo : timePeriods){
                    splatfest.add(timePeriodPojo.toDeserialized(stages.getValue()));
                }
                if(rotation==null){
                    rotation = new Schedules();
                }
                rotation.splatfest = splatfest;
                schedules.setValue(rotation);
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

    //Really just an appendix sort of organ until SplatnetConnected is not needed.
    @Override
    public void update(Bundle bundle) {
    }
}
