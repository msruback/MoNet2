package com.mattrubacky.monet2.data.rooms;

import com.mattrubacky.monet2.data.deserialized_entities.Brand;
import com.mattrubacky.monet2.data.deserialized_entities.Gear;
import com.mattrubacky.monet2.data.deserialized.splatoon.KeyName;
import com.mattrubacky.monet2.data.deserialized_entities.SalmonStage;
import com.mattrubacky.monet2.data.deserialized_entities.Skill;
import com.mattrubacky.monet2.data.deserialized_entities.Special;
import com.mattrubacky.monet2.data.deserialized.splatoon.SplatfestGrade;
import com.mattrubacky.monet2.data.deserialized_entities.Stage;
import com.mattrubacky.monet2.data.deserialized_entities.Sub;
import com.mattrubacky.monet2.data.deserialized_entities.Weapon;

import androidx.room.TypeConverter;

public class SplatnetConverters {
    @TypeConverter
    public static Stage toStage(int value){
        Stage stage = new Stage();
        stage.id = value;
        return stage;
    }

    @TypeConverter
    public static int fromStage(Stage stage){
        return stage.id;
    }

    @TypeConverter
    public static KeyName toKeyName(String value){
        KeyName keyName = new KeyName();
        keyName.key = value;
        return keyName;
    }

    @TypeConverter
    public static String fromKeyName(KeyName keyName){
        return keyName.key;
    }

    @TypeConverter
    public static Brand toBrand(int value){
        Brand brand = new Brand();
        brand.id = value;
        return brand;
    }

    @TypeConverter
    public static int fromBrand(Brand brand){
        return brand.id;
    }

    @TypeConverter
    public static Special toSpecial(int value){
        Special special = new Special();
        special.id = value;
        return special;
    }

    @TypeConverter
    public static int fromSpecial(Special special){
        return special.id;
    }

    @TypeConverter
    public static Sub toSub(int value){
        Sub sub = new Sub();
        sub.id = value;
        return sub;
    }

    @TypeConverter
    public static int fromSub(Sub sub){
        return sub.id;
    }

    @TypeConverter
    public static Skill toSkill(int value){
        Skill skill = new Skill();
        skill.id = value;
        return skill;
    }

    @TypeConverter
    public static int fromSkill(Skill skill){
        return skill.id;
    }

    @TypeConverter
    public static SplatfestGrade toGrade(String value){
        SplatfestGrade grade = new SplatfestGrade();
        grade.name = value;
        return grade;
    }
    @TypeConverter
    public static String fromGrade(SplatfestGrade grade) {
        return grade.name;
    }

    @TypeConverter
    public static int fromGear(Gear gear){ return gear.id;}

    @TypeConverter
    public static Gear toGear(int id){
        Gear gear = new Gear();
        gear.id = id;
        return gear;
    }

    @TypeConverter
    public static SalmonStage toSalmonStage(int id){
        SalmonStage salmonStage = new SalmonStage();
        salmonStage.id = id;
        return salmonStage;
    }

    @TypeConverter
    public static int fromSalmonStage(SalmonStage salmonStage){
        return salmonStage.id;
    }

    @TypeConverter
    public static Weapon toWeapon(int id){
        Weapon weapon = new Weapon();
        weapon.id = id;
        return weapon;
    }
    @TypeConverter
    public static int fromWeapon(Weapon weapon){
        return weapon.id;
    }
}
