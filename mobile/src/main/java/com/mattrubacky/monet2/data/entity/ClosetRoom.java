package com.mattrubacky.monet2.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.mattrubacky.monet2.data.deserialized_entities.Gear;
import com.mattrubacky.monet2.data.deserialized.splatoon.GearSkills;
import com.mattrubacky.monet2.data.deserialized_entities.Skill;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "closet",
        foreignKeys = {
                @ForeignKey(entity = Gear.class,
                        parentColumns = "gear_id",
                        childColumns = "closet_gear"
                ),
                @ForeignKey(entity = Skill.class,
                        parentColumns = "skill_id",
                        childColumns = "main"
                ),
                @ForeignKey(entity = Skill.class,
                        parentColumns = "skill_id",
                        childColumns = "fr_sub"
                ),
                @ForeignKey(entity = Skill.class,
                        parentColumns = "skill_id",
                        childColumns = "sc_sub"
                ),
                @ForeignKey(entity = Skill.class,
                        parentColumns = "skill_id",
                        childColumns = "tr_sub"
                )
        },
        indices = {
                @Index(value = "closet_gear"),
                @Index(value = "main"),
                @Index(value="fr_sub"),
                @Index(value = "sc_sub"),
                @Index(value = "tr_sub")
        })
public class ClosetRoom implements Parcelable {
    @PrimaryKey
    @ColumnInfo(name="closet_gear")
    public Gear gear;

    public Skill main;
    @ColumnInfo(name = "fr_sub")
    public Skill sub1;
    @ColumnInfo(name = "sc_sub")
    public Skill sub2;
    @ColumnInfo(name = "tr_sub")
    public Skill sub3;

    public ClosetRoom(Gear gear, Skill main, Skill sub1, Skill sub2, Skill sub3){
        this.gear = gear;
        this.main = main;
        this.sub1 = sub1;
        this.sub2 = sub2;
        this.sub3 = sub3;
    }

    @Ignore
    public ClosetRoom(Gear gear, GearSkills gearSkills){
        this.gear = gear;
        this.main = gearSkills.main;
        if(gearSkills.subs.size()>0){
            this.sub1 = gearSkills.subs.get(0);
            if(gearSkills.subs.size()>1){
                this.sub2 = gearSkills.subs.get(1);
                if(gearSkills.subs.size()>2){
                    this.sub3 = gearSkills.subs.get(2);
                }
            }
        }
    }

    @Ignore
    protected ClosetRoom(Parcel in) {
        gear = in.readParcelable(Gear.class.getClassLoader());
        main = in.readParcelable(Skill.class.getClassLoader());
        sub1 = in.readParcelable(Skill.class.getClassLoader());
        sub2 = in.readParcelable(Skill.class.getClassLoader());
        sub3 = in.readParcelable(Skill.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(gear, flags);
        dest.writeParcelable(main, flags);
        dest.writeParcelable(sub1, flags);
        dest.writeParcelable(sub2, flags);
        dest.writeParcelable(sub3, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ClosetRoom> CREATOR = new Creator<ClosetRoom>() {
        @Override
        public ClosetRoom createFromParcel(Parcel in) {
            return new ClosetRoom(in);
        }

        @Override
        public ClosetRoom[] newArray(int size) {
            return new ClosetRoom[size];
        }
    };
}
