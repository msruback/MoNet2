package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 10/17/2017.
 */
class GearSkills implements Parcelable {
    public GearSkills(){}
    @SerializedName("main")
    Skill main;
    @SerializedName("subs")
    ArrayList<Skill> subs;

    protected GearSkills(Parcel in) {
        main = in.readParcelable(Skill.class.getClassLoader());
        subs = in.createTypedArrayList(Skill.CREATOR);
    }

    public static final Creator<GearSkills> CREATOR = new Creator<GearSkills>() {
        @Override
        public GearSkills createFromParcel(Parcel in) {
            return new GearSkills(in);
        }

        @Override
        public GearSkills[] newArray(int size) {
            return new GearSkills[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(main, flags);
        dest.writeTypedList(subs);
    }
}
