package com.mattrubacky.monet2.data.rooms;

import android.content.Context;

import com.mattrubacky.monet2.data.deserialized_entities.Brand;
import com.mattrubacky.monet2.data.deserialized_entities.Gear;
import com.mattrubacky.monet2.data.deserialized_entities.RewardGear;
import com.mattrubacky.monet2.data.deserialized_entities.SalmonRunWeapon;
import com.mattrubacky.monet2.data.deserialized_entities.SalmonStage;
import com.mattrubacky.monet2.data.deserialized_entities.Skill;
import com.mattrubacky.monet2.data.deserialized_entities.Special;
import com.mattrubacky.monet2.data.deserialized_entities.Stage;
import com.mattrubacky.monet2.data.deserialized_entities.Sub;
import com.mattrubacky.monet2.data.deserialized_entities.TimePeriod;
import com.mattrubacky.monet2.data.deserialized_entities.Weapon;
import com.mattrubacky.monet2.data.entity.BattleRoom;
import com.mattrubacky.monet2.data.entity.ClosetRoom;
import com.mattrubacky.monet2.data.entity.PlayerRoom;
import com.mattrubacky.monet2.data.entity.ProductRoom;
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
import com.mattrubacky.monet2.data.entity.SalmonShiftRoom;
import com.mattrubacky.monet2.data.entity.SplatfestResultRoom;
import com.mattrubacky.monet2.data.entity.SplatfestRoom;
import com.mattrubacky.monet2.data.stats.GearStats;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {  Brand.class, Gear.class, RewardGear.class, SalmonRunWeapon.class, SalmonStage.class,
        Skill.class, Special.class, Stage.class, Sub.class, TimePeriod.class, Weapon.class,
        BattleRoom.class, ClosetRoom.class, PlayerRoom.class, ProductRoom.class, SalmonShiftRoom.class,
        SplatfestResultRoom.class, SplatfestRoom.class},version=1,exportSchema = false)
@TypeConverters({SplatnetConverters.class})
public abstract class TestDatabase extends RoomDatabase {

    private static final String DB_NAME = "splatnetDatabase.db";
    private static volatile TestDatabase instance;

    public static synchronized TestDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static TestDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                TestDatabase.class,
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
