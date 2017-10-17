package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 */

public class User implements Parcelable {
    public User(){}

    @SerializedName("unique_id")
    String uniqueId;
    @SerializedName("prinicipal_id")
    String id;
    @SerializedName("nickname")
    String name;
    @SerializedName("player_rank")
    int rank;
    @SerializedName("udemae_tower")
    Rank tower;
    @SerializedName("udemae_rainmaker")
    Rank rainmaker;
    @SerializedName("udemae_zones")
    Rank splatzones;
    @SerializedName("udemae")
    Rank udamae;
    @SerializedName("weapon")
    Weapon weapon;
    @SerializedName("head_skills")
    GearSkills headSkills;
    @SerializedName("clothes_skills")
    GearSkills clothesSkills;
    @SerializedName("shoes_skills")
    GearSkills shoeSkills;
    @SerializedName("head")
    Gear head;
    @SerializedName("clothes")
    Gear clothes;
    @SerializedName("shoes")
    Gear shoes;
    @SerializedName("fes_grade")
    SplatfestGrade grade;

    protected User(Parcel in) {
        uniqueId = in.readString();
        id = in.readString();
        name = in.readString();
        rank = in.readInt();
        tower = in.readParcelable(Rank.class.getClassLoader());
        rainmaker = in.readParcelable(Rank.class.getClassLoader());
        splatzones = in.readParcelable(Rank.class.getClassLoader());
        udamae = in.readParcelable(Rank.class.getClassLoader());
        weapon = in.readParcelable(Weapon.class.getClassLoader());
        headSkills = in.readParcelable(GearSkills.class.getClassLoader());
        clothesSkills = in.readParcelable(GearSkills.class.getClassLoader());
        shoeSkills = in.readParcelable(GearSkills.class.getClassLoader());
        head = in.readParcelable(Gear.class.getClassLoader());
        clothes = in.readParcelable(Gear.class.getClassLoader());
        shoes = in.readParcelable(Gear.class.getClassLoader());
        grade = in.readParcelable(SplatfestGrade.class.getClassLoader());
    }

    public static final Creator<Annie.User> CREATOR = new Creator<Annie.User>() {
        @Override
        public Annie.User createFromParcel(Parcel in) {
            return new Annie.User(in);
        }

        @Override
        public Annie.User[] newArray(int size) {
            return new Annie.User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uniqueId);
        dest.writeString(id);
        dest.writeString(name);
        dest.writeInt(rank);
        dest.writeParcelable(tower, flags);
        dest.writeParcelable(rainmaker, flags);
        dest.writeParcelable(splatzones, flags);
        dest.writeParcelable(udamae, flags);
        dest.writeParcelable(weapon, flags);
        dest.writeParcelable(headSkills, flags);
        dest.writeParcelable(clothesSkills, flags);
        dest.writeParcelable(shoeSkills, flags);
        dest.writeParcelable(head, flags);
        dest.writeParcelable(clothes, flags);
        dest.writeParcelable(shoes, flags);
        dest.writeParcelable(grade, flags);
    }
}
