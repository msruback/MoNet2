package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 11/16/2017.
 */

public class NicknameRequest implements Parcelable{
    public NicknameRequest(){}
    @SerializedName("nickname_and_icons")
    public ArrayList<NicknameIcon> nicknameIcons;

    protected NicknameRequest(Parcel in) {
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

    public static final Creator<NicknameRequest> CREATOR = new Creator<NicknameRequest>() {
        @Override
        public NicknameRequest createFromParcel(Parcel in) {
            return new NicknameRequest(in);
        }

        @Override
        public NicknameRequest[] newArray(int size) {
            return new NicknameRequest[size];
        }
    };
}
