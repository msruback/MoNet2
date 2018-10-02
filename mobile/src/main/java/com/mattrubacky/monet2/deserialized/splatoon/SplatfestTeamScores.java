package com.mattrubacky.monet2.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * The number of wins each side has in both the Solo and Team battle categories
 * Part of the SplatfestResult object
 * Note: Since only Alpha v. Bravo battles count, Alpha wins are Bravo losses, and vice versa
 */
public class SplatfestTeamScores implements Parcelable{
    public SplatfestTeamScores(){}

    //Alpha's wins in Solo Battles
    @SerializedName("alpha_solo")
    public int alphaSolo;

    //Alpha's wins in Team Battles
    @SerializedName("alpha_team")
    public int alphaTeam;

    //Bravo's wins in Solo Battles
    @SerializedName("bravo_solo")
    public int bravoSolo;

    //Bravo's wins in Team Battles
    @SerializedName("bravo_team")
    public int bravoTeam;

    protected SplatfestTeamScores(Parcel in) {
        alphaSolo = in.readInt();
        alphaTeam = in.readInt();
        bravoSolo = in.readInt();
        bravoTeam = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(alphaSolo);
        dest.writeInt(alphaTeam);
        dest.writeInt(bravoSolo);
        dest.writeInt(bravoTeam);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SplatfestTeamScores> CREATOR = new Creator<SplatfestTeamScores>() {
        @Override
        public SplatfestTeamScores createFromParcel(Parcel in) {
            return new SplatfestTeamScores(in);
        }

        @Override
        public SplatfestTeamScores[] newArray(int size) {
            return new SplatfestTeamScores[size];
        }
    };
}
