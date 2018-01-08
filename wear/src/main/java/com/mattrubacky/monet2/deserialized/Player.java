package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * The stats of a player in a battle
 */
public class Player implements Parcelable {
    public Player(){}

    //The player's identity
    @SerializedName("player")
    public User user;

    //The number of deaths the player had in the game
    @SerializedName("death_count")
    public int deaths;

    //The number of kills the player had in the game, including assists
    @SerializedName("kill_count")
    public int kills;

    //The number of assists the player had in the game
    @SerializedName("assist_count")
    public int assists;

    //The amount the player inked in the game
    @SerializedName("game_paint_point")
    public int points;

    //The amount of times the player used their special in the game
    @SerializedName("special_count")
    public int special;

    protected Player(Parcel in) {
        user = in.readParcelable(User.class.getClassLoader());
        deaths = in.readInt();
        assists = in.readInt();
        kills = in.readInt();
        points = in.readInt();
        special = in.readInt();
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(user, flags);
        dest.writeInt(deaths);
        dest.writeInt(assists);
        dest.writeInt(kills);
        dest.writeInt(points);
        dest.writeInt(special);
    }
}
