package com.mattrubacky.monet2.rooms;

import android.content.Context;

import com.mattrubacky.monet2.deserialized.splatoon.SplatfestResult;
import com.mattrubacky.monet2.rooms.dao.entity.BrandDao;
import com.mattrubacky.monet2.rooms.dao.entity.ClosetDao;
import com.mattrubacky.monet2.rooms.dao.entity.GearDao;
import com.mattrubacky.monet2.rooms.dao.entity.SalmonGearDao;
import com.mattrubacky.monet2.rooms.dao.entity.SalmonShiftDao;
import com.mattrubacky.monet2.rooms.dao.entity.SalmonStageDao;
import com.mattrubacky.monet2.rooms.dao.entity.SalmonWeaponDao;
import com.mattrubacky.monet2.rooms.dao.entity.SkillDao;
import com.mattrubacky.monet2.rooms.dao.entity.SpecialDao;
import com.mattrubacky.monet2.rooms.dao.entity.SplatfestDao;
import com.mattrubacky.monet2.rooms.dao.entity.SplatfestResultDao;
import com.mattrubacky.monet2.rooms.dao.entity.StageDao;
import com.mattrubacky.monet2.rooms.dao.entity.SubDao;
import com.mattrubacky.monet2.rooms.dao.entity.TimePeriodDao;
import com.mattrubacky.monet2.rooms.dao.entity.WeaponDao;
import com.mattrubacky.monet2.rooms.entity.BrandRoom;
import com.mattrubacky.monet2.rooms.entity.ClosetRoom;
import com.mattrubacky.monet2.rooms.entity.GearRoom;
import com.mattrubacky.monet2.rooms.entity.SalmonGearRoom;
import com.mattrubacky.monet2.rooms.entity.SalmonShiftRoom;
import com.mattrubacky.monet2.rooms.entity.SalmonStageRoom;
import com.mattrubacky.monet2.rooms.entity.SalmonWeaponRoom;
import com.mattrubacky.monet2.rooms.entity.SkillRoom;
import com.mattrubacky.monet2.rooms.entity.SpecialRoom;
import com.mattrubacky.monet2.rooms.entity.SplatfestResultRoom;
import com.mattrubacky.monet2.rooms.entity.SplatfestRoom;
import com.mattrubacky.monet2.rooms.entity.StageRoom;
import com.mattrubacky.monet2.rooms.entity.SubRoom;
import com.mattrubacky.monet2.rooms.entity.TimePeriodRoom;
import com.mattrubacky.monet2.rooms.entity.WeaponRoom;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {  BrandRoom.class, ClosetRoom.class, GearRoom.class, SalmonGearRoom.class,
                        SalmonShiftRoom.class, SalmonStageRoom.class, SalmonWeaponRoom.class, SkillRoom.class,
                        SpecialRoom.class, SplatfestResultRoom.class, SplatfestRoom.class, StageRoom.class,
                        SubRoom.class,TimePeriodRoom.class, WeaponRoom.class},version=1,exportSchema = false)
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
    public abstract SalmonShiftDao getSalmonShiftDao();
    public abstract SalmonStageDao getSalmonStageDao();
    public abstract SalmonWeaponDao getSalmonWeaponDao();
    public abstract SalmonGearDao getSalmonGearDao();
    public abstract WeaponDao getWeaponDao();
    public abstract SpecialDao getSpecialDao();
    public abstract SubDao getSubDao();
    public abstract GearDao getGearDao();
    public abstract BrandDao getBrandDao();
    public abstract SkillDao getSkillDao();
    public abstract ClosetDao getClosetDao();
    public abstract SplatfestDao getSplatfestDao();
    public abstract SplatfestResultDao getSplatfestResultDao();
}
