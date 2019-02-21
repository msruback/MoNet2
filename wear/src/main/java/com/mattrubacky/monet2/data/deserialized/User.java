package com.mattrubacky.monet2.data.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * This class represents an player's stats and gear
 */

public class User implements Parcelable {
    public User(){}

    //The user's unique id(does not apply to anyone but the user)
    @SerializedName("unique_id")
    public String uniqueId;

    //A player's unique id (does not apply to the user)
    @SerializedName("prinicipal_id")
    public String id;

    //The player's nickname
    @SerializedName("nickname")
    public String name;

    //The player's level
    @SerializedName("player_rank")
    public int rank;

    //The player's star rank
    @SerializedName("star_rank")
    public int starRank;

    //The user's rank in Tower Control, is only available in the records endpoint
    @SerializedName("udemae_tower")
    public Rank tower;

    //The user's rank in Rainmaker, is only available in the records endpoint
    @SerializedName("udemae_rainmaker")
    public Rank rainmaker;

    //The user's rank in Splatzones, is only availably in the records endpoint
    @SerializedName("udemae_zones")
    public Rank splatzones;

    @SerializedName("udemae_clam")
    public Rank clam;

    //The player's rank, is only available in the results and results/{battle_id} endpoints
    @SerializedName("udemae")
    public Rank udamae;

    //The player's weapon
    @SerializedName("weapon")
    public Weapon weapon;

    //The abilities on a player's headgear
    @SerializedName("head_skills")
    public GearSkills headSkills;

    //The abilities on a player's clothing
    @SerializedName("clothes_skills")
    public GearSkills clothesSkills;

    //The abilities on a player's shoes
    @SerializedName("shoes_skills")
    public GearSkills shoeSkills;

    //The player's headgear
    @SerializedName("head")
    public Gear head;

    //The player's clothing
    @SerializedName("clothes")
    public Gear clothes;

    //The player's shoes
    @SerializedName("shoes")
    public Gear shoes;

    //If the battle is a Splatfest Battle, the player's Splatfest Grade will be stored here, otherwise this is null
    @SerializedName("fes_grade")
    public SplatfestGrade grade;

    protected User(Parcel in) {
        uniqueId = in.readString();
        id = in.readString();
        name = in.readString();
        rank = in.readInt();
        starRank = in.readInt();
        tower = in.readParcelable(Rank.class.getClassLoader());
        rainmaker = in.readParcelable(Rank.class.getClassLoader());
        splatzones = in.readParcelable(Rank.class.getClassLoader());
        clam = in.readParcelable(Rank.class.getClassLoader());
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

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
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
        dest.writeInt(starRank);
        dest.writeParcelable(tower, flags);
        dest.writeParcelable(rainmaker, flags);
        dest.writeParcelable(splatzones, flags);
        dest.writeParcelable(clam,flags);
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
