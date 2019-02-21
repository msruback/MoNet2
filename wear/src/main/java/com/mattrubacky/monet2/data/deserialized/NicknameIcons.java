package com.mattrubacky.monet2.data.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 11/16/2017.
 */

public class NicknameIcons implements Parcelable{
    public NicknameIcons(){}
    @SerializedName("nickname_and_icons")
    public ArrayList<NicknameIcon> nicknameIcons;

    protected NicknameIcons(Parcel in) {
        nicknameIcons = in.createTypedArrayList(NicknameIcon.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(nicknameIcons);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NicknameIcons> CREATOR = new Creator<NicknameIcons>() {
        @Override
        public NicknameIcons createFromParcel(Parcel in) {
            return new NicknameIcons(in);
        }

        @Override
        public NicknameIcons[] newArray(int size) {
            return new NicknameIcons[size];
        }
    };
}
