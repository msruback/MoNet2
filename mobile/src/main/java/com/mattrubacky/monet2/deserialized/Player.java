package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.com.mattrubacky.deserialized.User;

/**
 * Created by mattr on 10/17/2017.
 */
class Player implements Parcelable {
    public Player(){}

    @SerializedName("player")
    User user;
    @SerializedName("death_count")
    int deaths;
    @SerializedName("assist_count")
    int assists;
    @SerializedName("kill_count")
    int kills;
    @SerializedName("game_paint_point")
    int points;
    @SerializedName("special_count")
    int special;

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
