package com.mattrubacky.monet2.rooms;

import android.content.Context;

import com.mattrubacky.monet2.api.splatnet.Splatnet;
import com.mattrubacky.monet2.rooms.dao.entity.StageDao;
import com.mattrubacky.monet2.rooms.dao.entity.TimePeriodDao;
import com.mattrubacky.monet2.rooms.dao.pojo.TimePeriodPojoDao;
import com.mattrubacky.monet2.rooms.entity.StageRoom;
import com.mattrubacky.monet2.rooms.entity.TimePeriodRoom;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = { StageRoom.class, TimePeriodRoom.class},version=1)
public abstract class SplatnetDatabase extends RoomDatabase {

    private static final String DB_NAME = "splatnetDatabase.db";
    private static volatile SplatnetDatabase instance;

    public static synchronized SplatnetDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static SplatnetDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                SplatnetDatabase.class,
                DB_NAME).build();
    }

    public abstract StageDao getStageDao();
    public abstract TimePeriodDao getTimePeriodDao();
    public abstract TimePeriodPojoDao getTimePeriodPojoDao();
}
