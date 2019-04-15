package com.mattrubacky.monet2.data.deserialized_entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.data.entity.SalmonShiftRoom;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

/**
 * Created by mattr on 7/4/2018.
 */
@Entity(tableName = "salmon_weapons",
        primaryKeys = {"salmon_weapon_id","weapon_shift_id"},
        foreignKeys = {
                @ForeignKey(entity = Weapon.class,
                        parentColumns = "weapon_id",
                        childColumns = "salmon_weapon_id"),
                @ForeignKey(entity = SalmonShiftRoom.class,
                        parentColumns = "shift_id",
                        childColumns = "weapon_shift_id")
        },
        indices = {
                @Index(name="salmon_weapon",
                        value = "salmon_weapon_id"),
                @Index(name="weapon_shift",
                        value = "weapon_shift_id")
        })
public class SalmonRunWeapon implements Parcelable {

    //GSON constructor
    @Ignore
    public SalmonRunWeapon(){}

    //Rooms constructor
    public SalmonRunWeapon(int id, int shiftId){
        this.id = id;
        this.shiftId = shiftId;
    }

    @ColumnInfo(name = "salmon_weapon_id")
    @SerializedName("id")
    public int id;

    @Ignore
    @SerializedName("weapon")
    public Weapon weapon;

    @ColumnInfo(name = "weapon_shift_id")
    public int shiftId;

    protected SalmonRunWeapon(Parcel in) {
        id = in.readInt();
        weapon = in.readParcelable(Weapon.class.getClassLoader());
    }

    public static final Creator<SalmonRunWeapon> CREATOR = new Creator<SalmonRunWeapon>() {
        @Override
        public SalmonRunWeapon createFromParcel(Parcel in) {
            return new SalmonRunWeapon(in);
        }

        @Override
        public SalmonRunWeapon[] newArray(int size) {
            return new SalmonRunWeapon[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(weapon, flags);
    }
}
