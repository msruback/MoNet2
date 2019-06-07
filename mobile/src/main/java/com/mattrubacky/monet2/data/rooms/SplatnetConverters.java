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
    public static Integer fromBrand(Brand brand){
        return brand.id;
    }

    @TypeConverter
    public static Special toSpecial(Integer value){
        Special special = new Special();
        special.id = value;
        return special;
    }

    @TypeConverter
    public static Integer fromSpecial(Special special){
        if(special!=null) {
            return special.id;
        }
        return null;
    }

    @TypeConverter
    public static Sub toSub(Integer value){
        Sub sub = new Sub();
        sub.id = value;
        return sub;
    }

    @TypeConverter
    public static Integer fromSub(Sub sub){
        if (sub!=null) {
            return sub.id;
        }
        return null;
    }

    @TypeConverter
    public static Skill toSkill(Integer value){
        Skill skill = new Skill();
        skill.id = value;
        return skill;
    }

    @TypeConverter
    public static Integer fromSkill(Skill skill){
        if(skill!=null){
            return skill.id;
        }
        return null;
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
    public static int fromGear(Gear gear){ return gear.generatedId;}

    @TypeConverter
    public static Gear toGear(int id){
        Gear gear = new Gear();
        gear.generatedId = id;
        return gear;
    }

    @TypeConverter
    public static SalmonStage toSalmonStage(Integer id){
        SalmonStage salmonStage = new SalmonStage();
        salmonStage.id = id;
        return salmonStage;
    }

    @TypeConverter
    public static Integer fromSalmonStage(SalmonStage salmonStage){
        if(salmonStage!=null){
        return salmonStage.id;
        }
        return null;
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
