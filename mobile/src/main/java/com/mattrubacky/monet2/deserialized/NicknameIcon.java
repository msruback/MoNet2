package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 11/16/2017.
 */

public class NicknameIcon implements Parcelable{
    public NicknameIcon(){}

    @SerializedName("nickname")
    public String nickname;
    @SerializedName("thumbnail_url")
    public String url;
    @SerializedName("nsa_id")
    public String id;

    protected NicknameIcon(Parcel in) {
        nickname = in.readString();
        url = in.readString();
        id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nickname);
        dest.writeString(url);
        dest.writeString(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NicknameIcon> CREATOR = new Creator<NicknameIcon>() {
        @Override
        public NicknameIcon createFromParcel(Parcel in) {
            return new NicknameIcon(in);
        }

        @Override
        public NicknameIcon[] newArray(int size) {
            return new NicknameIcon[size];
        }
    };
}
