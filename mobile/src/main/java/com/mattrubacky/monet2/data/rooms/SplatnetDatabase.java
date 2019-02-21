package com.mattrubacky.monet2.data.rooms;

import android.content.Context;

import com.mattrubacky.monet2.data.deserialized.splatoon.Brand;
import com.mattrubacky.monet2.data.deserialized.splatoon.Gear;
import com.mattrubacky.monet2.data.deserialized.splatoon.GearSkills;
import com.mattrubacky.monet2.data.deserialized.splatoon.SalmonStage;
import com.mattrubacky.monet2.data.deserialized.splatoon.Skill;
import com.mattrubacky.monet2.data.deserialized.splatoon.Special;
import com.mattrubacky.monet2.data.deserialized.splatoon.Stage;
import com.mattrubacky.monet2.data.deserialized.splatoon.Sub;
import com.mattrubacky.monet2.data.deserialized.splatoon.TimePeriod;
import com.mattrubacky.monet2.data.deserialized.splatoon.Weapon;
import com.mattrubacky.monet2.data.rooms.dao.entity.BrandDao;
import com.mattrubacky.monet2.data.rooms.dao.entity.ClosetDao;
import com.mattrubacky.monet2.data.rooms.dao.entity.GearDao;
import com.mattrubacky.monet2.data.rooms.dao.entity.SalmonGearDao;
import com.mattrubacky.monet2.data.rooms.dao.entity.SalmonShiftDao;
import com.mattrubacky.monet2.data.rooms.dao.entity.SalmonStageDao;
import com.mattrubacky.monet2.data.rooms.dao.entity.SalmonWeaponDao;
import com.mattrubacky.monet2.data.rooms.dao.entity.SkillDao;
import com.mattrubacky.monet2.data.rooms.dao.entity.SpecialDao;
import com.mattrubacky.monet2.data.rooms.dao.entity.SplatfestDao;
import com.mattrubacky.monet2.data.rooms.dao.entity.SplatfestResultDao;
import com.mattrubacky.monet2.data.rooms.dao.entity.StageDao;
import com.mattrubacky.monet2.data.rooms.dao.entity.SubDao;
import com.mattrubacky.monet2.data.rooms.dao.entity.TimePeriodDao;
import com.mattrubacky.monet2.data.rooms.dao.entity.WeaponDao;
import com.mattrubacky.monet2.data.rooms.entity.SalmonGearRoom;
import com.mattrubacky.monet2.data.rooms.entity.SalmonShiftRoom;
import com.mattrubacky.monet2.data.rooms.entity.SalmonWeaponRoom;
import com.mattrubacky.monet2.data.rooms.entity.SplatfestResultRoom;
import com.mattrubacky.monet2.data.rooms.entity.SplatfestRoom;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {  Brand.class, GearSkills.class, Gear.class, SalmonGearRoom.class,
                        SalmonShiftRoom.class, SalmonStage.class, SalmonWeaponRoom.class, Skill.class,
                        Special.class, SplatfestResultRoom.class, SplatfestRoom.class, Stage.class,
                        Sub.class, TimePeriod.class, Weapon.class},version=1,exportSchema = false)
@TypeConverters({SplatnetConverters.class})
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
