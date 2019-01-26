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
import com.mattrubacky.monet2.rooms.pojo.TimePeriodPojo;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

public class RotationViewModel extends AndroidViewModel implements SplatnetConnected{
    private LiveData<List<TimePeriodPojo>> regular, ranked, league, splatfest;

    private MediatorLiveData<Schedules> schedules;

    SplatnetDatabase database;
    SplatnetConnector connector;

    public RotationViewModel(Application application) {
        super(application);
        database = SplatnetDatabase.getInstance(application);
        regular = database.getTimePeriodPojoDao().selectRegular();
        ranked = database.getTimePeriodPojoDao().selectGachi();
        league = database.getTimePeriodPojoDao().selectLeague();
        splatfest = database.getTimePeriodPojoDao().selectFestival();

        schedules.addSource(regular, new Observer<List<TimePeriodPojo>>() {
            @Override
            public void onChanged(List<TimePeriodPojo> timePeriodPojos) {
                Schedules rotation = schedules.getValue();
                ArrayList<TimePeriod> regular = new ArrayList<>();
                for(TimePeriodPojo timePeriodPojo : timePeriodPojos){
                    regular.add(timePeriodPojo.toDeserialized());
                }
                rotation.regular = regular;
                schedules.setValue(rotation);
            }
        });
        schedules.addSource(ranked, new Observer<List<TimePeriodPojo>>() {
            @Override
            public void onChanged(List<TimePeriodPojo> timePeriodPojos) {
                Schedules rotation = schedules.getValue();
                ArrayList<TimePeriod> ranked = new ArrayList<>();
                for(TimePeriodPojo timePeriodPojo : timePeriodPojos){
                    ranked.add(timePeriodPojo.toDeserialized());
                }
                rotation.ranked = ranked;
                schedules.setValue(rotation);
            }
        });
        schedules.addSource(league, new Observer<List<TimePeriodPojo>>() {
            @Override
            public void onChanged(List<TimePeriodPojo> timePeriodPojos) {
                Schedules rotation = schedules.getValue();
                ArrayList<TimePeriod> league = new ArrayList<>();
                for(TimePeriodPojo timePeriodPojo : timePeriodPojos){
                    league.add(timePeriodPojo.toDeserialized());
                }
                rotation.league = league;
                schedules.setValue(rotation);
            }
        });
        schedules.addSource(splatfest, new Observer<List<TimePeriodPojo>>() {
            @Override
            public void onChanged(List<TimePeriodPojo> timePeriodPojos) {
                Schedules rotation = schedules.getValue();
                ArrayList<TimePeriod> splatfest = new ArrayList<>();
                for(TimePeriodPojo timePeriodPojo : timePeriodPojos){
                    splatfest.add(timePeriodPojo.toDeserialized());
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
